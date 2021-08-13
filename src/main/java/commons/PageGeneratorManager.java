package commons;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pageObjects.HomePageObject;

public class PageGeneratorManager {
    @Step("Open Home Page")
    public static HomePageObject getHomePage(WebDriver driver) {
        return new HomePageObject(driver);
    }

}

