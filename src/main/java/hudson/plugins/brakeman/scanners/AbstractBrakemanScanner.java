package hudson.plugins.brakeman.scanners;

import hudson.plugins.analysis.core.ParserResult;
import hudson.plugins.analysis.util.model.Priority;
import hudson.plugins.analysis.util.PluginLogger;

/**
 * A Java class that representats an abstract Scanner class for the Brakeman Output file
 * This class includes a specific strategy for implementing the parsing process
 * of the output file. Along with some general methods to help with the information
 * collection process.
 *
 * @author Thomas Baird
 */
public abstract class AbstractBrakemanScanner {

    public abstract boolean scan(String content, ParserResult project, PluginLogger logger);

    protected int getStart(int line) {
        int start = 0;
        if (line > 2) {
            start = line - 1;
        }
        return start;
    }

    protected int getEnd(int line) {
        return line + 1;
    }

    protected Priority checkPriority(String priority) {
        Priority prio = Priority.HIGH;
        if ("Medium".equalsIgnoreCase(priority)) {
            prio = Priority.NORMAL;
        } else if ("High".equalsIgnoreCase(priority)) {
            prio =  Priority.HIGH;
        } else if ("Weak".equalsIgnoreCase(priority)) {
            prio =  Priority.LOW;
        }
        return prio;
    }
}
