/*
 * Copyright (c) 2003, the JUNG Project and the Regents of the University 
 * of California
 * All rights reserved.
 *
 * This software is open-source under the BSD license; see either
 * "license.txt" or
 * http://jung.sourceforge.net/license.txt for a description.
 */
/*
 * Created on Dec 4, 2003
 */
package org.codecover.eclipse.views;

/**
 * 
 * @author Negar and James
 */

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.map.LazyMap;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.Graph;



/**
 * Positions vertices equally spaced on a regular circle.
 * Does not respect filter calls.
 *
 * @author Masanori Harada
 */
public class CoverageGraphLayout<V,E> extends AbstractLayout<V,E> {

	org.eclipse.swt.graphics.Point size;
	public CoverageGraphLayout(Graph<V,E> g,org.eclipse.swt.graphics.Point size) {
		super(g);
		this.size = size;
		this.setSize(new Dimension(size.x,size.y));
	}
	public void orderVertices(V[] vertices) {
		List<V> list = Arrays.asList(vertices);
		
		final Comparator<V> myComparator = new Comparator<V>() {
			public int compare(V n1, V n2){			
				return ((CoverageGraphNode)n1).getLable().compareTo(((CoverageGraphNode)n2).getLable());
			}
		};
	
		Collections.sort(list, myComparator);
	}
	
	public void reset() {
		initialize();
	}

	@SuppressWarnings("unchecked")
	public void initialize() {
		Graph<V,E> graph = getGraph();
		Dimension d = getSize();
		if(graph != null && d != null) {
			V[] vertices =
				(V[])graph.getVertices().toArray();
			orderVertices(vertices);

			double height = d.getHeight();
			double width = d.getWidth();
			
			double xTS = 500;
			double xSUT = 1000;
			
			double yTS = 200;
			double ySUT = 200;

			int NumOfSUTNodes = 0;
			int NumOfTestNodes = 0;
			int GraphHeight = 0;
			for (int i = 0; i < vertices.length; i++) {
				if( ((CoverageGraphNode)vertices[i]).type == "SUT" )
					NumOfSUTNodes ++;
				else
					NumOfTestNodes++;
			}
			if (NumOfSUTNodes > NumOfTestNodes)
				GraphHeight = NumOfSUTNodes * 30;
			else
				GraphHeight = NumOfTestNodes * 30;
			
			
			
			for (int i = 0; i < vertices.length; i++) {
				//Point2D coord = transform(vertices[i]);
				
				if( ((CoverageGraphNode)vertices[i]).type == "SUT" ){
					this.setLocation(vertices[i],xSUT,ySUT);
					ySUT += GraphHeight / NumOfSUTNodes;
				}
				else{
					this.setLocation(vertices[i],xTS,yTS);
					yTS += GraphHeight / NumOfTestNodes;
				}
			}
		}
	}
}
