/******************************************************************************
 * Copyright (c) 2007 Stefan Franke, Robert Hanussek, Benjamin Keil,          *
 *                    Steffen Kieß, Johannes Langauf,                         *
 *                    Christoph Marian Müller, Igor Podolskiy,                *
 *                    Tilmann Scheller, Michael Starzmann, Markus Wittlinger  *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/

package org.codecover.instrumentation.java15.manipulators;

import java.io.Writer;

import org.codecover.instrumentation.java15.counter.CounterIDManager;

/**
 * An abstract {@link Manipulator} which can be inherited by Default
 * Manipulators.
 * 
 * @author Christoph Müller
 * @version 1.0 ($Id$)
 * 
 * @see Manipulator
 * @see ArrayStatementManipulator
 * @see ArrayBranchManipulator
 * @see ArrayConditionManipulator
 * @see ArrayLoopManipulator
 */
public abstract class AbstractDefaultManipulator implements Manipulator {
    private Writer writer = null;

    private CounterIDManager counterIDManager = null;

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    /**
     * @return The writer.
     */
    protected Writer getWriter() {
        return this.writer;
    }

    /**
     * Gets the {@link #counterIDManager}.
     * 
     * @return The {@link CounterIDManager} of this {@link Manipulator}. Can be
     *         null.
     */
    public CounterIDManager getCounterIDManager() {
        return this.counterIDManager;
    }

    public void setCounterIDManager(CounterIDManager counterIDManager) {
        this.counterIDManager = counterIDManager;
    }
}
