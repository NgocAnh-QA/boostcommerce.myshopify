package shopify.user;

import commons.AbstractTest;
import commons.PageGeneratorManager;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.HomePageObject;

@Feature("Search Test")
public class Search extends AbstractTest {
    private WebDriver driver;
    private HomePageObject homePage;
    private  String searchData;

    @Parameters({ "browser", "url" })
    @BeforeClass
    public void beforeClass(String browserName, String urlValue) {
        log.info("Search - Pre condition: Open browser and go to url");
        driver = getBrowserDriver(browserName, urlValue);
        homePage = PageGeneratorManager.getHomePage(driver);
        searchData = "top";
    }

    @Story("Search with valid data")
    @Test
    public void TC_01_Search_With_Valid_Data() {
        log.info("Search - Step 01: Input to the search box");
        homePage.inputToSearchTextBox(searchData);

        log.info("Search - Step 02: Verify product rows on return to the search result containing search data");
        verifyTrue(homePage.areSearchResultsDisplayedWithSearchData(searchData));

        log.info("Search - Step 03: Verify Number of product rows > 1");
        verifyTrue(homePage.areNumberOfProductRowsDisplayedGreaterThan1());

    }
    @AfterClass
    public void afterClass() {
        log.info("Search - Post condition: Close browser and quit driver");
        closeBrowserAndDriver(driver);
    }

}
