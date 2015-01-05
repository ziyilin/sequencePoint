package edu.sjtu.stap.checkmate.framework.instrument;

import org.objectweb.asm.MethodVisitor;

/**
 * 
 * @author Ziyi Lin
 * 
 */
public interface MvFactory {

    /**
     * Generate proper methodVisitor for method with specified name and
     * description
     * 
     * @param mv
     * @param name
     *            name of method to transform.
     * @param desc
     *            description of method to transform.
     * @return
     */
    public MethodVisitor generateMethodVisitor(MethodVisitor mv, String name,
            String desc);
}
