package edu.sjtu.stap.checkmate.instrument;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import edu.sjtu.stap.checkmate.framework.instrument.Constants;
import edu.sjtu.stap.checkmate.framework.instrument.GeneralMV;

public class ConcurrentEventMV extends GeneralMV implements Opcodes {

	private String methodName;
	private String methodDesc;
	private List<Instru> instructionQ; 

	private class Instru{
		final String method;
		final Object[] parameters;
		
		public Instru(String m, Object... params){
			method=m;
			parameters=params;
		}
	}
	public ConcurrentEventMV(MethodVisitor mv, String name, String desc) {
		super(ASM5, mv);
		methodName = name;
		methodDesc = desc;
		instructionQ=new ArrayList<Instru>();
	}

	public void visitLineNumber(int line,Label l){
		mv.visitLineNumber(line, l);
		instructionQ.clear();
	}
	
	public void visitCode() {
		mv.visitCode();
		
			// if the method is declared as "synchronized", insert acquirelock
			// at the beginning of the method.
			if (isSynchMethod()) {
				mv.visitVarInsn(ALOAD, 0);
				// if method is static, should synchronize on the class.
				if (isStaticMethod()) {
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object",
							"getClass", "()Ljava/lang/Class;", false);
				}
				mv.visitMethodInsn(INVOKESTATIC,
						"edu/sjtu/stap/checkmate/control/Controller",
						"acquireLock", "(Ljava/lang/Object;)V", false);
			}
		
	}

	public void visitInsn(int opcode) {
		if (opcode == MONITORENTER) {
			mv.visitInsn(opcode);
			insertVariableLoadings();
			mv.visitMethodInsn(INVOKESTATIC,
					"edu/sjtu/stap/checkmate/control/Controller",
					"acquireLock", "(Ljava/lang/Object;)V", false);
			return;
		} else if (opcode == MONITOREXIT) {
			mv.visitMethodInsn(INVOKESTATIC,
					"edu/sjtu/stap/checkmate/control/Controller",
					"releaseLock", "()V", false);
		} else if (opcode >= IRETURN && opcode <= RETURN|| opcode == ATHROW) {
			String className=(String) getParams().get(Constants.QUALIFIED_CLASS_NAME);
			List<String> associClassList = (List<String>) getParams().get(
					Constants.ASSOC_CLASSES);
			if (!methodName.equals("<init>")&&associClassList!=null&& 
					associClassList.contains(className.replace("/", "."))){
				
				InsertControllerWC();
				
			}
			// print out trace program at then end of original program.
			if (methodName.equals("main")
					&& methodDesc.equals("([Ljava/lang/String;)V")) {
				mv.visitMethodInsn(INVOKESTATIC,
						"edu/sjtu/stap/checkmate/control/Controller",
						"printoutTraceProgram", "()V", false);
			}
			//If this is a synchronized method, close the synchronized block.
			if(isSynchMethod()){
				mv.visitMethodInsn(INVOKESTATIC,
						"edu/sjtu/stap/checkmate/control/Controller",
						"releaseLock", "()V", false);
			}
		}else if(opcode==AALOAD){
			instructionQ.add(new Instru("visitInsn", opcode));
		}
		
		mv.visitInsn(opcode);
	}

	public void visitFieldInsn(int opcode, String owner, String name,
			String desc) {
		mv.visitFieldInsn(opcode, owner, name, desc);
		List<String> associClassList = (List<String>) getParams().get(
				Constants.ASSOC_CLASSES);
		if (associClassList!=null&& associClassList.contains(owner.replace("/", ".")) && opcode == PUTFIELD
				&& !desc.equals("Ledu/sjtu/stap/checkmate/control/ConditionAnnotation;")
				&& !owner.equals("Ledu/sjtu/stap/checkmate/control/ConditionAnnotation;")) {
			InsertControllerWC();
		}
		if(opcode==Opcodes.GETFIELD||opcode==Opcodes.GETSTATIC){
			instructionQ.add(new Instru("visitFieldInsn",opcode,owner,name,desc));
		}
	}

	private void InsertControllerWC() {
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESTATIC,
				"edu/sjtu/stap/checkmate/control/Controller",
				"writeOrCall", "(Ljava/lang/Object;)V", false);
	}

	public void visitVarInsn(int opcode, int var) {
		//Loads only
		if(opcode>=Opcodes.ILOAD&&opcode<=Opcodes.SALOAD){
			instructionQ.add(new Instru("visitVarInsn",opcode,var));
		}
		mv.visitVarInsn(opcode, var);
	}

	public void visitMethodInsn(int opc, String owner, String name,
			String desc, boolean itf) {
		beforeMethodCall(opc, owner, name, desc, itf);
		mv.visitMethodInsn(opc, owner, name, desc, itf);
	}

	private void beforeMethodCall(int opc, String owner, String name,
			String desc, boolean itf) {
		boolean isObjectMethod = opc == INVOKEVIRTUAL
				&& owner.equals("java/lang/Object") && desc.equals("()V")
				&& !itf;
		boolean isThreadMethod = opc == INVOKEVIRTUAL
				&& owner.equals("java/lang/Thread") && desc.equals("()V")
				&& !itf;
		if (isObjectMethod && name.equals("wait")) {
			insertCode( "wait","(Ljava/lang/Object;)V");
		} else if (isObjectMethod && name.equals("notify")) {
			insertCode("notify","(Ljava/lang/Object;)V");
		} else if (isObjectMethod && name.equals("notifyAll")) {
			insertCode("notifyAll","(Ljava/lang/Object;)V");
		} else if (isThreadMethod && name.equals("start")) {
			insertCode("start","(Ljava/lang/Thread;)V");
		} else if (isThreadMethod && name.equals("join")) {
			insertCode("join","(Ljava/lang/Thread;)V");
		}
	}

	private void insertCode(String name, String desc) {
		mv.visitMethodInsn(INVOKESTATIC,
				"edu/sjtu/stap/checkmate/control/Controller", name,
				desc, false);
		insertVariableLoadings();
	}

	private void insertVariableLoadings() {
		for(Instru ins:instructionQ){
			if(ins.method.equals("visitVarInsn")){
				mv.visitVarInsn((Integer)ins.parameters[0],(Integer)ins.parameters[1]);
			}else if(ins.method.equals("visitInsn")){
				mv.visitInsn((Integer) ins.parameters[0]);
			}else if(ins.method.equals("visitFieldInsn")){
				mv.visitFieldInsn((Integer)ins.parameters[0],(String)ins.parameters[1],
						(String)ins.parameters[2],(String)ins.parameters[3]);
			}
				
		}
		instructionQ.clear();
	}
}
