package framework.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

@Slf4j
public class ScriptExecutor implements JavascriptExecutor {

    JavascriptExecutor executor;
    WebDriver driver;

    public static final String EXECUTE_WITHOUT_LOGGING = "EXECUTE_WITHOUT_LOGGING";

    public ScriptExecutor(WebDriver driver) {
        this.driver = driver;
        this.executor = (JavascriptExecutor) driver;
    }

    @Override
    public Object executeScript(String s, Object... objects) {
        boolean hasArgs = objects.length > 0;
        if (!hasArgs || (hasArgs && !objects[0].toString().contains(EXECUTE_WITHOUT_LOGGING)))
            log.info("'{}' {}\tArgs: {}", s, (objects.length > 1 ? "\n" : ""), objects);
        return this.executor.executeScript(s, objects);
    }

    @Override
    public Object executeAsyncScript(String s, Object... objects) {
        return this.executor.executeAsyncScript(s, objects);
    }

    public boolean pageReadyState(Object... objects) {
        return "complete".contains((String) executeScript("return document.readyState;", objects));
    }
}
