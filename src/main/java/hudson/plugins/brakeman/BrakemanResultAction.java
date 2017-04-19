package hudson.plugins.brakeman;

import java.util.Collection;

import hudson.model.Action;
import hudson.model.Run;
import hudson.plugins.analysis.core.AbstractResultAction;
import hudson.plugins.analysis.core.HealthDescriptor;
import hudson.plugins.analysis.core.PluginDescriptor;

/**
 * Controls the live cycle of the warnings results. This action persists the
 * results of the warnings analysis of a build and displays the results on the
 * build page. The actual visualization of the results is defined in the
 * matching <code>summary.jelly</code> file.
 * <p>
 * Moreover, this class renders the warnings result trend.
 * </p>
 *
 * @author Maximilian Odendahl
 */
public class BrakemanResultAction extends AbstractResultAction<BrakemanResult> {
    /** Unique identifier of this class. */
    private static final long serialVersionUID = -5329651349674842874L;

    /**
     * Creates a new instance of <code>BrakemanResultAction</code>.
     *
     * @param owner
     *            the associated build of this action
     * @param healthDescriptor
     *            health descriptor to use
     * @param result
     *            the result in this build
     */
    public BrakemanResultAction(final Run<?, ?> owner, final HealthDescriptor healthDescriptor, final BrakemanResult result) {
        super(owner, new BrakemanHealthDescriptor(healthDescriptor), result);
    }

    @Override
    public Collection<? extends Action> getProjectActions() {
      return asSet(new BrakemanProjectAction(getJob()));
    }

    /** {@inheritDoc} */
    public String getDisplayName() {
        return Messages.Brakeman_ProjectAction_Name();
    }

    /** {@inheritDoc} */
    @Override
    protected PluginDescriptor getDescriptor() {
        return BrakemanPublisher.BRAKEMAN_DESCRIPTOR;
    }

    /** {@inheritDoc} */
    @Override
    public String getMultipleItemsTooltip(final int numberOfItems) {
        return Messages.Brakeman_ResultAction_MultipleWarnings(numberOfItems);
    }

    /** {@inheritDoc} */
    @Override
    public String getSingleItemTooltip() {
        return Messages.Brakeman_ResultAction_OneWarning();
    }
}
