package product.pages;

import framework.elements.WebElementFacadeEx;
import framework.elements.sub.Button;
import framework.elements.sub.Link;
import framework.elements.sub.StaticTextField;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

import static product.localization.Localised.CHECK_YOUR_RATE_FOR_A_PERSONAL_LOAN_UPGRADE;

@Getter
public class BasePage extends AbstractPage {

    public String pageUrl = null;
    public String pageTitle = CHECK_YOUR_RATE_FOR_A_PERSONAL_LOAN_UPGRADE;

    protected static final String TITLE_XPATH = "//*[contains(@class,'title') and contains(@class,'secondary')]";
    protected static final String SUBMIT_XPATH = "//button[@type='submit']";

    @FindBy(xpath = TITLE_XPATH)
    protected WebElementFacadeEx pageID;

    @FindBy(xpath = SUBMIT_XPATH)
    protected Button Continue;

    @FindBy(xpath = "//header//div[@class='header-nav']")
    protected StaticTextField menu;

    @FindBy(xpath = "//header//a[@href='/funnel/logout']")
    protected Link signOut;

    @FindBy(xpath = "//header//a[@href='/portal/login']")
    protected Link signIn;
}
