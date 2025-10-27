package com.automation.pages;

import com.automation.keywords.WebKeywords;
import org.openqa.selenium.By;

/**
 * Login Page Object Model
 * Contains page elements and actions for login functionality
 */
public class LoginPage {
    
    // Page Elements
    private static final By USERNAME_FIELD = By.id("username");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("loginButton");
    private static final By FORGOT_PASSWORD_LINK = By.linkText("Forgot Password?");
    private static final By ERROR_MESSAGE = By.className("error-message");
    private static final By SUCCESS_MESSAGE = By.className("success-message");
    private static final By REMEMBER_ME_CHECKBOX = By.id("rememberMe");
    private static final By SIGNUP_LINK = By.linkText("Sign Up");
    
    /**
     * Enter username
     * @param username Username to enter
     */
    public static void enterUsername(String username) {
        WebKeywords.enterText(USERNAME_FIELD, username);
    }
    
    /**
     * Enter password
     * @param password Password to enter
     */
    public static void enterPassword(String password) {
        WebKeywords.enterText(PASSWORD_FIELD, password);
    }
    
    /**
     * Click login button
     */
    public static void clickLoginButton() {
        WebKeywords.clickElement(LOGIN_BUTTON);
    }
    
    /**
     * Click forgot password link
     */
    public static void clickForgotPasswordLink() {
        WebKeywords.clickElement(FORGOT_PASSWORD_LINK);
    }
    
    /**
     * Click remember me checkbox
     */
    public static void clickRememberMeCheckbox() {
        WebKeywords.clickElement(REMEMBER_ME_CHECKBOX);
    }
    
    /**
     * Click signup link
     */
    public static void clickSignupLink() {
        WebKeywords.clickElement(SIGNUP_LINK);
    }
    
    /**
     * Get error message text
     * @return Error message
     */
    public static String getErrorMessage() {
        return WebKeywords.getText(ERROR_MESSAGE);
    }
    
    /**
     * Get success message text
     * @return Success message
     */
    public static String getSuccessMessage() {
        return WebKeywords.getText(SUCCESS_MESSAGE);
    }
    
    /**
     * Check if error message is displayed
     * @return true if error message is visible
     */
    public static boolean isErrorMessageDisplayed() {
        return WebKeywords.isElementVisible(ERROR_MESSAGE);
    }
    
    /**
     * Check if success message is displayed
     * @return true if success message is visible
     */
    public static boolean isSuccessMessageDisplayed() {
        return WebKeywords.isElementVisible(SUCCESS_MESSAGE);
    }
    
    /**
     * Check if login button is enabled
     * @return true if login button is enabled
     */
    public static boolean isLoginButtonEnabled() {
        return WebKeywords.findElement(LOGIN_BUTTON).isEnabled();
    }
    
    /**
     * Perform complete login action
     * @param username Username
     * @param password Password
     */
    public static void performLogin(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    
    /**
     * Perform login with remember me option
     * @param username Username
     * @param password Password
     * @param rememberMe Remember me option
     */
    public static void performLogin(String username, String password, boolean rememberMe) {
        enterUsername(username);
        enterPassword(password);
        
        if (rememberMe) {
            clickRememberMeCheckbox();
        }
        
        clickLoginButton();
    }
    
    /**
     * Clear login form
     */
    public static void clearLoginForm() {
        WebKeywords.enterText(USERNAME_FIELD, "");
        WebKeywords.enterText(PASSWORD_FIELD, "");
    }
    
    /**
     * Wait for login page to load
     */
    public static void waitForPageLoad() {
        WebKeywords.waitForElementVisible(LOGIN_BUTTON, 10);
    }
    
    /**
     * Verify login page elements are present
     * @return true if all required elements are present
     */
    public static boolean verifyPageElements() {
        return WebKeywords.isElementPresent(USERNAME_FIELD) &&
               WebKeywords.isElementPresent(PASSWORD_FIELD) &&
               WebKeywords.isElementPresent(LOGIN_BUTTON);
    }
}
