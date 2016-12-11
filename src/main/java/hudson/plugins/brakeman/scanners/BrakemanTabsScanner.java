package hudson.plugins.brakeman.scanners;

import hudson.plugins.analysis.core.ParserResult;
import hudson.plugins.analysis.util.PluginLogger;
import hudson.plugins.analysis.util.model.Priority;
import hudson.plugins.brakeman.Warning;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Java class that represents a tabs Scanner of the Brakeman Output file
 * This class includes a specific strategy for implementing the parsing process
 * of the output file.
 *
 * @author Thomas Baird
 */
public class BrakemanTabsScanner extends AbstractBrakemanScanner {

    private boolean result;

    private static Pattern pattern = Pattern.compile("^([^\t]+?)\t(\\d+)\t([\\w\\s]+?)\t(\\w+)\t([^\t]+?)\t(High|Medium|Weak)", Pattern.MULTILINE);
    /**
     * Creates a new instance of <code>BrakemanTabsScanner</code>
     */
    public BrakemanTabsScanner() {
        this.result = true;
    }

    public boolean scan(String content, ParserResult project, PluginLogger logger) {
        Matcher m = pattern.matcher(content);
        this.scanWarnings(m, project);
        return result;
    }

    private void scanWarnings(Matcher m, ParserResult project) {
        while(m.find()) {
            String fileName = m.group(1);
            int line = Integer.parseInt(m.group(2));
            String type = m.group(3);
            String category = m.group(4);
            String message = m.group(5);
            Priority priority = checkPriority(m.group(6));

            project.addAnnotation(new Warning(fileName, getStart(line), getEnd(line), type, category, message, priority));
        }
    }
}
