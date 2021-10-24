package product.pages.selecttypes;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum LoanPurpose {
    DISABLED,
    CREDIT_CARD("Pay off Credit Cards"),
    DEBT_CONSOLIDATION("Debt Consolidation"),
    SMALL_BUSINESS("Business"),
    HOME_IMPROVEMENT("Home Improvement"),
    LARGE_PURCHASE("Large Purchase"),
    OTHER("Other");

    String description;

    LoanPurpose(String description) {
        this.description = description;
    }

}
