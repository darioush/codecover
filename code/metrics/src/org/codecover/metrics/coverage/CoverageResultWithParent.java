package org.codecover.metrics.coverage;

/**
 * This class is a mutable child class of {@link CoverageResult}, that allows a hierachy of
 * {@link CoverageResult}s.
 * <p>
 * The effect is, that each {@link CoverageResultWithParent} can aggregate coverage using
 * {@link #increment(int, int)} and delegates these calls to the parent too.
 *
 * @author Christoph Müller
 *
 * @version 1.0 ($Id: CoverageResult.java 1 2007-12-12 17:37:26Z t-scheller $)
 */
public class CoverageResultWithParent extends CoverageResult {

    private final CoverageResultWithParent parent;

    public CoverageResultWithParent(CoverageResultWithParent parent) {
        this.parent = parent;
    }

    public CoverageResultWithParent getParent() {
        return this.parent;
    }

    public void increment(CoverageResult coverageResult) {
        if (!coverageResult.isNull()) {
            increment(coverageResult.coveredItems, coverageResult.totalItems);
        }
    }

    public void increment(int coveredItems, int totalItems) {
        if (totalItems < coveredItems || totalItems < 0 || coveredItems < 0) {
            throw new IllegalArgumentException(coveredItems + " | " + totalItems);
        }
        if (totalItems == 0) {
            // nothing to do
            return;
        }

        super.coveredItems += coveredItems;
        super.totalItems += totalItems;
        if (this.parent != null) {
            this.parent.increment(coveredItems, totalItems);
        }
    }
}
