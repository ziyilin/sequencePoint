package edu.sjtu.stap.checkmate.framework.instrument;

import java.util.List;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * This MethodVisitor deals with general methods.
 * User can employ this class when she wants to 
 * do something to all methods.
 * 
 * @author Ziyi Lin
 *
 */
public class GeneralMV extends MethodVisitor {

	private boolean synchMethod;
	private boolean staticMethod;
	//methods need to deal with specific rules.
	private List<MethodInfor> specialMethods;
	
	public GeneralMV(int arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public GeneralMV(int arg0, MethodVisitor arg1) {
		super(arg0, arg1);
	}

	public void checkMethodModifier(int access) {
		synchMethod = (access & Opcodes.ACC_SYNCHRONIZED) == 1;
		staticMethod = (access & Opcodes.ACC_STATIC) == 1;
	}

	public boolean isSynchMethod() {
		return synchMethod;
	}

	public boolean isStaticMethod() {
		return staticMethod;
	}

	public List<MethodInfor> getSpecialMethods() {
		return specialMethods;
	}

	public void setSpecialMethods(List<MethodInfor> specialMethods) {
		this.specialMethods = specialMethods;
	}
}
