package framework.elements.sub;

import framework.elements.WebElementFacadeEx;
import framework.elements.WebElementFacadeExImpl;
import framework.utils.AutomationException;
import net.serenitybdd.core.annotations.ImplementedBy;

import java.util.Optional;

@ImplementedBy(WebElementFacadeExImpl.class)
public interface CheckBox extends WebElementFacadeEx {

    default boolean isChecked() {
        String _true = "true";
        Optional<Boolean> out = Optional.ofNullable(_true.equals(this.getAttribute("data-checked")));
        if (!out.isPresent())
            out = Optional.ofNullable(_true.equals(this.findBy(this.getXpathExpression() + "/..").getAttribute("data-checked")));
        return out.orElseThrow(() -> new AutomationException("Checkbox [xpath: %s] don't have 'data-checked' attribute", this.getXpathExpression()));
    }

    default WebElementFacadeEx set(Object value) {
        if (value instanceof String)
            value = Boolean.parseBoolean(value.toString());
        boolean currentState = this.isCurrentlyVisible() ? this.isSelected() : this.isChecked();
        if (((Boolean) value) != currentState) {
            this.waitUntilClickable().click();
        }
        return this;
    }
}
