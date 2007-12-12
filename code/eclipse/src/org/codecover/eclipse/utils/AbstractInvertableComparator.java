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

import org.eclipse.swt.SWT;

/**
 * TODO comment
 * 
 * @author Markus Wittlinger
 * @version 1.0 ($Id: AbstractInvertableComparator.java 1697 2007-07-24
 *          16:58:09Z wittlims $)
 * @param <T>
 */
public abstract class AbstractInvertableComparator<T> implements
        InvertableComparator<T> {
    private static final int DEFAULT_DIRECTION = SWT.UP;

    private final InvertableComparator<T> inverse = new InvertableComparator<T>() {

        public int compare(T o1, T o2) {
            return (-1) * AbstractInvertableComparator.this.compare(o1, o2);
        }

        public InvertableComparator<T> getInverseSorter() {
            return AbstractInvertableComparator.this;
        }

        public int getSortDirection() {
            return SWT.DOWN;
        }

        public InvertableComparator<T> getDefaultSorter() {
            if (getSortDirection() == AbstractInvertableComparator.DEFAULT_DIRECTION) {
                return this;
            } else {
                return getInverseSorter();
            }
        }
    };

    /**
     * (non-Javadoc)
     * 
     * @see org.codecover.eclipse.utils.InvertableComparator#getDefaultSorter()
     */
    public InvertableComparator<T> getDefaultSorter() {
        if (getSortDirection() == AbstractInvertableComparator.DEFAULT_DIRECTION) {
            return this;
        } else {
            return getInverseSorter();
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.codecover.eclipse.utils.InvertableComparator#getInverseSorter()
     */
    public InvertableComparator<T> getInverseSorter() {
        return this.inverse;
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.codecover.eclipse.utils.InvertableComparator#getSortDirection()
     */
    public int getSortDirection() {
        return SWT.UP;
    }
}
