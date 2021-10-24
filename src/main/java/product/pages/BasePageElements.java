package product.pages;

import framework.elements.WebElementFacadeEx;
import lombok.Getter;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;

@Getter
public final class BasePageElements extends AbstractPage {

    @FindBy(xpath = "//div[not(@data-fetching='false') and @data-fetching='true']")
    private WebElementFacade preloadingIndicator;

    @Override
    public WebElementFacadeEx getPageID() {
        return null;
    }
}
