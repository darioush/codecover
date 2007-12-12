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

import org.codecover.metrics.Metric;
import org.codecover.model.TestCase;
import org.codecover.model.mast.*;

/**
 * This interface must be implemented by all coverage metrics.
 * <p>
 * Method overview:
 * <ul>
 * <li>getCoverage(...) returns the cummulated coverage for the given element
 * and all descendants
 * <li>getCoverageLocal(...) returns the coverage for the given element
 * <li>getCoverageHints(...) returns the coverage Hints to annotate the given
 * element
 * <li>accept(...) calls the Visitor with local coverage information and Hints.
 * If CoverageResult has getTotalItems()==0, one of the returned Hints must
 * be a {@link CoverageWrapper} with getTotalItems() > 0.
 * <li> 
 * </ul>
 * 
 * @author Markus Wittlinger, Tilmann Scheller, Steffen Kieß
 * FIXME COMMENT IMMEDIATELY: What to comment? :@Markus
 * @version 1.0 ($Id$)
 */
public interface CoverageMetric extends Metric {
    
    /**
     * A Coverage Hint. It contains information the Metric has to annotate
     * about the coverage result of a MAST-Element. Should only be used if the
     * {@link CoverageResult} has > 0 TotalItems and CoverdItems < TotalItems.
     * <p>
     * To give a Hint about an element with 0 TotalItems in getLocalCoverge use
     * {@link CoverageWrapper}.
     * <p>
     * If your Hint should be shown as a Tooltip in highlighted code, implement
     * {@link EnglishTextHint}.
     * @author Steffen Kieß
     * @version 1.0 ($Id$)
     */
    public static interface Hint {
    }
    
    /**
     * A coverage {@link Hint} to return when no local coverage is available,
     * but we want to display it.
     * 
     * @author Johannes Langauf
     * @version 1.0 ($Id$)
     */
    public static interface CoverageWrapper extends Hint {
        
        /**
         * @return the wrapped {@link CoverageResult}, with TotalItems > 0
         */
        public CoverageResult getResult();
    }
    
    /**
     * @see Hint
     * 
     * @author Johannes Langauf
     * @version 1.0 ($Id$)
     */
    public static interface EnglishTextHint extends Hint {
        public String toEnglishText();
    }
    
    public static interface Visitor {
        public void visit(HierarchyLevel statement, CoverageResult result, Set<Hint> hints);
        public void visit(BasicStatement statement, CoverageResult result, Set<Hint> hints);
        public void visit(ConditionalStatement statement, CoverageResult result, Set<Hint> hints);
        public void visit(LoopingStatement statement, CoverageResult result, Set<Hint> hints);
        public void visit(StatementSequence statement, CoverageResult result, Set<Hint> hints);
        public void visit(Branch statement, CoverageResult result, Set<Hint> hints);
        public void visit(RootTerm term, CoverageResult result, Set<Hint> hints);
        public void visit(BasicBooleanTerm term, RootTerm rootTerm, CoverageResult result, Set<Hint> hints);
        public void visit(OperatorTerm term, RootTerm rootTerm, CoverageResult result, Set<Hint> hints);
    }
    
    public static class DefaultVisitor implements Visitor {
        public void visit(HierarchyLevel statement, CoverageResult result, Set<Hint> hints) {
        }

        public void visit(BasicStatement statement, CoverageResult result, Set<Hint> hints) {
        }

        public void visit(ConditionalStatement statement, CoverageResult result, Set<Hint> hints) {
        }

        public void visit(LoopingStatement statement, CoverageResult result, Set<Hint> hints) {
        }

        public void visit(StatementSequence statement, CoverageResult result, Set<Hint> hints) {
        }

        public void visit(Branch statement, CoverageResult result, Set<Hint> hints) {
        }

        public void visit(RootTerm term, CoverageResult result, Set<Hint> hints) {
        }

        public void visit(BasicBooleanTerm term, RootTerm rootTerm, CoverageResult result, Set<Hint> hints) {
        }

        public void visit(OperatorTerm term, RootTerm rootTerm, CoverageResult result, Set<Hint> hints) {
        }
    }
    
    /**
     * Calculates the coverage of the parts of the ast under the given Testcases
     * 
     * @param testCases
     *            The list containing TestCases, whose combined coverage is
     *            calculated here.
     * @param statement
     *            The Statement whose coverage is to be calculated.
     * @return the {@link CoverageResult} of the {@link Statement}
     */
    public CoverageResult getCoverage(List<TestCase> testCases,
                                      Statement statement);
    public CoverageResult getCoverageLocal(List<TestCase> testCases,
                                      Statement statement);
    public Set<Hint> getHints(List<TestCase> testCases,
                                      Statement statement);
    public void accept(List<TestCase> testCases, Statement statement, Visitor post);

    /**
     * Calculates the coverage of the parts of the ast under the given Testcases
     * 
     * @param testCases
     *            The list containing TestCases, whose combined coverage is
     *            calculated here.
     * @param term
     *            The RootTerm whose coverage is to be calculated.
     * @return the {@link CoverageResult} of the {@link RootTerm}
     */
    public CoverageResult getCoverage(List<TestCase> testCases, RootTerm term);
    public CoverageResult getCoverageLocal(List<TestCase> testCases, RootTerm term);
    public Set<Hint> getHints(List<TestCase> testCases,
                                      RootTerm term);
    public void accept(List<TestCase> testCases, RootTerm term, Visitor post);

    /**
     * Calculates the coverage of the parts of the ast under the given Testcases
     * 
     * @param testCases
     *            The list containing TestCases, whose combined coverage is
     *            calculated here.
     * @param statements
     *            The StatementSequence which contains the statements whose
     *            coverage is to be calculated.
     * @return the {@link CoverageResult} of the {@link StatementSequence}
     */
    public CoverageResult getCoverage(List<TestCase> testCases,
                                      StatementSequence statements);
    public CoverageResult getCoverageLocal(List<TestCase> testCases,
                                      StatementSequence statements);
    public Set<Hint> getHints(List<TestCase> testCases, StatementSequence statements);
    public void accept(List<TestCase> testCases, StatementSequence statements, Visitor post);

    /**
     * Calculates the coverage of the parts of the ast under the given Testcases
     * 
     * @param testCases
     *            The list containing TestCases, whose combined coverage is
     *            calculated here.
     * @param level
     *            The HierarchyLevel which is the entry point into the AST.
     * @return the {@link CoverageResult} of the {@link HierarchyLevel}
     */
    public CoverageResult getCoverage(List<TestCase> testCases,
                                      HierarchyLevel level);
    public CoverageResult getCoverageLocal(List<TestCase> testCases,
                                      HierarchyLevel level);
    public Set<Hint> getHints(List<TestCase> testCases, HierarchyLevel level);
    public void accept(List<TestCase> testCases, HierarchyLevel level, Visitor post);

    /**
     * Calculates the coverage of the parts of the ast under the given Testcases
     * 
     * @param testCases
     *            The list containing TestCases, whose combined coverage is
     *            calculated here.
     * @param branch
     *            The Branch whose coverage is to be calculated.
     * @return the {@link CoverageResult} of the {@link Branch}
     */
    public CoverageResult getCoverage(List<TestCase> testCases, Branch branch);
    public CoverageResult getCoverageLocal(List<TestCase> testCases, Branch branch);
    public Set<Hint> getHints(List<TestCase> testCases, Branch branch);
    public void accept(List<TestCase> testCases, Branch branch, Visitor post);

}
