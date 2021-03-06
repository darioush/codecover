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

package org.codecover.model.exceptions;

/**
 * @author Markus Wittlinger
 * @version 1.0 ($Id$)
 */
public class FileSaveException extends ModelException {
    private static final long serialVersionUID = 4226015022563242502L;

    /**
     * Construct a new FileSaveException object with <code>message</code> as
     * its detail message.
     * 
     * @param message
     *            The message for this exception.
     */
    public FileSaveException(String message) {
        super(message);
    }

    /**
     * Constructs a new FileSaveException with the specified
     * <code>message</code> and <code>cause</code>.
     * 
     * @param message
     *            The message for this exception.
     * @param cause
     *            The cause of this exception.
     */
    public FileSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
