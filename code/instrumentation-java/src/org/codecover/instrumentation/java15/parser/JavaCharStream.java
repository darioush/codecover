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

package org.codecover.instrumentation.java15.parser;

import java.io.IOException;

/**
 * An implementation of interface CharStream, where the stream is assumed to
 * contain only ASCII characters (with java-like unicode escape processing).
 * 
 * @author Generated By: JavaCC: JavaCharStream.java, Version 4.0
 * @author Christoph Müller
 * 
 * @version 1.2 ($Id$) <br>
 *          modified by Christoph Müller:<br>
 *          <ul>
 *          <li>add offset handling</li>
 *          <li>delete column and line handling</li>
 *          <li>set String as source</li>
 *          </ul>
 * 
 */
public class JavaCharStream implements CharStream {
    private static final int BUFFER_SIZE = 2048;

    private static final int DOUBLE_BUFFER_SIZE = BUFFER_SIZE * 2;

    static final int hexval(char c) throws IOException {
        switch (c) {
        case '0':
            return 0;
        case '1':
            return 1;
        case '2':
            return 2;
        case '3':
            return 3;
        case '4':
            return 4;
        case '5':
            return 5;
        case '6':
            return 6;
        case '7':
            return 7;
        case '8':
            return 8;
        case '9':
            return 9;

        case 'a':
        case 'A':
            return 10;
        case 'b':
        case 'B':
            return 11;
        case 'c':
        case 'C':
            return 12;
        case 'd':
        case 'D':
            return 13;
        case 'e':
        case 'E':
            return 14;
        case 'f':
        case 'F':
            return 15;
        }

        throw new IOException(); // Should never come here
    }

    private int bufpos = -1;

    private int bufsize;

    private int available;

    private int tokenBegin;

    private int bufLine[];
    private int bufColumn[];
    private int bufOffset[];

    private int line = 1;
    private int column = 0;
    private int offset = 0;
    
    private boolean prevCharIsCR = false;
    private boolean prevCharIsLF = false;

    private char[] buffer;

    private int inBuf = 0;

    private String sourceFileContent;

    private int contentLength;

    private int contentPosition;
    
    private int tabSize = 4;

    /**
     * @param i the tabsize in columns
     */
    protected void setTabSize(int i) {
        this.tabSize = i;
    }

    /**
     * @return the tabsize in columns
     */
    protected int getTabSize() {
        return this.tabSize;
    }

    private void expandBuff(boolean wrapAround) {
        char[] newbuffer = new char[this.bufsize + BUFFER_SIZE - 1];
        int newbufLine[] = new int[this.bufsize + BUFFER_SIZE];
        int newbufColumn[] = new int[this.bufsize + BUFFER_SIZE];
        int newbufOffset[] = new int[this.bufsize + BUFFER_SIZE];

        try {
            if (wrapAround) {
                System.arraycopy(this.buffer, this.tokenBegin, newbuffer, 0, this.bufsize - 1 - this.tokenBegin);
                System.arraycopy(this.buffer, 0, newbuffer, this.bufsize - 1 - this.tokenBegin, this.bufpos);
                this.buffer = newbuffer;

                System.arraycopy(this.bufLine, this.tokenBegin, newbufLine, 0, this.bufsize - this.tokenBegin);
                System.arraycopy(this.bufLine, 0, newbufLine, this.bufsize - this.tokenBegin, this.bufpos);
                this.bufLine = newbufLine;

                System.arraycopy(this.bufColumn, this.tokenBegin, newbufColumn, 0, this.bufsize - this.tokenBegin);
                System.arraycopy(this.bufColumn, 0, newbufColumn, this.bufsize - this.tokenBegin, this.bufpos);
                this.bufColumn = newbufColumn;

                System.arraycopy(this.bufOffset, this.tokenBegin, newbufOffset, 0, this.bufsize - this.tokenBegin);
                System.arraycopy(this.bufOffset, 0, newbufOffset, this.bufsize - this.tokenBegin, this.bufpos);
                this.bufOffset = newbufOffset;

                this.bufpos += (this.bufsize - this.tokenBegin);
            } else {
                System.arraycopy(this.buffer, this.tokenBegin, newbuffer, 0, this.bufsize - this.tokenBegin - 1);
                this.buffer = newbuffer;

                System.arraycopy(this.bufLine, this.tokenBegin, newbufLine, 0, this.bufsize - this.tokenBegin);
                this.bufLine = newbufLine;

                System.arraycopy(this.bufColumn, this.tokenBegin, newbufColumn, 0, this.bufsize - this.tokenBegin);
                this.bufColumn = newbufColumn;

                System.arraycopy(this.bufOffset, this.tokenBegin, newbufOffset, 0, this.bufsize - this.tokenBegin);
                this.bufOffset = newbufOffset;

                this.bufpos -= this.tokenBegin;
            }
        } catch (Throwable t) {
            throw new Error(t.getMessage());
        }

        this.available = (this.bufsize += BUFFER_SIZE) - 1;
        this.tokenBegin = 0;
    }

    private char readRawChar() throws IOException {
        if (++this.contentPosition >= this.contentLength) {
            this.contentPosition--;

            if (this.bufpos != 0) {
                --this.bufpos;
                backup(0);
            } else {
                this.bufLine[this.bufpos] = this.line;
                this.bufColumn[this.bufpos] = this.column;
                // this is kind a messy trick ->
                // to allow getEndOffset() to read a correct offset at the next index   
                this.bufOffset[this.bufpos] = this.offset;
                this.bufOffset[this.bufpos + 1] = this.offset;
            }
            throw new IOException();
        }

        return this.sourceFileContent.charAt(this.contentPosition);
    }

    public char beginToken() throws IOException {
        if (this.inBuf > 0) {
            --this.inBuf;

            if (++this.bufpos == this.bufsize - 1) {
                this.bufpos = 0;
            }

            this.tokenBegin = this.bufpos;
            return this.buffer[this.bufpos];
        }

        this.tokenBegin = 0;
        this.bufpos = -1;

        return readChar();
    }

    private void adjustBuffSize() {
        if (this.available == this.bufsize - 1) {
            if (this.tokenBegin > BUFFER_SIZE) {
                this.bufpos = 0;
                this.available = this.tokenBegin - 1;
            } else {
                expandBuff(false);
            }
        } else if (this.available > this.tokenBegin) {
            this.available = this.bufsize - 1;
        } else if ((this.tokenBegin - this.available) < BUFFER_SIZE) {
            expandBuff(true);
        } else {
            this.available = this.tokenBegin - 1;
        }
    }

    private void updateLineColumnOffset(char c) {
        this.column++;

        if (this.prevCharIsLF)
        {
            this.prevCharIsLF = false;
            this.line += (this.column = 1);
        }
        else if (this.prevCharIsCR)
        {
            this.prevCharIsCR = false;
           if (c == '\n')
           {
               this.prevCharIsLF = true;
           }
           else
               this.line += (this.column = 1);
        }

        switch (c)
        {
           case '\r' :
               this.prevCharIsCR = true;
              break;
           case '\n' :
               this.prevCharIsLF = true;
              break;
           case '\t' :
               this.column--;
               this.column += (this.tabSize - (this.column % this.tabSize));
              break;
           default :
              break;
        }

        this.bufLine[this.bufpos] = this.line;
        this.bufColumn[this.bufpos] = this.column;
        // we set the start offset of this char -> (bufpos)
        this.bufOffset[this.bufpos] = this.offset;
        // we set the end offset of this char -> (bufpos + 1)
        this.bufOffset[this.bufpos + 1] = ++this.offset;
    }

    public char readChar() throws IOException {
        if (this.inBuf > 0) {
            --this.inBuf;

            if (++this.bufpos == this.bufsize - 1) {
                this.bufpos = 0;
            }

            return this.buffer[this.bufpos];
        }

        char c1;

        if (++this.bufpos == this.available) {
            adjustBuffSize();
        }

        this.buffer[this.bufpos] = c1 = readRawChar();
        updateLineColumnOffset(c1);
        if (c1 == '\\') {
            int backSlashCnt = 1;

            while (true) // Read all the backslashes
            {
                try {
                    c1 = readRawChar();
                    if (c1 != 'u' || (backSlashCnt & 1) != 1) {
                        if (++this.bufpos == this.available) {
                            adjustBuffSize();
                        }

                        this.buffer[this.bufpos] = c1;
                        updateLineColumnOffset(c1);
                        if (c1 != '\\') {
                            // found a non-backslash char
                            backup(backSlashCnt);
                            return '\\';
                        }
                    } else {
                        // we found an odd number of backslashes and a 'u'
                        break;
                    }
                } catch (IOException e) {
                    if (backSlashCnt > 1) {
                        backup(backSlashCnt);
                    }

                    return '\\';
                }

                backSlashCnt++;
            } // while Read all the backslashes

            // Here, we have seen an odd number of backslash's followed by an
            // 'u'
            StringBuilder charSequence = new StringBuilder(7);
            charSequence.append('\\');
            try {
                do {
                    charSequence.append('u');
                    this.column++;
                    this.offset++;
                } while ((c1 = readRawChar()) == 'u');

                charSequence.append(c1);
                char c2 = readRawChar();
                charSequence.append(c2);
                char c3 = readRawChar();
                charSequence.append(c3);
                char c4 = readRawChar();
                charSequence.append(c4);
                char cAll = (char) (hexval(c1) << 12 |
                                    hexval(c2) << 8 |
                                    hexval(c3) << 4 |
                                    hexval(c4));
                this.buffer[this.bufpos] = cAll;

                this.column += 4;
                this.offset += 4;
                // we have to correct the end of this offset
                this.bufOffset[this.bufpos + 1] = this.offset;

                if (backSlashCnt == 1) {
                    return cAll;
                }

                backup(backSlashCnt - 1);
                return '\\';
            } catch (IOException e) {
                throw new Error("Invalid escape character: " + charSequence.toString());
            }
        }
        // it was no backslash
        return (c1);
    }

    public int getBeginLine() {
        return this.bufLine[this.tokenBegin];
    }

    public int getEndLine() {
        return this.bufLine[this.bufpos];
    }

    public int getBeginColumn() {
        return this.bufColumn[this.tokenBegin];
    }

    public int getEndColumn() {
        return this.bufColumn[this.bufpos];
    }

    public int getStartOffset() {
        return this.bufOffset[this.tokenBegin];
    }

    public int getEndOffset() {
        return this.bufOffset[this.bufpos + 1];
    }

    public void backup(int amount) {
        this.inBuf += amount;
        if ((this.bufpos -= amount) < 0) {
            this.bufpos += this.bufsize - 1;
        }
    }

    JavaCharStream(String sourceFileContent) {
        reInit(sourceFileContent);
    }

    void reInit(String newSourceFileContent) {
        this.sourceFileContent = newSourceFileContent;
        this.contentLength = this.sourceFileContent.length();
        this.contentPosition = -1;
        this.line = 1;
        this.column = 0;
        this.offset = 0;

        if (this.buffer == null || DOUBLE_BUFFER_SIZE != this.buffer.length) {
            this.bufsize = DOUBLE_BUFFER_SIZE;
            this.available = this.bufsize - 1;
            this.buffer = new char[DOUBLE_BUFFER_SIZE - 1];
            this.bufLine = new int[DOUBLE_BUFFER_SIZE];
            this.bufColumn = new int[DOUBLE_BUFFER_SIZE];
            this.bufOffset = new int[DOUBLE_BUFFER_SIZE];
        }
        this.tokenBegin = 0;
        this.inBuf = 0;
    }

    public String getParsedImage() {
        if (this.bufpos >= this.tokenBegin) {
            return new String(this.buffer, this.tokenBegin, this.bufpos - this.tokenBegin + 1);
        }

        return new String(this.buffer, this.tokenBegin, this.bufsize - 1 - this.tokenBegin)
            + new String(this.buffer, 0, this.bufpos + 1);
    }

    public String getSourceFileImage() {
        return this.sourceFileContent.substring(getStartOffset(), getEndOffset());
    }

    public char[] getSuffix(int len) {
        char[] ret = new char[len];

        if ((this.bufpos + 1) >= len) {
            System.arraycopy(this.buffer, this.bufpos - len + 1, ret, 0, len);
        } else {
            System.arraycopy(this.buffer, this.bufsize - 1 - (len - this.bufpos - 1), ret, 0, len - this.bufpos - 1);
            System.arraycopy(this.buffer, 0, ret, len - this.bufpos - 1, this.bufpos + 1);
        }

        return ret;
    }

    public void done() {
        this.buffer = null;
        this.bufLine = null;
        this.bufColumn = null;
        this.bufOffset = null;
    }
}