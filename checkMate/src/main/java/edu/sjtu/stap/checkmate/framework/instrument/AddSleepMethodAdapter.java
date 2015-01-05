package edu.sjtu.stap.checkmate.framework.instrument;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AddSleepMethodAdapter extends MethodVisitor implements Opcodes {

    // sleep time, in ms
    protected long sTime;

    public AddSleepMethodAdapter(MethodVisitor mv, long sleepTime) {
        super(ASM5, mv);
        this.sTime = sleepTime;
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        mv.visitMaxs(maxStack, maxLocals + 1);
    }

}
