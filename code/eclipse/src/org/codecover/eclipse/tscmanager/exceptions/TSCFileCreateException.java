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

package org.codecover.eclipse.tscmanager.exceptions;

/**
 * This exception is thrown if a test session container file couldn't be
 * created.
 * 
 * @author Robert Hanussek
 * @version 1.0 ($Id$)
 */
@SuppressWarnings("serial")
public class TSCFileCreateException extends Exception {

    public TSCFileCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TSCFileCreateException(String message) {
        super(message);
    }

}
