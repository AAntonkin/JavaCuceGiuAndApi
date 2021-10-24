package product.pages.letsstart;

import framework.elements.WebElementFacadeEx;
import framework.elements.sub.Button;
import framework.elements.sub.Dropdown;
import framework.elements.sub.TextField;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import product.pages.BasePage;

import static product.localization.Localised.CHECK_YOUR_RATE;
import static product.localization.Localised.PERSONAL_LOANS_AND_CARDS;

@Getter
public class PersonalLoansAndCards extends BasePage {

    public String pageUrl = "/funnel/nonDMFunnel";
    public String pageIdText = CHECK_YOUR_RATE;
    public String pageTitle = PERSONAL_LOANS_AND_CARDS;

    @FindBy(xpath = SUBMIT_XPATH)
    private WebElementFacadeEx pageID;

    @FindBy(xpath = SUBMIT_XPATH)
    private Button checkYourRate;

    @FindBy(xpath = "//input[@name='desiredAmount']")
    private TextField desiredAmount;

    @FindBy(xpath = "//select[@name='loan-purpose']")
    private Dropdown loanPurpose;
}
