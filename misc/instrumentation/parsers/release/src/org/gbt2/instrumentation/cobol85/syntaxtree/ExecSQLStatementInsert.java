﻿///////////////////////////////////////////////////////////////////////////////
//
// $Id$
// 
///////////////////////////////////////////////////////////////////////////////

//
// Generated by JTB 1.3.2
//

package org.gbt2.instrumentation.cobol85.syntaxtree;

/**
 * Grammar production:
 * <PRE>
 * f0 -> &lt;INSERT&gt;
 * f1 -> &lt;INTO&gt;
 * f2 -> CobolWord()
 * f3 -> ( &lt;LPARENCHAR&gt; ( SQLIdentifier() )+ &lt;RPARENCHAR&gt; )
 * f4 -> &lt;VALUES&gt;
 * f5 -> ( &lt;LPARENCHAR&gt; ( SQLIdentifier() )+ &lt;RPARENCHAR&gt; )
 * </PRE>
 */
public class ExecSQLStatementInsert implements Node {
   private Node parent;
   public NodeToken f0;
   public NodeToken f1;
   public CobolWord f2;
   public NodeSequence f3;
   public NodeToken f4;
   public NodeSequence f5;

   public ExecSQLStatementInsert(NodeToken n0, NodeToken n1, CobolWord n2, NodeSequence n3, NodeToken n4, NodeSequence n5) {
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

   public ExecSQLStatementInsert(CobolWord n0, NodeSequence n1, NodeSequence n2) {
      f0 = new NodeToken("insert");
      if ( f0 != null ) f0.setParent(this);
      f1 = new NodeToken("into");
      if ( f1 != null ) f1.setParent(this);
      f2 = n0;
      if ( f2 != null ) f2.setParent(this);
      f3 = n1;
      if ( f3 != null ) f3.setParent(this);
      f4 = new NodeToken("values");
      if ( f4 != null ) f4.setParent(this);
      f5 = n2;
      if ( f5 != null ) f5.setParent(this);
   }

   public void accept(org.gbt2.instrumentation.cobol85.visitor.Visitor v) {
      v.visit(this);
   }
   public <R,A> R accept(org.gbt2.instrumentation.cobol85.visitor.GJVisitor<R,A> v, A argu) {
      return v.visit(this,argu);
   }
   public <R> R accept(org.gbt2.instrumentation.cobol85.visitor.GJNoArguVisitor<R> v) {
      return v.visit(this);
   }
   public <A> void accept(org.gbt2.instrumentation.cobol85.visitor.GJVoidVisitor<A> v, A argu) {
      v.visit(this,argu);
   }
   public void setParent(Node n) { parent = n; }
   public Node getParent()       { return parent; }
}
