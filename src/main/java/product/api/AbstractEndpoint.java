package product.api;

import lombok.Getter;

import static config.ProjectProperties.PRODUCT_API_URL;

@Getter
public abstract class AbstractEndpoint {

    protected abstract String getPartialUrl();

    public String getUrl() {
        return PRODUCT_API_URL.get() + this.getPartialUrl();
    }
}
