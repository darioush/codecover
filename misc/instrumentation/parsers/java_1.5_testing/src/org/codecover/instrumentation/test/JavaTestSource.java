package org.codecover.instrumentation.test;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * @author Christoph Müller 
 *
 * @version 1.0 ($Id$)
 */
public interface JavaTestSource {
    public String getName();
    public String getFullName();
    public Charset getCharset();
    public Reader getReader() throws IOException;
    public long getSize();
}
