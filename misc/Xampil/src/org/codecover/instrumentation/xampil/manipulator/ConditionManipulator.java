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

import java.util.List;

import org.codecover.instrumentation.booleanterms.InstrBasicBooleanTerm;
import org.codecover.instrumentation.xampil.parser.InstrumentableItemCounter;

/**
 * This Manipulator is used for instrumentation of basic boolean terms.
 * 
 * @see DummyConditionManipulator
 * @see DefaultConditionManipulator
 * 
 * @author Stefan Franke
 */
public interface ConditionManipulator extends Manipulator {

    public void writeDeclarations(InstrumentableItemCounter counter);
    
    public void writeCoverageLogFileOutput(InstrumentableItemCounter counter);
    
    public void manipulate(String ifConditionID, List<InstrBasicBooleanTerm> termList);
}
