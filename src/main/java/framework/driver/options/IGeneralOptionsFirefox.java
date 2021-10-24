package framework.driver.options;

import org.openqa.selenium.firefox.FirefoxOptions;
import static config.ProjectProperties.getMobileOptions;

public class IGeneralOptionsFirefox extends FirefoxOptions implements IGeneralOptions {

    public IGeneralOptionsFirefox turnMobileView() {
        this.setCapability("mobileEmulation", getMobileOptions());
        return this;
    }
}
