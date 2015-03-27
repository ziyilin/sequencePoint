import com.sun.jdi.*;
import com.sun.jdi.request.*;
import com.sun.jdi.event.*;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.io.PrintWriter;

/**
 * 
 * @author Yilei
 * 
 */
public class EventThread extends Thread {

	private final VirtualMachine vm; // Running VM
	private final String[] excludes; // Packages to exclude
	private final PrintWriter writer; // Where output goes

	static String nextBaseIndent = ""; // Starting indent for next thread

	private boolean connected = true; // Connected to VM
	private boolean vmDied = true; // VMDeath occurred

	// Maps ThreadReference to ThreadTrace instances
	private Map<ThreadReference, ThreadTrace> traceMap = new HashMap<>();

	// Monitor Map, which maps Sequence Point to ThreadReference
	private Map<Integer, ThreadReference> monitorMap = new ConcurrentHashMap<>();
	private String applicationName, mapName = "SP";
	private static int currentSP = 1;

	EventThread(VirtualMachine vm, String[] excludes, PrintWriter writer, String applicationName) {
		super("event-handler");
		this.vm = vm;
		this.excludes = excludes;
		this.writer = writer;
		this.applicationName = applicationName;
        
		this.setEventRequests(true);

		// Assume we will monitor the MonitorMap in OneThreadDemo.class.
	}

	/**
	 * Run the event handling thread. As long as we are connected, get event
	 * sets off the queue and dispatch the events within them.
	 */
	@Override
	public void run() {
		EventQueue queue = vm.eventQueue();
		while (connected) {
			try {
				EventSet eventSet = queue.remove();
				EventIterator it = eventSet.eventIterator();
				while (it.hasNext()) {
					handleEvent(it.nextEvent());
				}
				eventSet.resume();
			} catch (InterruptedException exc) {
				// Ignore
			} catch (VMDisconnectedException discExc) {
				handleDisconnectedException();
				break;
			}
		}
	}

	/**
	 * Create the desired event requests, and enable them so that we will get
	 * events.
	 * 
	 * @param excludes
	 *            Class patterns for which we don't want events
	 * @param watchFields
	 *            Do we want to watch assignments to fields
	 */
	void setEventRequests(boolean watchFields) {
		EventRequestManager mgr = vm.eventRequestManager();

		// want all exceptions
		ExceptionRequest excReq = mgr.createExceptionRequest(null, true, true);
		// suspend so we can step
		excReq.setSuspendPolicy(EventRequest.SUSPEND_ALL);
		excReq.enable();

		MethodEntryRequest menr = mgr.createMethodEntryRequest();
		for (int i = 0; i < excludes.length; ++i) {
			menr.addClassExclusionFilter(excludes[i]);
		}
		menr.setSuspendPolicy(EventRequest.SUSPEND_NONE);
		menr.enable();

		MethodExitRequest mexr = mgr.createMethodExitRequest();
		for (int i = 0; i < excludes.length; ++i) {
			mexr.addClassExclusionFilter(excludes[i]);
		}
		mexr.setSuspendPolicy(EventRequest.SUSPEND_NONE);
		mexr.enable();

		ThreadStartRequest tsr = mgr.createThreadStartRequest();
		tsr.setSuspendPolicy(EventRequest.SUSPEND_ALL);
		tsr.enable();
		
		ThreadDeathRequest tdr = mgr.createThreadDeathRequest();
		// Make sure we sync on thread death
		tdr.setSuspendPolicy(EventRequest.SUSPEND_ALL);
		tdr.enable();

		if (watchFields) {
			ClassPrepareRequest cpr = mgr.createClassPrepareRequest();
			for (int i = 0; i < excludes.length; ++i) {
				cpr.addClassExclusionFilter(excludes[i]);
			}
			// Monitor the given classes
			cpr.addClassFilter(applicationName);
			cpr.setSuspendPolicy(EventRequest.SUSPEND_NONE);
			cpr.enable();
		}
		
		/*if (watchFields) {
            ClassPrepareRequest cpr = mgr.createClassPrepareRequest();
            for (int i=0; i<excludes.length; ++i) {
                cpr.addClassExclusionFilter(excludes[i]);
            }
            cpr.setSuspendPolicy(EventRequest.SUSPEND_ALL);
            cpr.enable();
        } */
	}

	/**
	 * This class keeps context on events in one thread. In this implementation,
	 * context is the indentation prefix.
	 */
	class ThreadTrace {
		final ThreadReference thread;
		final String baseIndent;
		static final String threadDelta = "                     ";
		StringBuffer indent;

		ThreadTrace(ThreadReference thread) {
			this.thread = thread;
			this.baseIndent = nextBaseIndent;
			indent = new StringBuffer(baseIndent);
			nextBaseIndent += threadDelta;
			println("====== " + thread.name() + " ======");
			// System.out.println("Log: " + this.thread.name());
		}

		private void println(String str) {
			writer.print(indent);
			writer.println(str);
		}

		/**
		 * Thread start event
		 * 
		 * @param event
		 */
		void threadStartEvent(ThreadStartEvent event) {
			println("Thread " + event.thread().name() + " Start");
		}

		void methodEntryEvent(MethodEntryEvent event) {
			println(event.method().name() + "  --  "
					+ event.method().declaringType().name());
			indent.append("| ");
		}

		void methodExitEvent(MethodExitEvent event) {
			indent.setLength(indent.length() - 2);
		}

		void fieldWatchEvent(ModificationWatchpointEvent event) {
			Field field = event.field();
			Value value = event.valueToBe();
			System.out.println("Value:" + value.toString());
			println("In thread " + thread.name() + ", " + field.name()
					+ " from " + event.valueCurrent() + " to be " + value);
			if (Integer.valueOf(value.toString()) == currentSP) {
				notifySuspendedThread();
			} else {
				event.thread().suspend();
				println("Suspend Thread " + event.thread().name());
				monitorMap.put(Integer.valueOf(value.toString()), event.thread()); 
			}
		}

		// Notify suspended thread with the next SequencePoint to resume.
		private void notifySuspendedThread() {
			if ( monitorMap.containsKey(++currentSP)) {
				 ThreadReference threadReference = monitorMap.get(currentSP);
				 if ( threadReference.isSuspended() ) {
					 threadReference.resume();
					 println("Resume Thread " + threadReference.name());
					 monitorMap.remove(currentSP);
					 notifySuspendedThread();
				 }
			}
		}

		void exceptionEvent(ExceptionEvent event) {
			println("Exception: " + event.exception() + " catch: "
					+ event.catchLocation());

			// Step to the catch
			EventRequestManager mgr = vm.eventRequestManager();
			StepRequest req = mgr.createStepRequest(thread,
					StepRequest.STEP_MIN, StepRequest.STEP_INTO);
			req.addCountFilter(1); // next step only
			req.setSuspendPolicy(EventRequest.SUSPEND_ALL);
			req.enable();
		}

		// Step to exception catch
		void stepEvent(StepEvent event) {
			// Adjust call depth
			int cnt = 0;
			indent = new StringBuffer(baseIndent);
			try {
				cnt = thread.frameCount();
			} catch (IncompatibleThreadStateException exc) {
			}
			while (cnt-- > 0) {
				indent.append("| ");
			}

			EventRequestManager mgr = vm.eventRequestManager();
			mgr.deleteEventRequest(event.request());
		}

		void threadDeathEvent(ThreadDeathEvent event) {
			indent = new StringBuffer(baseIndent);
			println("====== " + thread.name() + " end ======");
		}
	}

	/**
	 * Returns the ThreadTrace instance for the specified thread, creating one
	 * if needed.
	 */
	ThreadTrace threadTrace(ThreadReference thread) {
		ThreadTrace trace = traceMap.get(thread);
		System.out.println(thread.name() + thread.hashCode());
		if (trace == null) {
			trace = new ThreadTrace(thread);
			traceMap.put(thread, trace);
		}
		return trace;
	}

	/**
	 * Dispatch incoming events
	 */
	private void handleEvent(Event event) {
		if (event instanceof ExceptionEvent) {
			exceptionEvent((ExceptionEvent) event);
		} else if (event instanceof ModificationWatchpointEvent) {
			fieldWatchEvent((ModificationWatchpointEvent) event);
		} else if (event instanceof ThreadStartEvent) {
			threadStartEvent((ThreadStartEvent) event);
		} else if (event instanceof MethodEntryEvent) {
			methodEntryEvent((MethodEntryEvent) event);
		} else if (event instanceof MethodExitEvent) {
			methodExitEvent((MethodExitEvent) event);
		} else if (event instanceof StepEvent) {
			stepEvent((StepEvent) event);
		} else if (event instanceof ThreadDeathEvent) {
			threadDeathEvent((ThreadDeathEvent) event);
		} else if (event instanceof ClassPrepareEvent) {
			classPrepareEvent((ClassPrepareEvent) event);
		} else if (event instanceof VMStartEvent) {
			vmStartEvent((VMStartEvent) event);
		} else if (event instanceof VMDeathEvent) {
			vmDeathEvent((VMDeathEvent) event);
		} else if (event instanceof VMDisconnectEvent) {
			vmDisconnectEvent((VMDisconnectEvent) event);
		} else {
			throw new Error("Unexpected event type");
		}
	}

	/***
	 * A VMDisconnectedException has happened while dealing with another event.
	 * We need to flush the event queue, dealing only with exit events (VMDeath,
	 * VMDisconnect) so that we terminate correctly.
	 */
	synchronized void handleDisconnectedException() {
		EventQueue queue = vm.eventQueue();
		while (connected) {
			try {
				EventSet eventSet = queue.remove();
				EventIterator iter = eventSet.eventIterator();
				while (iter.hasNext()) {
					Event event = iter.nextEvent();
					if (event instanceof VMDeathEvent) {
						vmDeathEvent((VMDeathEvent) event);
					} else if (event instanceof VMDisconnectEvent) {
						vmDisconnectEvent((VMDisconnectEvent) event);
					}
				}
				eventSet.resume(); // Resume the VM
			} catch (InterruptedException exc) {
				// ignore
			}
		}
	}

	private void vmStartEvent(VMStartEvent event) {
		writer.println("-- VM Started --");
	}

	// Forward event for thread specific processing
	private void threadStartEvent(ThreadStartEvent event) {
		threadTrace(event.thread()).threadStartEvent(event);
	}

	// Forward event for thread specific processing
	private void methodEntryEvent(MethodEntryEvent event) {
		threadTrace(event.thread()).methodEntryEvent(event);
	}

	// Forward event for thread specific processing
	private void methodExitEvent(MethodExitEvent event) {
		threadTrace(event.thread()).methodExitEvent(event);
	}

	// Forward event for thread specific processing
	private void stepEvent(StepEvent event) {
		threadTrace(event.thread()).stepEvent(event);
	}

	// Forward event for thread specific processing
	private void fieldWatchEvent(ModificationWatchpointEvent event) {
		threadTrace(event.thread()).fieldWatchEvent(event);
	}

	void threadDeathEvent(ThreadDeathEvent event) {
		ThreadTrace trace = traceMap.get(event.thread());
		if (trace != null) { // only want threads we care about
			trace.threadDeathEvent(event); // Forward event
		}
	}

	/**
	 * A new class has been loaded. Set watchpoints on each of its fields
	 */
	private void classPrepareEvent(ClassPrepareEvent event) {
		// Monitor the fields we care, for example, Sequence Point
		ReferenceType refType = event.referenceType();
		EventRequestManager erm = vm.eventRequestManager();
		// System.out.println("Log2: " + event.thread().hashCode());

		Field field = refType.fieldByName(mapName);
		ModificationWatchpointRequest modificationWatchpointRequest = erm
				.createModificationWatchpointRequest(field);
		modificationWatchpointRequest.setEnabled(true);
	}

	private void exceptionEvent(ExceptionEvent event) {
		ThreadTrace trace = traceMap.get(event.thread());
		if (trace != null) { // only want threads we care about
			trace.exceptionEvent(event); // Forward event
		}
	}

	public void vmDeathEvent(VMDeathEvent event) {
		vmDied = true;
		writer.println("-- The application exited --");
	}

	public void vmDisconnectEvent(VMDisconnectEvent event) {
		connected = false;
		if (!vmDied) {
			writer.println("-- The application has been disconnected --");
		}
	}
}
