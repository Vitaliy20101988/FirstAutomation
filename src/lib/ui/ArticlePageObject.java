package lib.ui;

import io.appium.java_client.AppiumDriver;

public class ArticlePageObject extends MainPageObject {

    private static final String
        SAVE_BUTTON = "xpath://*[@text='Save']",
        ADD_TO_LIST_BTN = "id:org.wikipedia:id/snackbar_action",
        CREATE_NEW_BTN = "xpath://*[@text='Create new']",
        NAME_OF_LIST_INPUT = "xpath://*[@resource-id='org.wikipedia:id/custom']//*[@resource-id='org.wikipedia:id/text_input_container' and @instance='2']",
        OK_BTN = "xpath://*[@text='OK']",
        ARTICLE_NAME_TMP = "xpath://*[@text='ARTICLE_NAME']",
        MY_LISTS_BTN = "xpath://*[@text='My lists']",
        ARTICLE_TITLE_TMP = "xpath://android.view.View[@*=\"ARTICLE_NAME\"][1]",
        LIST_NAME_BTN_TMP = "xpath://*[@text='LIST_NAME']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    // TEMPLATE METHODS //

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

    public void clickToArticleByName(String articleName) {
        waitForElementAndClick(getArticleName(articleName), "'" + articleName + "' link is not present");
    }

    public void clickToList(String listName) {
        waitForElementAndClick(getListName(listName), "'" + listName + "' link is not present");
    }

    public void clickSaveBtn() {
        waitForElementAndClick(SAVE_BUTTON, "Cannot find element with text 'Save'");
    }

    public void clickAddToListBtn() {
        waitForElementAndClick(ADD_TO_LIST_BTN, "Cannot find element 'ADD TO LIST'");
    }

    public void clickCreateNewBtn() {
        waitForElementAndClick(CREATE_NEW_BTN, "'Create New' btn is not present");
    }

    public void enterDataNameOfList(String listName) {
        waitForElementAndEnterData(NAME_OF_LIST_INPUT, listName, "Input field 'name of list is not displayed'");
    }

    public void clickOKBtn() {
        waitForElementAndClick(OK_BTN, "'OK' btn is not present");
    }

    public void verifyArticleIsDisplayed(String articleName) {
        waitForElementPresentBy(getArticleName(articleName), "Cannot find '" + articleName);
        assertElementPresent(getArticleName(articleName), "'" + articleName + "' is not present");
    }

    public void clickMyListsBtn() {
        waitForElementAndClick(MY_LISTS_BTN, "'My lists' btn is not present");
    }

    public void deleteArticle(String articleName) {
        swipeLeft(getArticleName(articleName), "Cannot delete article '" + articleName + "'");
    }

    public void verifyArticleTitleHasText(String articleName, String expectedArticleTitle) {
        assertElementHasText(getArticleTitleTmp(articleName), "name", expectedArticleTitle,
                "Text is not contain '" + expectedArticleTitle + "'");
    }

    public void verifyArticleTitleIsPresent(String articleName) {
        assertElementPresent(getArticleTitleTmp(articleName), "Name of article is not present");
    }
}
