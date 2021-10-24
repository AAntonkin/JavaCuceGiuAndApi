package stepdefinitions;

import framework.utils.StringParser;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import product.Handlers.DataSupplier;
import product.api.AbstractEndpoint;
import testparsers.EndpointData;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GeneralApiSteps extends GeneralSteps {

    @Given("User make Post call to {string} endpoint with such data:")
    public void userMakeGetCallToByLeadSecretEndpoint(String endpointName, EndpointData data) {
        AbstractEndpoint endpoint = genericHandler.getEndpoint(endpointName);
        session.put(endpointName, given()
                .when()
                .log()
                .all()
                .headers(data.getHeaders())
                .body(data.getBody())
                .post(endpoint.getUrl())
                .then()
        );
    }

    @Then("User see response from {string} endpoint contains such data in any place:")
    public void userSeeResponseFromByLeadSecretEndpointWithSuchData(String endpointName, DataTable data) {
        JsonPath jsonPath = ((ValidatableResponse) session.get(endpointName)).extract().body().jsonPath();
        Map<String, String> dataToSet = data.asMap(String.class, String.class);
        for (String key : dataToSet.keySet()) {
            String expectedData = DataSupplier.parse(dataToSet.get(key));
            String actualData = DataSupplier.findValue(jsonPath.get(), key);
            assertThat(actualData)
                    .as("Response from '%s' doesn't contain '%s' field", endpointName, key)
                    .isNotEmpty();
            if (expectedData.contains("*"))
                assertThat(StringParser.delete(actualData,"*"))
                        .as("Value of '%s' field (from '%s' endpoint) is not equal to '%s'", key, endpointName, expectedData)
                        .matches(expectedData);
            else
                assertThat(actualData)
                        .as("Value of '%s' field (from '%s' endpoint) is not equal to '%s'", key, endpointName, expectedData)
                        .isEqualTo(expectedData);
        }
    }

    @Then("User see that response from {string} endpoint contains {string} with {string} value")
    public void userSeeResponseFromByLeadSecretEndpointWithSuchData(String endpointName, String fieldName, String value) {

    }

    @Then("User see response status from {string} endpoint is {int}")
    public void userSeeResponseStatusFromByLeadSecretEndpointIs(String endpointName, int statusCode) {
        ((ValidatableResponse) session.get(endpointName))
                .assertThat()
                .statusCode(statusCode);
    }
}
