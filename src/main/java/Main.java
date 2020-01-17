import java.net.URL;
import java.net.MalformedURLException;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Main {


    //curl -u "mykolasagaidakov1:QXqJtq6eaFns8N4AEhgy" -X POST "https://api-cloud.browserstack.com/app-automate/upload" -F "file=@./app-release.apk"
    //{"app_url":"bs://15124254e6ee751496bfa8ca8c5b60db27994ed8"}
    private static String userName = "mykolasagaidakov1";
    private static String accessKey = "QXqJtq6eaFns8N4AEhgy";
    private static DesiredCapabilities caps;
    public static void main(String args[]) throws MalformedURLException, InterruptedException {
        caps = new DesiredCapabilities();
        AndroidDriver<AndroidElement> driver = setCapsToBrowserStack();
        //AndroidDriver<AndroidElement> driver = setCapsToLocal();

        driver.startActivity(new Activity("com.overonixtest", ".MainActivity"));

        testLogin(driver);

        driver.quit();
    }

    private static void testLogin(AndroidDriver<AndroidElement> driver) {
        AndroidElement loginElement = (AndroidElement) new WebDriverWait(driver, 30).until(
                ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("loginId")));
        loginElement.sendKeys("test@test.com");

        AndroidElement passwordElement = (AndroidElement) new WebDriverWait(driver, 30).until(
                ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("passwordId")));
        passwordElement.sendKeys("test");

        AndroidElement startElement = (AndroidElement) new WebDriverWait(driver, 30).until(
                ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("buttonId")));
        startElement.click();

        AndroidElement result = driver.findElementByClassName("android.widget.TextView");
        assert(result.getText().equals("Test complete"));
    }

    private static AndroidDriver<AndroidElement> setCapsToLocal() throws MalformedURLException {
        caps.setCapability("deviceName", "FA7AC1A05483");
        caps.setCapability("platformName", "Android");
        caps.setCapability("app", "/Users/sagaidak/Downloads/app-release.apk");

        AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("http://0.0.0.0:4723/wd/hub"), caps);

        return driver;
    }

    private static AndroidDriver<AndroidElement> setCapsToBrowserStack() throws MalformedURLException {
        caps.setCapability("device", "Samsung Galaxy S8");
        caps.setCapability("os_version", "7.0");
        caps.setCapability("project", "My First Project");
        caps.setCapability("build", "My First Build");
        caps.setCapability("name", "Bstack-[Java] Sample Test");
        caps.setCapability("app", "bs://15124254e6ee751496bfa8ca8c5b60db27994ed8");

        AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("https://"+userName+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub"), caps);

        return driver;
    }
}