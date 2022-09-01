import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeWork23Test {
    static final Logger userLogger = LogManager.getLogger(HomeWork23Test.class);
    private WebDriver driver;
    private static final String URL_1 = "https://duckduckgo.com";
    private static final String URL_2 = "https://demo.w3layouts.com/demos_new/template_demo/03-10-2020/photoflash-liberty-demo_Free/685659620/web/index.html?_ga=2.181802926.889871791.1632394818-2083132868.1632394818";
    private static final String URL_3 = "https://otus.ru";

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void testChromeWithHeadless() {
        userLogger.info("Начиню выполнять тест 1");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = ConfigWebDriver.getDriver(options);

        driver.get(URL_1);
        var element = driver.findElement(By.id("search_form_input_homepage"));
        element.sendKeys("ОТУС");
        element.submit();
        element = driver.findElement(By.id("links"));
        var elements = element.findElement(By.id("r1-0"))
                .findElement(By.tagName("h2"))
                .findElement(By.tagName("a"))
                .findElement(By.tagName("span"));
        var att = elements.getText();
        assertThat(att).as("Результат 1 строки не соответствует ожидаемому")
                .contains("Онлайн‑курсы для профессионалов, дистанционное обучение");
        userLogger.info("Тест 1 пройден");
    }

    @Test
    public void testChromeWithKiosk() {
        userLogger.info("Начиню выполнять тест 2");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--kiosk");
        driver = ConfigWebDriver.getDriver(options);
        driver.get(URL_2);
        driver
                .findElement(By.className("w3-gallery"))
                .findElement(By.tagName("div"))
                .findElement(By.className("portfolio-area"))
                .findElements(By.className("portfolio-item2")).get(0)
                .findElement(By.tagName("span"))
                .findElement(By.tagName("a"))
                .findElement(By.className("content-overlay"))
                .click();

        var webElement = driver.switchTo().parentFrame()
                .findElement(By.className("pp_pic_holder"));
        assertThat(webElement.isDisplayed()).as("Модальное окно не открылось").isTrue();
        userLogger.info("Тест 2 пройден");
    }

    @Test
    public void testChromeWithFullScreen() {
        userLogger.info("Начиню выполнять тест 3");
        var login = "ya.osipova.sis+1@gmail.com";
        var password = "Test1!test";

        driver = ConfigWebDriver.getDriver();
        driver.manage().window().maximize();
        driver.get(URL_3);
        driver.findElement(By.cssSelector("button.header2__auth")).click();
        var webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2L));
        var form = webDriverWait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form[@action = '/login/']")));

        var element = form.findElement(By.xpath(".//input[@name = 'email']"));
        element.clear();
        element.sendKeys(login);

        element = form.findElement(By.xpath(".//input[@name = 'password']"));
        element.clear();
        element.sendKeys(password);

        form.findElement(By.xpath(".//button[@type = 'submit']")).submit();

        userLogger.info("Тест 2 пройден: :" + driver.manage().getCookies());
    }

    @AfterEach
    public void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
