package framework.utils;

import framework.utils.ScreenSnipper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

@Slf4j
public class AutomationException extends RuntimeException {

    public AutomationException(String message) {
        super(message);
        log.error(message);
    }

    public AutomationException(WebDriver driver, String message) {
        super(message + "\n" + new ScreenSnipper(driver).makeASnip());
    }

    public AutomationException(String message, Object... args) {
        this(String.format(message, args));
    }

    public AutomationException(@NonNull WebDriver driver, String message, Object... args) {
        this(driver, String.format(message, args));
    }
}
