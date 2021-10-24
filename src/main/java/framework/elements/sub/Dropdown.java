package framework.elements.sub;

import framework.elements.WebElementFacadeEx;
import framework.elements.WebElementFacadeExImpl;
import net.serenitybdd.core.annotations.ImplementedBy;

@ImplementedBy(WebElementFacadeExImpl.class)
public interface Dropdown extends WebElementFacadeEx {

    default WebElementFacadeEx set(Object value) {
        this.select().byVisibleText(value.toString());
        return this;
    }
}
