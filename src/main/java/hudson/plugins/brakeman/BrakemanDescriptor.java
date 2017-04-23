package hudson.plugins.brakeman;

import hudson.maven.MavenModuleSet;
import hudson.model.AbstractProject;
import hudson.model.FreeStyleProject;
import hudson.plugins.analysis.core.PluginDescriptor;

import net.sf.json.JSONObject;

import org.apache.maven.project.MavenProject;
import org.kohsuke.stapler.StaplerRequest;

/**
 * Descriptor for the class {@link BrakemanPublisher}. Used as a singleton. The
 * class is marked as public so that it can be accessed from views.
 *
 * @author Maximilian Odendahl
 */
public final class BrakemanDescriptor extends PluginDescriptor {
    /** Plug-in name. */
    public static final String PLUGIN_NAME = "brakeman";
    /** Icon to use for the result and project action. */
    public static final String ACTION_ICON = "/plugin/brakeman/icons/warnings-24x24.png";
    /** The URL of the result action. */
    public static final String RESULT_URL = PluginDescriptor.createResultUrlName(PLUGIN_NAME);


    /**
     * Instantiates a new find bugs descriptor.
     */
    BrakemanDescriptor() {
        super(BrakemanPublisher.class);
    }

    /** {@inheritDoc} */
    @Override
    public String getDisplayName() {
        return Messages.Brakeman_Publisher_Name();
    }

    /** {@inheritDoc} */
    @Override
    public String getPluginName() {
        return PLUGIN_NAME;
    }

    /** {@inheritDoc} */
    @Override
    public String getIconUrl() {
        return ACTION_ICON;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public boolean isApplicable(final Class<? extends AbstractProject> jobType) {
        
        return FreeStyleProject.class.isAssignableFrom(jobType);
    }

}
