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

package org.codecover.metrics.coverage;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.codecover.model.TestCase;
import org.codecover.model.mast.*;
import org.codecover.model.utils.criteria.Criterion;

/**
 * This class would need to override getCoverage(testCases, statement).
 * 
 * @author Markus Wittlinger, Tilmann Scheller
 * @version 1.0 ($Id$)
 */
public class StatementCoverage extends AbstractCoverageMetric {

    /**
     * @author Steffen Kieß
     * @version 1.0 ($Id$)
     */
    public static interface ExecutionsHint extends Hint {
        /**
         * Gets the number of executions
         * @return the number of executions
         */
        public long getNumberOfExecutions();
    }
    
    private static final class ExecutionsHintImpl implements ExecutionsHint {
        private long numberOfExecutions;

        /**
         * Constructor.
         * 
         * @param numberOfExecutions
         *            the number of executions.
         */
        public ExecutionsHintImpl(long numberOfExecutions) {
            if (numberOfExecutions < 0) {
                throw new IllegalArgumentException("numberOfExecutions < 0");
            }
            
            this.numberOfExecutions = numberOfExecutions;
        }

        public long getNumberOfExecutions() {
            return this.numberOfExecutions;
        }
    }

    private static final String CACHING_KEY = "StatementCoverage";

    private static final String DESCRIPTION = "";

    private static final String NAME = "Statement Coverage";

    private static StatementCoverage instance = new StatementCoverage();

    private StatementCoverage() {
        super(CACHING_KEY);
        if (instance != null) {
            throw new RuntimeException("This should not happen.");
        }
    }

    /**
     * This method returns an instance of StatementCoverage.
     * 
     * @return instance of StatementCoverage.
     */
    public static StatementCoverage getInstance() {
        return instance;
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.codecover.metrics.Metric#getDescription()
     */
    public String getDescription() {
        return DESCRIPTION;
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.codecover.metrics.Metric#getName()
     */
    public String getName() {
        return NAME;
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.codecover.metrics.Metric#getRequiredCriteria()
     */
    public Set<Criterion> getRequiredCriteria() {
        return Collections.<Criterion>singleton(
            org.codecover.model.utils.criteria.StatementCoverage.getInstance());
    }
    
    /**
     * A static {@link CoverageResult} representing zero of one covered items.
     */
    public static final CoverageResult zeroOneResult = new CoverageResult(0, 1);
    /**
     * A static {@link CoverageResult} representing one of one covered items.
     */
    public static final CoverageResult oneOneResult = new CoverageResult(1, 1);
    
    /**
     * Calculates the
     * {@link org.codecover.model.utils.criteria.StatementCoverage}
     * 
     * @see org.codecover.metrics.coverage.AbstractCoverageMetric#getCoverage(java.util.List,
     *      org.codecover.model.mast.Statement)
     */
    public CoverageResult getCoverageLocal(List<TestCase> testCases,
            Statement statement) {
        if (statement instanceof BasicStatement) {
            // check whether the item has been covered in one of the test cases
            for (TestCase testCase : testCases) {
                if ((testCase.getCoverageCount(statement.getCoverableItem())) > 0) {
                    return oneOneResult;
                }
            }
            return zeroOneResult;
        } else {
            return CoverageResult.NULL;
        }
    }

    public CoverageResult getCoverageLocal(List<TestCase> testCases, RootTerm term) {
        return CoverageResult.NULL;
    }
    
    public CoverageResult getCoverageLocal(List<TestCase> testCases,
                                           StatementSequence statements) {
        return CoverageResult.NULL;
    }
    
    public CoverageResult getCoverageLocal(List<TestCase> testCases,
                                           HierarchyLevel level) {
        return CoverageResult.NULL;
    }
    
    public CoverageResult getCoverageLocal(List<TestCase> testCases, Branch branch) {
        return CoverageResult.NULL;
    }

    public Set<Hint> getHints(List<TestCase> testCases,
                              Statement statement) {
        if (statement instanceof ConditionalStatement) {
            return noHints;
        } else if (statement instanceof LoopingStatement) {
            return noHints;
        } else if (statement instanceof BasicStatement) {
            long executions = 0;
            
            for (final TestCase testCase : testCases) {
                executions += testCase.getCoverageCount(statement.getCoverableItem());
            }
            
            final Hint hint = new ExecutionsHintImpl(executions);
            return Collections.singleton(hint);
        } else {
            throw new RuntimeException();
        }
    }

    public Set<Hint> getHints(List<TestCase> testCases,
                              RootTerm term) {
        return noHints;
    }

    public Set<Hint> getHints(List<TestCase> testCases, StatementSequence statements) {
        return noHints;
    }

    public Set<Hint> getHints(List<TestCase> testCases, HierarchyLevel level) {
        return noHints;
    }

    public Set<Hint> getHints(List<TestCase> testCases, Branch branch) {
        return noHints;
    }
}
