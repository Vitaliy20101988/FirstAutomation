import lib.TestCore;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FirstTest extends TestCore {

    private MainPageObject mainPageObject;
    private SearchPageObject searchPageObject;
    private ArticlePageObject articlePage;

    protected void setUp() throws Exception {
        super.setUp();
        mainPageObject = new MainPageObject(driver);
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'SKIP')]"),
                "'SKIP' button is not displayed.");

        this.mainPageObject = new MainPageObject(driver);
        this.searchPageObject = new SearchPageObject(driver);
        this.articlePage = new ArticlePageObject(driver);
    }

    @Test
    public void testSearch() {
        WebElement search_input = searchPageObject.initSearchLine();
        mainPageObject.assertElementHasText(search_input, "Search Wikipedia",
                "Search Wikipedia is not displayed.");
    }

    @Test
    public void testCancelSearch() {
        searchPageObject.enterDataToSearchInput("Java");
        searchPageObject.verifySearchLinksArePresent();
        searchPageObject.clickCloseBtn();
        searchPageObject.verifySearchResultDisappeared();
    }

    @Test
    public void testSearchResponse() {
        String textValue = "Java";
        searchPageObject.enterDataToSearchInput(textValue);
        searchPageObject.verifySearchLinksContainsText(textValue);
    }

    @Test
    public void testTwoArticles() {
        String text = "Java";
        String java_link_name = "Java (programming language)";
        String list_name = "java";
        String second_article = "Appium";
        searchPageObject.enterDataToSearchInput(text);
        articlePage.clickToArticleInSearchList(java_link_name);
        articlePage.clickSaveBtn();
        articlePage.clickAddToListBtn();
        articlePage.clickCreateNewBtn();
        articlePage.enterDataNameOfList(list_name);
        articlePage.clickOKBtn();

        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"),
                "'Backspace' btn is not present"
        );

        mainPageObject.waitForElementAndEnterData(
                By.id("org.wikipedia:id/search_src_text"),
                second_article,
                "input field 'Search Wikipedia is not present"
        );

        articlePage.clickToArticleInSearchList(second_article);
        articlePage.clickSaveBtn();
        articlePage.clickAddToListBtn();

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@class='android.view.ViewGroup']//*[@text='" + list_name + "']"),
                "'" + list_name + "' list is not present"
        );

        mainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"),
                "'Backspace' btn is not present"
        );

        searchPageObject.clickCloseBtn();

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_toolbar']//*[@class='android.widget.ImageButton']"),
                "'Backspace' btn is not present"
        );

        articlePage.clickMyListsBtn();

        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/menu_search_lists"),
                "'menu search list' btn is not present"
        );

        articlePage.clickToList(list_name);
        articlePage.deleteArticle(java_link_name);
        articlePage.verifyArticleIsDisplayed(second_article);
        articlePage.clickToArticleByName(second_article);

        mainPageObject.assertElementHasText(
                By.xpath("//android.view.View[@*=\"Appium\"][1]"),
                "name",
                second_article,
                "Text is not contain '" + second_article + "'"
        );
    }

    @Test
    public void testAssertTitle() {

        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//*[contains(@text, 'Search Wikipedia')]"),
                "search input is not displayed"
        );

        mainPageObject.waitForElementAndEnterData(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "search input is not displayed"
        );

        String java_link_name = "Java (programming language)";
        mainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + java_link_name + "']"),
                "'" + java_link_name + "' link is not present"
        );

        mainPageObject.assertElementPresent(
                By.xpath("//android.view.View[@content-desc='" + java_link_name + "']"),
                "Name of article is not present"
        );
    }
}