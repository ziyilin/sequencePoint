package edu.sjtu.stap.checkmate.instrument;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import edu.illinois.jacontebe.asm.GeneralMV;
import edu.illinois.jacontebe.asm.MethodInfor;

public class ConcurrentEventMV extends GeneralMV implements Opcodes {

	private int lastLoadVar;
	private String methodName;
	private String methodDesc;

	public ConcurrentEventMV(MethodVisitor mv, String name, String desc) {
		super(ASM5, mv);
		methodName = name;
		methodDesc = desc;
	}

	public void visitCode() {
		mv.visitCode();
		if (mv instanceof GeneralMV) {
			// if the method is declared as "synchronized", insert acquirelock
			// at the beginning of the method.
			if (((GeneralMV) mv).isSynchMethod()) {
				mv.visitVarInsn(ALOAD, 0);
				// if method is static, should synchronize on the class.
				if (((GeneralMV) mv).isStaticMethod()) {
					mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object",
							"getClass", "()Ljava/lang/Class;", false);
				}
				mv.visitMethodInsn(INVOKESTATIC,
						"edu/sjtu/stap/checkmate/control/Controller",
						"acquireLock", "(Ljava/lang/Object;)V", false);
			}
		}
	}

	public void visitInsn(int opcode) {
		switch (opcode) {
		case MONITORENTER:
			mv.visitInsn(opcode);
			mv.visitVarInsn(ALOAD, lastLoadVar);
			mv.visitMethodInsn(INVOKESTATIC,
					"edu/sjtu/stap/checkmate/control/Controller",
					"acquireLock", "(Ljava/lang/Object;)V", false);
			return;
		case MONITOREXIT:
			mv.visitMethodInsn(INVOKESTATIC,
					"edu/sjtu/stap/checkmate/control/Controller",
					"releaseLock", "()V", false);
			mv.visitInsn(opcode);
			return;
		case RETURN:
			for (MethodInfor m : getSpecialMethods()) {
				if (m.getMethodName().equals(methodName)
						&& m.getMethodDescription().equals(methodDesc)) {
					mv.visitMethodInsn(INVOKESTATIC,
							"edu/sjtu/stap/checkmate/control/Controller",
							"printoutTraceProgram", "()V", false);
					break;
				}
			}
			mv.visitInsn(opcode);
			return;
		}
		mv.visitInsn(opcode);
	}

	public void visitVarInsn(int opcode, int var) {
		if (opcode == ALOAD) {
			lastLoadVar = var;
		}
		mv.visitVarInsn(opcode, var);
	}

	public void visitMethodInsn(int opc, String owner, String name,
			String desc, boolean itf) {
		boolean objectChecked = opc == INVOKEVIRTUAL
				&& owner.equals("java/lang/Object") && desc.equals("()V")
				&& !itf;
		boolean threadChecked = opc == INVOKEVIRTUAL
				&& owner.equals("java/lang/Thread") && desc.equals("()V")
				&& !itf;

		if (objectChecked && name.equals("wait")) {
			// Insert before wait() is called.
			insertCode(opc, owner, name, desc, itf, "wait");
		}else if (objectChecked && name.equals("notify")) {
			insertCode(opc, owner, name, desc, itf, "notify");
		}else if (objectChecked && name.equals("notifyAll")) {
			insertCode(opc, owner, name, desc, itf, "notifyAll");
		}else if (threadChecked && name.equals("start")) {
			mv.visitMethodInsn(INVOKESTATIC,
					"edu/sjtu/stap/checkmate/control/Controller", "start",
					"(Ljava/lang/Thread;)V", false);
			mv.visitVarInsn(ALOAD, lastLoadVar);
			
		}else if (threadChecked && name.equals("join")) {
			mv.visitMethodInsn(INVOKESTATIC,
					"edu/sjtu/stap/checkmate/control/Controller", "join",
					"(Ljava/lang/Thread;)V", false);
			mv.visitVarInsn(ALOAD, lastLoadVar);
		}
		mv.visitMethodInsn(opc, owner, name, desc, itf);
	}

	private void insertCode(int opc, String owner, String name, String desc,
			boolean itf, String methodName) {
		mv.visitMethodInsn(INVOKESTATIC,
				"edu/sjtu/stap/checkmate/control/Controller", methodName,
				"(Ljava/lang/Object;)V", false);
		mv.visitVarInsn(ALOAD, lastLoadVar);
		
	}
}
