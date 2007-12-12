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

package org.codecover.eclipse.utils;

import org.codecover.model.mast.HierarchyLevel;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

/**
 * @author Markus Wittlinger
 * @version 1.0 ($Id$)
 */
public class CodeCoverSorter extends ViewerSorter {

    /**
     * The key to be used in storing the comparator in the column
     */
    public static final String COMPARATOR_KEY = "org.codecover.eclipse.utils.InvertableComparator"; //$NON-NLS-1$

    // Simple data structure for grouping
    // sort information by column.
    private final class SortInfo {
        TreeColumn column;
        int index;
    }

    private TreeViewer viewer;

    private SortInfo[] infos;

    /**
     * Constructor
     * 
     * @param viewer
     * @param columns   the columns in the order they are displayed (must have
     *                  the correct indices)
     */
    public CodeCoverSorter(TreeViewer viewer, TreeColumn[] columns) {
        this.viewer = viewer;
        this.infos = new SortInfo[columns.length];
        for (int i = 0; i < columns.length; i++) {
            this.infos[i] = new SortInfo();
            this.infos[i].column = columns[i];
            this.infos[i].index = i;
            createSelectionListener(columns[i], this.infos[i]);
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Viewer viewer, Object o1, Object o2) {
        Object d1 = o1;
        Object d2 = o2;
        for (int i = 0; i < this.infos.length; i++) {
            TreeColumn column = this.infos[i].column;
            int index = this.infos[i].index;

            // If we have HierarchyLevels, we get the cached coverage from the
            // column, everything else we get, we put through to the comparator
            if (o1 instanceof HierarchyLevel && o2 instanceof HierarchyLevel) {
                d1 = column.getData(((HierarchyLevel) o1).getId());
                d2 = column.getData(((HierarchyLevel) o2).getId());

                if (viewer instanceof ContentViewer) {
                    IBaseLabelProvider baseLabelProvider = ((ContentViewer) viewer)
                            .getLabelProvider();
                    if (baseLabelProvider instanceof ITableLabelProvider) {
                        ITableLabelProvider labelProvider = (ITableLabelProvider) baseLabelProvider;
                        if (d1 == null) {
                            labelProvider.getColumnText(o1, index);
                            d1 = column.getData(((HierarchyLevel) o1).getId());
                        }
                        if (d2 == null) {
                            labelProvider.getColumnText(o2, index);
                            d2 = column.getData(((HierarchyLevel) o2).getId());
                        }
                    }
                }
            }

            InvertableComparator<? super Object> comparator = getInvertableComparator(this.infos[i]);
            int result = comparator.compare(d1, d2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
    
    private void createSelectionListener(final TreeColumn column,
            final SortInfo info) {
        column.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                sortUsing(info);
            }
        });
    }

    private void sortUsing(SortInfo info) {
        Object[] expandedElements;
        if (info == this.infos[0]) {
            setInvertableComparator(info, getInvertableComparator(info)
                    .getInverseSorter());
        } else {
            for (int i = 0; i < this.infos.length; i++) {
                if (info == this.infos[i]) {
                    System.arraycopy(this.infos, 0, this.infos, 1, i);
                    this.infos[0] = info;
                    setInvertableComparator(info, getInvertableComparator(info)
                            .getDefaultSorter());
                    break;
                }
            }
        }

        this.viewer.getTree().setSortColumn(info.column);
        this.viewer.getTree().setSortDirection(
                getInvertableComparator(info).getSortDirection());

        expandedElements = this.viewer.getExpandedElements();
        this.viewer.refresh();
        this.viewer.setExpandedElements(expandedElements);
    }

    @SuppressWarnings("unchecked")
    private InvertableComparator<? super Object> getInvertableComparator(
            SortInfo info) {
        return (InvertableComparator<? super Object>) info.column
                .getData(COMPARATOR_KEY);
    }

    private void setInvertableComparator(SortInfo info,
            InvertableComparator<? super Object> invertableComparator) {
        info.column.setData(COMPARATOR_KEY, invertableComparator);
    }
}
