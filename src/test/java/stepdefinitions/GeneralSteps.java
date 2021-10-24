package stepdefinitions;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.SessionMap;
import net.thucydides.core.steps.ScenarioSteps;
import product.Handlers.GenericHandler;
import product.pages.AbstractPage;

public class GeneralSteps extends ScenarioSteps {

    protected SessionMap<Object, Object> session = Serenity.getCurrentSession();

    protected GenericHandler genericHandler;

    public <T extends AbstractPage> T goToPage(String pageName) {
        AbstractPage page = genericHandler.getWindow(pageName);
        page.openPage();
        return (T) page;
    }
}
