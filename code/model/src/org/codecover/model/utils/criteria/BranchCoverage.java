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

package org.codecover.model.utils.criteria;

/**
 * This is an {@link Criterion} describing BranchCoverage.
 * 
 * @author Christoph Müller
 * @version 1.0 ($Id$)
 */
public class BranchCoverage extends AbstractCriterion {

    /**
     * The prefix of branch ID's:<br>
     * <code>{@value #ID_PREFIX}</code>
     */
    public static final String ID_PREFIX = "B";

    /**
     * the String "<b>BranchCoverage</b>"
     */
    public static final String NAME = "BranchCoverage";

    private final String INVOCATION_METHOD = "getInstance";

    private static final BranchCoverage instance = new BranchCoverage();

    /**
     * @return The single instance of BranchCoverage;
     */
    public static BranchCoverage getInstance() {
        return instance;
    }

    private BranchCoverage() {
        super(NAME, "org.codecover.model.utils.criteria.BranchCoverage", "org.codecover");
    }

}
