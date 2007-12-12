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

import org.codecover.metrics.coverage.CoverageMetric.Hint;
import org.codecover.model.TestCase;
import org.codecover.model.mast.*;

/**
 * This abstract class implements the CoverageMetric. It provides a default
 * implementation for all methods declared in CoverageMetric. Classes
 * subclassing AbstractCoverageMetric need only override those methods, whose
 * implementation here differs from behaviour they require. It also provides
 * caching functionality for the coverage results calculated for
 * HierarchyLevels. Every calculation done solely with the methods of this class
 * will always return zero coverage. But they provide a way to traverse the AST.
 * To implement e.g. statement coverage, it would be necessary to override
 * getCoverage(testCases, statement) and check, if a basic statement is given
 * and return the true coverage. But for the traversal of the AST one can call
 * super.getCoverage(testCases, statement).
 * 
 * @author Markus Wittlinger, Tilmann Scheller
 * @version 1.0 ($Id$)
 */
public abstract class AbstractCoverageMetric implements CoverageMetric {

    private static final String CACHINGKEY_HIERARCHYLEVEL = ".getCoverage_HierarchyLevel";

    private final String cachingKeyName;

    /**
     * A empty {@link CoverageMetric.Hint} set.
     */
    protected static final Set<Hint> noHints = Collections.<Hint> emptySet();

    private static final class SumVisitor implements Visitor {
        private int coveredItems = 0;

        private int totalItems = 0;

        /**
         * Adds a {@link CoverageResult}
         * 
         * @param result
         *            the given result to add.
         */
        public void add(CoverageResult result) {
            this.coveredItems += result.getCoveredItems();
            this.totalItems += result.getTotalItems();
        }

        /**
         * Creates a {@link CoverageResult}.
         * 
         * @return the created {@link CoverageResult}.
         */
        public CoverageResult createResult() {
            if (this.coveredItems > this.totalItems || this.coveredItems < 0
                    || this.totalItems < 0) {
                // We might get here thanks to Java's ingenious (= nonexistent)
                // overflow handling.
                throw new RuntimeException();
            }

            if (this.totalItems == 0) {
                return CoverageResult.NULL;
            } else {
                return new CoverageResult(this.coveredItems, this.totalItems);
            }
        }

        public void visit(HierarchyLevel statement, CoverageResult result,
                Set<Hint> hints) {
            add(result);
        }

        public void visit(BasicStatement statement, CoverageResult result,
                Set<Hint> hints) {
            add(result);
        }

        public void visit(ConditionalStatement statement,
                CoverageResult result, Set<Hint> hints) {
            add(result);
        }

        public void visit(LoopingStatement statement, CoverageResult result,
                Set<Hint> hints) {
            add(result);
        }

        public void visit(StatementSequence statement, CoverageResult result,
                Set<Hint> hints) {
            add(result);
        }

        public void visit(Branch statement, CoverageResult result,
                Set<Hint> hints) {
            add(result);
        }

        public void visit(RootTerm term, CoverageResult result, Set<Hint> hints) {
            add(result);
        }

        public void visit(BasicBooleanTerm term, RootTerm rootTerm,
                CoverageResult result, Set<Hint> hints) {
            add(result);
        }

        public void visit(OperatorTerm term, RootTerm rootTerm,
                CoverageResult result, Set<Hint> hints) {
            add(result);
        }
    }

    /**
     * This method performs the actual calculation of the coverage for
     * HierarchyLevels. It calls getCoverage(testCases, statements) for every
     * StatementSequence it contains. It unifies the the list of results into a
     * single result and returns it.
     * 
     * @param testCases
     *            The list containing TestCases, whose combined coverage is
     *            calculated here.
     * @param level
     *            The HierarchyLevel which is the entry point into the AST.
     * @return the unifed {@link CoverageResult} encompassing all
     *         {@link StatementSequence}s and child {@link HierarchyLevel}s of
     *         this {@link HierarchyLevel}
     */
    private CoverageResult getUncachedCoverage(List<TestCase> testCases,
            HierarchyLevel level) {
        SumVisitor visitor = new SumVisitor();

        // We mustn't simply do a
        // accept(testCases, level, visitor);
        // because this would not cache the sublevels

        for (StatementSequence sequence : level.getSequences()) {
            accept(testCases, sequence, visitor);
        }

        // sum up coverage of all children
        for (HierarchyLevel child : level.getChildren()) {
            visitor.add(getCoverage(testCases, child));
        }

        visitor.add(getCoverageLocal(testCases, level));

        return visitor.createResult();
    }

    /**
     * This method checks first, if the given statement is a BasicStatement. Was
     * that the case, a coverage of zero is returned. If not, it calls itself
     * recursively. Also getCoverage(testCases, term) is called for every root
     * term the statement contains.
     * 
     * @param testCases
     *            The list containing TestCases, whose combined coverage is
     *            calculated here.
     * @param statement
     *            the {@link Statement} whose coverage is to be determined
     * @return the {@link CoverageResult} of the {@link Statement}
     */
    public CoverageResult getCoverage(List<TestCase> testCases,
            Statement statement) {
        SumVisitor visitor = new SumVisitor();

        accept(testCases, statement, visitor);

        return visitor.createResult();
    }

    /**
     * This method calls getCoverage(testCases, statement) for all the
     * Statements the StatementSequence contains. It unifies the the list of
     * results into a single result and returns it.
     * 
     * @param testCases
     *            The list containing TestCases, whose combined coverage is
     *            calculated here.
     * @param statements
     *            the statements, whose coverage is to be determined
     * @return the unified {@link CoverageResult} of all {@link Statement}s in
     *         the {@link StatementSequence}
     */
    public final CoverageResult getCoverage(List<TestCase> testCases,
            StatementSequence statements) {
        SumVisitor visitor = new SumVisitor();

        accept(testCases, statements, visitor);

        return visitor.createResult();
    }

    /**
     * This method always returns zero coverage.
     * 
     * @param testCases
     *            The list containing TestCases, whose combined coverage is
     *            calculated here.
     * @param term
     *            the {@link RootTerm}, whose coverage is to de determined
     * @return {@link CoverageResult#NULL}
     */
    public final CoverageResult getCoverage(List<TestCase> testCases,
            RootTerm term) {
        SumVisitor visitor = new SumVisitor();

        accept(testCases, term, visitor);

        return visitor.createResult();
    }

    /**
     * This method first checks, if the coverage of the given TestCases and the
     * given HierarchyLevel has been calculated and stored in the meta data
     * before. If that is the case, the stored results are returned. If not, the
     * protected method getUncachedCoverage(testCases, level) is called and its
     * results are stored in the meta data and then returned.
     * 
     * @param testCases
     *            The list containing TestCases, whose combined coverage is
     *            calculated here.
     * @param level
     *            The HierarchyLevel which is the entry point into the AST.
     * @return the unifed {@link CoverageResult} encompassing all
     *         {@link StatementSequence}s and child {@link HierarchyLevel}s of
     *         this {@link HierarchyLevel}
     */
    public final CoverageResult getCoverage(List<TestCase> testCases,
            HierarchyLevel level) {
        CoverageResult result = null;

        if (testCases.size() == 1) {
            // use cache only for single test cases
            Object cacheEntry = testCases.get(0).getMetaData(
                    this.cachingKeyName + CACHINGKEY_HIERARCHYLEVEL
                            + level.getId());

            if (cacheEntry == null) {
                // calculate coverage and store result in cache
                result = getUncachedCoverage(testCases, level);
                testCases.get(0).setMetaData(
                        this.cachingKeyName + CACHINGKEY_HIERARCHYLEVEL
                                + level.getId(), result);
            } else {
                // return coverage result fetched from cache
                result = (CoverageResult) cacheEntry;
            }
        } else {
            // no caching for lists of test cases
            result = getUncachedCoverage(testCases, level);
        }

        return result;
    }

    /**
     * This method calls getCoverage(testCases, statements) for every
     * StatementSequence it contains. It unifies the the list of results into a
     * single result and returns it.
     * 
     * @param testCases
     *            The list containing TestCases, whose combined coverage is
     *            calculated here.
     * @param branch
     *            the branch, whose coverage is to be determined
     * @return the {@link CoverageResult} of the {@link StatementSequence} of
     *         the {@link Branch}
     */
    public final CoverageResult getCoverage(List<TestCase> testCases,
            Branch branch) {
        SumVisitor visitor = new SumVisitor();

        accept(testCases, branch, visitor);

        return visitor.createResult();
    }

    /**
     * Contructor, used to assure, that a cachingKeyName is set.
     * 
     * @param cachingKeyName
     *            The key used for the storage of the results of coverage
     *            calculations on HierarchyLevels. It must be unique to ensure,
     *            that results are not stored incorrectly.
     */
    protected AbstractCoverageMetric(String cachingKeyName) {
        this.cachingKeyName = cachingKeyName;
    }

    public void accept(List<TestCase> testCases, HierarchyLevel level,
            Visitor post) {
        for (StatementSequence sequence : level.getSequences()) {
            accept(testCases, sequence, post);
        }

        // sum up coverage of all children
        for (HierarchyLevel child : level.getChildren()) {
            accept(testCases, child, post);
        }
        // delegate local coverage measurement to subclass
        CoverageResult result = getCoverageLocal(testCases, level);
        Set<Hint> hints = getHints(testCases, level);
        if (isCoverage(result, hints)) {
            //we have local coverage information, send it
            post.visit(level, result, hints);
        }
    }

    public void accept(List<TestCase> testCases, Branch branch, Visitor post) {
        accept(testCases, branch.getSequence(), post);
        // delegate local coverage measurement to subclass
        CoverageResult result = getCoverageLocal(testCases, branch);
        Set<Hint> hints = getHints(testCases, branch);
        if (isCoverage(result, hints)) {
            //we have local coverage information, send it
            post.visit(branch, result, hints);
        }
    }

    public void accept(List<TestCase> testCases, StatementSequence statements,
            Visitor post) {
        for (Statement statement : statements.getStatements()) {
            accept(testCases, statement, post);
        }
        // delegate local coverage measurement to subclass
        CoverageResult result = getCoverageLocal(testCases, statements);
        Set<Hint> hints = getHints(testCases, statements);
        if (isCoverage(result, hints)) {
            //we have local coverage information, send it
            post.visit(statements, result, hints);
        }
    }

    public void accept(List<TestCase> testCases, Statement statement,
            Visitor post) {
        for (RootTerm term : statement.getTerms()) {
            accept(testCases, term, post);
        }

        if (statement instanceof ConditionalStatement) {
            List<Branch> branches = ((ConditionalStatement) statement)
                    .getBranches();

            for (Branch branch : branches) {
                accept(testCases, branch, post);
            }

            // delegate local coverage measurement to subclass
            CoverageResult result = getCoverageLocal(testCases, statement);
            Set<Hint> hints = getHints(testCases, statement);
            if (isCoverage(result, hints)) {
                //we have local coverage information, send it
                post.visit((ConditionalStatement) statement, result, hints);
            }
        } else if (statement instanceof LoopingStatement) {
            accept(testCases, ((LoopingStatement) statement).getBody(), post);
            
            // delegate local coverage measurement to subclass
            CoverageResult result = getCoverageLocal(testCases, statement);
            Set<Hint> hints = getHints(testCases, statement);
            if (isCoverage(result, hints)) {
                //we have local coverage information, send it
                post.visit((LoopingStatement) statement, result, hints);
            }
        } else if (statement instanceof BasicStatement) {
            
            // delegate local coverage measurement to subclass
            CoverageResult result = getCoverageLocal(testCases, statement);
            Set<Hint> hints = getHints(testCases, statement);
            if (isCoverage(result, hints)) {
                //we have local coverage information, send it
                post.visit((BasicStatement) statement, result, hints);
            }
        } else {
            throw new RuntimeException();
        }
    }

    public void accept(List<TestCase> testCases, RootTerm term, Visitor post) {
        // StrictConditionCoverage has to override this in order to call
        // the visit(BooleanTerm, ...) methods.

        // delegate local coverage measurement to subclass
        CoverageResult result = getCoverageLocal(testCases, term);
        Set<Hint> hints = getHints(testCases, term);
        if (isCoverage(result, hints)) {
            //we have local coverage information, send it
            post.visit(term, result, hints);
        }
    }
    
    /**
     * @return true iff the hints and result contain coverage information
     */
    private static boolean isCoverage(final CoverageResult result,
            final Set<Hint> hints) {
        //check for WrappedCoverage in hints is omitted for performance reasons
        return result.getTotalItems() > 0 || ! hints.isEmpty();
    }

}
