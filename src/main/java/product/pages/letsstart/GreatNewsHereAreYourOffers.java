package product.pages.letsstart;

import framework.elements.sub.StaticTextField;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import product.pages.BasePage;

import static product.localization.Localised.*;

@Getter
public class GreatNewsHereAreYourOffers extends BasePage {

    public String pageUrl = "/funnel/offer-page";
    public String pageIdText = GREAT_NEWS_HERE_ARE_YOUR_OFFERS;
    public String pageTitle = AFFORDABLE_ONLINE_PERSONAL_LOANS_UPGRADE;

    private static final String OFFER_CARD_XPATH = "//div[@data-auto='offer-card-content-submit']";

    @FindBy(xpath = YOUR_LOAN_AMOUNT)
    private StaticTextField yourLoanAmount;

    @FindBy(xpath = "//span[@data-auto='userLoanAmount']")
    private StaticTextField loanAmount;

    @FindBy(xpath = OFFER_CARD_XPATH + "//div[@data-auto='defaultMonthlyPayment']")
    private StaticTextField monthlyPayment;

    @FindBy(xpath = OFFER_CARD_XPATH + "//li[@data-auto='defaultLoanTerm']/div")
    private StaticTextField term;

    @FindBy(xpath = OFFER_CARD_XPATH + "//li[@data-auto='defaultLoanInterestRate']/div")
    private StaticTextField interestRate;

    @FindBy(xpath = OFFER_CARD_XPATH + "//div[@data-auto='defaultAPR']")
    private StaticTextField APR;
}
