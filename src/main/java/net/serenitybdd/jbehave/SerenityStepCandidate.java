package net.serenitybdd.jbehave;

import com.thoughtworks.paranamer.Paranamer;
import net.serenitybdd.jbehave.reflection.Extract;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.model.TableTransformers;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.Step;
import org.jbehave.core.steps.StepCandidate;
import org.jbehave.core.steps.StepMonitor;
import org.jbehave.core.steps.StepType;
import org.jbehave.core.steps.context.StepsContext;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class SerenityStepCandidate extends StepCandidate {

    private final StepCandidate stepCandidate;

    public SerenityStepCandidate(StepCandidate stepCandidate) {

        super(stepCandidate.getPatternAsString(),
                stepCandidate.getPriority(),
                stepCandidate.getStepType(),
                stepCandidate.getMethod(),
                (Class<?>) Extract.field("stepsType").from(stepCandidate),
                (InjectableStepsFactory) Extract.field("stepsFactory").from(stepCandidate),
                new StepsContext(),
                (Keywords) Extract.field("keywords").from(stepCandidate),
                new RegexPrefixCapturingPatternParser(),
                new ParameterConverters(new TableTransformers()),
                new ParameterControls());
        this.composedOf(stepCandidate.composedSteps());
        this.stepCandidate = stepCandidate;
    }

    @Override
    public Method getMethod() {
        return stepCandidate.getMethod();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Integer getPriority() {
        return stepCandidate.getPriority();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String getPatternAsString() {
        return stepCandidate.getPatternAsString();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public StepType getStepType() {
        return stepCandidate.getStepType();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String getStartingWord() {
        return stepCandidate.getStartingWord();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void useStepMonitor(StepMonitor stepMonitor) {
        super.useStepMonitor(stepMonitor);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void doDryRun(boolean dryRun) {
        super.doDryRun(dryRun);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void useParanamer(Paranamer paranamer) {
        super.useParanamer(paranamer);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void composedOf(String[] steps) {
        super.composedOf(steps);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean isComposite() {
        return stepCandidate.isComposite();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String[] composedSteps() {
        return stepCandidate.composedSteps();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean ignore(String stepAsString) {
        return stepCandidate.ignore(stepAsString);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean isPending() {
        return stepCandidate.isPending();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean matches(String stepAsString) {
        return stepCandidate.matches(stepAsString);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean matches(String step, String previousNonAndStep) {
        return stepCandidate.matches(step, previousNonAndStep);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public Step createMatchedStep(String stepAsString, Map<String, String> namedParameters) {
        return stepCandidate.createMatchedStep(stepAsString, namedParameters);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void addComposedSteps(List<Step> steps, String stepAsString, Map<String, String> namedParameters, List<StepCandidate> allCandidates) {
        super.addComposedSteps(steps, stepAsString, namedParameters, allCandidates);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean isAndStep(String stepAsString) {
        return stepCandidate.isAndStep(stepAsString);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean isIgnorableStep(String stepAsString) {
        return stepCandidate.isIgnorableStep(stepAsString);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        return stepCandidate.toString();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
