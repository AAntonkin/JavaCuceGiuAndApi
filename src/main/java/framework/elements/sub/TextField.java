package framework.elements.sub;

import framework.elements.WebElementFacadeEx;
import framework.elements.WebElementFacadeExImpl;
import net.serenitybdd.core.annotations.ImplementedBy;

@ImplementedBy(WebElementFacadeExImpl.class)
public interface TextField extends WebElementFacadeEx {

    default WebElementFacadeEx set(Object value) {
        this.type(value.toString());
        return this;
    }
}
