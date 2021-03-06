﻿///////////////////////////////////////////////////////////////////////////////
//
// $Id$
// 
// created by: Christoph Müller
// created at: 24.02.2007 17:30:00
//
///////////////////////////////////////////////////////////////////////////////

//
// Generated by JTB 1.3.2
//

package org.gbt2.instrumentation.java15.syntaxtree;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import org.gbt2.instrumentation.java15.visitor.GJNoArguVisitor;
import org.gbt2.instrumentation.java15.visitor.GJVisitor;
import org.gbt2.instrumentation.java15.visitor.GJVoidVisitor;
import org.gbt2.instrumentation.java15.visitor.Visitor;
import org.gbt2.instrumentation.java15.visitor.VisitorWithException;

/**
 * Represents a single token in the grammar. If the "-tk" option is used, also
 * contains a Vector of preceding special tokens.
 */
public class NodeToken implements Node {
    public NodeToken(String s) {
        this(s, -1, -1, -1, -1, -1);
    }

    public NodeToken(String s, int kind, int beginLine, int beginColumn,
            int endLine, int endColumn) {
        tokenImage = s;
        specialTokens = null;
        this.kind = kind;
        this.beginLine = beginLine;
        this.beginColumn = beginColumn;
        this.endLine = endLine;
        this.endColumn = endColumn;
    }

    public NodeToken getSpecialAt(int i) {
        if (specialTokens == null)
            throw new java.util.NoSuchElementException("No specials in token");
        return specialTokens.elementAt(i);
    }

    public int numSpecials() {
        if (specialTokens == null)
            return 0;
        return specialTokens.size();
    }

    public void addSpecial(NodeToken s) {
        if (specialTokens == null)
            specialTokens = new Vector<NodeToken>();
        specialTokens.addElement(s);
        s.setParent(this);
    }

    public void trimSpecials() {
        if (specialTokens == null)
            return;
        specialTokens.trimToSize();
    }

    public String toString() {
        return tokenImage;
    }

    public String withSpecials() {
        if (specialTokens == null)
            return tokenImage;

        StringBuffer buf = new StringBuffer();

        for (Enumeration<NodeToken> e = specialTokens.elements(); e
                .hasMoreElements();)
            buf.append(e.nextElement().toString());

        buf.append(tokenImage);
        return buf.toString();
    }

    public void accept(VisitorWithException v) throws IOException {
        v.visit(this);
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public <R, A> R accept(GJVisitor<R, A> v, A argu) {
        return v.visit(this, argu);
    }

    public <R> R accept(GJNoArguVisitor<R> v) {
        return v.visit(this);
    }

    public <A> void accept(GJVoidVisitor<A> v, A argu) {
        v.visit(this, argu);
    }

    public void setParent(Node n) {
        parent = n;
    }

    public Node getParent() {
        return parent;
    }

    private Node parent;

    public String tokenImage;

    // Stores a list of NodeTokens
    public Vector<NodeToken> specialTokens;

    // -1 for these ints means no position info is available.
    public int beginLine, beginColumn, endLine, endColumn;

    // Equal to the JavaCC token "kind" integer.
    // -1 if not available.
    public int kind;
}
