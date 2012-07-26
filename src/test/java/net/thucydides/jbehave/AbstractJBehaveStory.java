package net.thucydides.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.reports.xml.XMLTestOutcomeReporter;
import net.thucydides.core.util.MockEnvironmentVariables;
import net.thucydides.core.webdriver.Configuration;
import net.thucydides.core.webdriver.SystemPropertiesConfiguration;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.TxtOutput;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AbstractJBehaveStory {

    protected MockEnvironmentVariables environmentVariables;
    protected Configuration systemConfiguration;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    protected File outputDirectory;

    @Before
    public void prepareReporter() {

        environmentVariables = new MockEnvironmentVariables();

        outputDirectory = temporaryFolder.newFolder("output");
        environmentVariables.setProperty("thucydides.outputDirectory", outputDirectory.getAbsolutePath());
        systemConfiguration = new SystemPropertiesConfiguration(environmentVariables);


        System.out.println("Report directory:" + this.outputDirectory);
    }


    protected void run(ThucydidesJUnitStories stories) {
        try {
            stories.run();
        } catch(Throwable e) {
            e.printStackTrace();
        }
    }

    protected List<TestOutcome> loadTestOutcomes() throws IOException {
        XMLTestOutcomeReporter outcomeReporter = new XMLTestOutcomeReporter();
        List<TestOutcome> testOutcomes = outcomeReporter.loadReportsFrom(outputDirectory);
        Collections.sort(testOutcomes, new Comparator<TestOutcome>() {
            @Override
            public int compare(TestOutcome testOutcome, TestOutcome testOutcome1) {
                return testOutcome.getTitle().compareTo(testOutcome1.getTitle());
            }
        });
        return testOutcomes;
    }
}
