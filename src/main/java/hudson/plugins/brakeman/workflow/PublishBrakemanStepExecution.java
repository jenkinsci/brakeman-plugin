package hudson.plugins.brakeman.workflow;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Run;
import hudson.plugins.brakeman.BrakemanPublisher;
import hudson.plugins.analysis.util.PluginLogger;
import org.jenkinsci.plugins.workflow.steps.AbstractSynchronousStepExecution;
import org.jenkinsci.plugins.workflow.steps.StepContextParameter;
import org.jenkinsci.plugins.workflow.steps.StepExecution;

import javax.inject.Inject;

/**
 * Created by apaulin on 7/1/16.
 */
public class PublishBrakemanStepExecution extends AbstractSynchronousStepExecution<Void> {

    @StepContextParameter
    private transient FilePath ws;

    @StepContextParameter
    private transient Run build;

    @StepContextParameter
    private transient Launcher launcher;

    @Inject
    private transient PublishBrakemanStep step;

    private transient PluginLogger logger;


    @Override
    protected Void run() throws Exception {
        new BrakemanPublisher(step.getOutputFile()).publishReport(build, ws, logger);
        return null;
    }
}
