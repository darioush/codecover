/******************************************************************************
 * Copyright (c) 2007 Stefan Franke, Robert Hanussek, Benjamin Keil,          *
 *                    Steffen Kieß, Johannes Langauf,                         *
 *                    Christoph Marian Müller, Igor Podolskiy,                *
 *                    Tilmann Scheller, Michael Starzmann, Markus Wittlinger  *
 * All rights reserved. This file may be used, modifies and redistributed     *
 * under the terms of either the Eclipse Public License v1.0 which            *
 * accompanies this distribution and is available at                          *
 * http://www.eclipse.org/legal/epl-v10.html or the MIT license, available at *
 * http://www.opensource.org/licenses/mit-license.php                         *
 ******************************************************************************/

package org.codecover.model.utils;

/**
 * A class which will log {@link LogMessage}s.
 * 
 * @author Steffen Kieß
 * @version 1.0 ($Id$)
 */
public abstract class Logger {
    
    /**
     * A dummy logger that doesn't log anything.
     */
    public static final Logger NULL = new Logger() {
            @Override
            protected void writeLogMessage(LogMessage message) {
                // Do nothing here.
            }
        };
    
    protected abstract void writeLogMessage(LogMessage message);

    /**
     * Writes the {@link LogMessage} and throws a {@link FatalException}, if
     * the {@link LogLevel} was {@link LogLevel#FATAL}
     * 
     * @param message
     *            the {@link LogMessage}
     */
    public void log(LogMessage message) {
        if (message == null) {
            throw new NullPointerException("message == null");
        }

        writeLogMessage(message);

        if (message.getLevel() == LogLevel.FATAL) {
            throw new FatalException(message);
        }
    }
    
    /**
     * Calls {@link #log(LogMessage)}.
     * 
     * @param level
     *            the given log level
     * @param message
     *            the given message
     * @param exception
     *            the given exception
     */
    public void log(LogLevel level, String message, Exception exception) {
        log(new LogMessage(level, message, exception));
    }

    /**
     * Calls {@link #log(LogLevel, String, Exception)}, with null as the
     * exception.
     * 
     * @param level
     *            the given log level
     * @param message
     *            the given message
     */
    public void log(LogLevel level, String message) {
        log(level, message, null);
    }

    /**
     * Writes an fatal message.<br>
     * <br>
     * This is a message, that are used for fatal outputs even in the the
     * <code>quiet</code> mode of the batch commands. This will throw a
     * {@link FatalException}
     * 
     * @param message
     *            The message to write at the fatal level.
     */
    public void fatal(String message) {
        log(LogLevel.FATAL, message);
    }

    /**
     * Writes an error message.<br>
     * <br>
     * This is a message, that are used for error outputs even in the the
     * <code>quiet</code> mode of the batch commands.
     * 
     * @param message
     *            The message to write at the error level.
     */
    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    /**
     * Writes a warning message.<br>
     * <br>
     * This is the level for messages of the <code>normal</code> mode of the
     * batch commands.
     * 
     * @param message
     *            The message to write at the warning level.
     */
    public void warning(String message) {
        log(LogLevel.WARNING, message);
    }

    /**
     * Writes an info message.<br>
     * <br>
     * This is the level for messages of the <code>verbose</code> mode of the
     * batch commands.
     * 
     * @param message
     *            The message to write at the info level.
     */
    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    /**
     * Writes an debug message.<br>
     * 
     * @param message
     *            The message to write at the debug level.
     */
    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    /**
     * Writes an fatal message.<br>
     * <br>
     * This is a message, that are used for fatal outputs even in the the
     * <code>quiet</code> mode of the batch commands. This will throw a
     * {@link FatalException}
     * 
     * @param message
     *            The message to write at the fatal level.
     * @param exception
     *            the given exception
     */
    public void fatal(String message, Exception exception) {
        log(LogLevel.FATAL, message, exception);
    }

    /**
     * Writes an error message.<br>
     * <br>
     * This is a message, that are used for error outputs even in the the
     * <code>quiet</code> mode of the batch commands.
     * 
     * @param message
     *            The message to write at the error level.
     * @param exception
     *            the given exception
     */
    public void error(String message, Exception exception) {
        log(LogLevel.ERROR, message, exception);
    }

    /**
     * Writes a warning message.<br>
     * <br>
     * This is the level for messages of the <code>normal</code> mode of the
     * batch commands.
     * 
     * @param message
     *            The message to write at the warning level.
     * @param exception
     *            the given exception
     */
    public void warning(String message, Exception exception) {
        log(LogLevel.WARNING, message, exception);
    }

    /**
     * Writes an info message.<br>
     * <br>
     * This is the level for messages of the <code>verbose</code> mode of the
     * batch commands.
     * 
     * @param message
     *            The message to write at the info level.
     * @param exception
     *            the given exception
     */
    public void info(String message, Exception exception) {
        log(LogLevel.INFO, message, exception);
    }

    /**
     * Writes an debug message.<br>
     * 
     * @param message
     *            The message to write at the debug level.
     * @param exception
     *            the given exception
     */
    public void debug(String message, Exception exception) {
        log(LogLevel.DEBUG, message, exception);
    }
}
