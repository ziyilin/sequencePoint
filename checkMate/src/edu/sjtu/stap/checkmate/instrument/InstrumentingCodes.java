package edu.sjtu.stap.checkmate.instrument;

import org.objectweb.asm.MethodVisitor;

import edu.illinois.jacontebe.asm.CodeTemplate;

public class InstrumentingCodes extends CodeTemplate {

	public InstrumentingCodes(MethodVisitor mv) {
		super(mv);
	}

	
}
