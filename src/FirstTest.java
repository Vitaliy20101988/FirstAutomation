import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.1");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/vitaliy/Documents/IdeaProjects/JavaAppiumAutomation/apks/org.wikipedia_50341_apps.evozi.com.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'SKIP')]"),
                "'SKIP' button is not displayed.");

        WebElement search_input = waitForElementPresentBy(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//*[contains(@text, 'Search Wikipedia')]"),
                "search input is not displayed"
        );

        assertElementHasText(search_input, "Search Wikipedia",
                "Search Wikipedia is not displayed.");
    }

    private WebElement waitForElementPresentBy(By byElement, String error_message, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(byElement)
        );
    }

    private WebElement waitForElementPresentBy(By byElement, String error_message) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.withMessage(error_message);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(byElement)
        );
    }

    private WebElement waitForElementAndClick(By elementBy, String error_message) {
        WebElement element = waitForElementPresentBy(elementBy, error_message);
        element.click();
        return element;
    }

    private void assertElementHasText(WebElement element, String expectedText, String error_message) {
        Assert.assertEquals(
                error_message,
                expectedText,
                element.getAttribute("text")
        );
    }
}