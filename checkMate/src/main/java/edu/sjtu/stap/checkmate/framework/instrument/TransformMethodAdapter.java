package edu.sjtu.stap.checkmate.framework.instrument;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

/**
 * This class adds code into a specified method.
 * 
 * @author Ziyi Lin
 * 
 */
public class TransformMethodAdapter extends ClassVisitor implements Opcodes {

	private MvFactory factory;
	private Map<String, Object> params;

	public TransformMethodAdapter(ClassVisitor cv,
			 MvFactory mvFactory,Map<String, Object> params) {
		super(ASM5, cv);
		this.factory = mvFactory;
		this.params=params;
		//methods need specific rule to transform
		//this.specialMethods = methodInformations;
	}

	/**
	 * If methodInfor is null, <code>true</code> will be returned, which means
	 * all methods will be instrumented.
	 * 
	 * @param name
	 * @param desc
	 * @param methodInforList 
	 * @return
	 */
	private boolean contains(String name, String desc, List<MethodInfor> methodInforList) {
		//No special methods means all methods need to deal with all methods.
		if (methodInforList == null) {
			return true;
		}
		//Only methods in this list need to manipulate, unless mixMode is set to true.
		for (MethodInfor mi : methodInforList) {
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
		
		List<MethodInfor> methodInforList = (List<MethodInfor>) params
				.get(Constants.SPECIAL_METHODS);
		// User can put method name and description directly into property
		// instead of using List<MethodInfor> when there is only one method
		// need to transform.
		// On the other hand, when method name, description and
		// List<MethodInfor> are all null,
		// we will instrument all methods.
		if (methodInforList == null) {
			String methodName = (String) params.get(Constants.METHOD_NAME);
			String methodDesc = (String) params.get(Constants.METHOD_DESC);
			if (methodName != null || methodDesc != null) {
				MethodInfor mi = new MethodInfor(methodName, methodDesc);
				methodInforList = new ArrayList<MethodInfor>();
				methodInforList.add(mi);
				params.put(Constants.SPECIAL_METHODS, methodInforList);
			}
		}
		boolean mix = params.get(Constants.MIX_MODE) == null ? false
				: (Boolean) params.get(Constants.MIX_MODE);

		if (mv != null && (mix || contains(name, desc,methodInforList))) {
			GeneralMV gmv = (GeneralMV) factory.generateMethodVisitor(mv, name,
					desc);
			gmv.checkMethodModifier(access);
			gmv.setParams(params);
			return gmv;
		}
		return mv;
	}
	
}
