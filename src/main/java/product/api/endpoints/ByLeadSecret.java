package product.api.endpoints;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import product.api.AbstractEndpoint;
import product.api.bodyparts.LoanAppResumptionInfo;

import java.util.List;

@Getter
public class ByLeadSecret extends AbstractEndpoint {

    String partialUrl = "/api/brfunnelorch/v2/resume/byLeadSecret";

    @JsonProperty("loanAppResumptionInfo")
    LoanAppResumptionInfo loanAppResumptionInfo;

    @JsonProperty("offers")
    List<Object> offers;

    @JsonProperty("selectedOffer")
    Object selectedOffer;

    @JsonProperty("requiredAgreements")
    List<Object> requiredAgreements;

    @JsonProperty("resetOptions")
    List<String> resetOptions;

    @JsonProperty("originalLoanApp")
    Object originalLoanApp;
}
