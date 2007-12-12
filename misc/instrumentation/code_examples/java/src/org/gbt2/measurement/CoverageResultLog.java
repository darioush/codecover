///////////////////////////////////////////////////////////////////////////////
//
// $Id$
// 
// created by: Christoph Müller
// created at: 22.03.2007 22:47:35
//
///////////////////////////////////////////////////////////////////////////////

package org.gbt2.measurement;

/**
 * @author Christoph Müller
 * @version 1.0 - 22.03.2007
 * 
 */
public interface CoverageResultLog {
    public void startTestCase(String testCaseName);

    public void endTestCase(String testCaseName);

    public void startFile(String fileName);

    public void writeCounter(String counterName, long counterValue);
    
    public void close();
}
