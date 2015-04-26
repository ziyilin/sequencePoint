package edu.sjtu.stap.checkmate.loadclass;

import java.util.Map;
import java.util.SortedSet;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import edu.sjtu.stap.checkmate.framework.instrument.InstrumentInfo;
import edu.sjtu.stap.checkmate.framework.instrument.MvFactory;

public class LineBasedMVFactory implements MvFactory {

	private final Map<Integer, SortedSet<InstrumentInfo>> instrumentInfoMap;
	private final String className;

	public LineBasedMVFactory(String className,
			Map<Integer, SortedSet<InstrumentInfo>> instrumentInfoMap) {
		this.className = className;
		this.instrumentInfoMap = instrumentInfoMap;
	}

	@Override
	public MethodVisitor generateMethodVisitor(MethodVisitor mv, String name,
			String desc) {
		return new LineBasedMV(mv, instrumentInfoMap, className);
	}

}

class LineBasedMV extends MethodVisitor implements Opcodes {

	private final Map<Integer, SortedSet<InstrumentInfo>> instrumentInfoMap;
	private final String className;

	public LineBasedMV(MethodVisitor mv,
			Map<Integer, SortedSet<InstrumentInfo>> instrumentInfoMap, String className) {
		super(ASM5, mv);
		this.instrumentInfoMap = instrumentInfoMap;
		this.className = className;
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		mv.visitLineNumber(line, start);
		if (instrumentInfoMap.containsKey(line)) {
			// Iterate Column
			InstrumentInfo instrumentInfo = instrumentInfoMap.get(line).first();
			if (instrumentInfo.getType().equals(InstrumentInfo.TYPE_SPCHECK))
				new SpCodeTemplate(mv)
						.insertSpCheckment(instrumentInfo.getSp());
			else
			// InstrumentInfo.TYPE_INSERT
			if (instrumentInfo.getInstance().equals(InstrumentInfo.INS_CURRENT))
				new SpCodeTemplate(mv).insertSpToCurrentThread(instrumentInfo
						.getSp());
			else {
				String insInfo[] = instrumentInfo.getInstance().split("[+]");
				String varType = insInfo[0];
				String varName = insInfo[1];
				if (varType.equals(InstrumentInfo.VAR_LOCAL)) {
					int localVarIndex = getLocalVarIndex(varName);
					new SpCodeTemplate(mv).insertSpToThread(
							instrumentInfo.getSp(), localVarIndex);
				} else {
					new SpCodeTemplate(mv).insertSpToThread(className,
							instrumentInfo.getSp(), varName);
				}
			}

		}
	}

	private int getLocalVarIndex(String varName) {
		// TODO Auto-generated method stub
		return 0;
	}
}
