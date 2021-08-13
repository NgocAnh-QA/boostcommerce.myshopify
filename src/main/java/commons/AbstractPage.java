package commons;

import io.qameta.allure.Step;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class AbstractPage {
    WebDriverWait explicitWait;
    WebElement element;
    JavascriptExecutor jsExecutor;
    Actions action;
    Select select;

    private final Log log = LogFactory.getLog(getClass());

    protected void openPageUrl(WebDriver driver, String url) {
        try {
            driver.get(url);
        } catch (Exception e) {
            log.error("URL cannot opened: " + e.getMessage());
        }
    }

    protected String getCurrentPageUrl(WebDriver driver) {
        String url = "";
        try {
            url = driver.getCurrentUrl();
        } catch (Exception e) {
            log.error("Cannot get current URL: " + e.getMessage());
        }
        return url;
    }

    protected String getCurrentPageTitle(WebDriver driver) {
        String title = "";
        try {
            title = driver.getTitle();
        } catch (Exception e) {
            log.error("Cannot get current Title: " + e.getMessage());
        }
        return title;
    }

    protected String getCurrentPageSource(WebDriver driver) {
        String pageSource = "";
        try {
            pageSource = driver.getPageSource();
        } catch (Exception e) {
            log.error("Cannot get current Page Source: " + e.getMessage());
        }
        return pageSource;
    }

    protected void backToPage(WebDriver driver) {
        try {
            driver.navigate().back();
        } catch (Exception e) {
            log.error("Cannot back to page: " + e.getMessage());
        }
    }

    protected void forwardToPage(WebDriver driver) {
        try {
            driver.navigate().forward();
        } catch (Exception e) {
            log.error("Cannot forward to page: " + e.getMessage());
        }
    }

    protected void refreshCurrentPage(WebDriver driver) {
        try {
            driver.navigate().refresh();
        } catch (Exception e) {
            log.error("Cannot refresh current page: " + e.getMessage());
        }
    }

    protected void acceptAlert(WebDriver driver) {
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            log.error("Cannot accept Alert: " + e.getMessage());
        }
    }

    protected void cancelAlert(WebDriver driver) {
        try {
            driver.switchTo().alert().dismiss();
        } catch (Exception e) {
            log.error("Cannot cancel Alert: " + e.getMessage());
        }
    }

    protected String getTextAlert(WebDriver driver) {
        String textAlert = "";
        try {
            textAlert = driver.switchTo().alert().getText();
        } catch (Exception e) {
            log.error("Cannot get text alert: " + e.getMessage());
        }
        return textAlert;
    }

    protected void setTextAlert(WebDriver driver, String value) {
        try {
            driver.switchTo().alert().sendKeys(value);
        } catch (Exception e) {
            log.error("Cannot set to text alert: " + e.getMessage());
        }
    }

    // Windows
    protected String getWindowID(WebDriver driver) {
        String windowID = "";
        try {
            windowID = driver.getWindowHandle();
        } catch (Exception e) {
            log.error("Cannot get window ID: " + e.getMessage());
        }
        return windowID;
    }

    protected Set<String> getListWindowsID(WebDriver driver) {
        Set<String> listWindowID = new HashSet<String>();
        try {
            listWindowID = driver.getWindowHandles();
        } catch (Exception e) {
            log.error("Cannot get list window ID: " + e.getMessage());
        }
        return listWindowID;
    }

    protected void switchWindowByID(WebDriver driver, String ID) {
        try {
            Set<String> IdWinDow = driver.getWindowHandles();
            for (String a : IdWinDow) {
                if (!a.equals(ID)) {
                    driver.switchTo().window(a);
                }
            }
        } catch (Exception e) {
            log.error("Cannot switch window by ID: " + e.getMessage());
        }
    }

    protected void switchWindowByTitle(WebDriver driver, String expectedTitle) {
        try {
            Set<String> windows = driver.getWindowHandles();
            for (String a : windows) {
                driver.switchTo().window(a);
                if (driver.getTitle().equals(expectedTitle)) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Cannot switch window by title: " + e.getMessage());
        }
    }

    protected void closeAllWindowsWithoutParent(WebDriver driver, String ID) {
        try {
            Set<String> IdWinDows = driver.getWindowHandles();
            for (String a : IdWinDows) {
                if (!a.equals(ID)) {
                    driver.switchTo().window(a);
                    sleepInSecond(2);
                    driver.close();
                }
            }
        } catch (Exception e) {
            log.error("Cannot close all window without parent: " + e.getMessage());
        }
    }

    // Element
    protected WebElement getElement(WebDriver driver, String locator) {
        try {
            element = driver.findElement(getByXpth(locator));
        } catch (Exception e) {
            log.error("Cannot get element: " + e.getMessage());
        }
        return element;
    }

    protected WebElement getElement(WebDriver driver, String locator, String... values) {
        try {
            element = driver.findElement(getByXpth(castToParameter(locator, values)));
        } catch (Exception e) {
            log.error("Cannot get element: " + e.getMessage());
        }
        return element;
    }

    protected List<WebElement> getElements(WebDriver driver, String locator) {
        List<WebElement> listElements = new ArrayList<WebElement>();
        try {
            listElements = driver.findElements(getByXpth(locator));
        } catch (Exception e) {
            log.error("Cannot get list elements: " + e.getMessage());
        }
        return listElements;
    }

    protected List<WebElement> getElements(WebDriver driver, String locator, String... values) {
        List<WebElement> listElements = new ArrayList<WebElement>();
        try {
            listElements = driver.findElements(getByXpth(castToParameter(locator, values)));
        } catch (Exception e) {
            log.error("Cannot get list elements: " + e.getMessage());
        }
        return listElements;
    }

    protected int getSizeElements(WebDriver driver, String locator) {
        int sizeElement = 0;
        try {
            sizeElement = driver.findElements(getByXpth(locator)).size();
        } catch (Exception e) {
            log.error("Cannot get size elements: " + e.getMessage());
        }
        return sizeElement;
    }

    protected int getSizeElements(WebDriver driver, String locator, String... values) {
        int sizeElement = 0;
        try {
            sizeElement = driver.findElements(getByXpth(castToParameter(locator, values))).size();
        } catch (Exception e) {
            log.error("Cannot get size elements: " + e.getMessage());
        }
        return sizeElement;
    }

    protected By getByXpth(String locator) {
        return By.xpath(locator);
    }

    protected void clickToElement(WebDriver driver, String locator) {
        try {
            element = getElement(driver, locator);
            element.click();
        } catch (Exception e) {
            log.error("Cannot click to element: " + e.getMessage());
        }
    }

    protected void clickToElement(WebDriver driver, String locator, String... values) {
        try {
            element = getElement(driver, castToParameter(locator, values));
            element.click();
        } catch (Exception e) {
            log.error("Cannot click to element: " + e.getMessage());
        }
    }

    protected void sendKeyToElement(WebDriver driver, String locator, String textValue) {
        try {
            element = getElement(driver, locator);
            element.clear();
            element.sendKeys(textValue);
        } catch (Exception e) {
            log.error("Cannot senkey to element: " + e.getMessage());
        }
    }

    protected void sendKeyToElement(WebDriver driver, String textValue, String locator, String... values) {
        try {
            element = getElement(driver, castToParameter(locator, values));
            element.clear();
            element.sendKeys(textValue);
        } catch (Exception e) {
            log.error("Cannot senkey to element: " + e.getMessage());
        }
    }

    protected void clickToTextboxTypeNumber(WebDriver driver, String locator, String values) {
        try {
            element = getElement(driver, castToParameter(locator, values));
            for (int i = 0; i < Integer.parseInt(values); i++) {
                clickToElement(driver, locator);
            }
        } catch (Exception e) {
            log.error("Cannot click to textbox by type number: " + e.getMessage());
        }
    }

    protected void clearTextbox(WebDriver driver, String locator, String... values) {
        try {
            element = getElement(driver, castToParameter(locator, values));
            element.clear();
        } catch (Exception e) {
            log.error("Cannot clear textbox: " + e.getMessage());
        }
    }

    protected void clearTextbox(WebDriver driver, String locator) {
        try {
            element = getElement(driver, locator);
            element.clear();
        } catch (Exception e) {
            log.error("Cannot clear textbox: " + e.getMessage());
        }
    }

    protected void selectItemByVisible(WebDriver driver, String textValue, String locator) {
        try {
            element = getElement(driver, locator);
            select = new Select(element);
            select.selectByVisibleText(textValue);
        } catch (Exception e) {
            log.error("Cannot select item by visible: " + e.getMessage());
        }
    }

    protected void selectItemByVisible(WebDriver driver, String textValue, String locator, String... values) {
        try {
            element = getElement(driver, castToParameter(locator, values));
            select = new Select(element);
            select.selectByVisibleText(textValue);
        } catch (Exception e) {
            log.error("Cannot select item by visible: " + e.getMessage());
        }
    }

    protected String getFirstSelectedTextInDropdown(WebDriver driver, String locator) {
        String text = "";
        try {
            element = getElement(driver, locator);
            select = new Select(element);
            text = select.getFirstSelectedOption().getText();
        } catch (Exception e) {
            log.error("Cannot get selected in dropdown: " + e.getMessage());
        }
        return text;
    }

    protected String getFirstSelectedTextInDropdown(WebDriver driver, String locator, String...values) {
        String text = "";
        try {
            element = getElement(driver, castToParameter(locator,values));
            select = new Select(element);
            text = select.getFirstSelectedOption().getText();
        } catch (Exception e) {
            log.error("Cannot get selected in dropdown: " + e.getMessage());
        }
        return text;
    }

    protected boolean isDropdownMultiple(WebDriver driver, String locator) {
        boolean checkMultiple = false;
        try {
            element = getElement(driver, locator);
            select = new Select(element);
            checkMultiple = select.isMultiple();
        } catch (Exception e) {
            log.error("Dropdown multiple error: " + e.getMessage());
        }
        return checkMultiple;
    }

    protected String getElementAttribute(WebDriver driver, String locator, String attributeName) {
        String elementAttribute = "";
        try {
            element = getElement(driver, locator);
            elementAttribute = element.getAttribute(attributeName);
        } catch (Exception e) {
            log.error("Cannot get element attribute: " + e.getMessage());
        }
        return elementAttribute;
    }

    protected String getElementAttribute(WebDriver driver, String locator, String attributeName, String... values) {
        String elementAttribute = "";
        try {
            element = getElement(driver, castToParameter(locator, values));
            elementAttribute = element.getAttribute(attributeName);
        } catch (Exception e) {
            log.error("Cannot get element attribute: " + e.getMessage());
        }
        return elementAttribute;
    }

    protected String getElementText(WebDriver driver, String locator) {
        String elementText = "";
        try {
            element = getElement(driver, locator);
            elementText = element.getText().trim();
        } catch (Exception e) {
            log.error("Cannot get element text: " + e.getMessage());
        }
        return elementText;
    }

    protected String getElementText(WebDriver driver, String locator, String... values) {
        String elementText = "";
        try {
            element = getElement(driver, castToParameter(locator, values));
            elementText = element.getText();
        } catch (Exception e) {
            log.error("Cannot get element text: " + e.getMessage());
        }
        return elementText;
    }

    protected int countElementSize(WebDriver driver, String locator) {
        int elementSize = 0;
        try {
            elementSize = getElements(driver, locator).size();
        } catch (Exception e) {
            log.error("Cannot get elements size: " + e.getMessage());
        }
        return elementSize;
    }

    protected int countElementSize(WebDriver driver, String locator, String... values) {
        int elementSize = 0;
        try {
            elementSize = getElements(driver, castToParameter(locator, values)).size();
        } catch (Exception e) {
            log.error("Cannot get elements size: " + e.getMessage());
        }
        return elementSize;
    }

    protected void checkToCheckBox(WebDriver driver, String locator, String... values) {
        try {
            element = getElement(driver, castToParameter(locator, values));
            if (!element.isSelected()) {
                element.click();
            }
        } catch (Exception e) {
            log.error("Cannot check to checkbox: " + e.getMessage());
        }
    }

    protected void checkToCheckBox(WebDriver driver, String locator) {
        try {
            element = getElement(driver, locator);
            if (!element.isSelected()) {
                element.click();
            }
        } catch (Exception e) {
            log.error("Cannot check to checkbox: " + e.getMessage());
        }
    }

    protected void unCheckToCheckBox(WebDriver driver, String locator) {
        try {
            element = getElement(driver, locator);
            if (element.isSelected()) {
                element.click();
            }
        } catch (Exception e) {
            log.error("Cannot uncheck to checkbox: " + e.getMessage());
        }
    }

    // Undisplay
    protected boolean isElementUnDisplayed(WebDriver driver, String locator) {
        overrideGlobalTimeout(driver, GlobalConstants.SHORT_TIMEOUT);
        List<WebElement> elements = getElements(driver, locator);
        overrideGlobalTimeout(driver, GlobalConstants.LONG_TIMEOUT);
        if (elements.size() == 0) {
            return true;
        } else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean isElementUnDisplayed(WebDriver driver, String locator, String... values) {
        overrideGlobalTimeout(driver, GlobalConstants.SHORT_TIMEOUT);
        List<WebElement> elements = getElements(driver, locator, values);
        overrideGlobalTimeout(driver, GlobalConstants.LONG_TIMEOUT);
        if (elements.size() == 0) {
            return true;
        } else if (elements.size() > 0 && !elements.get(0).isDisplayed()) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean isElementDisplayed(WebDriver driver, String locator) {
        boolean check = true;
        try {
            if (getElement(driver, locator).isDisplayed()) {
                return check;
            }
        } catch (Exception e) {
            check = false;
            log.error("Element is displayed error: " + e.getMessage());
        }
        return check;

    }

    protected boolean isElementDisplayed(WebDriver driver, String locator, String... values) {
        boolean check = true;
        try {
            if (getElement(driver, castToParameter(locator, values)).isDisplayed()) {
                return check;
            }
        } catch (Exception e) {
            check = false;
            log.error("Element is enabled error: " + e.getMessage());
        }
        return check;
    }

    protected boolean isElementEnabled(WebDriver driver, String locator) {
        boolean check = true;
        try {
            if (getElement(driver, locator).isEnabled()) {
                return check;
            }
        } catch (Exception e) {
            check = false;
            log.error("Element is enabled error: " + e.getMessage());
        }
        return check;
    }

    protected boolean isElementSelected(WebDriver driver, String locator) {
        boolean check = true;
        try {
            if (getElement(driver, locator).isSelected()) {
                return check;
            }
            ;
        } catch (Exception e) {
            check = false;
            log.error("Element is selected error: " + e.getMessage());
        }
        return check;
    }

    protected boolean isElementSelected(WebDriver driver, String locator, String... values) {
        boolean check = true;
        try {
            if (getElement(driver, castToParameter(locator, values)).isSelected()) {
                return check;
            }
        } catch (Exception e) {
            check = false;
            log.error("Element is selected error: " + e.getMessage());
        }
        return check;
    }

    protected void switchToFrame(WebDriver driver, String locator) {
        try {
            element = getElement(driver, locator);
            driver.switchTo().frame(element);
        } catch (Exception e) {
            log.error("Cannot switch to frame: " + e.getMessage());
        }
    }

    protected void switchToDefaultContent(WebDriver driver) {
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            log.error("Cannot switch to default content: " + e.getMessage());
        }
    }

    protected void doubleClickToElement(WebDriver driver, String locator) {
        try {
            action = new Actions(driver);
            action.doubleClick(getElement(driver, locator)).perform();
        } catch (Exception e) {
            log.error("Cannot double click to element: " + e.getMessage());
        }
    }

    protected void rightClickToElement(WebDriver driver, String locator) {
        try {
            action = new Actions(driver);
            action.contextClick(getElement(driver, locator)).perform();
        } catch (Exception e) {
            log.error("Cannot right click to element: " + e.getMessage());
        }
    }

    protected void hoverMouseToElement(WebDriver driver, String locator) {
        try {
            action = new Actions(driver);
            action.moveToElement(getElement(driver, locator)).perform();
        } catch (Exception e) {
            log.error("Cannot hover mouse to element: " + e.getMessage());
        }
    }

    protected void hoverMouseToElement(WebDriver driver, String locator, String... value) {
        try {
            action = new Actions(driver);
            action.moveToElement(getElement(driver, String.format(locator, (Object[]) value))).perform();
        } catch (Exception e) {
            log.error("Cannot hover mouse to element: " + e.getMessage());
        }
    }

    protected void clickAndHoldToElement(WebDriver driver, String locator) {
        try {
            action = new Actions(driver);
            action.clickAndHold(getElement(driver, locator)).perform();
        } catch (Exception e) {
            log.error("Cannot click and hold to element: " + e.getMessage());
        }
    }

    protected void dragAnđropElement(WebDriver driver, String sourceLocator, String targetLocator) {
        try {
            action = new Actions(driver);
            action.dragAndDrop(getElement(driver, sourceLocator), getElement(driver, targetLocator)).perform();
        } catch (Exception e) {
            log.error("Cannot drag and drop element: " + e.getMessage());
        }
    }

    protected void sendKeyBoardToElement(WebDriver driver, String locator, Keys key) {
        try {
            action = new Actions(driver);
            action.sendKeys(getElement(driver, locator), key).perform();
        } catch (Exception e) {
            log.error("Cannot send key board to element: " + e.getMessage());
        }
    }

    // Custom dropdown
    protected void selectItemInCustomDropdown(WebDriver driver, String locatorParent, String locatorAllItem, String valueExpected, String locatorExpected) {
        sleepInSecond(2);
        clickToElement(driver, locatorParent);

        waitAllElementsPresence(driver, locatorAllItem);

        List<WebElement> listAllElement = getElements(driver, locatorAllItem);

        for (WebElement itemElement : listAllElement) {
            if (itemElement.getText().equals(valueExpected)) {
                scrollToElementByJS(driver, locatorExpected);

                waitForElementClickable(driver, locatorExpected);
                sleepInSecond(2);

                itemElement.click();
                sleepInSecond(2);
                break;
            }
        }

    }

    protected void multipleSelect(WebDriver driver, String xpathParent, String xpathAllItem, String... expectedValue) {
        // 1. Click vào dropdown cho nó xổ hết tất cả các item ra
        driver.findElement(By.xpath(xpathParent)).click();

        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpathAllItem)));

        List<WebElement> allItems = driver.findElements(By.xpath(xpathAllItem));

        for (WebElement items : allItems) {
            for (String item : expectedValue) {
                if (items.getText().equals(item)) {
                    jsExecutor.executeScript("arguments[0].scrollIntoView(true)", items);
                    sleepInSecond(1);
                    items.click();
                    break;
                }
            }
        }
    }

    protected void selectElementInDropdown(WebDriver driver, String xpathParent, String xpatAllItem, String textExpected) {
        driver.findElement(By.xpath(xpathParent)).click();

        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpatAllItem)));

        List<WebElement> listAllElement = driver.findElements(By.xpath(xpatAllItem));

        for (WebElement itemElement : listAllElement) {
            if (itemElement.getText().equals(textExpected)) {
                jsExecutor.executeScript("arguments[0].scrollIntoView(true)", itemElement);

                explicitWait.until(ExpectedConditions.elementToBeClickable(itemElement));
                sleepInSecond(2);

                itemElement.click();
                sleepInSecond(2);
                break;
            }
        }

    }

    protected void selectItemInDropdown(WebDriver driver, String locator, String itemValue, String... values) {
        element = getElement(driver, locator, values);
        select = new Select(element);
        select.selectByVisibleText(itemValue);

    }

    protected void checkSelectedItem(WebDriver driver, String locatorListItems, String locatorSelectedItem, String locatorResult, String[] expectedValue) {
        List<WebElement> AllItems = getElements(driver, locatorListItems);
        List<WebElement> itemsSelected = getElements(driver, locatorSelectedItem);

        int numberSelected = itemsSelected.size();
        int numberItems = AllItems.size() - 1;

        Assert.assertEquals(numberSelected, expectedValue.length);

        String selectedText = getElementText(driver, locatorResult);
        if (numberSelected <= 3 && numberSelected >= 0) {
            for (String items : expectedValue) {
                if (selectedText.contains(items)) {
                    break;
                }
            }
        } else {
            Assert.assertEquals(getElementText(driver, locatorResult), numberSelected + " of " + numberItems + " selected");
        }
    }

    // Javascript Executor
    protected void sendKeyToElementByJS(WebDriver driver, String locator, String text) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].setAttribute('value','" + text + "')", getElement(driver, locator));
    }

    protected void sendKeyToElementByJS(WebDriver driver, String text, String locator, String... values) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].setAttribute('value','" + text + "')", getElement(driver, String.format(locator, (Object[]) values)));
    }

    protected void clickElementByJS(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click()", getElement(driver, locator));
    }

    protected void navigateToUrlByJS(WebDriver driver, String url) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.location='" + url + "'");
    }

    protected Object getDomainByJS(WebDriver driver) {
        jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript("return document.domain");
    }

    protected Object getTitleByJS(WebDriver driver) {
        jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript("return document.title");
    }

    protected Object getUrlByJS(WebDriver driver) {
        jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript("return document.URL");
    }

    protected Object getInnerTextByJS(WebDriver driver) {
        jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript("return document.documentElement.innerText");
    }

    protected String getHiddentTextByJS(WebDriver driver, String cssLocator) {
        jsExecutor = (JavascriptExecutor) driver;
        return (String) jsExecutor.executeScript("return document.querySelector(\"" + cssLocator + "\").textContent");
    }

    protected Object scrollToElementByJS(WebDriver driver, String locator, String... values) {
        jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript("arguments[0].scrollIntoView(true)", getElement(driver, castToParameter(locator, values)));
    }

    protected Object scrollToElementByJS(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript("arguments[0].scrollIntoView(true)", getElement(driver, locator));
    }

    protected Object scrollToBottomPageByJS(WebDriver driver) {
        jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    protected Object removeAttributeByJS(WebDriver driver, String locator, String attribute) {
        jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript("arguments[0].removeAttribute('" + attribute + "')", getElement(driver, locator));
    }

    protected void highlightElement(WebDriver driver, String locator) {
        element = getElement(driver, locator);
        String originalStyle = element.getAttribute("style");
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    protected void highlightElement(WebDriver driver, String locator, String...value) {
        element = getElement(driver, castToParameter(locator, value));
        String originalStyle = element.getAttribute("style");
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
        sleepInSecond(1);
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
    }

    protected boolean areExpectedTextInInnerText(WebDriver driver, String textExpected) {
        String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
        return textActual.equals(textExpected);
    }

    protected boolean isImageLoaded(WebDriver driver, String locator) {
        getElement(driver, locator);
        boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth !=\"underfined\"&& arguments[0].naturalWidth > 0", getElement(driver, locator));
        if (status) {
            return true;
        } else {
            return false;
        }
    }

    // Wait
    protected void sleepInSecond(long time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void waitForAllElementsVisible(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
        try {
            explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpth(locator)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void waitForAllElementsVisible(WebDriver driver, String locator, String... values) {
        explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
        try {
            explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpth(castToParameter(locator, values))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void waitForElementVisible(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
        try {
            explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpth(locator)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void waitForElementVisible(WebDriver driver, String locator, String... values) {
        explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpth(castToParameter(locator, values))));
    }

    protected void waitForElementClickable(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpth(locator)));
    }

    protected void waitForElementClickable(WebDriver driver, String locator, String... values) {
        explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
        explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpth(castToParameter(locator, values))));
    }

    protected String castToParameter(String locator, String... values) {
        locator = String.format(locator, (Object[]) values);
        return locator;
    }

    protected void waitForElementInvisible(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, GlobalConstants.SHORT_TIMEOUT);
        overrideGlobalTimeout(driver, GlobalConstants.SHORT_TIMEOUT);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpth(locator)));
        overrideGlobalTimeout(driver, GlobalConstants.LONG_TIMEOUT);
    }

    protected void waitAlertPresence(WebDriver driver) {
        explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
        explicitWait.until(ExpectedConditions.alertIsPresent());
    }

    protected void waitElementPresence(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
        explicitWait.until(ExpectedConditions.presenceOfElementLocated(getByXpth(locator)));
    }

    protected void waitAllElementsPresence(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpth(locator)));
    }

    protected void overrideGlobalTimeout(WebDriver driver, long timeOut) {
        driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
    }

    protected List<String> getElementsText(WebDriver driver, String locator) {
        List<WebElement> items = getElements(driver, locator);
        ArrayList<String> listItems = new ArrayList<String>();
        for (WebElement webElement : items) {
            listItems.add(webElement.getText().trim());
        }
        return listItems;
    }

    protected List<String> getElementsText(WebDriver driver, String locator, String...values) {
        List<WebElement> items = getElements(driver, castToParameter(locator, values));
        ArrayList<String> listItems = new ArrayList<String>();
        for (WebElement webElement : items) {
            listItems.add(webElement.getText().trim());
        }
        return listItems;
    }

    protected boolean isResultEqualsKeyword(WebDriver driver, String keyword, String resultLocator) {
        int check = 0;
        List<String> listItems = getElementsText(driver, resultLocator);
        for (String p : listItems) {
            if (p.trim().equals(keyword)) {
                check++;
            }
        }
        if (listItems.size() == check) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean isResultContainsKeyword(WebDriver driver, String keyword, String resultLocator) {
        int check = 0;
        List<String> listItems = getElementsText(driver, resultLocator);
        for (String p : listItems) {
            if (p.trim().contains(keyword)) {
                check++;
            }
        }
        if (listItems.size() == check) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean isResultContainsKeyword(WebDriver driver, String resultLocator, String... valueExpected) {
        int check = 0;
        List<String> listItems = getElementsText(driver, resultLocator);
        String[] arrayItems = new String[listItems.size()];
        arrayItems = listItems.toArray(arrayItems);

        for (int i = 0; i < arrayItems.length; i++) {
            for (int j = 0; j < valueExpected.length; j++) {
                if (arrayItems[i].equals(valueExpected[j])) {
                    check++;
                }
            }
        }
        if (listItems.size() == check) {
            return true;
        } else {
            return false;
        }
    }


    protected void selectInListCheckBox(WebDriver driver, String listLocatorCheckboxItems, String LocatorLabelItems, String... nameOptions) {
        List<WebElement> checkboxList = getElements(driver, listLocatorCheckboxItems);
        List<WebElement> checkboxLabelList = getElements(driver, LocatorLabelItems);

        for (WebElement w : checkboxList) {
            if (w.isSelected()) {
                w.click();
            }
        }
        WebElement[] arrayCheckbox = new WebElement[checkboxList.size()];
        WebElement[] arrayLabel = new WebElement[checkboxLabelList.size()];

        arrayCheckbox = checkboxList.toArray(arrayCheckbox);
        arrayLabel = checkboxLabelList.toArray(arrayLabel);
        for (int i = 0; i < nameOptions.length; i++) {
            for (int j = 0; j < arrayCheckbox.length; j++) {
                if (nameOptions[i].equals(arrayLabel[j].getText())) {
                    if (!arrayCheckbox[j].isSelected()) {
                        arrayCheckbox[j].click();
                    }
                }
            }
        }
        sleepInSecond(2);
    }


    protected boolean isDataDisplayedAtTable(WebDriver driver, String locatorColumnName, String locatorColumn, String columnName, String textValue) {
        boolean check = false;
        int indexOfColumnName = getSizeElements(driver, locatorColumnName, columnName) + 1;
        locatorColumn = castToParameter(locatorColumn, String.valueOf(indexOfColumnName));
        List<String> items = getElementsText(driver, locatorColumn);
        for (String s : items) {
            if (s.contains(textValue)) {
                check = true;
            }
        }
        return check;
    }

    protected boolean isDataStringSortedAscending(WebDriver driver, String locator) {
        ArrayList<String> arrayList = new ArrayList<String>();
        List<WebElement> elementList = driver.findElements(By.xpath(locator));
        for (WebElement element : elementList) {
            arrayList.add(element.getText());
        }
        ArrayList<String> sortedList = new ArrayList<>();
        for (String child : arrayList) {
            sortedList.add(child);
        }
        Collections.sort(sortedList);
        return sortedList.equals(arrayList);

    }

    protected boolean isDataStringSortedDescending(WebDriver driver, String locator) {
        ArrayList<String> arrayList = new ArrayList<String>();
        List<WebElement> elementList = driver.findElements(By.xpath(locator));
        for (WebElement element : elementList) {
            arrayList.add(element.getText());
        }
        ArrayList<String> sortedList = new ArrayList<>();
        for (String child : arrayList) {
            sortedList.add(child);
        }
        Collections.sort(sortedList);
        Collections.reverse(sortedList);
        return sortedList.equals(arrayList);

    }

    protected boolean isDataFloatSortedAscending(WebDriver driver, String locator) {
        ArrayList<Float> arrayList = new ArrayList<Float>();
        List<WebElement> elementList = driver.findElements(By.xpath(locator));
        for (WebElement element : elementList) {
            arrayList.add(Float.parseFloat(element.getText().replace("$", "").replace(",", "").trim()));
        }
        ArrayList<Float> sortedList = new ArrayList<Float>();
        for (Float child : arrayList) {
            sortedList.add(child);
        }
        Collections.sort(sortedList);
        return sortedList.equals(arrayList);
    }

    protected boolean isDataFloatSortedDescending(WebDriver driver, String locator) {
        ArrayList<Float> arrayList = new ArrayList<Float>();
        List<WebElement> elementList = driver.findElements(By.xpath(locator));
        for (WebElement element : elementList) {
            arrayList.add(Float.parseFloat(element.getText().replace("$", "").replace(",", "").trim()));
        }
        ArrayList<Float> sortedList = new ArrayList<Float>();
        for (Float child : arrayList) {
            sortedList.add(child);
        }
        Collections.sort(sortedList);
        Collections.reverse(sortedList);
        return sortedList.equals(arrayList);
    }

    protected boolean isDataDateSortedAscending(WebDriver driver, String locator) {
        ArrayList<Date> arrayList = new ArrayList<Date>();
        List<WebElement> elementList = driver.findElements(By.xpath(locator));
        for (WebElement element : elementList) {
            arrayList.add(convertStringToDate(element.getText()));
        }
        ArrayList<Date> sortedList = new ArrayList<Date>();
        for (Date child : arrayList) {
            sortedList.add(child);
        }
        Collections.sort(sortedList);
        return sortedList.equals(arrayList);
    }


    protected Date convertStringToDate(String dateInString) {
        dateInString = dateInString.replace(".", "");
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
        Date date = null;
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}
