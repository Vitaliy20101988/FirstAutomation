package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public void turnScreen(ScreenOrientation orientation) {
        driver.rotate(orientation);
    }

    public void swipeLeft(By elementBy, String error_message) {
        WebElement element = waitForElementPresentBy(
                elementBy,
                error_message,
                10);
        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();

        int middleY = (upperY + lowerY) / 2;

        TouchAction touchAction = new TouchAction(driver);
        touchAction
                .press(rightX, middleY)
                .waitAction(150)
                .moveTo(leftX, middleY)
                .release()
                .perform();


    }

    public List<WebElement> convertByToWebElement(List<By> elements) {
        List<WebElement> webElements = new ArrayList<>();
        for (By tmp : elements) {
            webElements.add(waitForElementPresentBy(tmp,
                    "element " + tmp + " is not displayed"));
        } return webElements;
    }

    public List<By> getSearchLinksList() {
        List<By> presentElementsList = new ArrayList<>();
        for (By tmp : getSearchingLinksXpathes()) {
            try {
                waitForElementPresentBy(tmp,
                        tmp + " is not present.");
                presentElementsList.add(tmp);
            } catch (TimeoutException e) {
                return presentElementsList;
            }
        } return presentElementsList;
    }

    public List<By> getSearchingLinksXpathes() {
        List<By> elementsList = new ArrayList<>();
        for (int i = 1; i < 21; i+=2) {
            elementsList.add(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title' and @instance='" + i + "']"));
        }
        return elementsList;
    }

    // WAITS //

    public WebElement waitForElementPresentBy(By byElement, String error_message, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(byElement)
        );
    }

    public WebElement waitVisability(By byElement, String error_message, long timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message);
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(byElement)
        );
    }

    public WebElement waitForElementPresentBy(By byElement, String error_message) {
        return waitForElementPresentBy(byElement, error_message, 5);
    }

    public boolean waitForElementIsNotPresent(By byElement, String error_message) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.withMessage(error_message);
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(byElement)
        ) ;
    }

    public WebElement waitForElementAndClick(By elementBy, String error_message) {
        WebElement element = waitVisability(elementBy, error_message, 10);
        element.click();
        return element;
    }

    public WebElement waitForElementAndEnterData(By elementBy, String value, String error_message) {
        WebElement element = waitVisability(elementBy, error_message, 10);
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

    public void assertElementHasText(By elementBy, String attributeName, String expectedText, String error_message) {
        WebElement element = waitForElementPresentBy(elementBy, error_message);
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

    public void assertElementPresent(By elementBy, String error_message) {
        try {
            Assert.assertTrue(error_message,
                    driver.findElement(elementBy).isDisplayed());
        } catch (TimeoutException | NoSuchElementException e) {
            Assert.fail(error_message);
        }
    }

    public void assertElementNotPresent(By elementBy, String error_message) {
        try {
            Assert.assertTrue(error_message,
                    waitForElementIsNotPresent(elementBy, error_message));
        } catch (TimeoutException e) {
            Assert.fail(error_message);
        }
    }

    public void assertElementsPresent(List<By> elements, String error_message) {
        for (By tmp : elements) {
            assertElementPresent(tmp, error_message);
        }
    }
}
