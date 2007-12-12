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

package org.codecover.instrumentation.ada2005;

import java.io.File;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

import org.codecover.instrumentation.Instrumenter;
import org.codecover.instrumentation.InstrumenterProvider;

/**
 * This class is just exported to a jar file to test
 * {@link InstrumenterProvider}.
 * 
 * @author Christoph Müller
 * 
 * @version 1.0 ($Id$)
 */
public class InstrumenterDescriptor extends
        org.codecover.instrumentation.InstrumenterDescriptor {

    /**
     * Creates a new {@link InstrumenterDescriptor} and sets the properties.
     */
    public InstrumenterDescriptor() {
        super(InstrumenterDescriptor.class.getName());
        super.setLanguageName("Ada");
        super.setDescription("test class for InstrumenterProvider");
        super.setAuthor("no author");
        super.setDefaultCharset(Charset.defaultCharset());
    }

    @Override
    public boolean isLanguageSupported(String languageNameToCheck) {
        return Pattern.compile("Ada( 2005)?", Pattern.CASE_INSENSITIVE).matcher(languageNameToCheck).matches();
    }

    @Override
    protected Instrumenter getInstrumenter() {
        return null;
    }
    
    @Override
    public boolean accept(File file) {
        return true;
    }
}
