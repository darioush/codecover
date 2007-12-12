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

import java.util.*;

import org.codecover.model.*;
import org.codecover.model.mast.*;
import org.codecover.model.utils.*;
import org.codecover.model.utils.criteria.Criterion;

/**
 * This class implements branch coverage, by overwriting getCoverageLocal and
 * get coverageHint methods.
 * 
 * @author Markus Wittlinger, Tilmann Scheller
 * @version 1.0 ($Id$)
 */
public class BranchCoverage extends AbstractCoverageMetric {

    /**
     * A coverage hint for branch coverage with wrapped coverage status.
     * 
     * @author Steffen Kieß
     * @version 1.0 ($Id$)
     * @see CoverageMetric.EnglishTextHint
     * @see CoverageMetric.CoverageWrapper
     */
    public static interface BranchHint extends EnglishTextHint, CoverageWrapper {
        /**
         * Gets the {@link ConditionalStatement}.
         * 
         * @return the {@link ConditionalStatement}.
         */
        public ConditionalStatement getStatement();

        /**
         * Gets whether or not the given {@link Branch} is covered
         * 
         * @param branch
         *            the given {@link Branch}
         * @return true iff {@link Branch} is covered.
         */
        public boolean isBranchCovered(Branch branch);
    }

    private static final class BranchHintImpl implements BranchHint {
        private final CoverageResult result;

        private final ConditionalStatement statement;

        private final Map<Branch, Boolean> branchMap;

        /**
         * Constructor
         * 
         * @param result
         *            the {@link CoverageResult}
         * @param statement
         *            the {@link ConditionalStatement}
         * @param branchMap
         *            the {@link Map} of {@link Branch Branches} and their
         *            coverage.
         */
        public BranchHintImpl(CoverageResult result,
                ConditionalStatement statement, Map<Branch, Boolean> branchMap) {
            if (result == null || statement == null || branchMap == null) {
                throw new NullPointerException();
            }

            this.result = result;
            this.statement = statement;
            this.branchMap = CollectionUtil.copy(branchMap);
        }

        public CoverageResult getResult() {
            return this.result;
        }

        public ConditionalStatement getStatement() {
            return this.statement;
        }

        public boolean isBranchCovered(Branch branch) {
            Boolean isCovered = this.branchMap.get(branch);
            if (isCovered == null) {
                throw new IllegalArgumentException(
                        "query for branch not in map");
            }
            return isCovered;
        }

        public String toEnglishText() {
            if (this.result.getCoveredItems() == this.result.getTotalItems()) {
                return null;
            } else {
                String note = "Unexecuted branches: ";
                for (Branch branch : getStatement().getBranches()) {
                    if (!isBranchCovered(branch)) {
                        String decision = "";
                        boolean wroteLocation = true;
                        for (Location location : branch.getDecision().getLocations()) {
                            if (wroteLocation) {
                                note += location.getContent();
                                wroteLocation = false;
                            } else {
                                note += " " + location.getContent();
                            }
                        }
                        
                        if (wroteLocation) {
                            /* got no decision keyword */
                            //TODO: would be nice if model had some id to distinguish between branches and show to user
                            
                            if (branch.isImplicit()) {
                                note += "implicit (omitted) branch,  ";
                            } else {
                                // assume that no decision keyword and explicit
                                // branch can only be then (true for Java)
                                note += "then,  ";
                            }
                        } else {
                            note += decision + ",  ";
                        }
                    }
                }
                // the last three Characters are ", " or unnecessary, because no
                // branches are unexecuted
                note = note.substring(0, note.length() - 3);
                return note;
            }
        }
    }

    private static final String CACHING_KEY = "BranchCoverage";

    private static final String NAME = "Branch Coverage";

    private static final String DESCRIPTION = "";

    private static BranchCoverage instance = new BranchCoverage();

    private BranchCoverage() {
        super(CACHING_KEY);
    }

    /**
     * This method returns an instance of BranchCoverage.
     * 
     * @return instance of BranchCoverage.
     */
    public static BranchCoverage getInstance() {
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
        return Collections
                .<Criterion> singleton(org.codecover.model.utils.criteria.BranchCoverage
                        .getInstance());
    }

    /**
     * Calculates the {@link org.codecover.model.utils.criteria.BranchCoverage}
     * 
     * @see org.codecover.metrics.coverage.AbstractCoverageMetric#getCoverage(java.util.List,
     *      org.codecover.model.mast.Branch)
     */
    public CoverageResult getCoverageLocal(List<TestCase> testCases,
            Branch branch) {
        int coveredItems = 0;
        int totalItems = 0;

        totalItems = totalItems + 1;
        // check whether the branch has been covered in one of the test cases
        for (TestCase testCase : testCases) {
            if ((testCase.getCoverageCount(branch.getCoverableItem())) > 0) {
                coveredItems = coveredItems + 1;
                break;
            }
        }

        return new CoverageResult(coveredItems, totalItems);
    }

    public CoverageResult getCoverageLocal(List<TestCase> testCases,
            Statement statement) {
        return CoverageResult.NULL;
    }

    public CoverageResult getCoverageLocal(List<TestCase> testCases,
            RootTerm term) {
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

    public Set<Hint> getHints(List<TestCase> testCases, Statement statement) {
        if (statement instanceof ConditionalStatement) {
            final ConditionalStatement cStatement = (ConditionalStatement) statement;

            final int branches = cStatement.getBranches().size();
            final Map<Branch, Boolean> branchMap = new HashMap<Branch, Boolean>();
            int coveredBranches = 0;

            for (final Branch branch : cStatement.getBranches()) {
                if (getCoverageLocal(testCases, branch).getCoveredItems() == 0) {
                    branchMap.put(branch, false);
                } else {
                    branchMap.put(branch, true);
                    coveredBranches++;
                }
            }

            final Hint hint = new BranchHintImpl(new CoverageResult(
                    coveredBranches, branches), cStatement, branchMap);
            return Collections.singleton(hint);
        } else if (statement instanceof LoopingStatement) {
            return noHints;
        } else if (statement instanceof BasicStatement) {
            return noHints;
        } else {
            throw new RuntimeException();
        }
    }

    public Set<Hint> getHints(List<TestCase> testCases, RootTerm term) {
        return noHints;
    }

    public Set<Hint> getHints(List<TestCase> testCases,
            StatementSequence statements) {
        return noHints;
    }

    public Set<Hint> getHints(List<TestCase> testCases, HierarchyLevel level) {
        return noHints;
    }

    public Set<Hint> getHints(List<TestCase> testCases, Branch branch) {
        return noHints;
    }

}
