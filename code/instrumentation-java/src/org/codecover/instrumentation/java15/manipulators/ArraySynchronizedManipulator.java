package org.codecover.instrumentation.java15.manipulators;

import static org.codecover.instrumentation.java15.counter.CounterIDManager.LOG_NAME;
import static org.codecover.instrumentation.java15.counter.CounterIDManager.getNumberFromSyncStatementID;
import static org.codecover.instrumentation.java15.visitor.TreeDumperWithException.LINE_SEPARATOR;
import static org.codecover.model.utils.criteria.SynchronizedStatementCoverage.ID_SUFFIX_ABOVE;
import static org.codecover.model.utils.criteria.SynchronizedStatementCoverage.ID_SUFFIX_ONE;
import static org.codecover.model.utils.criteria.SynchronizedStatementCoverage.ID_SUFFIX_ZERO;
import static org.codecover.model.utils.criteria.SynchronizedStatementCoverage.ID_PREFIX;

import java.io.IOException;
import java.io.Writer;

import org.codecover.instrumentation.java15.counter.CounterManager;
import org.codecover.instrumentation.java15.syntaxtree.SynchronizedStatement;
import org.codecover.instrumentation.measurement.CoverageCounterLog;

/**
 * @author 
 *
 * @version 1.0 ($Id$)
 */
public class ArraySynchronizedManipulator extends AbstractDefaultManipulator
        implements SynchronizedManipulator, CounterManager {

    //array syncs:
    //offset description
    //0      wait counter; temporary variable for counting the number of waiting threads; needs to be in a "global" scope
    //1      number of threads passed through synchronized block without waiting
    //2      number of threads passed through synchronized block with waiting for one additional thread waiting
    //3      number of threads passed through synchronized block with more than one additional threads waiting 
    private static final String ARRAY_NAME = "syncs";

    private static final String COUNTER_INCREMENTING_LOCAL_DECLARATIONS = "int _waitCounter = %1$s." + ARRAY_NAME
            + "[%2$d].incrementAndGet();";

    private static final String COUNTER_INCREMENTING_COUNTER_CHECK = "_waitCounter = _waitCounter > 3 ? 3 : _waitCounter;";
    
    private static final String COUNTER_INCREMENTING = "%1$s." + ARRAY_NAME
            + "[%2$d + _waitCounter].incrementAndGet();";

    private static final String COUNTER_DECREMENTING = "%1$s." + ARRAY_NAME
            + "[%2$d].decrementAndGet();";

    private static final String COUNTER_DECLARATION = "public static AtomicLong[] "
            + ARRAY_NAME + " = new AtomicLong[%1$d];";

    private static final String COUNTER_FOR_LOOP = "for (int i = 0; i <= %1$d; i++)";

    private static final String COUNTER_RESET = ARRAY_NAME + "[i].set(0L);";

    private static final String COUNTER_RESET_WAIT_COUNTER = ARRAY_NAME + "[i * 4].set(0L);";

    private static final String COUNTER_SERIALIZE_IF_ZERO = "if ("
            + ARRAY_NAME + "[i * 4 + 1].get() != 0L)";

    private static final String COUNTER_SERIALIZE_PASS_COUNTER_ZERO = LOG_NAME
            + "." + CoverageCounterLog.PASS_COUNTER_METHOD_NAME + "(\""
            + ID_PREFIX + "\" + i + \"" + ID_SUFFIX_ZERO + "\", " + ARRAY_NAME
            + "[i * 4 + 1].get());";

    private static final String COUNTER_RESET_ZERO = ARRAY_NAME
            + "[i * 4 + 1].set(0L);";

    private static final String COUNTER_SERIALIZE_IF_ONE = "if ( " + ARRAY_NAME
            + "[i * 4 + 2].get() != 0L);";

    private static final String COUNTER_SERIALIZE_PASS_COUNTER_ONE = LOG_NAME
            + "." + CoverageCounterLog.PASS_COUNTER_METHOD_NAME + "(\""
            + ID_PREFIX + "\" + i + \"" + ID_SUFFIX_ONE + "\", " + ARRAY_NAME
            + "[i * 4 + 2].get());";

    private static final String COUNTER_RESET_ONE = ARRAY_NAME
            + "[i * 4 + 2].set(0L)";

    private static final String COUNTER_SERIALIZE_IF_ABOVE = "if ( "
            + ARRAY_NAME + "[i * 4 + 3].get() != 0L)";

    private static final String COUNTER_SERIALIZE_PASS_COUNTER_ABOVE = LOG_NAME
            + "." + CoverageCounterLog.PASS_COUNTER_METHOD_NAME + "(\""
            + ID_PREFIX + "\" + i + \"" + ID_SUFFIX_ABOVE + "\", "
            + ARRAY_NAME + "[i * 4 + 3].get());";

    private static final String COUNTER_RESET_ABOVE = ARRAY_NAME
            + "[i * 4 + 3].set(0L);";
    
    private int maxSyncID;

    /**
     * Constructor
     */
    public ArraySynchronizedManipulator() {
        this.maxSyncID = 0;
    }

    public void manipulateBefore(SynchronizedStatement n, String statementID)
            throws IOException {
        Writer writer = super.getWriter();
        
        int ID = getNumberFromSyncStatementID(statementID);
        this.maxSyncID = Math.max(this.maxSyncID, ID);
        
        //int _waitCounter = syncs[ID * 4].incrementAndGet(); 
        //_waitCounter = _waitCounter > 3 ? 3 : _waitCounter;
        //syncs[ID * 4 + _waitCounter].incrementAndGet();
        writer.write(LINE_SEPARATOR);
        writer.write(String.format(COUNTER_INCREMENTING_LOCAL_DECLARATIONS,
                super.getCounterIDManager().getInnerClassName(),
                new Integer(ID * 4)));
        writer.write(LINE_SEPARATOR);
        writer.write(COUNTER_INCREMENTING_COUNTER_CHECK);
        writer.write(LINE_SEPARATOR);
        writer.write(String.format(COUNTER_INCREMENTING,
                super.getCounterIDManager().getInnerClassName(),
                new Integer(ID * 4)));
        writer.write(LINE_SEPARATOR);
    }

    public void manipulateInner(SynchronizedStatement n, String statementID)
            throws IOException {
        Writer writer = super.getWriter();
        int ID = getNumberFromSyncStatementID(statementID);
        this.maxSyncID = Math.max(this.maxSyncID, ID);
        // TODO Automatisch erstellter Methoden-Stub
        writer.write(LINE_SEPARATOR);
        writer.write(String.format(COUNTER_DECREMENTING,
                super.getCounterIDManager().getInnerClassName(),
                new Integer(ID * 4)));
        writer.write(LINE_SEPARATOR);
    }

    public boolean requiresBlockExpansionsForBranches() {
        return false;
    }

    public boolean requiresBlockExpansionsForLoops() {
        return false;
    }

    public void writeDeclarations() throws IOException {
        // we need three counters for every synchronized ID

        Writer writer = super.getWriter();
        writer.write("    ");
        writer.write(String.format(COUNTER_DECLARATION,
                new Integer((this.maxSyncID + 1) * 4)));
        writer.write(LINE_SEPARATOR);

    }

    public void writeReset() throws IOException {
        Writer writer = super.getWriter();

        writer.write("      ");
        writer.write(String.format(COUNTER_FOR_LOOP,
                new Integer((this.maxSyncID + 1) * 4 - 1)));
        writer.write(" {");
        writer.write(LINE_SEPARATOR);
        writer.write("        ");
        writer.write(COUNTER_RESET);
        writer.write(LINE_SEPARATOR);
        writer.write("      }");
        writer.write(LINE_SEPARATOR);
    }

    public void writeSerialzeAndReset() throws IOException {
        Writer writer = super.getWriter();

        writer.write("      ");
        writer.write(String.format(COUNTER_FOR_LOOP, new Integer(
                this.maxSyncID)));
        writer.write(" {");
        writer.write(LINE_SEPARATOR);

        writer.write("        ");
        writer.write(COUNTER_RESET_WAIT_COUNTER);
        writer.write(LINE_SEPARATOR);

        // the if for zero threads -> syncs[i * 4 + 1]
        writer.write("        ");
        writer.write(COUNTER_SERIALIZE_IF_ZERO);
        writer.write(" {");
        writer.write(LINE_SEPARATOR);
        writer.write("          ");
        writer.write(COUNTER_SERIALIZE_PASS_COUNTER_ZERO);
        writer.write(LINE_SEPARATOR);
        writer.write("          ");
        writer.write(COUNTER_RESET_ZERO);
        writer.write(LINE_SEPARATOR);
        writer.write("        }");
        writer.write(LINE_SEPARATOR);

        // the if for one thread -> syncs[i * 4 + 2]
        writer.write("        ");
        writer.write(COUNTER_SERIALIZE_IF_ONE);
        writer.write(" {");
        writer.write(LINE_SEPARATOR);
        writer.write("          ");
        writer.write(COUNTER_SERIALIZE_PASS_COUNTER_ONE);
        writer.write(LINE_SEPARATOR);
        writer.write("          ");
        writer.write(COUNTER_RESET_ONE);
        writer.write(LINE_SEPARATOR);
        writer.write("        }");
        writer.write(LINE_SEPARATOR);

        // the if for more than one thread -> syncs[i * 4 + 3]
        writer.write("        ");
        writer.write(COUNTER_SERIALIZE_IF_ABOVE);
        writer.write(" {");
        writer.write(LINE_SEPARATOR);
        writer.write("          ");
        writer.write(COUNTER_SERIALIZE_PASS_COUNTER_ABOVE);
        writer.write(LINE_SEPARATOR);
        writer.write("          ");
        writer.write(COUNTER_RESET_ABOVE);
        writer.write(LINE_SEPARATOR);
        writer.write("        }");
        writer.write(LINE_SEPARATOR);

        // "}" of for loop
        writer.write("      }");
        writer.write(LINE_SEPARATOR);
    }

}
