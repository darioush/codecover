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
import org.codecover.instrumentation.java15.counter.CounterManager;
import org.codecover.instrumentation.java15.visitor.InstrumentationVisitor;

/**
 * This is an interface for manipulators.<br>
 * <br>
 * It allows to set a {@link Writer} and a {@link CounterIDManager}. Moreover
 * it extends the interface {@link CounterManager} cause all manipulators are
 * handed over to {@link CounterIDManager#addCounterManager(CounterManager)}.
 * 
 * @see CounterManager
 * @see StatementManipulator
 * @see BranchManipulator
 * @see ConditionManipulator
 * @see LoopManipulator
 * @see AbstractDummyManipulator
 * @see AbstractDefaultManipulator
 * 
 * @author Christoph Müller
 * @version 1.0 ($Id$)
 */
public interface Manipulator extends CounterManager {
    /**
     * Sets the writer for incrementing statements and modifications.
     * 
     * @param writer
     */
    public void setWriter(Writer writer);

    /**
     * Whether or whether not statements should occur in Block Statements.<br>
     * <br>
     * This information is needed by the {@link InstrumentationVisitor} to
     * expand single line statements - e.g.
     * 
     * <pre>
     * if (a == 1)
     *     a++;
     * </pre>
     * 
     * into
     * 
     * <pre>
     * if (a == 1) {
     *     a++;
     * }
     * </pre>
     * 
     * that a++ can be instrumented. Here statements in if / else are
     * considered.
     * 
     * @return true &rarr; Statements must be expanded to Block Statements if
     *         necessary.
     */
    public boolean requiresBlockExpansionsForBranches();

    /**
     * Whether or whether not statements should occur in Block Statements.<br>
     * <br>
     * This information is needed by the {@link InstrumentationVisitor} to
     * expand single line statements - e.g.
     * 
     * <pre>
     * while ( a <= 10 )
     *     a++;
     * </pre>
     * 
     * into
     * 
     * <pre>
     * while ( a <= 10 ) {
     *     a++;
     * }
     * </pre>
     * 
     * that a++ can be instrumented. Here statements in loops are
     * considered.
     * 
     * @return true &rarr; Statements must be expanded to Block Statements if
     *         necessary.
     */
    public boolean requiresBlockExpansionsForLoops();
}
