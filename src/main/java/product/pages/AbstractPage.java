package product.pages;

import framework.utils.ScriptExecutor;
import framework.elements.WebElementFacadeEx;
import framework.utils.Framework;
import framework.utils.AutomationException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.webdriver.exceptions.ElementShouldBeVisibleException;
import org.joda.time.DateTime;
import org.openqa.selenium.ScriptTimeoutException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import product.Handlers.WaitConditions;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Function;

import static config.ProjectProperties.PRODUCT_SITE_URL;
import static framework.utils.ScriptExecutor.EXECUTE_WITHOUT_LOGGING;

@Slf4j
@Getter
@DefaultUrl("")
public abstract class AbstractPage extends PageObject {

    public String pageIdText = null;
    public String pageUrl = PRODUCT_SITE_URL.get();
    public String pageTitle = null;
    static ScriptExecutor scriptExecutor;

    public String getPageUrl(String subUrl) {
        return this.getPageUrl() + "/" + subUrl;
    }
    public abstract WebElementFacadeEx getPageID();

    public WebDriver getDriver() {
        WebDriver driver = super.getDriver();
        if (driver == null && Serenity.getWebdriverManager().getCurrentDriver() != null) {
            driver = Serenity.getWebdriverManager().getCurrentDriver();
            this.setDriver(driver);
        }/**/
        return driver;
    }

    static public ScriptExecutor getScriptExecutor(WebDriver driver) {
        if (scriptExecutor == null)
            scriptExecutor = new ScriptExecutor(driver);
        return scriptExecutor;
    }

    public ScriptExecutor getScriptExecutor() {
        return getScriptExecutor(this.getDriver());
    }

    public BasePageElements getBasePageElements() {
        return new BasePageElements().withDriver(this.getDriver());
    }

    public void openPage() {
        openPage(this.getPageUrl());
    }

    public void openPage(String url) {
        log.info("Page url: {}", url);
        WebDriver thisDriver = this.getDriver();
        this.setDefaultBaseUrl(PRODUCT_SITE_URL.get() + url);
        this.open();
        this.isReadyForUse(10000);
        thisDriver.get(url);
    }

    public boolean isReadyForUse(int timeOutMilliseconds) {
        waitUntilSoft(p -> this.isReadyForUse(), timeOutMilliseconds, 1000);
        return this.isReadyForUse();
    }

    public Boolean isReadyForUse() {
        try {
            boolean scriptExec = getScriptExecutor().pageReadyState(EXECUTE_WITHOUT_LOGGING);
            boolean isOpened = pageIsOpened();
            boolean isLoading = getBasePageElements().getPreloadingIndicator().isCurrentlyVisible();
            log.debug("pageReadyState: {}. isOpened: {}. isLoading: {}", scriptExec, isOpened, isLoading);
            return scriptExec && isOpened && !isLoading;
        } catch (ScriptTimeoutException e) {
            log.error("[EXCEPTION] ScriptTimeoutException. Return false");
            return false;
        }
    }

    public Boolean pageIsOpened() {
        boolean isOpened = false;
        int tries = 3;
        while (tries-- > 0)
            try {
                if (isOpened = this.getPageID().isCurrentlyVisible()) {
                    if (this.getPageIdText() != null)
                        isOpened = this.getPageID().getText().trim().equalsIgnoreCase(this.getPageIdText());
                } else {
                    log.debug("'getPageID' for '{}' page is not visible", this.getPageID().getClass().getSimpleName());
                }
                break;
            } catch (StaleElementReferenceException e) {
                log.debug("'StaleElementReferenceException' page refreshed: {}", e.getMessage());
            }
        return isOpened;
    }

    public <T extends PageObject> T waitUntilSoft(Function isTrue, int timeOutMilliseconds, int pollingIntervalMilliseconds) {
        return waitUntilSoft(isTrue, timeOutMilliseconds, pollingIntervalMilliseconds, 1);
    }

    public <T extends PageObject> T waitUntilSoft(Function isTrue, int timeOutMilliseconds, int pollingIntervalMilliseconds, int positiveTries) {
        String methodName = Framework.getParentMethodName("waitUntilSoft", "isReadyForUse", "waitUntilVisibleSoft", "waitReadyForUseWithRefresh");

        String timeFormat = "HH:mm:ss";
        String started = new DateTime().toString(timeFormat);
        long startTime = System.currentTimeMillis();
        Optional<Object> res = Optional.empty();
        int maximumTries = 100;
        try {
            while (--maximumTries > 0 && positiveTries > 0)
                try {
                    res = Optional.ofNullable(this.waitForCondition().pollingEvery(Duration.ofMillis(pollingIntervalMilliseconds))
                            .withTimeout(Duration.ofMillis(timeOutMilliseconds)).until(isTrue));
                    positiveTries -= res.isPresent() && Boolean.parseBoolean(res.get().toString()) ? 1 : 0;
                } catch (StaleElementReferenceException | ElementShouldBeVisibleException e) {
                    log.debug("{} Got StaleElementReferenceException. Repeat waiting.", methodName);
                }
        } catch (WebDriverException | AutomationException e) {
            log.debug("{} waited for {} second(s) with {} milliseconds interval, finished with value => {} ", methodName, timeOutMilliseconds / 1000, pollingIntervalMilliseconds, res);
        } catch (NullPointerException e) {
            log.debug(NullPointerException.class.getSimpleName() + " => this={} ", this);
        }
        log.debug("{} waiting finished with value => {} in {} milliseconds [{} -> {}]",
                methodName, res.orElse(null), System.currentTimeMillis() - startTime, started, new DateTime().toString(timeFormat));
        return (T) this;
    }

    public <T extends AbstractPage> T waitFor(WaitConditions waitCondition, int timeOutMilliseconds, int positiveTries) {
        return waitFor(waitCondition, timeOutMilliseconds, 1000, positiveTries);
    }

    public <T extends AbstractPage> T waitFor(WaitConditions waitCondition, int timeOutMilliseconds, int pollingIntervalMilliseconds, int positiveTries) {
        switch (waitCondition) {
            case IS_NOT_VISIBLE:
                waitUntilSoft(p -> !this.pageIsOpened(), timeOutMilliseconds, pollingIntervalMilliseconds, positiveTries);
                break;
            case READY_FOR_USE:
                waitUntilSoft(p -> this.isReadyForUse(), timeOutMilliseconds, pollingIntervalMilliseconds, positiveTries);
                break;
            default:
                throw new AutomationException("'%s' condition is not implemented", waitCondition);
        }
        return (T) this;
    }

    public AbstractPage clickOn(WebElementFacadeEx wex, int tries, int waitInterval) {
        String message = "";
        log.info("clickOn ({}, {}, {})", wex.getXpathExpression(), tries, waitInterval);
        while (--tries > 0) {
            log.debug("clickOn tries left: {}", tries);
            try {
                this.clickOn(wex);
                break;
            } catch (Exception e) {
                message = e.getMessage();
                log.debug("Click failed! {}", message);
                if (tries <= 0) throw new AutomationException("Click failed! \n\t%s", message);
            }
            Framework.waitABit(waitInterval);
        }
        return this;
    }
}
