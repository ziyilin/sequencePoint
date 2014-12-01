import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import chord.instr.InstrScheme;
import chord.project.Chord;
import chord.project.analyses.DynamicAnalysis;
import edu.sjtu.stap.checkmate.control.Controller;

@Chord(name = "observeExecution")
public class Instrumentor extends DynamicAnalysis{

	InstrScheme scheme;
	
    @Override
    public InstrScheme getInstrScheme() {
    	System.out.println("hi, ss");
    	if (scheme != null){
            return scheme;
        }
        scheme = new InstrScheme();
        // ***TODO***: Choose (<event1>, <args1>), ... (<eventN>, <argsN>)
        // depending upon the kind and format of events required by this
        // dynamic analysis to be generated for this during an instrumented
        // program's execution.
        scheme.setAcquireLockEvent(true, true, true);
        scheme.setReleaseLockEvent(true, true, true);
        scheme.setWaitEvent(true, true, true);
        scheme.setNotifyAllEvent(true, true, true);
        scheme.setNotifyAnyEvent(true, true, true);
        scheme.setThreadStartEvent(true, true, true);
        scheme.setThreadJoinEvent(true, true, true);
        scheme.setBefMethodCallEvent(true, true, true);
        scheme.setPutfieldPrimitiveEvent(true, true, true, true);
        scheme.setPutfieldReferenceEvent(true, true, true, true, true);
        scheme.setPutstaticPrimitiveEvent(true, true, true, true);
        scheme.setPutstaticReferenceEvent(true, true, true, true, true);
        return scheme;
    }
    
    public void doneAllPasses(){
    	System.out.println("done");
    	String code=Controller.createTraceProgram();
    	try {
			FileUtils.writeStringToFile(new File("traceprogram.txt"), code);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	throw new NullPointerException();

    }
    
    @Override
    public void processAcquireLock(int l, int t, int o){
    	//Report that the executed statement is AcquireLock
    	System.out.println("hi, acquire");
    	Controller.acquireLock(t,o);
    }
    
    @Override
    public void processReleaseLock(int l, int t, int o){
    	Controller.releaseLock(t);
    }

    @Override
    public void processWait(int l, int t, int o){
    	Controller.wait(t, o);
    }
    
    @Override
    public void processNotifyAny(int l, int t, int o){
    	Controller.notify(t,o);
    }
    
    @Override
    public void processNotifyAll(int l, int t, int o){
    	Controller.notifyAll(t, o);
    }
    
    @Override
    public void processThreadStart(int i, int t, int o){
    	Controller.start(t,o);
    }
    
    /**
     * Before thread t calls the join() method of java.lang.Thread 
     * at program point i (in domain I) to join with thread o.
     *
     *@param t thread to call join.
     *@param o thread to join with.
     */
    @Override
    public void processThreadJoin(int i, int t, int o) { 
    	Controller.join(t, o);
    }
}
