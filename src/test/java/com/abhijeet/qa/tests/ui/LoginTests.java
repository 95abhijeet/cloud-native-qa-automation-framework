package com.abhijeet.qa.tests.ui;

import com.abhijeet.qa.base.BaseTest;
import com.abhijeet.qa.pages.LoginPage;
import com.abhijeet.qa.utils.TestDataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

// import static org.testng.Assert.assertTrue;

public class LoginTests extends BaseTest {

    // TC-UI-L-001
    @Description("Verify successful login with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void loginWithValidCredentials() {

        Map<String, Object> user = TestDataUtil.getUserData("valid_username");
        LoginPage loginPage = new LoginPage(page);

        loginPage.navigateToLoginPage();
        loginPage.login(user.get("username").toString(), user.get("password").toString());

        Assert.assertTrue(
                page.url().contains("inventory"),
                "User should be redirected to Product Catalog page after successful login"
        );
    }

    // TC-UI-L-002
    @Description("Verify login fails with invalid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void loginWithInvalidCredentials() {

        Map<String, Object> user = TestDataUtil.getUserData("invalid_username");
        LoginPage loginPage = new LoginPage(page);

        loginPage.navigateToLoginPage();
        loginPage.login(user.get("username").toString(), user.get("password").toString());

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