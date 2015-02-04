package edu.sjtu.stap.checkmate.control;

public abstract class ConditionAnnotation {
	//protected static int counter=0;
	protected Object o;
	protected int condId;
	protected boolean curVal;
	
	public ConditionAnnotation(Object obj) {
		o=obj;
		condId=this.hashCode();
		associateWithObject(obj);
		initCond();
	}
	
	public abstract boolean isConditionTrue();
	
	synchronized public void  notifyBegin(Object lock){
		int lockId= AddLinesToTraceProgram.getInstance().getUniqueObjId(lock);
		boolean val=isConditionTrue();
		AddLinesToTraceProgram.getInstance().addLineWithConditionId("if(c"+condId+"){",condId);
		if(!val){
			AddLinesToTraceProgram.getInstance().addLineWithLockId("synchronized(l"+lockId+"){",lockId);
			AddLinesToTraceProgram.getInstance().addLineWithLockId("l"+lockId+".notify();",lockId);
			ending();
		}
	}
	
	public void notifyEnd(){
		ending();
	}
	
	synchronized public void waitBegin(Object lock){
		int lockId= AddLinesToTraceProgram.getInstance().getUniqueObjId(lock);
		boolean val=isConditionTrue();
		AddLinesToTraceProgram.getInstance().addLineWithConditionId("if(c"+condId+"){",condId);
		if(!val){
			AddLinesToTraceProgram.getInstance().addLineWithLockId("synchronized(l"+lockId+"){",lockId);
			AddLinesToTraceProgram.getInstance().addLineWithLockId("try{l"+lockId+".wait();\n}catch(InterruptedException e){e.printStackTrace();}",lockId);
			ending();
		}
	}
	
	public void waitEnd(){
		ending();
	}

	synchronized public void logChange(){
		boolean newVal=isConditionTrue();
		if(newVal!=curVal){
			AddLinesToTraceProgram.getInstance().addLineWithConditionId("c"+condId+"="+newVal+";",condId);
			curVal=newVal;
		}
	}
	
	private void associateWithObject(Object obj){
		
		AnnotationRegisterCenter.getInstance().register(this);
	}
	
	private void initCond(){
		curVal=isConditionTrue();
		AddLinesToTraceProgram.getInstance().addLineWithConditionId("c"+condId+"="+curVal+";", condId);
	}
	
	private void ending() {
		AddLinesToTraceProgram.getInstance().addLine("} //ending\n");
	}

	public boolean matchAssociated(Object obj) {
		return obj.equals(o);
	}
}