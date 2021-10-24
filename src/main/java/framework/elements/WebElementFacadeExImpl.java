package framework.elements;

import framework.elements.sub.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.core.pages.WebElementFacadeImpl;
import net.thucydides.core.guice.Injectors;
import net.thucydides.core.util.EnvironmentVariables;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Field;

@Slf4j
@Getter
public class WebElementFacadeExImpl extends WebElementFacadeImpl implements WebElementFacadeEx, Button, CheckBox, Dropdown, Link, RadioButton, StaticTextField, TextField {

    EnvironmentVariables environmentVariables;
    ElementLocator locator;
    public WebElementFacadeExImpl(WebDriver driver, ElementLocator locator, WebElement webElement, long implicitTimeoutInMilliseconds, long waitForTimeoutInMilliseconds) {
        super(driver, locator, webElement, implicitTimeoutInMilliseconds, waitForTimeoutInMilliseconds);
        this.environmentVariables = Injectors.getInjector().getProvider(EnvironmentVariables.class).get();
        this.locator = locator;
    }

    String getClassName() {
        try {
            Field field = this.locator.getClass().getDeclaredField("field");
            field.setAccessible(true);
            String[] str1 = field.get(this.locator).toString().split(" ")[1].split("\\.");
            return str1[str1.length - 1];
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return "";
    }

    public WebElementFacadeEx set(Object value) {
        switch (getClassName()) {
            case "Dropdown":
                Dropdown.super.set(value);
                break;
            case "TextField":
                TextField.super.set(value);
                break;
            case "CheckBox":
                CheckBox.super.set(value);
                break;
        }
        return this;
    }
}
