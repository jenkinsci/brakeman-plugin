package hudson.plugins.brakeman;

import hudson.Extension;
import hudson.FilePath;
import hudson.model.Run;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.Result;
import hudson.plugins.analysis.core.*;
import hudson.plugins.analysis.util.PluginLogger;

import java.io.IOException;
import java.util.regex.Pattern;
import net.sf.json.JSONObject;
import net.sf.json.JSONException;

import org.kohsuke.stapler.DataBoundConstructor;
import hudson.plugins.brakeman.scanners.*;
import hudson.plugins.brakeman.ScanResult;

/**
 * Publishes the results of the warnings analysis (freestyle project type).
 *
 * @author Maximilian Odendahl
 */
// CHECKSTYLE:COUPLING-OFF
public class BrakemanPublisher extends HealthAwarePublisher {

	/** Unique ID of this class. */
	private static final long serialVersionUID = -5936973521277401765L;
	/** Descriptor of this publisher. */
	@Extension
	public static final BrakemanDescriptor BRAKEMAN_DESCRIPTOR = new BrakemanDescriptor();
	public String outputFile;
	/** Enum for possible file types for brakeman output */
	public enum FileType {
		JSON, TABS
	}

	/**
	 * Creates a new instance of <code>BrakemanPublisher</code>
	 *
	 * @param outputFile
	 *        Workspace path to Brakeman output
     */
	@DataBoundConstructor
	public BrakemanPublisher(final String outputFile) {
		super("BRAKEMAN");
		this.setDefaultEncoding("UTF-8");
		this.outputFile = outputFile;
	}

	/**
	 * Creates a new instance of <code>BrakemanPublisher</code>.
	 *
	 * @deprecated prefer setters from the base class
	 */
	// CHECKSTYLE:OFF
	@SuppressWarnings("PMD.ExcessiveParameterList")
		@Deprecated
		public BrakemanPublisher(final String healthy, final String unHealthy, final String thresholdLimit,
				final boolean useDeltaValues,
				final String unstableTotalAll, final String unstableTotalHigh, final String unstableTotalNormal, final String unstableTotalLow,
				final String unstableNewAll, final String unstableNewHigh, final String unstableNewNormal, final String unstableNewLow,
				final String failedTotalAll, final String failedTotalHigh, final String failedTotalNormal, final String failedTotalLow,
				final String failedNewAll, final String failedNewHigh, final String failedNewNormal, final String failedNewLow,
				final boolean canRunOnFailed, final boolean shouldDetectModules, final boolean canComputeNew, final String outputFile) {
			super(healthy, unHealthy, thresholdLimit, "UTF-8", useDeltaValues,
					unstableTotalAll, unstableTotalHigh, unstableTotalNormal, unstableTotalLow,
					unstableNewAll, unstableNewHigh, unstableNewNormal, unstableNewLow,
					failedTotalAll, failedTotalHigh, failedTotalNormal, failedTotalLow,
					failedNewAll, failedNewHigh, failedNewNormal, failedNewLow,
					canRunOnFailed, shouldDetectModules, canComputeNew, "BRAKEMAN");

			this.outputFile = outputFile;
		}
	// CHECKSTYLE:ON

	/**
	 * Creates a new parser set for old versions of this class.
	 *
	 * @return this
	 */
	@Override
		protected Object readResolve() {
			super.readResolve();
			return this;
		}

	/** {@inheritDoc} */
	@Override
		public Action getProjectAction(final AbstractProject<?, ?> project) {
			return new BrakemanProjectAction(project);
		}

	/** {@inheritDoc} */
	@Override
	public BuildResult perform(final Run<?, ?> build, final FilePath workspace, final PluginLogger logger) throws InterruptedException, IOException {
		return publishReport(build, workspace, logger);
	}

	public BuildResult publishReport(final Run<?, ?> build, final FilePath workspace, final PluginLogger logger) throws InterruptedException, IOException {
		FilePath brakemanOutput = new FilePath(workspace, this.outputFile);
		String output = brakemanOutput.readToString();

		ParserResult project = new ParserResult(workspace);

        AbstractBrakemanScanner scanner = createScanner(output);
		boolean successfulScan = scanner.scan(output, project, logger);
		BrakemanResult result = new BrakemanResult(build, getDefaultEncoding(), project, usePreviousBuildAsReference(), useOnlyStableBuildsAsReference(), new ScanResult(successfulScan, brakemanOutput.getName()));

		build.addAction(new BrakemanResultAction(build, this, result));

		return result;
	}

    /**
     * Determines the type of Scanner to use.
     *
     * @param outputContent
     * @return scanner
     */
	protected AbstractBrakemanScanner createScanner(String outputContent) {
        FileType scannerType = getFileType(outputContent);
        AbstractBrakemanScanner scanner;
        if(scannerType == FileType.JSON) {
            scanner = new BrakemanJSONScanner();
        } else {
            scanner = new BrakemanTabsScanner();
        }
        return scanner;
    }

	/**
	 * Determines the file type based upon the contents.
	 *
     * @param content
	 * @return result
	 */
	protected FileType getFileType(String content) {
		FileType result = FileType.TABS;
		try {
			JSONObject.fromObject(content);
			result = FileType.JSON;
		} catch (JSONException e) { }
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public PluginDescriptor getDescriptor() {
			return BRAKEMAN_DESCRIPTOR;
		}

	/** {@inheritDoc} */
	@Override
	protected boolean canContinue(final Result result) {
			return super.canContinue(result);
		}

  public hudson.matrix.MatrixAggregator createAggregator(hudson.matrix.MatrixBuild build,hudson.Launcher launcher,hudson.model.BuildListener listener) {
    return null;
  }
}
