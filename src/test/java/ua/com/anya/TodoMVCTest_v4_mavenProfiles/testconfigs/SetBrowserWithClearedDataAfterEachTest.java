package ua.com.anya.TodoMVCTest_v4_mavenProfiles.testconfigs;

import com.codeborne.selenide.Configuration;
import org.junit.After;
import org.junit.BeforeClass;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class SetBrowserWithClearedDataAfterEachTest {

    @BeforeClass
    public static void setup(){
        Configuration.browser = System.getProperty("driver.browser");
    }

    @After
    public void clearData(){
        executeJavaScript("localStorage.clear()");
    }
}
