package framework.driver.options;

import org.openqa.selenium.PageLoadStrategy;

public interface IGeneralOptions<T> {
    T setHeadless(boolean headless);

    T setPageLoadStrategy(PageLoadStrategy strategy);

    T addArguments(String... arguments);

    T turnMobileView();
}
