package product.api.bodyparts;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BorrowerResumptionInfo {

    @JsonProperty("firstName")
    String firstName;

    @JsonProperty("maskedEmail")
    String maskedEmail;

    @JsonProperty("ssnRequired")
    boolean ssnRequired;

    @JsonProperty("state")
    String state;

    @JsonProperty("email")
    String email;
}
