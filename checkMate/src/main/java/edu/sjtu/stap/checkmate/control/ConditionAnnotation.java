package edu.sjtu.stap.checkmate.control;

public abstract class ConditionAnnotation {
	protected static int counter=0;
	protected Object o;
	protected int condId;
	protected boolean curVal;
	
	public ConditionAnnotation(Object obj) {
		o=obj;
		condId=counter++;
		associateWithObject(obj);
		initCond();
	}
	
	public abstract boolean isConditionTrue();
	
	public void notifyBegin(Object lock){
		int lockId= AddLinesToTraceProgram.getInstance().getUniqueObjId(lock);
		boolean val=isConditionTrue();
		AddLinesToTraceProgram.getInstance().addLineWithConditionId("if(c"+condId+"){",condId);
		if(!val){
			AddLinesToTraceProgram.getInstance().addLineWithLockId("synchronized(l"+lockId+"){l"+lockId+".notify();}",lockId);
		}
	}
	
	public void notifyEnd(){
		ending();
	}
	
	public void waitBegin(Object lock){
		int lockId= AddLinesToTraceProgram.getInstance().getUniqueObjId(lock);
		boolean val=isConditionTrue();
		AddLinesToTraceProgram.getInstance().addLineWithConditionId("if(c"+condId+"){",condId);
		if(!val){
			AddLinesToTraceProgram.getInstance().addLineWithLockId("synchronized(l"+lockId+"){l"+lockId+".wait();}",lockId);
		}
	}
	
	public void waitEnd(){
		ending();
	}

	public void logChange(){
		boolean newVal=isConditionTrue();
		if(newVal!=curVal){
			AddLinesToTraceProgram.getInstance().addLineWithConditionId("c"+condId+"="+newVal+";",condId);
			curVal=newVal;
		}
	}
	
	private void associateWithObject(Object obj){
		String className=obj.getClass().getName();
		
		AnnotationRegisterCenter.getInstance().register(this);
	}
	
	private void initCond(){
		curVal=isConditionTrue();
		AddLinesToTraceProgram.getInstance().addLineWithConditionId("c"+condId+"="+curVal+";", condId);
	}
	
	private void ending() {
		AddLinesToTraceProgram.getInstance().addLine("}");
	}

	public boolean matchAssociated(Object obj) {
		return obj.equals(o);
	}
}