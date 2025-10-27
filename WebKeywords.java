package com.automation.keywords;

import com.automation.config.ConfigManager;
import com.automation.drivers.DriverManager;
import com.automation.utilities.ScreenshotUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.List;

/**
 * Web Keywords Library for common web automation actions
 * Provides reusable keywords for web testing
 */
public class WebKeywords {
    private static WebDriverWait wait;
    
    /**
     * Initialize WebDriver wait
     */
    private static void initializeWait() {
        if (wait == null) {
            wait = new WebDriverWait(DriverManager.getDriver(), 
                Duration.ofSeconds(ConfigManager.getExplicitWait()));
        }
    }
    
    /**
     * Open browser and navigate to URL
     * @param browserName Browser name
     * @param url URL to navigate
     */
    public static void openBrowser(String browserName, String url) {
        DriverManager.initializeDriver(browserName);
        navigateToURL(url);
    }
    
    /**
     * Open browser with default configuration
     * @param url URL to navigate
     */
    public static void openBrowser(String url) {
        openBrowser(ConfigManager.getBrowser(), url);
    }
    
    /**
     * Navigate to URL
     * @param url URL to navigate
     */
    public static void navigateToURL(String url) {
        DriverManager.getDriver().get(url);
    }
    
    /**
     * Close browser
     */
    public static void closeBrowser() {
        DriverManager.quitDriver();
    }
    
    /**
     * Find element by locator
     * @param locator Element locator
     * @return WebElement
     */
    public static WebElement findElement(By locator) {
        initializeWait();
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    /**
     * Find element by locator string
     * @param locatorType Locator type (id, name, xpath, css, etc.)
     * @param locatorValue Locator value
     * @return WebElement
     */
    public static WebElement findElement(String locatorType, String locatorValue) {
        By locator = getByLocator(locatorType, locatorValue);
        return findElement(locator);
    }
    
    /**
     * Find multiple elements
     * @param locator Element locator
     * @return List of WebElements
     */
    public static List<WebElement> findElements(By locator) {
        initializeWait();
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return DriverManager.getDriver().findElements(locator);
    }
    
    /**
     * Click element
     * @param locator Element locator
     */
    public static void clickElement(By locator) {
        initializeWait();
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }
    
    /**
     * Click element by locator string
     * @param locatorType Locator type
     * @param locatorValue Locator value
     */
    public static void clickElement(String locatorType, String locatorValue) {
        By locator = getByLocator(locatorType, locatorValue);
        clickElement(locator);
    }
    
    /**
     * Enter text in element
     * @param locator Element locator
     * @param text Text to enter
     */
    public static void enterText(By locator, String text) {
        initializeWait();
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Enter text by locator string
     * @param locatorType Locator type
     * @param locatorValue Locator value
     * @param text Text to enter
     */
    public static void enterText(String locatorType, String locatorValue, String text) {
        By locator = getByLocator(locatorType, locatorValue);
        enterText(locator, text);
    }
    
    /**
     * Get text from element
     * @param locator Element locator
     * @return Element text
     */
    public static String getText(By locator) {
        WebElement element = findElement(locator);
        return element.getText();
    }
    
    /**
     * Get text by locator string
     * @param locatorType Locator type
     * @param locatorValue Locator value
     * @return Element text
     */
    public static String getText(String locatorType, String locatorValue) {
        By locator = getByLocator(locatorType, locatorValue);
        return getText(locator);
    }
    
    /**
     * Select dropdown option by visible text
     * @param locator Dropdown locator
     * @param optionText Option text to select
     */
    public static void selectDropdownByText(By locator, String optionText) {
        WebElement dropdown = findElement(locator);
        Select select = new Select(dropdown);
        select.selectByVisibleText(optionText);
    }
    
    /**
     * Select dropdown option by value
     * @param locator Dropdown locator
     * @param optionValue Option value to select
     */
    public static void selectDropdownByValue(By locator, String optionValue) {
        WebElement dropdown = findElement(locator);
        Select select = new Select(dropdown);
        select.selectByValue(optionValue);
    }
    
    /**
     * Check if element is present
     * @param locator Element locator
     * @return true if element is present
     */
    public static boolean isElementPresent(By locator) {
        try {
            DriverManager.getDriver().findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if element is visible
     * @param locator Element locator
     * @return true if element is visible
     */
    public static boolean isElementVisible(By locator) {
        try {
            WebElement element = DriverManager.getDriver().findElement(locator);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Wait for element to be visible
     * @param locator Element locator
     * @param timeoutSeconds Timeout in seconds
     */
    public static void waitForElementVisible(By locator, int timeoutSeconds) {
        WebDriverWait customWait = new WebDriverWait(DriverManager.getDriver(), 
            Duration.ofSeconds(timeoutSeconds));
        customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for element to be clickable
     * @param locator Element locator
     * @param timeoutSeconds Timeout in seconds
     */
    public static void waitForElementClickable(By locator, int timeoutSeconds) {
        WebDriverWait customWait = new WebDriverWait(DriverManager.getDriver(), 
            Duration.ofSeconds(timeoutSeconds));
        customWait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Scroll to element
     * @param locator Element locator
     */
    public static void scrollToElement(By locator) {
        WebElement element = findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    /**
     * Hover over element
     * @param locator Element locator
     */
    public static void hoverOverElement(By locator) {
        WebElement element = findElement(locator);
        Actions actions = new Actions(DriverManager.getDriver());
        actions.moveToElement(element).perform();
    }
    
    /**
     * Double click element
     * @param locator Element locator
     */
    public static void doubleClickElement(By locator) {
        WebElement element = findElement(locator);
        Actions actions = new Actions(DriverManager.getDriver());
        actions.doubleClick(element).perform();
    }
    
    /**
     * Right click element
     * @param locator Element locator
     */
    public static void rightClickElement(By locator) {
        WebElement element = findElement(locator);
        Actions actions = new Actions(DriverManager.getDriver());
        actions.contextClick(element).perform();
    }
    
    /**
     * Get page title
     * @return Page title
     */
    public static String getPageTitle() {
        return DriverManager.getDriver().getTitle();
    }
    
    /**
     * Get current URL
     * @return Current URL
     */
    public static String getCurrentUrl() {
        return DriverManager.getDriver().getCurrentUrl();
    }
    
    /**
     * Refresh page
     */
    public static void refreshPage() {
        DriverManager.getDriver().navigate().refresh();
    }
    
    /**
     * Navigate back
     */
    public static void navigateBack() {
        DriverManager.getDriver().navigate().back();
    }
    
    /**
     * Navigate forward
     */
    public static void navigateForward() {
        DriverManager.getDriver().navigate().forward();
    }
    
    /**
     * Take screenshot
     * @param testName Test name for screenshot file
     * @return Screenshot file path
     */
    public static String takeScreenshot(String testName) {
        return ScreenshotUtility.captureScreenshot(DriverManager.getDriver(), testName);
    }
    
    /**
     * Execute JavaScript
     * @param script JavaScript code
     * @return Script execution result
     */
    public static Object executeJavaScript(String script) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        return js.executeScript(script);
    }
    
    /**
     * Switch to frame by index
     * @param frameIndex Frame index
     */
    public static void switchToFrame(int frameIndex) {
        DriverManager.getDriver().switchTo().frame(frameIndex);
    }
    
    /**
     * Switch to frame by name or id
     * @param frameNameOrId Frame name or id
     */
    public static void switchToFrame(String frameNameOrId) {
        DriverManager.getDriver().switchTo().frame(frameNameOrId);
    }
    
    /**
     * Switch to default content
     */
    public static void switchToDefaultContent() {
        DriverManager.getDriver().switchTo().defaultContent();
    }
    
    /**
     * Convert locator string to By object
     * @param locatorType Locator type
     * @param locatorValue Locator value
     * @return By locator
     */
    private static By getByLocator(String locatorType, String locatorValue) {
        switch (locatorType.toLowerCase()) {
            case "id":
                return By.id(locatorValue);
            case "name":
                return By.name(locatorValue);
            case "class":
            case "classname":
                return By.className(locatorValue);
            case "tag":
            case "tagname":
                return By.tagName(locatorValue);
            case "xpath":
                return By.xpath(locatorValue);
            case "css":
            case "cssselector":
                return By.cssSelector(locatorValue);
            case "linktext":
                return By.linkText(locatorValue);
            case "partiallinktext":
                return By.partialLinkText(locatorValue);
            default:
                throw new IllegalArgumentException("Locator type not supported: " + locatorType);
        }
    }
}
