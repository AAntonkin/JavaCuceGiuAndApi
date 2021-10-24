package runner;

import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(RunConfiguration.class)
@CucumberOptions(
        plugin = {"pretty"},
        features = "src/test/resources/features",
        extraGlue = {"stepdefinitions"}
)
public class CucumberTestSuite {

}
