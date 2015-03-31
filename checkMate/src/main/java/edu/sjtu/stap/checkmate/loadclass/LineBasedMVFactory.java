package edu.sjtu.stap.checkmate.loadclass;

import java.util.Map;
import java.util.SortedSet;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import edu.sjtu.stap.checkmate.framework.instrument.CodeTemplate;
import edu.sjtu.stap.checkmate.framework.instrument.CustomizedMethodVisitor;
import edu.sjtu.stap.checkmate.framework.instrument.InstrumentInfo;
import edu.sjtu.stap.checkmate.framework.instrument.MvFactory;

public class LineBasedMVFactory implements MvFactory {

	private Map<Integer, SortedSet<InstrumentInfo>> lineMap;

	public LineBasedMVFactory(Map<Integer, SortedSet<InstrumentInfo>> lineMap) {
		this.lineMap = lineMap;
	}

	@Override
	public MethodVisitor generateMethodVisitor(MethodVisitor mv, String name,
			String desc) {
		return new LineBasedMV(mv, 0);
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
