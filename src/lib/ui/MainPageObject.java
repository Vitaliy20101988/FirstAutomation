package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public void turnScreen(ScreenOrientation orientation) {
        driver.rotate(orientation);
    }

    public void swipeLeft(String locator, String error_message) {
        WebElement element = waitForElementPresentBy(
                locator,
                error_message,
                10);
        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();

        int middleY = (upperY + lowerY) / 2;

        TouchAction touchAction = new TouchAction(driver);
        touchAction
                .press(PointOption.point(rightX, middleY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(150)))
                .moveTo(PointOption.point(leftX, middleY))
                .release()
                .perform();


    }

    public List<WebElement> convertByToWebElement(List<String> elements) {
        List<WebElement> webElements = new ArrayList<>();
        for (String tmp : elements) {
            webElements.add(waitForElementPresentBy(tmp,
                    "element " + tmp + " is not displayed"));
        } return webElements;
    }

    // WAITS //

    public WebElement waitForElementPresentBy(String locator, String error_message, long timeOutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitVisibility(String locator, String error_message, long timeOutInSeconds) {
        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message);
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementPresentBy(String locator, String error_message) {
        return waitForElementPresentBy(locator, error_message, 5);
    }

    public boolean waitForElementIsNotPresent(String locator, String error_message) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.withMessage(error_message);
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(this.getLocatorByString(locator))
        ) ;
    }

    public WebElement waitForElementAndClick(String locator, String error_message) {
        WebElement element = waitVisibility(locator, error_message, 15);
        element.click();
        return element;
    }

    public WebElement waitForElementAndEnterData(String locator, String value, String error_message) {
        WebElement element = waitVisibility(locator, error_message, 15);
        element.sendKeys(value);
        return element;
    }

    // ASSERTS //
    public void assertElementHasText(WebElement element, String expectedText, String error_message) {
        Assert.assertEquals(
                error_message,
                expectedText,
                element.getAttribute("text")
        );
    }

    public void assertElementHasText(String locator, String attributeName, String expectedText, String error_message) {
        WebElement element = waitForElementPresentBy(locator, error_message);
        Assert.assertEquals(
                "Texts are not equals",
                expectedText,
                element.getAttribute(attributeName)
        );
    }

    public void assertElementContainsText(WebElement element, String expectedText, String error_message) {
        Assert.assertTrue(
                error_message,
                element.getAttribute("text").contains(expectedText)
        );
    }

    public void assertElementsContainsText(List<WebElement> elements, String expectedText, String error_message) {
        for (WebElement tmp : elements) {
            assertElementContainsText(tmp, expectedText, error_message);
        }
    }

    public void assertElementPresent(String locator, String error_message) {
        try {
            Assert.assertTrue(error_message,
                    driver.findElement(this.getLocatorByString(locator)).isDisplayed());
        } catch (TimeoutException | NoSuchElementException e) {
            Assert.fail(error_message);
        }
    }

    public void assertElementNotPresent(String locator, String error_message) {
        try {
            Assert.assertTrue(error_message,
                    waitForElementIsNotPresent(locator, error_message));
        } catch (TimeoutException e) {
            Assert.fail(error_message);
        }
    }

    public void assertElementsPresent(List<String> elements, String error_message) {
        for (String tmp : elements) {
            assertElementPresent(tmp, error_message);
        }
    }

    private By getLocatorByString(String locatorWithType) {
        String[] explodedLocator = locatorWithType.split(Pattern.quote(":"), 2);
        String byType = explodedLocator[0];
        String locator = explodedLocator[1];
        switch (byType) {
            case "xpath":
                return By.xpath(locator);
            case "id":
                return By.id(locator);
            default:
                throw new IllegalArgumentException("Cannot get type of locator: " + locatorWithType);
        }
    }
}
