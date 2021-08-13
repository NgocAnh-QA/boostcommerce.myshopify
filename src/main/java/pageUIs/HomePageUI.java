package pageUIs;

public class HomePageUI {
    public static final String SEARCH_TEXT_BOX = "//input[@id='boost-pfs-search-box-1']";
    public static final String PRODUCTS_ON_SEARCH_RESULT = "//li[@data-group='products']//li//p[contains(@class,'product-title')]";
    public static final String DYNAMIC_PRODUCT_NAME_ON_SEARCH_RESULT = "//li[@data-group='products']//li//p[contains(@class,'product-title') and contains(.,'%s')]";
}
