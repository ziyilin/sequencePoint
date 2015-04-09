package edu.sjtu.stap.checkmate.loadclass;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 
 * @author Yilei
 * 
 */
public class SpCodeTemplate implements Opcodes {

	private MethodVisitor mv;

	public SpCodeTemplate(MethodVisitor mv) {
		this.mv = mv;
	}

	/**
	 * SpMap.insertSP(sp, Thread.currentThread().hashCode());
	 * 
	 * @param sp
	 */
	public void insertSpToCurrentThread(int sp) {
		mv.visitIntInsn(BIPUSH, sp);
		mv.visitMethodInsn(INVOKESTATIC, "java/lang/Thread", "currentThread",
				"()Ljava/lang/Thread;", false);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "hashCode",
				"()I", false);
		mv.visitMethodInsn(INVOKESTATIC, "SpMap", "insertSP", "(II)V", false);
	}

	/**
	 * SpMap.insertSP(sp, t.currentThread().hashCode());
	 * 
	 * @param sp
	 */
	public void insertSpToThread(int sp, String instance) {
	}

	/**
	 * SpMap.checkThread(sp);
	 * 
	 * @param sp
	 */
	public void insertSpCheckment(int sp) {
		mv.visitIntInsn(BIPUSH, sp);
		mv.visitMethodInsn(INVOKESTATIC, "SpMap", "checkThread", "(I)V", false);
	}

}