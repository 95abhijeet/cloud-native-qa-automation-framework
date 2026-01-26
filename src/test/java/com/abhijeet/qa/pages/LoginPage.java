package com.abhijeet.qa.pages;

import com.microsoft.playwright.Page;

public class LoginPage {

    private final Page page;

    // Locators
    private final String usernameField = "#user-name";
    private final String passwordField = "#password";
    private final String signInButton = "#login-button";
    private final String securityAlert = "[data-test='error']";


    public LoginPage(Page page) {
        this.page = page;
    }

    public void navigateToPortal() {
        page.navigate("https://www.saucedemo.com/");
    }

    public void enterUsername(String username) {
        page.fill(usernameField, username);
    }

    public void enterPassword(String password) {
        page.fill(passwordField, password);
    }

    public void clickLogin() {
        page.click(signInButton);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public boolean isSecurityAlertVisible() {
        return page.isVisible(securityAlert);
    }

    public String getAuthenticationFailureMessage() {
        return page.textContent(securityAlert);
    }
}
