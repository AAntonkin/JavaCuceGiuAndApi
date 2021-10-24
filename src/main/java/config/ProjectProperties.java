package config;

import framework.driver.DriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static config.ProjectProperties.TestEnvironment.PROD;
import static io.github.bonigarcia.wdm.config.DriverManagerType.CHROME;

@Slf4j
@NoArgsConstructor
public enum ProjectProperties {
    APP_USER_PASSWORD,
    APP_USER_NAME,
    PRODUCT_SITE_URL,
    PRODUCT_API_URL,
    DRIVER_MANAGER(DriverManager.class.getName()),
    SCREENSHOT_DIR("target/screenshot"),
    BROWSER_IS_HEADLESS(false, Boolean.class),
    BROWSER_NAME(CHROME, DriverManagerType.class),
    SELENIUM_LOGS_DISABLED(false, Boolean.class),
    MOBILE_DEVICE_NAME("", false),
    ENV(PROD, TestEnvironment.class);

    static private Properties prop = null;

    static {
        for (ProjectProperties property : ProjectProperties.values()) {
            Object value = property.get();
            if ((value == null || StringUtils.isBlank(value.toString())) && property.mustBeSetUpped) {
                log.error("'{}' parameter is missing", property.name());
                throw new IllegalStateException("Please provide '" + property.name() + "' parameter");
            }
        }
    }

    Class clazz = String.class;
    Boolean mustBeSetUpped = false;
    Object value = getProperty(this.name());

    ProjectProperties(Object defaultValue, Boolean... mustBeSetUpped) {
        this();
        this.value = this.value == null ? defaultValue : this.value;
        this.clazz = this.clazz == null || !this.value.getClass().equals(this.clazz) ? this.value.getClass() : this.clazz;
        if (mustBeSetUpped.length > 0) this.mustBeSetUpped = mustBeSetUpped[0];
        convertValue();
    }

    ProjectProperties(Object defaultValue, Class clazz, Boolean... mustBePresent) {
        this(defaultValue, mustBePresent);
        this.clazz = clazz;
        convertValue();
    }

    private static String getProperty(String propertyName) {
        String propertyValue = System.getProperty(propertyName);
        propertyValue = StringUtils.isBlank(propertyValue) ? getFromConfigFile(propertyName) : propertyValue;
        return StringUtils.isBlank(propertyValue) ? System.getenv(propertyName) : propertyValue;
    }

    @SneakyThrows
    public static String getFromConfigFile(String propertyName) {
        if (prop == null) {
            String propFileName = "project.properties";
            prop = new Properties();
            File file = new File(propFileName);

            if (file.exists()) {
                FileInputStream inputStream = new FileInputStream(file);
                prop.load(inputStream);
                inputStream.close();
            }
        }
        return prop.getProperty(propertyName);
    }

    private void convertValue() {
        switch (this.clazz.getSimpleName()) {
            case "TestEnvironment":
                this.value = TestEnvironment.valueOf(this.value.toString());
                break;
            case "DriverManagerType":
                this.value = DriverManagerType.valueOf(this.value.toString().toUpperCase());
                break;
            case "Boolean":
                this.value = Boolean.parseBoolean(this.value.toString());
                break;
        }
    }

    @Override
    public String toString() {
        if (value == null) value = this.name();
        return value.toString();
    }

    public <T> T get() {
        switch (this) {
            case PRODUCT_SITE_URL:
            case PRODUCT_API_URL:
                if ((this.value == null || this.value.equals(this.name())) && (T) ENV.<TestEnvironment>get() != null)
                    this.value = PRODUCT_SITE_URL == this ? ENV.<TestEnvironment>get().frontUrl : ENV.<TestEnvironment>get().getApiUrl();
                break;
        }
        return (T) this.value;
    }

    public Boolean isSet() {
        switch (this) {
            case MOBILE_DEVICE_NAME:
                return !((String) MOBILE_DEVICE_NAME.get()).isEmpty();
        }
        return this.value != null && !this.value.toString().isEmpty();
    }

    public static Map<String, Object> getMobileOptions() {
        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", MOBILE_DEVICE_NAME.get());
        return mobileEmulation;
    }

    @Getter
    public enum TestEnvironment {
        UAT("<TO SETUP>", "<TO SETUP>"),
        QA("<TO SETUP>", "<TO SETUP>"),
        PROD("https://www.credify.tech", "https://credapi.credify.tech");

        String frontUrl;
        String apiUrl;
        String text;

        TestEnvironment(String frontUrl, String apiUrl) {
            this.frontUrl = frontUrl;
            this.apiUrl = apiUrl;
            this.text = name();
        }
    }
}
