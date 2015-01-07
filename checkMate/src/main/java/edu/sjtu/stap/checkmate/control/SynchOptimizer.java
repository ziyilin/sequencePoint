package edu.sjtu.stap.checkmate.control;

import java.util.List;

public class SynchOptimizer {

	public static void optimize() {
		for (long t : AddLinesToTraceProgram.getInstance().getThrToLines()
				.keySet()) {
			List<String> codesOfAThread = AddLinesToTraceProgram
					.getInstance().getThrToLines().get(t);
			for (int i = 0; i < codesOfAThread.size(); i++) {
				if(codesOfAThread.get(i).startsWith("synchronized")){
					
				}else if(codesOfAThread.get(i).equals("}")){
					
				}
			}
		}
	}
	
	class OptimizableBlock{
		int startIndex;
		int endIndex;
	}

}
