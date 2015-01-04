package edu.sjtu.stap.checkmate.control;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import edu.sjtu.stap.checkmate.AddLinesToTraceProgram;
import edu.sjtu.stap.checkmate.AnnotationRegisterCenter;
import edu.sjtu.stap.checkmate.ConditionAnnotation;

public class Controller {

	public static void acquireLock(Object lock) {
		write2Map("synchronized(l" + lock.hashCode() + "){");
	}

	public static void acquireLock(int thread, int monitor) {
		write2Map("synchronized(l" + monitor + "){", thread);
	}

	public static void releaseLock() {
		write2Map("}\n");
	}

	public static void releaseLock(int thread) {
		write2Map("}\n", thread);
	}

	public static void wait(int thread, int monitor) {
		write2Map("l" + monitor + ".wait();\n", thread);
	}

	public static void wait(Object lock) {
		write2Map("l" + lock.hashCode() + ".wait();\n");
	}

	public static void notify(Object lock) {
		write2Map("l" + lock.hashCode() + ".notify();\n");
	}

	public static void notify(int thread, int monitor) {
		write2Map("l" + monitor + ".notify();\n", thread);

	}

	public static void notifyAll(Object lock) {
		write2Map("l" + lock.hashCode() + ".notifyAll();\n");
	}

	public static void notifyAll(int thread, int monitor) {
		write2Map("l" + monitor + ".notifyAll();\n", thread);
	}

	public static void start(Thread t) {

		write2Map("t" + t.getId() + ".start();\n", Thread.currentThread()
				.getId());
	}

	public static void start(int thread, int subjectThread) {

		write2Map("t" + subjectThread + ".start();\n", thread);
	}

	public static void join(Thread t) {
		write2Map("t" + t.getId() + ".join();\n", Thread.currentThread()
				.getId());
	}
	
	public static void join(int thread, int subjectThread) {
		write2Map("t" + subjectThread + ".join();\n", thread);
	}

	public static void writeOrCall(Object o) {
		ConditionAnnotation associatedCondition = AnnotationRegisterCenter
				.getInstance().findMatchAssociats(o);
		if (associatedCondition != null) {
			associatedCondition.logChange();
		}

	}

	private static void write2Map(String contents) {

		AddLinesToTraceProgram.getInstance().addLine(contents,
				Thread.currentThread().getId());
	}

	private static void write2Map(String contents, long tid) {

		AddLinesToTraceProgram.getInstance().addLine(contents, tid);
	}

	public static String createTraceProgram() {
		String program = "public class TraceProgram{\n";
		for (int i : AddLinesToTraceProgram.getInstance().getLocks()) {
			program += "static Object l" + i + "=new Object();\n";
		}
		for (int i : AddLinesToTraceProgram.getInstance().getPredictates()) {
			program += "static boolean c" + i + ";\n";
		}
		for (long t : AddLinesToTraceProgram.getInstance().getThrToLines()
				.keySet()) {
			program += "static Thread t" + t
					+ "=new Thread()\n{ public void run(){";
			for (String s : AddLinesToTraceProgram.getInstance()
					.getThrToLines().get(t)) {
				program += s + "\n";
			}
			program += "}};\n";
		}

		program += "public static void main(String[] args){\n";
		for (long t : AddLinesToTraceProgram.getInstance().getThrToLines()
				.keySet()) {
			if (!checkExists("t" + t + ".start();")) {
				program += "t" + t + ".start();\n";
			}
		}
		program += "}}";
		return program;
	}
	
	public static void printoutTraceProgram(){
		String code=Controller.createTraceProgram();
    	try {
    		System.out.println("Saving trace program");
			FileUtils.writeStringToFile(new File("traceprogram.txt"), code);
			System.out.println("Trace program saved");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static boolean checkExists(String token) {
		for (long key : AddLinesToTraceProgram.getInstance().getThrToLines()
				.keySet()) {
			for (String s : AddLinesToTraceProgram.getInstance()
					.getThrToLines().get(key)) {
				if (s.contains(token)) {
					return true;
				}
			}
		}
		return false;
	}

}
