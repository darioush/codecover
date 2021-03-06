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

package org.codecover.instrumentation.xampil.manipulator;

import org.codecover.instrumentation.xampil.syntaxtree.Statement;

/**
 * @author Christoph Müller, Stefan Franke
 */
public class DummyStatementManipulator extends AbstractDummyManipulator
        implements StatementManipulator {
    
    public void writeDeclarations(int instrumentableItemCount) {
        // nothing to declare
    }
    
    public void writeCoverageLogFileOutput(int statementCount) {
        // nothing to write
    }

    public void manipulate(Statement n, String statementID) {
        // nothing to manipulate
    }
}
