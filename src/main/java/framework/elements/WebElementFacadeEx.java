package framework.elements;

import net.serenitybdd.core.annotations.ImplementedBy;
import net.serenitybdd.core.pages.WebElementExpectations;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.WebElementFacadeImpl;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@ImplementedBy(WebElementFacadeExImpl.class)
public interface WebElementFacadeEx extends WebElementFacade {

    ElementLocator getLocator();

    WebElementFacadeEx set (Object value);

    default String getXpathExpression() {
        Object field = null;
        String failed = "<FILED TO RETRIEVE XPATH/CSS LOCATOR FROM ELEMENT>";
        try {
                ElementLocator l = getLocator();
                if (l == null) {
                    Object obj;
                    try {
                        obj = FieldUtils.readField(this, "webElement", true);
                        obj = FieldUtils.readField(obj, "h", true);
                    } catch (IllegalArgumentException e) {
                        obj = FieldUtils.readField(this, "h", true);
                    }
                    l = (ElementLocator) FieldUtils.readField(obj, "locator", true);
                }
                field = FieldUtils.readField(l, "by", true);
            String fieldNameToGet = field.getClass().getSimpleName().contains("ssSelector") ? "cssSelector" : "xpathExpression";
            return (String) FieldUtils.readField(field, fieldNameToGet, true);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            String out = failed;
            try {
                field = FieldUtils.readField(this, "foundBy", true);
            } catch (IllegalAccessException | IllegalArgumentException ex) {
            }
            if (field == null)
                field = this.toString();
            if (field.toString().contains("xpath"))
                out = ((String) field).split("xpath: ")[1];
            else if (field.toString().contains("css"))
                out = ((String) field).split("css: ")[1];
            return out.trim().substring(0, out.length() - 1);
        }
    }
}


