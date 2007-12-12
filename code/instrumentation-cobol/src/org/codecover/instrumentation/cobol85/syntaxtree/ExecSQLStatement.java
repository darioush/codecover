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

package org.codecover.instrumentation.cobol85.syntaxtree;

/**
 * Grammar production:
 * <PRE>
 * f0 -> &lt;EXEC&gt;
 * f1 -> &lt;SQL&gt;
 * f2 -> ( ExecSQLStatementDeclare() | ExecSQLStatementUpdate() | ExecSQLStatementInsert() | ExecSQLStatementInclude() | ExecSQLStatementSelect() | ExecSQLStatementDelete() | ExecSQLStatementSet() | ExecSQLStatementLock() | ExecSQLStatementBegin() | ExecSQLStatementEnd() | ExecSQLStatementCommit() | ExecSQLStatementWhenever() | ( &lt;CLOSE&gt; SQLIdentifier() ) | ( &lt;OPEN&gt; SQLIdentifier() ) | ( &lt;FETCH&gt; CobolWord() &lt;INTO&gt; ( SQLParameter() [ &lt;COMMACHAR&gt; ] )+ ) | ( &lt;WHENEVER&gt; ( &lt;SQLERROR&gt; | &lt;SQLWARNING&gt; | ( &lt;NOT&gt; &lt;FOUND&gt; ) ) &lt;CONTINUE&gt; ) )
 * f3 -> &lt;END_EXEC&gt;
 * </PRE>
 */
public class ExecSQLStatement implements Node {
   private Node parent;
   public NodeToken f0;
   public NodeToken f1;
   public NodeChoice f2;
   public NodeToken f3;

   public ExecSQLStatement(NodeToken n0, NodeToken n1, NodeChoice n2, NodeToken n3) {
      f0 = n0;
      if ( f0 != null ) f0.setParent(this);
      f1 = n1;
      if ( f1 != null ) f1.setParent(this);
      f2 = n2;
      if ( f2 != null ) f2.setParent(this);
      f3 = n3;
      if ( f3 != null ) f3.setParent(this);
   }

   public ExecSQLStatement(NodeChoice n0) {
      f0 = new NodeToken("exec");
      if ( f0 != null ) f0.setParent(this);
      f1 = new NodeToken("sql");
      if ( f1 != null ) f1.setParent(this);
      f2 = n0;
      if ( f2 != null ) f2.setParent(this);
      f3 = new NodeToken("end-exec");
      if ( f3 != null ) f3.setParent(this);
   }

   public void accept(org.codecover.instrumentation.cobol85.visitor.Visitor v) {
      v.visit(this);
   }
   public <R,A> R accept(org.codecover.instrumentation.cobol85.visitor.GJVisitor<R,A> v, A argu) {
      return v.visit(this,argu);
   }
   public <R> R accept(org.codecover.instrumentation.cobol85.visitor.GJNoArguVisitor<R> v) {
      return v.visit(this);
   }
   public <A> void accept(org.codecover.instrumentation.cobol85.visitor.GJVoidVisitor<A> v, A argu) {
      v.visit(this,argu);
   }
   public void setParent(Node n) { parent = n; }
   public Node getParent()       { return parent; }
}
