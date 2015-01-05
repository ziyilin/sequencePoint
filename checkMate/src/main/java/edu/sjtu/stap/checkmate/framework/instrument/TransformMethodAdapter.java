package edu.sjtu.stap.checkmate.framework.instrument;

import java.util.List;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * This class adds code into a specified method.
 * 
 * @author Ziyi Lin
 * 
 */
public class TransformMethodAdapter extends ClassVisitor implements Opcodes {

	private MvFactory factory;
	private List<MethodInfor> methodInfor;
	private boolean mixMode;

	public TransformMethodAdapter(ClassVisitor cv,
			List<MethodInfor> methodInformations, MvFactory mvFactory,
			boolean mix) {
		super(ASM5, cv);
		this.factory = mvFactory;
		this.methodInfor = methodInformations;
		this.mixMode = mix;
	}

	/**
	 * If methodInfor is null, <code>true</code> will be returned, which means
	 * all methods will be instrumented.
	 * 
	 * @param name
	 * @param desc
	 * @return
	 */
	private boolean contains(String name, String desc) {
		if (methodInfor == null) {
			return true;
		}
		for (MethodInfor mi : methodInfor) {
			if (name.equals(mi.getMethodName())
					&& desc.equals(mi.getMethodDescription())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {

		MethodVisitor mv = cv.visitMethod(access, name, desc, signature,
				exceptions);

		if (mv != null && (mixMode || contains(name, desc))) {
			GeneralMV gmv = (GeneralMV) factory.generateMethodVisitor(mv, name,
					desc);
			gmv.checkMethodModifier(access);
			gmv.setSpecialMethods(methodInfor);
			return gmv;
		}
		return mv;
	}
	
}
