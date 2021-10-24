package framework.utils;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import static config.ProjectProperties.SCREENSHOT_DIR;
import static framework.utils.Framework.getParentMethodName;

@Slf4j
@NoArgsConstructor
public class ScreenSnipper extends AShot {

    static WebDriver driver;

    public ScreenSnipper(WebDriver driver) {
        this.setDriver(driver);
    }

    private WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver webDriver) {
        driver = webDriver;
    }

    public String getFilePath(String... excludeNames) {
        return getParentMethodName(excludeNames).replace(":", "_");
    }

    @SneakyThrows
    public String makeASnip() {
        return makeASnip(getFilePath("makeASnip", "screenshots", "OverridedException"));
    }

    @SneakyThrows
    public String makeASnip(String fileName) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        File localScreenshots = new File(SCREENSHOT_DIR.<String>get());
        if (!localScreenshots.exists() || !localScreenshots.isDirectory()) {
            localScreenshots.mkdirs();
        }
        String finalName = localScreenshots.getPath() + "/" + fileName + ".t" + timestamp.getTime();
        File newFilePNG = new File(finalName.replace("<", "").replace(">", "") + ".png");
        Screenshot screenshot = this.shootingStrategy(ShootingStrategies.viewportPasting(0)).takeScreenshot(getDriver());
        try {
            ImageIO.write(screenshot.getImage(), "PNG", newFilePNG);
            log.info("SCREENSHOT: {}\n{}", fileName.replace("_", ":").replace("(", "").replace(")", ""), newFilePNG.getPath());
        } catch (NullPointerException e) {
            e.printStackTrace();
            log.info("SCREENSHOT FAILED: got NullPointerException");
        } catch (IOException e) {
            e.printStackTrace();
            log.info("SCREENSHOT FAILED: got IOException: {}", e.getMessage());
        }
        return newFilePNG.getPath();
    }
}
