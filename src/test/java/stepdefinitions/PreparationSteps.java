package stepdefinitions;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.steps.StepEventBus;

@Slf4j
public class PreparationSteps extends GeneralSteps {

    @Before
    public void before() {
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (StepEventBus.getEventBus().getBaseStepListener().aStepHasFailed()
                && !scenario.getSourceTagNames().contains("@api"))
            log.error(genericHandler.makeScreenshot());
    }
}
