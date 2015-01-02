package edu.sjtu.stap.checkmate.instrument;

import org.objectweb.asm.MethodVisitor;

import edu.illinois.jacontebe.asm.MvFactory;

public class ThreadMVFactory implements MvFactory {

	@Override
	public MethodVisitor generateMethodVisitor(MethodVisitor mv, String name,
			String signature) {
		if(name.equals("start")){
			return new StartMV(mv);
		}else if(name.equals("join")){
			return new JoinMV(mv);
		}
		return mv;
	}

}

class StartMV extends CustomizedMethodVisitor{

	public StartMV(MethodVisitor mv) {
		super(mv);
	}
	
	public void visitCode(){
		mv.visitCode();
		
	}
	
}

class JoinMV extends CustomizedMethodVisitor{

	public JoinMV(MethodVisitor mv) {
		super(mv);
	}
	
	
	
}