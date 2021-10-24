package product.pages.letsstart;

import framework.elements.sub.*;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import product.pages.BasePage;

import static product.localization.Localised.LAST_STEP_BEFORE_YOU_GET_YOUR_RATE;

@Getter
public class LastStepBeforeYouGetYourRate extends BasePage {

    public String pageIdText = LAST_STEP_BEFORE_YOU_GET_YOUR_RATE;

    @FindBy(xpath = TITLE_XPATH)
    private StaticTextField HowMuchMoneyDoYouMakeInAYear;

    @FindBy(xpath = "//input[@name='username']")
    private TextField emailAddress;

    @FindBy(xpath = "//input[@name='password']")
    private TextField password;

    @FindBy(xpath = "//input[@name='agreements']/..")
    private CheckBox agreement;

    @FindBy(xpath = "//a[@data-auto='TERMS_OF_USE-Link']")
    private Link termOfUse;

    @FindBy(xpath = "//a[@data-auto='ESIGN-Link']")
    private Link esignActConsent;

    @FindBy(xpath = "//a[@data-auto='CREDIT_PROFILE_AUTHORIZATION-Link']")
    private Link creditProfileAuthorization;

    @FindBy(xpath = "//a[@data-auto='PRIVACY_POLICY-Link']")
    private Link privacyPolicy;

    @FindBy(xpath = SUBMIT_XPATH)
    private Button checkYourRate;
}
