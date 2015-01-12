package edu.sjtu.stap.checkmate.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * This class reduce redundant "synchronized" keywords.
 * Empty synchronized blocks shall be removed. For example, the code
 * <pre><code>
 * synchronized(this){
 * 	synchronized(this){
 * 	}
 * 	wait();
 * }</code></pre>
 * shall be optimized to 
 * <pre><code>
 * synchronized(this){
 * 	wait();
 * }</pre></code>
 * 
 * @author Ziyi Lin
 *
 */
public class SynchOptimizer {

	public static void optimize() {
		for (long t : AddLinesToTraceProgram.getInstance().getThrToLines()
				.keySet()) {
			List<String> codesOfAThread = AddLinesToTraceProgram
					.getInstance().getThrToLines().get(t);
			Stack<Integer> stack=new Stack<Integer>();
			List<Integer> optimizeCandidates=new ArrayList<Integer>();
			for (int i = 0; i < codesOfAThread.size(); i++) {
				String str=codesOfAThread.get(i);
				//Keep not empty synchronized sections
				if(!str.startsWith("synchronized") && !str.startsWith("}") && !str.trim().equals("")){
					stack.clear();
				}
				else if (str.startsWith("synchronized")){
					//if two nested synchronized sections monitor on different objects, keep both.  
					if(!stack.empty()&&!str.equals(codesOfAThread.get(stack.peek()))){
						stack.clear();
					}else{
						stack.push(i);
					}
				}else if (str.startsWith("}") && !stack.isEmpty()){
					optimizeCandidates.add(stack.pop());
					optimizeCandidates.add(i);
				}
			}
			Collections.sort(optimizeCandidates);
			for(int i=optimizeCandidates.size()-1;i>=0;i--){
				int removeIndex=optimizeCandidates.get(i);
				AddLinesToTraceProgram
				.getInstance().getThrToLines().get(t).remove(removeIndex);
			}
		}
	}	
}
