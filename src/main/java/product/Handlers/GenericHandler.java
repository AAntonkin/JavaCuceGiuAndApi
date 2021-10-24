package product.Handlers;

import framework.elements.WebElementFacadeEx;
import framework.utils.AutomationException;
import framework.utils.ReflectEx;
import framework.utils.ScreenSnipper;
import framework.utils.StringParser;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import product.api.AbstractEndpoint;
import product.pages.AbstractPage;

import java.util.Arrays;

@Slf4j
public class GenericHandler extends PageObject {

    static ScreenSnipper screenSnipper;

    static public ScreenSnipper getScreenSnipper(WebDriver driver) {
        if (screenSnipper == null)
            screenSnipper = new ScreenSnipper(driver);
        return screenSnipper;
    }

    public ScreenSnipper getScreenSnipper() {
        if (screenSnipper == null)
            screenSnipper = new ScreenSnipper(this.getDriver());
        return screenSnipper;
    }

    public <T extends AbstractPage> T getWindow(String windowName) {
        return ((T) getObject(windowName, AbstractPage.class)).withDriver(this.getDriver());
    }

    public <T extends AbstractEndpoint> T getEndpoint(String endpointName) {
        return getObject(endpointName, AbstractEndpoint.class);
    }

    private <T> T getObject(String objectName, Class clazz) {
        String windowToGet = objectName;
        windowToGet = windowToGet.replace(" ", "").replaceAll("'", "");
        String[] windowNameVaraiants = new String[]{windowToGet, windowToGet + "Page", windowToGet + "Window", windowToGet + "Endpoint"};

        Object resultingWindow = null;
        ReflectEx refl = new ReflectEx();

        try {
            for (String name : windowNameVaraiants)
                if ((resultingWindow = (refl.getWindowByName(name, clazz))) != null)
                    break;
        } catch (NullPointerException e) {
        }
        if (resultingWindow == null) throw new AutomationException(
                this.getDriver()
                , "Window [Initial:'%s', Modified:'%s']  is not declared as extension of '%s'"
                , objectName
                , windowToGet
                , clazz.getSimpleName()
        );
        log.info("'{}' reflected to '{}' class", windowToGet, resultingWindow.getClass());
        return (T) resultingWindow;
    }

    public <T extends WebElementFacadeEx> T getElement(String fieldName, String windowName, Object... args) {
        return getField(getWindow(windowName), fieldName, args);
    }

    public <T extends WebElementFacadeEx> T getElement(String fieldName, AbstractPage page, Object... args) {
        return getField(page, fieldName, args);
    }

    public <T> T getField(AbstractPage page, String fieldNameInitial, Object... args) {

        T resultingElement;
        String fieldName = fieldNameInitial;
        String fieldNameAsConstString = "get" + fieldName.replace(" ", "_").replaceAll("'", "").toUpperCase();
        fieldName = getterNameTemplate(fieldName);
        resultingElement = (T) ReflectEx.getElementByNameOrNull(page, fieldName, args);
        if (resultingElement == null) {
            resultingElement = (T) ReflectEx.getElementByNameOrNull(page, fieldNameAsConstString, args);
        }
        if (resultingElement == null) {
            resultingElement = (T) ReflectEx.getElementByNameOrNull(page.getBasePageElements(), fieldName, args);
        }
        String argsValues = "(" + StringParser.delete(Arrays.toString(args), "[", "]") + ")";
        log.info("'{}' reflected to '{}{}' method which returned '{}' element for '{}' window",
                fieldNameInitial, fieldName, argsValues, resultingElement, page.getClass());
        return resultingElement;
    }

    public String getterNameTemplate(String fieldName) {
        return getNameTemplate(fieldName, "get");
    }

    public String getNameTemplate(String fieldName, String paramName) {
        return paramName + StringParser.upper(fieldName.replace(" ", "").replaceAll("'", ""), 0);
    }

    public String makeScreenshot() {
        return getScreenSnipper().makeASnip();
    }

}
