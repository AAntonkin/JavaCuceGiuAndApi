package framework.driver;

import framework.driver.options.IGeneralOptions;
import framework.driver.options.IGeneralOptionsChrome;
import framework.driver.options.IGeneralOptionsFirefox;
import framework.driver.options.IGeneralOptionsSafari;
import framework.utils.ScreenSnipper;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.concurrent.TimeUnit;

import static config.ProjectProperties.*;

@Slf4j
public class DriverManager implements DriverSource {

    private WebDriver driver;

    @Override
    public WebDriver newDriver() {
        if (driver == null) {
            switch (BROWSER_NAME.<DriverManagerType>get()) {
                case SAFARI:
                    driver = new SafariDriver(getOptions());
                    break;
                case FIREFOX:
                    driver = new FirefoxDriver(getOptions());
                    break;
                case CHROME:
                default:
                    driver = new ChromeDriver(getOptions());
                    break;
            }
            if (BROWSER_IS_HEADLESS.<Boolean>get() || MOBILE_DEVICE_NAME.isSet()) {
                if (MOBILE_DEVICE_NAME.isSet())
                    log.info("Dimension is set to original on Mobile Emulator");
                else
                    driver.manage().window().setSize(new Dimension(1920, 1080));
            } else
                driver.manage().window().maximize();
            log.info("Chrome webDriver started with dimensions below: \n" +
                    "Height: " + driver.manage().window().getSize().getHeight() + "\n" +
                    "Width: " + driver.manage().window().getSize().getWidth());
            driver.manage().timeouts().pageLoadTimeout(30L, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(5L, TimeUnit.SECONDS);
            driver = new EventFiringWebDriver(driver);
        }
        return driver;
    }

    static <T extends MutableCapabilities> T getOptions() {
        T options;
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        switch (BROWSER_NAME.<DriverManagerType>get()) {
            case SAFARI:
                options = (T) new IGeneralOptionsSafari();
                WebDriverManager.safaridriver().setup();
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                options = (T) new IGeneralOptionsFirefox();
                break;
            case CHROME:
            default:
                WebDriverManager.chromedriver().setup();
                WebDriverManager.chromedriver().config().setForceDownload(true);
                options = (T) new IGeneralOptionsChrome();
                ((IGeneralOptions) options).addArguments(
                        "--disable-infobars",
                        "--disable-notifications",
                        "--disable-extensions",
                        "--disable-gpu",
                        "enable-automation",
                        "--disable-dev-shm-usage",
                        "--no-sandbox",
                        "--dns-prefetch-disable");
                break;
        }
        ((IGeneralOptions) options).setPageLoadStrategy(PageLoadStrategy.NONE);
        ((IGeneralOptions) options).setHeadless(BROWSER_IS_HEADLESS.get());
        desiredCapabilities.setPlatform(Platform.ANY);
        desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        desiredCapabilities.setCapability("marionette", true);
        if (MOBILE_DEVICE_NAME.isSet()) {
            ((IGeneralOptions) options).turnMobileView();
        }
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, SELENIUM_LOGS_DISABLED.get().toString());
        options.merge(desiredCapabilities);
        return options;
    }

    @Override
    public boolean takesScreenshots() {
        log.info(new ScreenSnipper(this.driver).makeASnip());
        return true;
    }
}
