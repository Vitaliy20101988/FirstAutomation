package tests;

import lib.TestCore;
import lib.ui.HomePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class SearchTests extends TestCore {

    private SearchPageObject searchPage;

    protected void setUp() throws Exception {
        super.setUp();
        this.searchPage = new SearchPageObject(driver);
        HomePageObject homePage = new HomePageObject(driver);

        homePage.clickSkipBtn();
    }

    @Test
    public void testSearch() {
        String text = "Search Wikipedia";
        WebElement search_input = searchPage.initSearchLine();
        searchPage.verifySearchInputHasText(search_input, text);
    }

    @Test
    public void testCancelSearch() {
        searchPage.enterDataToSearchInit("Java");
        searchPage.verifySearchLinksArePresent();
        searchPage.clickCloseBtn();
        searchPage.verifySearchResultDisappeared();
    }

    @Test
    public void testSearchResponse() {
        String textValue = "Java";
        searchPage.enterDataToSearchInit(textValue);
        searchPage.verifySearchLinksContainsText(textValue);
    }

    @Test
    public void testFindArticleByNameAndDescription() {
        String textValue = "Java";
        String expected_first_Article_Title = "Java (programming language)";
        String expected_first_Article_Description = "Object-oriented programming language";
        String expected_second_Article_Title = "Java";
        String expected_second_Article_Description = "Indonesian island";
        String expected_third_Article_Title = "JavaScript";
        String expected_third_Article_Description = "High-level programming language";
        searchPage.enterDataToSearchInit(textValue);
        searchPage.getArticleTitleAndDescription(expected_first_Article_Title, expected_first_Article_Description);
        searchPage.getArticleTitleAndDescription(expected_second_Article_Title, expected_second_Article_Description);
        searchPage.getArticleTitleAndDescription(expected_third_Article_Title, expected_third_Article_Description);
    }
}
