package edu.sjtu.stap.checkmate.instrument;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import edu.illinois.jacontebe.asm.CodeTemplate;


public class CustomizedMethodVisitor extends MethodVisitor implements Opcodes{

	protected CodeTemplate codeTemplate;
	
	public CustomizedMethodVisitor(MethodVisitor mv) {
		 super(ASM5, mv);
	     codeTemplate = new CodeTemplate(mv);
	}

}
