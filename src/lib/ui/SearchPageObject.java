package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SearchPageObject extends MainPageObject {

    private static final String
        SEARCH_INIT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_container']//*[contains(@text, 'Search Wikipedia')]",
        CLOSE_BTN = "org.wikipedia:id/search_close_btn",
        FIRST_SEARCHING_LINK = "//*[@resource-id='org.wikipedia:id/page_list_item_title' and @instance='3']";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement initSearchLine() {
        return waitForElementPresentBy(By.xpath(SEARCH_INIT_ELEMENT), "Search input is not displayed");
    }

    public void enterDataToSearchInput(String text) {
        String err_msg = "Search input is not displayed";
        waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), err_msg);
        waitForElementAndEnterData(By.xpath(SEARCH_INIT_ELEMENT), text, err_msg);
    }

    public void verifySearchLinksArePresent() {
        assertElementsPresent(getSearchLinksList(), "Element is not present");
    }

    public void clickCloseBtn() {
        waitForElementAndClick(By.id(CLOSE_BTN), "Cannot click 'CLOSE' button.");
    }

    public void verifySearchResultDisappeared() {
        assertElementNotPresent(By.xpath(FIRST_SEARCHING_LINK), "'Search link' is present");
    }

    public void verifySearchLinksContainsText(String text) {
        assertElementsContainsText(convertByToWebElement(getSearchLinksList()), text, "Element does not contain text "  + text);
    }
}


