import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
        turnScreen(ScreenOrientation.PORTRAIT);
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

    @Test
    public void saveTwoArticles() throws InterruptedException {
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

        String java_link_name = "Java (programming language)";
        waitForElementAndClick(
                By.xpath("//*[@text='" + java_link_name + "']"),
                "'" + java_link_name + "' link is not present"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Save']"),
                "Cannot find element with text 'Save'"
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find element 'ADD TO LIST'"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Create new']"),
                "'Create list' btn is not present"
        );

        String list_name = "java";
        waitForElementAndEnterData(
                By.xpath("//*[@resource-id='org.wikipedia:id/custom']//*[@resource-id='org.wikipedia:id/text_input_container' and @instance='2']"),
                list_name,
                "Input field 'name of list is not displayed'"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "'OK' btn is not present"
        );

        Thread.sleep(2000);

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"),
                "'Backspace' btn is not present"
        );

      //  Thread.sleep(5000);

//        waitForElementAndClick(
//                By.xpath("//*[@text='Search Wikipedia']"),
//                "'Search Wikipedia input is not present'"
//        );

        String second_article = "Appium";
        waitForElementAndEnterData(
                By.id("org.wikipedia:id/search_src_text"),
                second_article,
                "input field 'Search Wikipedia is not present"
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Appium']"),
                "Appium link is not present"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Save']"),
                "Cannot find element with text 'Save'"
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find element 'ADD TO LIST'"
        );

//        String move_from_saved_to_another_reading_list_btn = "Move from Saved to another reading list";
//        waitForElementAndClick(
//                By.xpath("//*[@text='" + move_from_saved_to_another_reading_list_btn + "']"),
//                "'" + move_from_saved_to_another_reading_list_btn + "' is not present"
//        );

        waitForElementAndClick(
                By.xpath("//*[@class='android.view.ViewGroup']//*[@text='" + list_name + "']"),
                "'" + list_name + "' list is not present"
        );

//        waitForElementAndClick(
//                By.id("org.wikipedia:id/page_toolbar_button_show_overflow_menu"),
//                "show_overflow_menu btn is not present"
//        );


        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"),
                "'Backspace' btn is not present"
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "'Close' btn is not present"
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_toolbar']//*[@class='android.widget.ImageButton']"),
                "'Backspace' btn is not present"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='My lists']"),
                "'My lists' btn is not present"
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/menu_search_lists"),
                "'menu search list' btn is not present"
        );



        waitForElementAndClick(
                By.xpath("//*[@text='" + list_name + "']"),
                "'" + list_name + "' is not present"
        );

        swipeLeft(
                By.xpath("//*[@text='" + java_link_name + "']"),
                ""
        );

        waitForElementPresentBy(
                By.xpath("//*[@text='" + second_article + "']"),
                "'" + second_article + "' is not present");

        assertElementPresent(
                By.xpath("//*[@text='" + second_article + "']"),
                "'" + second_article + "' is not present"
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + second_article + "']"),
                "Cannot click to '" + second_article + "'"
        );

        assertElementPresent(
                By.xpath("(//android.view.View[@content-desc='" + second_article + "'])[1]"),
                "Name of article is not present"
        );

        assertElementHasText(
                By.xpath("(//android.view.View[@content-desc=\"Appium\"])[1]"),
                "content-desc",
                second_article,
                "Text is not contain '" + second_article + "'"
        );



        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void assertTitle() {
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

        String java_link_name = "Java (programming language)";
        waitForElementAndClick(
                By.xpath("//*[@text='" + java_link_name + "']"),
                "'" + java_link_name + "' link is not present"
        );

        assertElementPresent(
                By.xpath("//android.view.View[@content-desc='" + java_link_name + "']"),
                "Name of article is not present"
        );


    }


    private void turnScreen(ScreenOrientation orientation) {
        driver.rotate(orientation);
    }

        private void swipeLeft(By elementBy, String error_message) {
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

    private void assertElementHasText(By elementBy, String attributeName, String expectedText, String error_message) {
        WebElement element = waitForElementPresentBy(elementBy, error_message);
        Assert.assertEquals(
                "Texts are not equals",
                expectedText,
                element.getAttribute(attributeName)
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
        try {
            Assert.assertTrue(error_message,
                    driver.findElement(elementBy).isDisplayed());
        } catch (TimeoutException | NoSuchElementException e) {
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