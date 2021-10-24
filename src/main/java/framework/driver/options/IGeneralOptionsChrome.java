package framework.driver.options;

import config.ProjectProperties;
import org.openqa.selenium.chrome.ChromeOptions;

public class IGeneralOptionsChrome extends ChromeOptions implements IGeneralOptions {

    public IGeneralOptionsChrome turnMobileView() {
        this.setExperimentalOption("mobileEmulation", ProjectProperties.getMobileOptions());
        return this;
    }
}
