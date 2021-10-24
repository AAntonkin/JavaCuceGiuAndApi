package product.pages.letsstart;

import framework.elements.sub.RadioButton;
import framework.elements.sub.StaticTextField;
import framework.elements.sub.TextField;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import product.pages.BasePage;

import static product.localization.Localised.*;

@Getter
public class LetsGetStartedWithBasicInformation extends BasePage {

    public String pageUrl = null;
    public String pageIdText = LETS_GET_STARTED_WITH_SOME_BASIC_INFORMATION;

    @FindBy(xpath = TITLE_XPATH)
    private StaticTextField letsGetStarted;

    @FindBy(xpath = "//div[@class='section row']//label[*[contains(text(),'" + INDIVIDUAL + "')]]")
    private RadioButton individual;

    @FindBy(xpath = "//div[@class='section row']//label[*[contains(.,'" + JOINT_APPLICATION + "')]]")
    private RadioButton jointApplication;

    @FindBy(xpath = "//input[@name='borrowerFirstName']")
    private TextField firstName;

    @FindBy(xpath = "//input[@name='borrowerLastName']")
    private TextField lastName;

    @FindBy(xpath = "//input[@name='borrowerStreet']")
    private TextField homeAddress;

    @FindBy(xpath = "//input[@name='borrowerCity']")
    private TextField city;

    @FindBy(xpath = "//input[@name='borrowerState']")
    private TextField state;

    @FindBy(xpath = "//input[@name='borrowerZipCode']")
    private TextField zipCode;

    @FindBy(xpath = "//input[@name='borrowerDateOfBirth']")
    private TextField dateOfBirth;
}
