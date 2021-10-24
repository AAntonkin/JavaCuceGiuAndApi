package runner;

import net.serenitybdd.core.di.WebDriverInjectors;
import net.serenitybdd.core.environment.WebDriverConfiguredEnvironment;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.webdriver.Configuration;
import net.thucydides.core.webdriver.DriverConfiguration;
import org.junit.runners.model.InitializationError;

import static config.ProjectProperties.DRIVER_MANAGER;
import static net.thucydides.core.ThucydidesSystemProperty.*;
import static net.thucydides.core.model.TakeScreenshots.FOR_FAILURES;

public class RunConfiguration extends CucumberWithSerenity {

    protected Configuration configuration;
    protected EnvironmentVariables environmentVariables;

    public RunConfiguration(Class clazz) throws InitializationError {
        super(clazz);
        configuration = WebDriverInjectors.getInjector().getInstance(DriverConfiguration.class);
        environmentVariables = WebDriverConfiguredEnvironment.getEnvironmentVariables();
        configuration.setIfUndefined(SERENITY_CONSOLE_COLORS.getPropertyName(), "true");
        configuration.setIfUndefined(WEBDRIVER_DRIVER.getPropertyName(), "provided");
        configuration.setIfUndefined(WEBDRIVER_PROVIDED_TYPE.getPropertyName(), "mydriver");
        configuration.setIfUndefined("webdriver.provided.mydriver", DRIVER_MANAGER.get());
        configuration.setIfUndefined("serenity.driver.capabilities", "mydriver");
        configuration.setIfUndefined(SERENITY_TAKE_SCREENSHOTS.getPropertyName(), FOR_FAILURES.name());
        configuration.setIfUndefined(WEBDRIVER_TIMEOUTS_IMPLICITLYWAIT.getPropertyName(), "5000");
        configuration.setIfUndefined(WEBDRIVER_WAIT_FOR_TIMEOUT.getPropertyName(), "5000");
    }
}
