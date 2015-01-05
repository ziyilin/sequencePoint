package edu.sjtu.stap.squencepoint.instrumentation;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import edu.sjtu.stap.checkmate.framework.instrument.CodeTemplate;
import edu.sjtu.stap.checkmate.framework.instrument.CustomizedMethodVisitor;
import edu.sjtu.stap.checkmate.framework.instrument.MvFactory;

public class LineBasedMVFactory implements MvFactory {

	private int line;

	public LineBasedMVFactory(int line) {
		this.line = line;
	}

	@Override
	public MethodVisitor generateMethodVisitor(MethodVisitor mv, String name,
			String desc) {
		return new LineBasedMV(mv, line);
	}

}

class LineBasedMV extends CustomizedMethodVisitor {

	private int line;

	public LineBasedMV(MethodVisitor mv, int line) {
		super(mv);
		this.line = line;
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		mv.visitLineNumber(line, start);
		if (line == this.line) {
			new CodeTemplate(mv).addPrintlnCodes("Instrument at line " + line);
		}
	}
}
