package product.pages;

import lombok.Getter;

import static product.localization.Localised.AFFORDABLE_ONLINE_PERSONAL_LOANS_UPGRADE;
import static product.localization.Localised.YOUVE_BEEN_SUCCESSFULLY_LOGGED_OUT_SEE_YOU_LATER;

@Getter
public class SeeYouLater extends BasePage {
    public String pageUrl = "/funnel/logout";
    public String pageIdText = YOUVE_BEEN_SUCCESSFULLY_LOGGED_OUT_SEE_YOU_LATER;
    public String pageTitle = AFFORDABLE_ONLINE_PERSONAL_LOANS_UPGRADE;
}
