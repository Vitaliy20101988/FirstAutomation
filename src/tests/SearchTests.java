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
}
