package edu.sjtu.stap.checkmate.instrument;

import org.objectweb.asm.MethodVisitor;

import edu.illinois.jacontebe.asm.MvFactory;

public class CheckmateMVFactory implements MvFactory {

	@Override
	public MethodVisitor generateMethodVisitor(MethodVisitor mv, String name,
			String desc) {
		return new ConcurrentEventMV(mv,name,desc);
	}
}
