package org.codecover.instrumentation.java15.manipulators;

import java.io.IOException;

import org.codecover.instrumentation.java15.syntaxtree.SynchronizedStatement;

/**
 * @author 
 *
 * @version 1.0 ($Id$)
 */

public interface SynchronizedManipulator extends Manipulator {
    /**
     * 
     * @param n
     * @param statementID
     * @throws IOException
     */
    public void manipulateBefore(SynchronizedStatement n, String statementID)
        throws IOException;

    /**
     * 
     * @param n
     * @param statementID
     * @throws IOException
     */
    public void manipulateInner(SynchronizedStatement n, String statementID)
        throws IOException;
}
