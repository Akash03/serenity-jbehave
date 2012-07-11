package net.thucydides.jbehave;

import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.model.TestResult;
import net.thucydides.core.model.TestStep;
import net.thucydides.core.model.TestTag;
import org.junit.Test;

import java.util.List;

import static net.thucydides.core.matchers.PublicThucydidesMatchers.containsResults;
import static net.thucydides.core.model.TestResult.FAILURE;
import static net.thucydides.core.model.TestResult.PENDING;
import static net.thucydides.core.model.TestResult.SKIPPED;
import static net.thucydides.core.model.TestResult.SUCCESS;
import static net.thucydides.core.reports.matchers.TestOutcomeMatchers.havingTag;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class WhenRunningJBehaveStories extends AbstractJBehaveStory {

    final static class AllStoriesSample extends ThucydidesJUnitStories {}

    final static class AStorySample extends ThucydidesJUnitStories {
        AStorySample(String storyName) {
            findStoriesCalled(storyName);
        }
    }

    @Test
    public void all_stories_on_the_classpath_should_be_run_by_default() throws Throwable {

        // Given
        ThucydidesJUnitStories stories = new AllStoriesSample();
        stories.setSystemConfiguration(systemConfiguration);
        stories.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(stories);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(greaterThan(10)));
    }

    final static class StoriesInTheSubsetFolderSample extends ThucydidesJUnitStories {
        StoriesInTheSubsetFolderSample() {
            findStoriesIn("stories/subset");
        }
    }

    @Test
    public void a_subset_of_the_stories_can_be_run_individually() throws Throwable {

        // Given
        ThucydidesJUnitStories stories = new StoriesInTheSubsetFolderSample();
        stories.setSystemConfiguration(systemConfiguration);
        stories.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(stories);

        // Then

        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
    }

    @Test
    public void stories_with_a_matching_name_can_be_run() throws Throwable {

        // Given
        ThucydidesJUnitStories stories = new AStorySample("*PassingBehavior.story");
        stories.setSystemConfiguration(systemConfiguration);
        stories.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(stories);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(3));
    }

    @Test
    public void pending_stories_should_be_reported_as_pending() throws Throwable {

        // Given
        ThucydidesJUnitStories pendingStory = new AStorySample("aPendingBehavior.story");

        pendingStory.setSystemConfiguration(systemConfiguration);
        pendingStory.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(pendingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.PENDING));
    }

    @Test
    public void pending_stories_should_report_the_given_when_then_steps() throws Throwable {

        // Given
        ThucydidesJUnitStories pendingStory = new AStorySample("aPendingBehavior.story");

        pendingStory.setSystemConfiguration(systemConfiguration);
        pendingStory.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(pendingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getStepCount(), is(4));
    }
    @Test
    public void implemented_pending_stories_should_be_reported_as_pending() throws Throwable {

        // Given
        ThucydidesJUnitStories pendingStory = new AStorySample("aPendingImplementedBehavior.story");

        pendingStory.setSystemConfiguration(systemConfiguration);
        pendingStory.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(pendingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.PENDING));
    }

    @Test
    public void passing_stories_should_be_reported_as_passing() throws Throwable {

        // Given
        ThucydidesJUnitStories passingStory = new AStorySample("aPassingBehavior.story");

        passingStory.setSystemConfiguration(systemConfiguration);
        passingStory.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
    }

    @Test
    public void a_passing_story_with_steps_should_record_the_steps() throws Throwable {

        // Given
        ThucydidesJUnitStories passingStory = new AStorySample("aPassingBehaviorWithSteps.story");

        passingStory.setSystemConfiguration(systemConfiguration);
        passingStory.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.SUCCESS));
        assertThat(outcomes.get(0).getNestedStepCount(), is(7));
    }

    @Test
    public void the_given_when_then_clauses_should_count_as_steps() throws Throwable {

        // Given
        ThucydidesJUnitStories passingStory = new AStorySample("aPassingBehaviorWithSteps.story");

        passingStory.setSystemConfiguration(systemConfiguration);
        passingStory.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(passingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();

        List<TestStep> steps = outcomes.get(0).getTestSteps();
        assertThat(steps.get(0).getDescription(), is("Given I have an implemented JBehave scenario"));
        assertThat(steps.get(1).getDescription(), is("And the scenario has steps"));
        assertThat(steps.get(2).getDescription(), is("When I run the scenario"));
        assertThat(steps.get(3).getDescription(), is("Then the steps should appear in the outcome"));
    }

    @Test
    public void failing_stories_should_be_reported_as_failing() throws Throwable {

        // Given
        ThucydidesJUnitStories failingStory = new AStorySample("aFailingBehavior.story");

        failingStory.setSystemConfiguration(systemConfiguration);
        failingStory.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(failingStory);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getResult(), is(TestResult.FAILURE));
    }

    @Test
    public void steps_after_a_failing_step_should_be_skipped() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aComplexFailingBehavior.story");

        story.setSystemConfiguration(systemConfiguration);
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));

        // And
        assertThat(outcomes.get(0), containsResults(SUCCESS, FAILURE, SKIPPED, SKIPPED, SKIPPED));
    }

    @Test
    public void a_test_with_a_pending_step_should_be_pending() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aBehaviorWithAPendingStep.story");

        story.setSystemConfiguration(systemConfiguration);
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));

        // And
        assertThat(outcomes.get(0).getResult() , is(PENDING));
        // And
        assertThat(outcomes.get(0), containsResults(SUCCESS, SUCCESS, SUCCESS, SUCCESS, SUCCESS, SUCCESS, PENDING, PENDING, SUCCESS));

    }

    @Test
    public void a_test_should_be_associated_with_a_corresponding_issue_if_specified() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aBehaviorWithAnIssue.story");

        story.setSystemConfiguration(systemConfiguration);
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(1));
        assertThat(outcomes.get(0).getIssueKeys(), hasItem("MYPROJ-456"));

    }

    @Test
    public void a_test_can_be_associated_with_several_issues() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aBehaviorWithMultipleIssues.story");

        story.setSystemConfiguration(systemConfiguration);
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getIssueKeys(), hasItems("MYPROJ-3", "MYPROJ-4", "MYPROJ-5"));

    }

    @Test(expected = AssertionError.class)
    public void a_test_running_a_failing_story_should_fail() throws Throwable {
        ThucydidesJUnitStories stories = new AFailingBehavior();// ASetOfBehaviorsContainingFailures();
        stories.setSystemConfiguration(systemConfiguration);
        stories.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);
        stories.run();
    }

    @Test(expected = AssertionError.class)
    public void a_test_running_a_failing_story_among_several_should_fail() throws Throwable {
        ThucydidesJUnitStories stories = new ASetOfBehaviorsContainingFailures();
        stories.setSystemConfiguration(systemConfiguration);
        stories.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);
        stories.run();
    }

    @Test
    public void a_test_story_can_be_associated_with_several_issues() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aBehaviorWithMultipleIssues.story");

        story.setSystemConfiguration(systemConfiguration);
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0).getIssueKeys(), hasItems("MYPROJ-1","MYPROJ-2","MYPROJ-3","MYPROJ-4","MYPROJ-5"));

    }
    @Test
    public void all_the_scenarios_in_a_story_should_be_associated_with_a_corresponding_issue_if_specified_at_the_story_level() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aBehaviorWithIssues.story");

        story.setSystemConfiguration(systemConfiguration);
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.size(), is(2));
        assertThat(outcomes.get(0).getIssueKeys(), hasItems("MYPROJ-123", "MYPROJ-456"));
        assertThat(outcomes.get(1).getIssueKeys(), hasItems("MYPROJ-123", "MYPROJ-789"));
    }


    @Test
    public void a_test_should_have_a_story_tag_matching_the_jbehave_story() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aBehaviorWithAnIssue.story");

        story.setSystemConfiguration(systemConfiguration);
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("A behavior with an issue").andType("story")));
    }

    @Test
    public void a_test_should_have_features_defined_by_the_feature_meta_field() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aBehaviorWithFeatures.story");

        story.setSystemConfiguration(systemConfiguration);
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("a feature").andType("feature")));
    }

    @Test
    public void a_test_should_have_features_defined_at_the_story_levelby_the_feature_meta_field() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aBehaviorWithFeatures.story");

        story.setSystemConfiguration(systemConfiguration);
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("a feature").andType("feature")));
        assertThat(outcomes.get(0), havingTag(TestTag.withName("another feature").andType("feature")));
    }


    @Test
    public void a_test_should_have_multiple_features_defined_at_the_story_level_by_the_feature_meta_field() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aBehaviorWithMultipleFeatures.story");

        story.setSystemConfiguration(systemConfiguration);
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("a feature").andType("feature")));
        assertThat(outcomes.get(0), havingTag(TestTag.withName("another feature").andType("feature")));
        assertThat(outcomes.get(0), havingTag(TestTag.withName("yet another feature").andType("feature")));
    }


    @Test
    public void a_test_should_have_tags_defined_by_the_tag_meta_field() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aBehaviorWithTags.story");

        story.setSystemConfiguration(systemConfiguration);
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("a domain").andType("domain")));
    }

    @Test
    public void a_test_should_have_storywide_tags_defined_by_the_tag_meta_field() throws Throwable {

        // Given
        ThucydidesJUnitStories story = new AStorySample("aBehaviorWithTags.story");

        story.setSystemConfiguration(systemConfiguration);
        story.configuredEmbedder().configuration().storyReporterBuilder().withReporters(printOutput);

        // When
        run(story);

        // Then
        List<TestOutcome> outcomes = loadTestOutcomes();
        assertThat(outcomes.get(0), havingTag(TestTag.withName("a domain").andType("domain")));
        assertThat(outcomes.get(0), havingTag(TestTag.withName("a capability").andType("capability")));
        assertThat(outcomes.get(0), havingTag(TestTag.withName("iteration 1").andType("iteration")));
    }

}
