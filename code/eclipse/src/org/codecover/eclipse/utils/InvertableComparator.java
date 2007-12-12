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

package org.codecover.eclipse.utils;

import java.util.Comparator;

/**
 * @author Markus Wittlinger
 * @version 1.0 ($Id$)
 * @param <T> 
 */
public interface InvertableComparator<T> extends Comparator<T> {

    /**
     * @return TODO
     */
    abstract InvertableComparator<T> getInverseSorter();
    
    /**
     * @return TODO
     */
    abstract InvertableComparator<T> getDefaultSorter();

    /**
     * @return TODO
     */
    public abstract int getSortDirection();

}
