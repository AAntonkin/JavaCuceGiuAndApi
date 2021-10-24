package framework.elements.sub;

import framework.elements.WebElementFacadeEx;
import framework.elements.WebElementFacadeExImpl;
import net.serenitybdd.core.annotations.ImplementedBy;

@ImplementedBy(WebElementFacadeExImpl.class)
public interface RadioButton extends WebElementFacadeEx {

    default WebElementFacadeEx set(Object value) {
        if (value instanceof String)
            value = Boolean.parseBoolean(value.toString());
        if (((Boolean) value) != this.isSelected()) {
            this.waitUntilClickable().click();
        }
        return this;
    }
}
