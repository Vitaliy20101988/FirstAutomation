package lib.ui;

import io.appium.java_client.AppiumDriver;

public class HomePageObject extends MainPageObject {

    private static final String SKIP_BTN = "xpath://*[contains(@text, 'SKIP')]";

    public HomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void clickSkipBtn() {
        waitForElementAndClick(SKIP_BTN, "'SKIP' button is not displayed.");
    }
}
