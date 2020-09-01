package io.jenkins.plugins.analysis.warnings;

import javax.annotation.Nonnull;

import org.kohsuke.stapler.DataBoundConstructor;

import io.jenkins.plugins.analysis.core.model.ReportScanningTool;

import hudson.Extension;

import hudson.plugins.brakeman.BrakemanParser;

/**
 * Provides a parser and customized messages for your tool.
 */
public class Brakeman extends ReportScanningTool {
    private static final long serialVersionUID = 75319755633492904L;
    static final String ID = "brakeman";

    /** Creates a new instance of {@link Brakeman}. */
    @DataBoundConstructor
    public Brakeman() {
        super();
        // empty constructor required for stapler
    }

    @Override
    public BrakemanParser createParser() {
        return new BrakemanParser();
    }

    /** Descriptor for this static analysis tool. */
    @Extension
    public static class Descriptor extends ReportScanningToolDescriptor {
        /** Creates the descriptor instance. */
        public Descriptor() {
            super(ID);
        }

        @Nonnull
        @Override
        public String getDisplayName() {
            return "Brakeman";
        }

        @Nonnull
        @Override
        public String getPattern() {
            return "brakeman-output.json";
        }
    }
}
