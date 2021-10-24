package product.api.bodyparts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class LoanAppResumptionInfo {

    @JsonProperty("loanAppId")
    int loanAppId;

    @JsonProperty("loanAppUuid")
    String loanAppUuid;

    @JsonProperty("referrer")
    String referrer;

    @JsonProperty("status")
    String status;

    @JsonProperty("productType")
    String productType;

    @JsonProperty("sourceSystem")
    String sourceSystem;

    @JsonProperty("desiredAmount")
    double desiredAmount;

    @JsonProperty("borrowerResumptionInfo")
    BorrowerResumptionInfo borrowerResumptionInfo;

    @JsonProperty("coBorrowerResumptionInfo")
    Object coBorrowerResumptionInfo;

    @JsonProperty("turnDown")
    boolean turnDown;

    @JsonProperty("hasLogin")
    boolean hasLogin;

    @JsonProperty("availableAppImprovements")
    List<Object> availableAppImprovements;

    @JsonProperty("cashOutAmount")
    Object cashOutAmount;

    @JsonProperty("canAddCollateral")
    boolean canAddCollateral;

    @JsonProperty("rewardProgramId")
    Object rewardProgramId;

    @JsonProperty("rewardProgramCode")
    Object rewardProgramCode;

    @JsonProperty("addon")
    Object addon;

    @JsonProperty("isMobileDiscountApplied")
    Object isMobileDiscountApplied;

    @JsonProperty("checkingDiscountAvailable")
    boolean checkingDiscountAvailable;
}