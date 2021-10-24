package framework.elements.sub;

import framework.elements.WebElementFacadeEx;
import framework.elements.WebElementFacadeExImpl;
import net.serenitybdd.core.annotations.ImplementedBy;

@ImplementedBy(WebElementFacadeExImpl.class)
public interface Button extends WebElementFacadeEx {

    default void click() {
        ((WebElementFacadeEx) this).click();
    }
}
