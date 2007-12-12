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

// Generated by JTB 1.3.2
package org.codecover.instrumentation.xampil.visitor;

import org.codecover.instrumentation.xampil.syntaxtree.Node;
import org.codecover.instrumentation.xampil.syntaxtree.NodeToken;

/**
 * Gets the last {@link NodeToken#endOffset}.
 */
public class EndOffset extends DepthFirstVisitor {
    private int foundEndOffset = -1;

    private EndOffset() {
        // private constructor
    }

    /**
     * Assigns {@link #foundEndOffset}.
     */
    @Override
    public void visit(NodeToken n) {
        this.foundEndOffset = n.endOffset;
    }

    /**
     * This method searches in a {@link Node} for the last
     * {@link NodeToken#endOffset}. <br>
     * This method calls:
     * 
     * <pre>
     * EndOffset endOffset = new EndOffset();
     * n.accept(endOffset);
     * return endOffset.foundEndOffset;
     * </pre>
     * 
     * @param n
     *            The Node to start the search from.
     * 
     * @return The last {@link NodeToken#endOffset}. <code>-1</code> if not
     *         present.
     */
    public static int getEndOffset(Node n) {
        EndOffset endOffset = new EndOffset();
        n.accept(endOffset);
        return endOffset.foundEndOffset;
    }
}