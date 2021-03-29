package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class ArticlePageObject extends MainPageObject {

    private static final String
        ARTICLE_NAME_IN_SEARCH_RESULT_TMP = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='ARTICLE_NAME']",
        SAVE_BUTTON = "//*[@text='Save']",
        ADD_TO_LIST_BTN = "org.wikipedia:id/snackbar_action",
        CREATE_NEW_BTN = "//*[@text='Create new']",
        NAME_OF_LIST_INPUT = "//*[@resource-id='org.wikipedia:id/custom']//*[@resource-id='org.wikipedia:id/text_input_container' and @instance='2']",
        OK_BTN = "//*[@text='OK']",
        ARTICLE_NAME_TMP = "//*[@text='ARTICLE_NAME']",
        MY_LISTS_BTN = "//*[@text='My lists']",
        ARTICLE_TITLE_TMP = "//android.view.View[@*=\"ARTICLE_NAME\"][1]",
        LIST_NAME_BTN_TMP = "//*[@text='LIST_NAME']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    // TEMPLATE METHODS //
    public String getArticleNameInSearchResult(String articleName) {
        return ARTICLE_NAME_IN_SEARCH_RESULT_TMP.replace("ARTICLE_NAME", articleName);
    }

    public String getArticleName(String articleName) {
        return ARTICLE_NAME_TMP.replace("ARTICLE_NAME", articleName);
    }

    public String getListName(String articleName) {
        return LIST_NAME_BTN_TMP.replace("LIST_NAME", articleName);
    }

    public String getArticleTitleTmp(String articleName) {
        return ARTICLE_TITLE_TMP.replace("ARTICLE_NAME", articleName);
    }
    // TEMPLATE METHODS //

    public void clickToArticleInSearchList(String articleName) {
        waitForElementAndClick(By.xpath(getArticleNameInSearchResult(articleName)), "'" + articleName + "' link is not present");
    }

    public void clickToArticleByName(String articleName) {
        waitForElementAndClick(By.xpath(getArticleName(articleName)), "'" + articleName + "' link is not present");
    }

    public void clickToList(String listName) {
        waitForElementAndClick(By.xpath(getListName(listName)), "'" + listName + "' link is not present");
    }

    public void clickSaveBtn() {
        waitForElementAndClick(By.xpath(SAVE_BUTTON), "Cannot find element with text 'Save'");
    }

    public void clickAddToListBtn() {
        waitForElementAndClick(By.id(ADD_TO_LIST_BTN), "Cannot find element 'ADD TO LIST'");
    }

    public void clickCreateNewBtn() {
        waitForElementAndClick(By.xpath(CREATE_NEW_BTN), "'Create New' btn is not present");
    }

    public void enterDataNameOfList(String listName) {
        waitForElementAndEnterData(By.xpath(NAME_OF_LIST_INPUT), listName, "Input field 'name of list is not displayed'");
    }

    public void clickOKBtn() {
        waitForElementAndClick(By.xpath(OK_BTN), "'OK' btn is not present");
    }

    public void verifyArticleIsDisplayed(String articleName) {
        waitForElementPresentBy(By.xpath(getArticleName(articleName)), "Cannot find '" + articleName);
        assertElementPresent(By.xpath(getArticleName(articleName)), "'" + articleName + "' is not present");
    }

    public void clickMyListsBtn() {
        waitForElementAndClick(By.xpath(MY_LISTS_BTN), "'My lists' btn is not present");
    }

    public void deleteArticle(String articleName) {
        swipeLeft(By.xpath(getArticleName(articleName)), "Cannot delete article '" + articleName + "'");
    }

    public void verifyArticleTitleHasText(String articleName, String expectedArticleTitle) {
        assertElementHasText(By.xpath(getArticleTitleTmp(articleName)), "name", expectedArticleTitle,
                "Text is not contain '" + expectedArticleTitle + "'");
    }

    public void verifyArticleTitleIsPresent(String articleName) {
        assertElementPresent(By.xpath(getArticleTitleTmp(articleName)), "Name of article is not present");
    }
}
