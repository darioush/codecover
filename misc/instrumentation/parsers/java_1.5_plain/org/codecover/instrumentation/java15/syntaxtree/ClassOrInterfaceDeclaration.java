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

//
// Generated by JTB 1.3.2
//

package org.codecover.instrumentation.java15.syntaxtree;

/**
 * Grammar production:
 * <PRE>
 * f0 -> ( "class" | "interface" )
 * f1 -> &lt;IDENTIFIER&gt;
 * f2 -> [ TypeParameters() ]
 * f3 -> [ ExtendsList() ]
 * f4 -> [ ImplementsList() ]
 * f5 -> ClassOrInterfaceBody()
 * </PRE>
 */
@SuppressWarnings("all")
public class ClassOrInterfaceDeclaration implements Node {
   private Node parent;
   public NodeChoice f0;
   public NodeToken f1;
   public NodeOptional f2;
   public NodeOptional f3;
   public NodeOptional f4;
   public ClassOrInterfaceBody f5;

   public ClassOrInterfaceDeclaration(NodeChoice n0, NodeToken n1, NodeOptional n2, NodeOptional n3, NodeOptional n4, ClassOrInterfaceBody n5) {
      f0 = n0;
      if ( f0 != null ) f0.setParent(this);
      f1 = n1;
      if ( f1 != null ) f1.setParent(this);
      f2 = n2;
      if ( f2 != null ) f2.setParent(this);
      f3 = n3;
      if ( f3 != null ) f3.setParent(this);
      f4 = n4;
      if ( f4 != null ) f4.setParent(this);
      f5 = n5;
      if ( f5 != null ) f5.setParent(this);
   }

   public void accept(org.codecover.instrumentation.java15.visitor.Visitor v) {
      v.visit(this);
   }
   public void accept(org.codecover.instrumentation.java15.visitor.VisitorWithException v) throws java.io.IOException {
      v.visit(this);
   }
   public <R,A> R accept(org.codecover.instrumentation.java15.visitor.GJVisitor<R,A> v, A argu) {
      return v.visit(this,argu);
   }
   public <R> R accept(org.codecover.instrumentation.java15.visitor.GJNoArguVisitor<R> v) {
      return v.visit(this);
   }
   public <A> void accept(org.codecover.instrumentation.java15.visitor.GJVoidVisitor<A> v, A argu) {
      v.visit(this,argu);
   }
   public void setParent(Node n) { parent = n; }
   public Node getParent()       { return parent; }
   /** for debugging purposes */
   @Override
   public String toString() {
      return org.codecover.instrumentation.java15.visitor.TreeSourceFileImageDumper.convertToString(this);
   }
}
