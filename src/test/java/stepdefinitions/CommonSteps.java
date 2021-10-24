package stepdefinitions;

import framework.elements.WebElementFacadeEx;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.Assertions;
import product.Handlers.DataSupplier;
import product.Handlers.VerificationVariables;
import product.pages.AbstractPage;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static product.Handlers.WaitConditions.IS_NOT_VISIBLE;

@Slf4j
public class CommonSteps extends GeneralSteps {

    @Given("User is on {string} page")
    public void user_is_on_page(String pageName) {
        AbstractPage page = goToPage(pageName);
        boolean pageIsOpened = page.pageIsOpened();
        if (!pageIsOpened) genericHandler.makeScreenshot();
        Assertions.assertThat(pageIsOpened)
                .as("Expected page '%s' was not opened! " + System.lineSeparator() + "Current url: %s", pageName, page.getDriver().getCurrentUrl())
                .isTrue();
    }

    @When("User enters such data in to fields on {string} page:")
    public void user_enters_such_data_in_to_fields_on_personal_loans_and_cards_page(String pageName, DataTable data) {
        AbstractPage page = genericHandler.getWindow(pageName);
        Map<String, String> dataToSet = data.asMap(String.class, String.class);
        for (String key : dataToSet.keySet()) {
            WebElementFacadeEx webElement = genericHandler.getElement(key, page);
            String valueToSet = DataSupplier.parse(dataToSet.get(key));
            log.info("Setting '{}' to '{}' filed", valueToSet, key);
            webElement.set(valueToSet);
        }
    }

    @And("User click/select {string} text/button/link/(check box)/(radio button)/(drop box) on {string} page")
    public void user_click_element_on_page(String elementName, String pageName) {
        AbstractPage page = genericHandler.getWindow(pageName);
        WebElementFacadeEx webElement = genericHandler.getElement(elementName, page);
        log.info("Click on '{}' button on '{}' page", elementName, pageName);
        assertThat(webElement)
                .as("'%s' element was not found on '%s' page", elementName, page)
                .isNotNull();
        page.clickOn(webElement, 5, 2000);
    }

    @And("User click/select {string} text/button/link/(check box)/(radio button)/(drop box) on {string} page and wait for next page to appear")
    public void user_click_element_on_page_and_wait_for_next_page_to_appear(String elementName, String pageName) {
        AbstractPage page = genericHandler.getWindow(pageName);
        WebElementFacadeEx webElement = genericHandler.getElement(elementName, page);
        assertThat(webElement)
                .as("'%s' element was not found on '%s' page", elementName, page)
                .isNotNull();
        int tries = 4;
        log.info("Star clicking!!!!!");
        while (tries-- > 0 && page.pageIsOpened()) {
            log.info("Clicking on '{}' button on '{}' page [Tries left: {}]", elementName, pageName, tries);
            page.clickOn(webElement, 2, 2000);
            page.waitFor(IS_NOT_VISIBLE, 2000, 5);
        }
        log.info("Stoped clicking!!!!!");
        assertThat(page.pageIsOpened())
                .as("Click on '%s' didn't close '%s' page", elementName, pageName)
                .isFalse();
    }

    @Then("User can see such values on {string} page:")
    public void userCanSeeSuchValuesOnLastStepBeforeYouGetYourRatePage(String pageName, DataTable data) {
        AbstractPage page = genericHandler.getWindow(pageName);
        Map<String, String> dataToSet = data.asMap(String.class, String.class);
        for (String key : dataToSet.keySet()) {
            WebElementFacadeEx webElement = genericHandler.getElement(key, page);
            assertThat(webElement)
                    .as("'%s' element was not found on '%s' page", key, page)
                    .isNotNull();
            String valueFromFeatureFile = dataToSet.get(key);
            String parsedValue = DataSupplier.parse(valueFromFeatureFile);
            String actualValue = webElement.getText();
            VerificationVariables genericVerification = VerificationVariables.getByText(parsedValue);
            AbstractStringAssert<?> verify = assertThat(actualValue)
                    .as("'%s' field actual value is '%s'", key, actualValue);
            log.info("Verification: '{}' actual value should be '{}'",actualValue,  parsedValue);
            switch (genericVerification) {
                case EMPTY:
                    verify.isEmpty();
                    break;
                case NOT_EMPTY:
                    verify.isNotEmpty();
                    break;
                default:
                    verify.isEqualTo(parsedValue);
                    break;
            }
            session.put(key, actualValue);
        }
    }

    @Given("User see that {string} page is opened")
    public void user_see_that_page_is_opened(String pageName) {
        AbstractPage page = genericHandler.getWindow(pageName);
        assertThat(page.pageIsOpened())
                .as("'%s' page was not opened", pageName)
                .isTrue();
    }
}
