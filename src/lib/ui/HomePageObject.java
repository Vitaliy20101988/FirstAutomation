package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class HomePageObject extends MainPageObject {

    private static final String SKIP_BTN = "//*[contains(@text, 'SKIP')]";

    public HomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void clickSkipBtn() {
        waitForElementAndClick(By.xpath(SKIP_BTN), "'SKIP' button is not displayed.");
    }
}
