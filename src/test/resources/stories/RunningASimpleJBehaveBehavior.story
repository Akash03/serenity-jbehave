Scenario: Running a simple successful JBehave story

Given a JBehave story
When we run the story with Thucydides
Then it should generate a Thucydides report for this story
And it should throw an exception