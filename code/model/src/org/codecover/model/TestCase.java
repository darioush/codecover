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

package org.codecover.model;

import java.util.*;

import org.codecover.model.exceptions.NameAlreadyUsedException;
import org.codecover.model.mast.*;
import org.codecover.model.utils.*;

/**
 * TestCase represents a test case. It contains a name, a comment, the date when
 * the test case was created and the coverage results. The method delete() can
 * be used to delete the TestCase. The method getCoverageCount(...) returns the
 * coverage data for an AST element. The methods setObjectMetaData(...) and
 * getObjectMetaData(...) can set and get meta data for specific AST elements
 * for this test case. This meta data might be e.g. cached coverage metrics.
 * 
 * @author Markus Wittlinger, Tilmann Scheller
 * @version 1.0 ($Id$)
 */
public class TestCase extends AbstractMetaDataProvider {
    private final TestSession testSession;

    private String name;

    private final Date date;

    private String comment;

    private final Map<CoverableItem, Long> coverageData;

    private final Map<String, Map<Long, Object>> objectMetaData = new TreeMap<String, Map<Long, Object>>();

    private final Map<CoverableItem, BooleanAssignmentMap> assignments;

    private Map<RootTerm, Map<BooleanAssignment, Boolean>> cachedAssignments = new HashMap<RootTerm, Map<BooleanAssignment, Boolean>>();

    /**
     * Is true iff delete() has been called successfully.
     */
    private boolean deleted = false;

    /**
     * Is true iff delete() has been started. (You cannot modify the TestCase
     * then.)
     */
    private boolean deleting = false;

    private final Object lock = new Object();

    @Override
    protected final void assertNotDeleted() {
        if (this.deleted) {
            throw new IllegalStateException("This TestCase has been deleted.");
        }
    }

    private void assertNotDeleting() {
        assertNotDeleted();
        if (this.deleting) {
            throw new IllegalStateException("This TestCase is being deleted.");
        }
    }

    /**
     * Contructor
     * 
     * @param testSession
     *            the test session containing this instance
     * @param date
     *            the date this test session was created
     * @param coverageData
     *            the measured coverage associated with this test case
     * @param name
     *            the name of the test case
     * @param comment
     *            the comment associated with this test case
     */
    TestCase(TestSession testSession, Date date,
            Map<CoverableItem, Long> coverageData,
            Map<CoverableItem, BooleanAssignmentMap> assignments,
            final String name, String comment) {
        if (testSession == null) {
            throw new NullPointerException("testSession == null");
        }
        if (name == null) {
            throw new NullPointerException("name == null");
        }
        if (comment == null) {
            throw new NullPointerException("comment == null");
        }
        if (date == null) {
            throw new NullPointerException("date == null");
        }
        if (coverageData == null) {
            throw new NullPointerException("coverageData == null");
        }
        if (assignments == null) {
            throw new NullPointerException("assignments == null");
        }

        this.date = (Date) date.clone();
        this.testSession = testSession;
        this.name = name;
        this.comment = comment;
        this.coverageData = CollectionUtil.copy(coverageData);
        this.assignments = CollectionUtil.copy(assignments);

        if (!getTestSession().getTestSessionContainer().getCoverableItems()
                .containsAll(coverageData.keySet())) {
            final Set<CoverableItem> items = getTestSession()
                    .getTestSessionContainer().getCoverableItems();
            for (CoverableItem item : coverageData.keySet()) {
                if (!items.contains(item)) {
                    throw new IllegalArgumentException("CoverableItem " + item
                            + " is in coverageData but not in the MAST");
                }
            }
            // Should never be reached
            throw new RuntimeException();
        }

        // Check whether all assignments have the correct length
        final Map<CoverableItem, RootTerm> rootTerms = getTestSession()
                .getTestSessionContainer().getRootTerms();
        for (Map.Entry<CoverableItem, BooleanAssignmentMap> entry : this.assignments
                .entrySet()) {

            if (!rootTerms.containsKey(entry.getKey())) {
                throw new IllegalArgumentException(
                        "assignments contains CoverableItem '"
                                + entry.getKey()
                                + "' not contained in this TestSessionContainer");
            } else {
                RootTerm rootTerm = rootTerms.get(entry.getKey());
                if (rootTerm.getTerm().getBasicBooleanTerms() != entry
                        .getValue().getLength()) {
                    throw new IllegalArgumentException(
                            "rootTerm.getTerm().getBasicBooleanTerms() ("
                                    + rootTerm.getTerm().getBasicBooleanTerms()
                                    + ") != entry.getValue().getLength() ("
                                    + entry.getValue().getLength()
                                    + ") for the RootTerm with the Prefix '"
                                    + rootTerm.getCoverableItem().getPrefix()
                                    + "' and the ID '"
                                    + rootTerm.getCoverableItem().getId()
                                    + "' in the test case '" + name
                                    + "' in the test session '"
                                    + getTestSession().getName() + "'");
                }
            }

        }
    }

    /**
     * Returns the measured coverage of the given coverable item during the run
     * of this testcase.
     * 
     * @param item
     *            the coverable item, whose coverage is sought.
     * @return the number of times the given coverable item was covered.
     */
    public long getCoverageCount(CoverableItem item) {
        if (item == null) {
            throw new NullPointerException("item == null");
        }

        //assertNotDeleted();

        final Long result = this.coverageData.get(item);
        if (result == null) {
            return 0;
        } else {
            return result;
        }
    }

    /**
     * Associates a given object as metadata with a given {@link MetaDataObject}
     * under a given key
     * 
     * @param name
     *            the key used in storing the object
     * @param metaDataObject
     *            the {@link MetaDataObject} the metadata is associated with
     * @param value
     *            the object to be stored as metadata
     */
    public void setObjectMetaData(String name, MetaDataObject metaDataObject,
            Object value) {
        // We still allow setting metadata while deleting
        assertNotDeleted();

        if (name == null) {
            throw new NullPointerException("name == null");
        }

        if (metaDataObject == null) {
            throw new NullPointerException("metaDataObject == null");
        }

        final long id = Internal.getMetaDataId(metaDataObject.getMetaData());

        synchronized (this.objectMetaData) {
            Map<Long, Object> map = this.objectMetaData.get(name);
            if (map == null) {
                map = new TreeMap<Long, Object>();
                this.objectMetaData.put(name, map);
            }
            map.put(id, value);
        }
    }

    /**
     * Associates a given object as metadata with a given {@link MetaDataObject}
     * under a given key
     * 
     * @param name
     *            the key used to retrieve the object
     * @param metaDataObject
     *            the {@link MetaDataObject} the metadata is associated with
     * @return the retrieved object or <code>null</code>, if no object was
     *         associated with the given {@link MetaDataObject} under the given
     *         key.
     */
    public Object getObjectMetaData(String name, MetaDataObject metaDataObject) {
        //assertNotDeleted();

        if (name == null) {
            throw new NullPointerException("name == null");
        }

        if (metaDataObject == null) {
            throw new NullPointerException("metaDataObject == null");
        }

        final long id = Internal.getMetaDataId(metaDataObject.getMetaData());

        synchronized (this.objectMetaData) {
            Map<Long, Object> map = this.objectMetaData.get(name);
            if (map == null) {
                return null;
            }
            return map.get(id);
        }
    }

    /**
     * Delete this {@link TestCase} from its enclosing {@link TestSession}. Any
     * further method call to this TestCase will cause a
     * {@link IllegalStateException} to be thrown. Any attempt to modify the
     * content of this TestCase during the removal (e.g. in listeners) will also
     * cause a {@link IllegalStateException}.
     */
    public void delete() {
        synchronized (this.lock) {
            assertNotDeleting();

            this.deleting = true;

            synchronized (getTestSession().lock) {
                this.testSession.removeTestCase(this);
            }
        }

        // the test case was removed, send change event
        notifyChangeListener(ChangeType.REMOVE);
        this.testSession.notifyChangeListener(ChangeType.CHANGE);

        synchronized (this.lock) {
            this.deleted = true;
        }
    }

    /**
     * @return the comment
     */
    public String getComment() {
        //assertNotDeleted();

        synchronized (this.lock) {
            return this.comment;
        }
    }

    /**
     * @param comment
     *            the comment to set
     */
    public void setComment(String comment) {
        if (comment == null) {
            throw new NullPointerException("comment == null");
        }
        
        assertNotDeleting();

        synchronized (this.lock) {
            this.comment = comment;
        }
        notifyChangeListener(ChangeType.CHANGE);
    }

    /**
     * @return the date
     */
    public Date getDate() {
        //assertNotDeleted();

        return (Date) this.date.clone();
    }

    /**
     * @return the name
     */
    public String getName() {
        //assertNotDeleted();

        synchronized (this.lock) {
            return this.name;
        }
    }

    /**
     * @param name
     *            the name to set
     * @throws NameAlreadyUsedException
     *             thrown, if a test case with the given name already exisits
     */
    public void setName(String name) throws NameAlreadyUsedException {
        if (name == null) {
            throw new NullPointerException("name == null");
        }

        synchronized (this.lock) {
            assertNotDeleting();

            synchronized (getTestSession().lock) {
                if (getTestSession().getTestCaseWithName(name) != null) {
                    throw new NameAlreadyUsedException(
                            "A test case with the name \"" + name
                                    + "\" already exists");
                }

                this.name = name;
            }
        }
        notifyChangeListener(ChangeType.CHANGE);
    }

    /**
     * @return the test session
     */
    public TestSession getTestSession() {
        //assertNotDeleted();

        return this.testSession;
    }

    void notifyChangeListener(ChangeType type) {
        getTestSession().getTestSessionContainer().testCaseEvent.emitChanged(
                type, this);
    }

    /**
     * Returns the assignments of the given term which occured during the run of
     * this testcase and the number of times, they were found.
     * 
     * @param term
     *            the RootTerm of whom to get the assignments
     * @return a Map containing all the assignments as well as the number of
     *         execution
     */
    public BooleanAssignmentMap getAssignmentsCount(RootTerm term) {
        //assertNotDeleted();

        if (term == null) {
            throw new NullPointerException("term == null");
        }

        final BooleanAssignmentMap result = this.assignments.get(term
                .getCoverableItem());
        if (result == null) {
            return BooleanAssignmentMap.createEmptyMap(term.getTerm()
                    .getBasicBooleanTerms());
        } else {
            // This check should not be needed, but it is fast (O(1)) and
            // might safe us from a lot of trouble
            if (term.getTerm().getBasicBooleanTerms() != result.getLength()) {
                throw new RuntimeException("Inconsistent data. This is *bad*.");
            }
            return result;
        }
    }

    /**
     * Returns the assignments of the given term which occured during the run of
     * this testcase and the result of the expression under each assignment.
     * 
     * @param term
     *            the RootTerm of whom to get the assignments
     * @return a Map containing all the assignments as well as the resulting
     *         boolean value
     */
    public Map<BooleanAssignment, Boolean> getAssignments(RootTerm term) {
        if (term == null) {
            throw new NullPointerException("term == null");
        }

        //assertNotDeleted();

        Map<BooleanAssignment, Boolean> result;
        synchronized (this.cachedAssignments) {
            result = this.cachedAssignments.get(term);
        }

        if (result == null) {
            result = new HashMap<BooleanAssignment, Boolean>();
            for (BooleanAssignment booleanAssignment : getAssignmentsCount(term)
                    .getData().keySet()) {
                Boolean assignmentResult = term
                        .getAssignmentResult(booleanAssignment);

                if (assignmentResult == null) {
                    // This should not happen. This means that there is an
                    // assignment in the map which cannot occour (according
                    // to the static data.)
                    // TODO: Check this on the TestCase construction
                    throw new RuntimeException("Illegal assignment in map");
                }

                result.put(booleanAssignment, assignmentResult);
            }
            result = Collections.unmodifiableMap(result);
            synchronized (this.cachedAssignments) {
                // Note: Two threads might compute the results for the same
                // term seperatly, but we don't care (the second put will
                // override the first result)
                this.cachedAssignments.put(term, result);
            }
        }

        return result;
    }

    Map<String, Map<Long, Object>> getObjectMetaDataMapEntries() {
        //assertNotDeleted();

        synchronized (this.objectMetaData) {
            // We have to return a copy due to threading issues
            return CollectionUtil.copy(this.objectMetaData);
        }
    }

    /**
     * Gets the map of {@link CoverableItem}s and {@link Long}s
     * 
     * @return the map holding the {@link CoverableItem}s with their number of
     *         occurences
     */
    public Map<CoverableItem, Long> getCoverageData() {
        //assertNotDeleted();

        return this.coverageData;
    }

    /**
     * Gets the map of {@link CoverableItem}s and {@link BooleanAssignmentMap}s
     * 
     * @return the map holding the {@link BooleanAssignment}s per
     *         {@link CoverableItem} with their number of occurences
     */
    public Map<CoverableItem, BooleanAssignmentMap> getAssignmentsMap() {
        //assertNotDeleted();

        return this.assignments;
    }
}
