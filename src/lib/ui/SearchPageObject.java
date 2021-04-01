package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchPageObject extends MainPageObject {

    private static final String
            ARTICLE_NAME_AND_DESCRIPTION_IN_SEARCH_RESULT_TMP = "xpath://*[@text='ARTICLE_NAME']/following-sibling::*[@text='ARTICLE_DESCRIPTION']",
    // TODO: fixed this xpath
            ARTICLE_NAME_IN_SEARCH_RESULT_TMP = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='ARTICLE_NAME']",
        SEARCH_INIT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_container']//*[contains(@text, 'Search Wikipedia')]",
        SEARCH_INPUT = "id:org.wikipedia:id/search_src_text",
        CLOSE_BTN = "id:org.wikipedia:id/search_close_btn",
        NAVIGATE_UP_BTN = "xpath://android.widget.ImageButton[@content-desc=\"Navigate up\"]",
        BACKSPACE_BTN = "xpath://*[@resource-id='org.wikipedia:id/search_toolbar']//*[@class='android.widget.ImageButton']",
        FIRST_SEARCHING_LINK = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title' and @instance='3']";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    // TEMPLATE METHODS //
    public String getArticleNameInSearchResult(String articleName) {
        return ARTICLE_NAME_IN_SEARCH_RESULT_TMP.replace("ARTICLE_NAME", articleName);
    }

    public String getArticleTitleAndDescription(String title, String description) {
        return ARTICLE_NAME_AND_DESCRIPTION_IN_SEARCH_RESULT_TMP.replace("ARTICLE_NAME", title).replace("ARTICLE_DESCRIPTION", description);
    }
    // TEMPLATE METHODS //

    public WebElement initSearchLine() {
        return waitForElementPresentBy(SEARCH_INIT_ELEMENT, "Search input is not displayed");
    }

    public void clickToArticleInSearchList(String articleName) {
        waitForElementAndClick(getArticleNameInSearchResult(articleName), "'" + articleName + "' link is not present");
    }

    public void waitForElementByTitleAndDescription(String title, String description){
        String error_msg = "Article with title: " + title + " and description: " + description + "is not present";
        waitVisibility(getArticleTitleAndDescription(title, description), error_msg, 10);
    }

    public void enterDataToSearchInit(String text) {
        String err_msg = "Search input is not displayed";
        waitForElementAndClick(SEARCH_INIT_ELEMENT, err_msg);
        waitForElementAndEnterData(SEARCH_INIT_ELEMENT, text, err_msg);
    }

    public void enterDataToSearchInput(String text) {
        String err_msg = "Search input is not displayed";
        waitForElementAndClick(SEARCH_INPUT, err_msg);
        waitForElementAndEnterData(SEARCH_INPUT, text, err_msg);
    }

    public void verifySearchLinksArePresent() {
        assertElementsPresent(getSearchLinksList(), "Element is not present");
    }

    public void verifySearchInputHasText(WebElement search_input, String expectedText) {
        assertElementHasText(search_input, "Search Wikipedia",
                "Search Wikipedia is not displayed.");
    }

    public void clickCloseBtn() {
        waitForElementAndClick(CLOSE_BTN, "Cannot click 'CLOSE' button.");
    }

    public void clickBackspaceBtn() {
        waitForElementAndClick(BACKSPACE_BTN, "Cannot click 'Backspace' button."); }

    public void clickNavigateUpBtn() {
        waitForElementAndClick(NAVIGATE_UP_BTN, "Cannot click 'NavigateUP' button.");
    }

    public void verifySearchResultDisappeared() {
        assertElementNotPresent(FIRST_SEARCHING_LINK, "'Search link' is present");
    }

    public void verifySearchLinksContainsText(String text) {
        assertElementsContainsText(convertByToWebElement(getSearchLinksList()), text, "Element does not contain text "  + text);
    }

    public List<String> getSearchingLinksXpathes() {
        List<String> elementsList = new ArrayList<>();
        for (int i = 1; i < 21; i+=2) {
            elementsList.add("xpath://*[@resource-id='org.wikipedia:id/page_list_item_title' and @instance='" + i + "']");
        }
        return elementsList;
    }

    public List<String> getSearchLinksList() {
        List<String> presentElementsList = new ArrayList<>();
        for (String tmp : getSearchingLinksXpathes()) {
            try {
                waitForElementPresentBy(tmp,
                        tmp + " is not present.");
                presentElementsList.add(tmp);
            } catch (TimeoutException e) {
                return presentElementsList;
            }
        } return presentElementsList;
    }
}


