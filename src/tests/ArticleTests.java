package tests;

import lib.TestCore;
import lib.ui.ArticlePageObject;
import lib.ui.HomePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends TestCore {

    private SearchPageObject searchPage;
    private ArticlePageObject articlePage;

    protected void setUp() throws Exception {
        super.setUp();
        this.searchPage = new SearchPageObject(driver);
        this.articlePage = new ArticlePageObject(driver);
        HomePageObject homePage = new HomePageObject(driver);

        homePage.clickSkipBtn();
    }

    @Test
    public void testTwoArticles() {
        String text = "Java";
        String java_link_name = "Java (programming language)";
        String list_name = "java";
        String second_article = "Appium";
        searchPage.enterDataToSearchInit(text);
        searchPage.clickToArticleInSearchList(java_link_name);
        articlePage.clickSaveBtn();
        articlePage.clickAddToListBtn();
        articlePage.clickCreateNewBtn();
        articlePage.enterDataNameOfList(list_name);
        articlePage.clickOKBtn();
        searchPage.clickNavigateUpBtn();
        searchPage.enterDataToSearchInput(second_article);
        searchPage.clickToArticleInSearchList(second_article);
        articlePage.clickSaveBtn();
        articlePage.clickAddToListBtn();
        articlePage.clickToList(list_name);
        searchPage.clickNavigateUpBtn();
        searchPage.clickCloseBtn();
        searchPage.clickBackspaceBtn();
        articlePage.clickMyListsBtn();
        articlePage.clickToList(list_name);
        articlePage.deleteArticle(java_link_name);
        articlePage.verifyArticleIsDisplayed(second_article);
        articlePage.clickToArticleByName(second_article);
        articlePage.verifyArticleTitleHasText(second_article, second_article);
    }

    @Test
    public void testAssertTitle() {
        String searchRequest = "Java";
        String java_link_name = "Java (programming language)";
        searchPage.enterDataToSearchInit(searchRequest);
        searchPage.clickToArticleInSearchList(java_link_name);
        articlePage.verifyArticleTitleIsPresent(java_link_name);
    }
}
