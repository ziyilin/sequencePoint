package edu.sjtu.stap.checkmate.control;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class AddLinesToTraceProgram {

	private Set<Integer> predictates;
	

	private Set<Integer> locks;
	
	private static class ClassHolder{
		public final static AddLinesToTraceProgram instance=new AddLinesToTraceProgram();
	}
	
	public static AddLinesToTraceProgram getInstance(){
		return ClassHolder.instance;
	}
	
	private AddLinesToTraceProgram(){
		predictates=new HashSet<Integer>();
		locks=new HashSet<Integer>();
	}
	
	private Map<Long, List<String>> thrToLines =new TreeMap<Long,List<String>>();
	
	public int getUniqueObjId(Object o){
		return o.hashCode();
	}
	
	public void addLineWithConditionId(String line, int conditionId){
		predictates.add(conditionId);
		addLine(line);
	}
	
	public void addLineWithLockId(String line, int lockId){
		locks.add(lockId);
		addLine(line);
	}
	
	public void addLineWithLockId(String line, int lockId, long tId){
		locks.add(lockId);
		addLine(line, tId);
	}
	
	public void addLine(String line){
		Thread t;
		synchronized(this){
			t=Thread.currentThread();
		}
		addLine(line, t.getId());
	}

	/**
	 * Add line with thread id
	 * @param line String contents to add 
	 * @param tId thread id
	 */
	public synchronized void addLine(String line, long tId) {
		List<String> list=thrToLines.get(tId);
		if(list==null){
			list=new ArrayList<String>();
		}
		if(list.add(line)){
			thrToLines.put(tId, list);
		}
	}
	
	public Set<Integer> getPredictates() {
		return predictates;
	}

	public Set<Integer> getLocks() {
		return locks;
	}

	public synchronized Map<Long, List<String>> getThrToLines() {
		return thrToLines;
	}
}
