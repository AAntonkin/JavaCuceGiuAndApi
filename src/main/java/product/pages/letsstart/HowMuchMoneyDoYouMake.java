package product.pages.letsstart;

import framework.elements.sub.StaticTextField;
import framework.elements.sub.TextField;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import product.pages.BasePage;

import static product.localization.Localised.HOW_MUCH_MONEY_DO_YOU_MAKE_IN_A_YEAR;

@Getter
public class HowMuchMoneyDoYouMake extends BasePage {

    public String pageIdText = HOW_MUCH_MONEY_DO_YOU_MAKE_IN_A_YEAR;

    @FindBy(xpath = TITLE_XPATH)
    private StaticTextField HowMuchMoneyDoYouMakeInAYear;

    @FindBy(xpath = "//input[@name='borrowerIncome']")
    private TextField individualAnnualIncome;

    @FindBy(xpath = "//input[@name='borrowerAdditionalIncome']")
    private TextField additionalAnnualIncome;
}
