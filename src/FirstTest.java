import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void cancelSearch() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'SKIP')]"),
                "'SKIP' button is not displayed.");

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//*[contains(@text, 'Search Wikipedia')]"),
                "search input is not displayed"
        );

        waitForElementAndEnterData(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "search input is not displayed"
        );

        assertElementsPresent(getSearchLinksList(),
                "element is not present");

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "search input is not displayed"
        );

        assertElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @instance='3']"),
                ""
        );
    }

    @Test
    public void verifySearchResponse() {
        String textValue = "Java";
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'SKIP')]"),
                "'SKIP' button is not displayed.");

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//*[contains(@text, 'Search Wikipedia')]"),
                "search input is not displayed"
        );

        waitForElementAndEnterData(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//*[contains(@text, 'Search Wikipedia')]"),
                textValue,
                "search input is not displayed"
        );
        assertElementsContainsText(convertByToWebElement(getSearchLinksList()),
                textValue,
                "Element does not contain text"
                );
    }

    private List<WebElement> convertByToWebElement(List<By> elements) {
        List<WebElement> webElements = new ArrayList<>();
        for (By tmp : elements) {
            webElements.add(waitForElementPresentBy(tmp,
                    "element " + tmp + " is not displayed"));
        } return webElements;
    }

    private List<By> getSearchLinksList() {
        List<By> presentElementsList = new ArrayList<>();
        for (By tmp : getSearchingLinksXpathes()) {
            try {
                waitForElementPresentBy(tmp,
                        "");
                presentElementsList.add(tmp);
            } catch (TimeoutException e) {
                return presentElementsList;
            }
        } return presentElementsList;
    }

    private List<By> getSearchingLinksXpathes() {
        List<By> elementsList = new ArrayList<>();
        for (int i = 1; i < 21; i+=2) {
            elementsList.add(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @instance='" + i + "']"));
        }
        return elementsList;
    }

    // WAITS //

    private WebElement waitForElementPresentBy(By byElement, String error_message, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(byElement)
        );
    }

    private WebElement waitForElementPresentBy(By byElement, String error_message) {
        return waitForElementPresentBy(byElement, error_message, 5);
    }

    private boolean waitForElementIsNotPresent(By byElement, String error_message) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.withMessage(error_message);
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(byElement)
        ) ;
    }

    private WebElement waitForElementAndClick(By elementBy, String error_message) {
        WebElement element = waitForElementPresentBy(elementBy, error_message);
        element.click();
        return element;
    }

    private WebElement waitForElementAndEnterData(By elementBy, String value, String error_message) {
        WebElement element = waitForElementPresentBy(elementBy, error_message);
        element.sendKeys(value);
        return element;
    }

    // ASSERTS //
    private void assertElementHasText(WebElement element, String expectedText, String error_message) {
        Assert.assertEquals(
                error_message,
                expectedText,
                element.getAttribute("text")
        );
    }

    private void assertElementContainsText(WebElement element, String expectedText, String error_message) {
        Assert.assertTrue(
                error_message,
                element.getAttribute("text").contains(expectedText)
        );
    }

    private void assertElementsContainsText(List<WebElement> elements, String expectedText, String error_message) {
        for (WebElement tmp : elements) {
            assertElementContainsText(tmp, expectedText, error_message);
        }
    }

    private void assertElementPresent(By elementBy, String error_message) {
        waitForElementPresentBy(elementBy, error_message);
        try {
            Assert.assertTrue(error_message,
                    driver.findElement(elementBy).isDisplayed());
        } catch (TimeoutException e) {
            Assert.fail(error_message);
        }
    }

    private void assertElementNotPresent(By elementBy, String error_message) {
        try {
            Assert.assertTrue(error_message,
                    waitForElementIsNotPresent(elementBy, error_message));
        } catch (TimeoutException e) {
            Assert.fail(error_message);
        }
    }

    private void assertElementsPresent(List<By> elements, String error_message) {
        for (By tmp : elements) {
            assertElementPresent(tmp, error_message);
        }
    }

}