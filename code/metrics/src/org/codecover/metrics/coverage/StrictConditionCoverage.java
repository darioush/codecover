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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.codecover.model.TestCase;
import org.codecover.model.mast.BasicBooleanTerm;
import org.codecover.model.mast.BooleanAssignment;
import org.codecover.model.mast.BooleanResult;
import org.codecover.model.mast.Branch;
import org.codecover.model.mast.HierarchyLevel;
import org.codecover.model.mast.RootTerm;
import org.codecover.model.mast.Statement;
import org.codecover.model.mast.StatementSequence;
import org.codecover.model.utils.criteria.ConditionCoverage;
import org.codecover.model.utils.criteria.Criterion;

/**
 * This class would need to override getCoverage(testCases, term).
 *
 * @author Markus Wittlinger, Tilmann Scheller
 * @version 1.0 ($Id$)
 */
public class StrictConditionCoverage extends AbstractCoverageMetric {
    private static final String CACHING_KEY = "StrictConditionCoverage";

    private static final String NAME = "Strict Condition Coverage";

    private static final String DESCRIPTION = "";

    private static StrictConditionCoverage instance = new StrictConditionCoverage();

    private StrictConditionCoverage() {
        super(CACHING_KEY);
    }

    /**
     * This method returns an instance of StrictConditionCoverage.
     *
     * @return instance of StrictConditionCoverage.
     */
    public static StrictConditionCoverage getInstance() {
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
          org.codecover.model.utils.criteria.ConditionCoverage.getInstance());
    }

    /**
     * Calculates the {@link ConditionCoverage}
     *
     * @see org.codecover.metrics.coverage.AbstractCoverageMetric#getCoverage(java.util.List,
     *      org.codecover.model.mast.RootTerm)
     */
    public CoverageResult getCoverageLocal(Collection<TestCase> testCases,
            RootTerm term) {
        // The real work is done in the accept(Collection<TestCase> testCases, final
        // RootTerm term, final Visitor post) method
        return CoverageResult.NULL;
    }

    /**
     * Calculates the set of {@link BooleanAssignment} for a specific
     * {@link BasicBooleanTerm} which contribute to its coverage.
     *
     * @param testCases
     *            the test cases containing the coverage data
     * @param term
     *            the {@link RootTerm} to whom the {@link BasicBooleanTerm}
     *            belongs to
     * @param basicTerm
     *            the {@link BasicBooleanTerm} whose coverage to calculate
     * @return the covering assignments of the {@link BasicBooleanTerm} for the
     *         given test cases
     */
    // TODO: try to replace this with a more elegant solution
    public Set<BooleanAssignment> getCoverage(Collection<TestCase> testCases,
            RootTerm term, BasicBooleanTerm basicTerm) {
        Map<BooleanAssignment, Boolean> assignmentsMap = new HashMap<BooleanAssignment, Boolean>();

        // store the assignments which contribute to the coverage of basicTerm
        Set<BooleanAssignment> coveringAssignments = new HashSet<BooleanAssignment>();

        // merge assignments of all the test cases
        for (TestCase testCase : testCases) {
            assignmentsMap.putAll(testCase.getAssignments(term));
        }

        int i;
        int j;
        int k;
        int differences = 0;
        Set<BooleanAssignment> assignmentsSet = assignmentsMap.keySet();
        BooleanAssignment[] assignments = new BooleanAssignment[assignmentsSet
                .size()];
        int position = term.getPositionOfTerm(basicTerm);

        // convert assignment set into assignment array
        i = 0;
        for (BooleanAssignment assignment : assignmentsSet) {
            assignments[i] = assignment;
            i++;
        }

        // compare every assignment with every other assignment
        for (i = 0; i < assignments.length - 1; i++) {
            for (j = i + 1; j < assignments.length; j++) {
                differences = 0;

                if (assignmentsMap.get(assignments[i]) != assignmentsMap
                        .get(assignments[j])) {
                    // results of both assignments are unequal therefore
                    // these assignments could increase in coverage
                    Iterator<BooleanResult> first = assignments[i].getResults()
                            .iterator();
                    Iterator<BooleanResult> second = assignments[j]
                            .getResults().iterator();

                    // compare assignments and count differences
                    BooleanResult a;
                    BooleanResult b;

                    a = assignments[i].getResults().get(position);
                    b = assignments[j].getResults().get(position);

                    if ((a == BooleanResult.TRUE && b == BooleanResult.FALSE)
                            || (a == BooleanResult.FALSE && b == BooleanResult.TRUE)) {
                        // BasicBooleanTerm is not equal in both
                        // assignments, therefore the assignments
                        // could lead to coverage of the BasicBooleanTerm

                        // now check whether the assignments of the other
                        // BasicBooleanTerms
                        // remain constant
                        for (k = 0; first.hasNext() && second.hasNext(); k++) {
                            a = first.next();
                            b = second.next();
                            if ((a == BooleanResult.TRUE && b == BooleanResult.FALSE)
                                    || (a == BooleanResult.FALSE && b == BooleanResult.TRUE)) {
                                differences++;

                                if (differences > 1) {
                                    break;
                                }
                            }
                        }

                        if (differences == 1) {
                            // results are not equal and exactly one
                            // basic boolean term is both true and false
                            // therefore this basic boolean term is covered
                            coveringAssignments.add(assignments[i]);
                            coveringAssignments.add(assignments[j]);
                        }
                    }

                }
            }
        }

        return coveringAssignments;
    }

    private static final CoverageResult zeroOneResult = new CoverageResult(0, 1);
    private static final CoverageResult oneOneResult = new CoverageResult(1, 1);

    @Override
    public void accept(Collection<TestCase> testCases, final RootTerm term,
            final PostMetricVisitor post) {
        Map<BooleanAssignment, Boolean> assignmentsMap = new HashMap<BooleanAssignment, Boolean>();

        // merge assignments of all the test cases
        for (TestCase testCase : testCases) {
            assignmentsMap.putAll(testCase.getAssignments(term));
        }

        int i;
        int j;
        int k;
        int differences = 0;
        int termIndex = 0;
        boolean[] coveredTerms;
        Set<BooleanAssignment> assignmentsSet = assignmentsMap.keySet();
        BooleanAssignment[] assignments = new BooleanAssignment[assignmentsSet
                .size()];

        // convert assignment set into assignment array
        i = 0;
        for (BooleanAssignment assignment : assignmentsSet) {
            assignments[i] = assignment;
            i++;
        }

        // terms which are covered are marked here
        coveredTerms = new boolean[term.getTerm().getBasicBooleanTerms()];

        // compare every assignment with every other assignment
        // TODO: this is O(n²) with n the number of assignments. This should
        // work faster.
        for (i = 0; i < assignments.length - 1; i++) {
            for (j = i + 1; j < assignments.length; j++) {
                differences = 0;

                if (assignmentsMap.get(assignments[i]) != assignmentsMap
                        .get(assignments[j])) {
                    // results of both assignments are unequal therefore
                    // these assignments could increase in coverage
                    Iterator<BooleanResult> first = assignments[i].getResults()
                            .iterator();
                    Iterator<BooleanResult> second = assignments[j]
                            .getResults().iterator();

                    // compare assignments and count differences
                    BooleanResult a;
                    BooleanResult b;
                    for (k = 0; first.hasNext() && second.hasNext(); k++) {
                        a = first.next();
                        b = second.next();
                        if ((a == BooleanResult.TRUE && b == BooleanResult.FALSE)
                                || (a == BooleanResult.FALSE && b == BooleanResult.TRUE)) {
                            differences++;
                            termIndex = k;

                            if (differences > 1) {
                                break;
                            }
                        }
                    }

                    if (differences == 1) {
                        // results are not equal and exactly one
                        // basic boolean term is both true and false
                        // therefore this basic boolean term is covered
                        coveredTerms[termIndex] = true;
                    }
                }
            }

        }

        // visit terms
        for (i = 0; i < coveredTerms.length; i++) {
            final BasicBooleanTerm bbTerm = term.getTermAtPosition(i);
            if (coveredTerms[i]) {
                post.visit(bbTerm, term, oneOneResult, noHints);
            } else {
                post.visit(bbTerm, term, zeroOneResult, noHints);
            }
        }

        super.accept(testCases, term, post);
    }

 
}
