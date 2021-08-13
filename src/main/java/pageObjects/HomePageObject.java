package pageObjects;

import commons.AbstractPage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pageUIs.HomePageUI;

import java.util.List;

public class HomePageObject extends AbstractPage {
    WebDriver driver;

    public HomePageObject(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Input to search box with value: {0}")
    public void inputToSearchTextBox(String searchData) {
        waitForElementVisible(driver, HomePageUI.SEARCH_TEXT_BOX);
        sendKeyToElement(driver, HomePageUI.SEARCH_TEXT_BOX, searchData);
    }

    @Step("Verify search results are displayed containing {0}")
    public boolean areSearchResultsDisplayedWithSearchData(String searchData) {
        int count =0;
        waitForElementVisible(driver, HomePageUI.PRODUCTS_ON_SEARCH_RESULT);
        List<String> listProductsOnSearchResult = getElementsText(driver, HomePageUI.PRODUCTS_ON_SEARCH_RESULT);
        for (String check : listProductsOnSearchResult) {
            highlightElement(driver, HomePageUI.DYNAMIC_PRODUCT_NAME_ON_SEARCH_RESULT, check);
            if (check.toLowerCase().contains(searchData.toLowerCase())){
                count++;
            }
        }
        if (count == listProductsOnSearchResult.size()){
            return true;
        }
        else {
            return false;
        }
    }

    @Step("Verify number of product rows are displayed greater than 1")
    public boolean areNumberOfProductRowsDisplayedGreaterThan1() {
        waitForElementVisible(driver, HomePageUI.PRODUCTS_ON_SEARCH_RESULT);
        int countActualProducts = countElementSize(driver, HomePageUI.PRODUCTS_ON_SEARCH_RESULT);
        if (countActualProducts > 1){
            return true;
        }
        else {
            return false;
        }
    }
}
