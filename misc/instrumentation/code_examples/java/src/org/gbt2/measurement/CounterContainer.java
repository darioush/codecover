///////////////////////////////////////////////////////////////////////////////
//
// $Id$
// 
// created by: Christoph Müller
// created at: 21.03.2007 12:06:19
//
///////////////////////////////////////////////////////////////////////////////

package org.gbt2.measurement;


/**
 * @author Christoph Müller
 * @version 1.0 - 21.03.2007
 *
 */
public interface CounterContainer {
    public void serializeAndReset(CoverageResultLog printer);
}
