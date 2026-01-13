package com.abhijeet.qa.tests.ui;

import com.abhijeet.qa.base.BaseTest;
import com.abhijeet.qa.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

// import static org.testng.Assert.assertTrue;

public class LoginTests extends BaseTest {

    // TC-UI-L-001
    @Test(description = "Verify successful login with valid credentials")
    public void loginWithValidCredentials() {
        LoginPage loginPage = new LoginPage(page);

        loginPage.navigateToLoginPage();
        loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(
                page.url().contains("inventory"),
                "User should be redirected to Product Catalog page after successful login"
        );
    }

    // TC-UI-L-002
    @Test(description = "Verify login fails with invalid credentials")
    public void loginWithInvalidCredentials() {
        LoginPage loginPage = new LoginPage(page);

        loginPage.navigateToLoginPage();
        loginPage.login("standard", "password");

        Assert.assertTrue(
                loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed for invalid login"
        );

        Assert.assertTrue(
                loginPage.getErrorMessageText().toLowerCase().contains("username and password"),
                "Correct error message should be shown"
        );
    }
}