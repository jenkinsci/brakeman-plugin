package hudson.plugins.brakeman;

import hudson.model.Job;
import hudson.plugins.analysis.core.AbstractProjectAction;
import hudson.plugins.analysis.core.ResultAction;


/**
 * Entry point to visualize the warnings trend graph in the project screen.
 * Drawing of the graph is delegated to the associated
 * {@link BrakemanResultAction}.
 *
 * @author Maximilian Odendahl
 */
public class BrakemanProjectAction extends AbstractProjectAction<ResultAction<BrakemanResult>> {
    /** Unique identifier of this class. */
    private static final long serialVersionUID = -654316141132780562L;

    /**
     * Instantiates a new Brakeman project action.
     *
     * @param job
     */
    public BrakemanProjectAction(final Job<?, ?> job) {
        this(job, BrakemanResultAction.class);
    }

    public BrakemanProjectAction(final Job<?, ?> job,
        final Class<? extends ResultAction<BrakemanResult>> type) {
      super(job, type, Messages._Brakeman_ProjectAction_Name(), Messages._Brakeman_Trend_Name(),
          BrakemanDescriptor.PLUGIN_NAME, BrakemanDescriptor.ACTION_ICON, BrakemanDescriptor.RESULT_URL);
    }

    /** {@inheritDoc} */
    public String getDisplayName() {
        return Messages.Brakeman_ProjectAction_Name();
    }

    /** {@inheritDoc} */
    @Override
    public String getTrendName() {
        return Messages.Brakeman_Trend_Name();
    }
}

