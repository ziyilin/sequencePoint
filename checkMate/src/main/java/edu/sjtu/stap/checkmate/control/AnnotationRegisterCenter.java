package edu.sjtu.stap.checkmate.control;

import java.util.ArrayList;
import java.util.List;

public class AnnotationRegisterCenter {

	private static class ClassHolder{
		public final static AnnotationRegisterCenter instance=new AnnotationRegisterCenter();
	}
	
	public static AnnotationRegisterCenter getInstance(){
		return ClassHolder.instance;
	}
	
	private List<ConditionAnnotation> annotations;
	
	private AnnotationRegisterCenter(){
		annotations=new ArrayList<ConditionAnnotation>();
	}
	
	public void register(ConditionAnnotation c){
		annotations.add(c);
	}
	
	public void deRegister(ConditionAnnotation c){
		annotations.remove(c);
	}
	
	public int size(){
		return annotations.size();
	}
	
	public List<ConditionAnnotation> getAnnotations(){
		return annotations;
	}

	public ConditionAnnotation findMatchAssociats(Object o) {
		for(ConditionAnnotation c:annotations){
			if(c.matchAssociated(o)){
				return c;
			}
		}
		return null;
	}
}
