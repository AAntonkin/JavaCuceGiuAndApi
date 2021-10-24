package product.pages;

import framework.elements.sub.TextField;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;

import static product.localization.Localised.SIGN_IN_UPGRADE;
import static product.localization.Localised.WELCOME_BACK;

@Getter
public class Login extends BasePage {

    public String pageUrl = "/portal/login";
    public String pageIdText = WELCOME_BACK;
    public String pageTitle = SIGN_IN_UPGRADE;

    @FindBy(xpath = "//input[@name='username']")
    private TextField emailAddress;

    @FindBy(xpath = "//input[@name='password']")
    private TextField password;

    @FindBy(xpath = SUBMIT_XPATH)
    private TextField signInToYourAccount;
}
