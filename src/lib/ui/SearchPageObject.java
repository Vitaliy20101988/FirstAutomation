package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SearchPageObject extends MainPageObject {

    private static final String
            ARTICLE_NAME_IN_SEARCH_RESULT_TMP = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='ARTICLE_NAME']",

    SEARCH_INIT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_container']//*[contains(@text, 'Search Wikipedia')]",
        SEARCH_INPUT = "org.wikipedia:id/search_src_text",
        CLOSE_BTN = "org.wikipedia:id/search_close_btn",
        NAVIGATE_UP_BTN = "//android.widget.ImageButton[@content-desc=\"Navigate up\"]",
        BACKSPACE_BTN = "//*[@resource-id='org.wikipedia:id/search_toolbar']//*[@class='android.widget.ImageButton']",
        FIRST_SEARCHING_LINK = "//*[@resource-id='org.wikipedia:id/page_list_item_title' and @instance='3']";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    // TEMPLATE METHODS //
    public String getArticleNameInSearchResult(String articleName) {
        return ARTICLE_NAME_IN_SEARCH_RESULT_TMP.replace("ARTICLE_NAME", articleName);
    }

    public String waitForElementByTitleAndDescription(String title, String description) {
        return null;
    }
    // TEMPLATE METHODS //

    public WebElement initSearchLine() {
        return waitForElementPresentBy(By.xpath(SEARCH_INIT_ELEMENT), "Search input is not displayed");
    }

    public void clickToArticleInSearchList(String articleName) {
        waitForElementAndClick(By.xpath(getArticleNameInSearchResult(articleName)), "'" + articleName + "' link is not present");
    }

    public void enterDataToSearchInit(String text) {
        String err_msg = "Search input is not displayed";
        waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), err_msg);
        waitForElementAndEnterData(By.xpath(SEARCH_INIT_ELEMENT), text, err_msg);
    }

    public void enterDataToSearchInput(String text) {
        String err_msg = "Search input is not displayed";
        waitForElementAndClick(By.id(SEARCH_INPUT), err_msg);
        waitForElementAndEnterData(By.id(SEARCH_INPUT), text, err_msg);
    }

    public void verifySearchLinksArePresent() {
        assertElementsPresent(getSearchLinksList(), "Element is not present");
    }

    public void verifySearchInputHasText(WebElement search_input, String expectedText) {
        assertElementHasText(search_input, "Search Wikipedia",
                "Search Wikipedia is not displayed.");
    }

    public void clickCloseBtn() {
        waitForElementAndClick(By.id(CLOSE_BTN), "Cannot click 'CLOSE' button.");
    }

    public void clickBackspaceBtn() {
        waitForElementAndClick(By.xpath(BACKSPACE_BTN), "Cannot click 'Backspace' button."); }

    public void clickNavigateUpBtn() {
        waitForElementAndClick(By.xpath(NAVIGATE_UP_BTN), "Cannot click 'NavigateUP' button.");
    }

    public void verifySearchResultDisappeared() {
        assertElementNotPresent(By.xpath(FIRST_SEARCHING_LINK), "'Search link' is present");
    }

    public void verifySearchLinksContainsText(String text) {
        assertElementsContainsText(convertByToWebElement(getSearchLinksList()), text, "Element does not contain text "  + text);
    }
}


