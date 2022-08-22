import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ConfigWebDriver {
    private static final String pathToDriver = System.getProperty("user.dir") + "\\lib\\chromedriver.exe";

    public static WebDriver getDriver(ChromeOptions options) {
        System.setProperty("webdriver.chrome.driver", pathToDriver);
        return new ChromeDriver(options);
    }
    public static WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", pathToDriver);
        return new ChromeDriver();
    }
}
