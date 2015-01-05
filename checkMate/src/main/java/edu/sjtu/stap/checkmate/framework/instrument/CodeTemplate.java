package edu.sjtu.stap.checkmate.framework.instrument;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 
 * @author Ziyi Lin
 * 
 */
public class CodeTemplate implements Opcodes {

    private MethodVisitor mv;

    public CodeTemplate(MethodVisitor mv) {
        this.mv = mv;
    }

    public void addEndOfSequence() {
        mv.visitMethodInsn(INVOKESTATIC,
                "edu/illinois/jacontebe/globalevent/GlobalDriver",
                "getInstance",
                "()Ledu/illinois/jacontebe/globalevent/GlobalDriver;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "edu/illinois/jacontebe/globalevent/GlobalDriver",
                "endOfSequence", "()V", false);
    }

    public void addEndStep(int sequenceValue) {
        mv.visitMethodInsn(INVOKESTATIC,
                "edu/illinois/jacontebe/globalevent/GlobalDriver",
                "getInstance",
                "()Ledu/illinois/jacontebe/globalevent/GlobalDriver;", false);
        mv.visitInsn(sequenceValue);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "edu/illinois/jacontebe/globalevent/GlobalDriver", "endStep",
                "(I)V", false);
    }

    /**
     * This is a print line method code snippet:
     * 
     * System.out.println(message)
     * 
     * @param message
     */
    public void addPrintlnCodes(String message) {
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out",
                "Ljava/io/PrintStream;");
        mv.visitLdcInsn(message);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println",
                "(Ljava/lang/String;)V", false);
    }

    /**
     * This is a sleep code snippet:
     * 
     * try{ Thread.sleep(sleepTime); } catch(InterruptedException e) {}
     * 
     * @param sleepTime
     */
    public void addSleepCodes(long sleepTime) {
        Label tryStart = new Label();
        Label tryEnd = new Label();
        Label catchStart = new Label();
        Label tryCatchEnd = new Label();
        mv.visitTryCatchBlock(tryStart, tryEnd, catchStart,
                "java/lang/InterruptedException");
        mv.visitLabel(tryStart);
        mv.visitLdcInsn(new Long(sleepTime));
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Thread", "sleep", "(J)V",
                false);
        mv.visitLabel(tryEnd);
        mv.visitJumpInsn(GOTO, tryCatchEnd);
        mv.visitLabel(catchStart);
        mv.visitFrame(F_SAME1, 0, null, 1,
                new Object[] { "java/lang/InterruptedException" });
        mv.visitVarInsn(ASTORE, 1);
        mv.visitLabel(tryCatchEnd);
        mv.visitFrame(F_SAME, 0, null, 0, null);
    }

    public void addStartStep(int sequenceValue) {
        mv.visitMethodInsn(INVOKESTATIC,
                "edu/illinois/jacontebe/globalevent/GlobalDriver",
                "getInstance",
                "()Ledu/illinois/jacontebe/globalevent/GlobalDriver;", false);
        mv.visitInsn(sequenceValue);
        mv.visitMethodInsn(INVOKEVIRTUAL,
                "edu/illinois/jacontebe/globalevent/GlobalDriver", "startStep",
                "(I)V", false);
    }
}