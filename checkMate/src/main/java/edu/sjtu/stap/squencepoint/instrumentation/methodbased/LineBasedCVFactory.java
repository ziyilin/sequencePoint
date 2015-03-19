package edu.sjtu.stap.squencepoint.instrumentation.methodbased;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import edu.sjtu.stap.checkmate.framework.instrument.MvFactory;

public class LineBasedCVFactory extends ClassVisitor implements Opcodes {

	private MvFactory factory;

	public LineBasedCVFactory(ClassVisitor cv, MvFactory mvFactory) {
		super(ASM5, cv);
		this.factory = mvFactory;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature,
				exceptions);
		if (mv != null) {
			mv = factory.generateMethodVisitor(mv, name, desc);
		}
		return mv;
	}
}
