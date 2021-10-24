package framework.driver.options;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.safari.SafariOptions;

import static config.ProjectProperties.getMobileOptions;

@Slf4j
public class IGeneralOptionsSafari extends SafariOptions implements IGeneralOptions {

    @Override
    public IGeneralOptionsSafari setHeadless(boolean headless) {
        log.warn("method is not implemented for Safari in automation framework.");
        return this;
    }

    @Override
    public IGeneralOptionsSafari setPageLoadStrategy(PageLoadStrategy strategy) {
        log.warn("method is not implemented for Safari in automation framework.");
        return this;
    }

    @Override
    public IGeneralOptionsSafari addArguments(String... arguments) {
        log.warn("method is not implemented for Safari in automation framework.");
        return this;
    }

    public IGeneralOptionsSafari turnMobileView() {
        this.setCapability("mobileEmulation", getMobileOptions());
        return this;
    }

}
