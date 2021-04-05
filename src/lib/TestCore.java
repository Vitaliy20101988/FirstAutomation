package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class TestCore extends TestCase {

    private static final String PLATFORM = System.getenv("PLATFORM");
    protected AppiumDriver driver;
    private static String appiumURL = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DesiredCapabilities desiredCapabilities = desiredCapabilities();
        if (PLATFORM.equals("ios")) {
            driver = new IOSDriver(new URL(appiumURL), desiredCapabilities);
        } else if (PLATFORM.equals("Android")) {
            driver = new AndroidDriver(new URL(appiumURL), desiredCapabilities);
        } else throw new Exception("Cannot get run platform from system environment " + PLATFORM);
    }

    @Override
    protected void tearDown() throws Exception{
        driver.quit();
        super.tearDown();
    }

    private DesiredCapabilities desiredCapabilities() throws Exception{
    //    String platform = System.getenv("PLATFORM");
        System.out.println(PLATFORM);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (PLATFORM.equals("android")) {
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "AndroidTestDevice");
            capabilities.setCapability("platformVersion", "8.1");
            capabilities.setCapability("automationName", "Appium");
            capabilities.setCapability("appPackage", "org.wikipedia");
            capabilities.setCapability("appActivity", ".main.MainActivity");
            capabilities.setCapability("app", "/Users/vitaliy/Documents/IdeaProjects/JavaAppiumAutomation/apks/org.wikipedia_50341_apps.evozi.com.apk");
        } else if (PLATFORM.equals("ios")) {
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("deviceName", "iPhone 8");
            capabilities.setCapability("platformVersion", "13.4");
            capabilities.setCapability("app", "/Users/vitaliy/Desktop/Wikipedia.app");
        } else {
            throw new Exception("Cannot get run platform from system environment " + PLATFORM);
        }
        return capabilities;
    }

}
