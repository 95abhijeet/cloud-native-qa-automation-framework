package com.abhijeet.qa.tests.ui;

import com.abhijeet.qa.base.BaseTest;
import com.abhijeet.qa.pages.LoginPage;
import com.abhijeet.qa.pages.CartPage;
import com.abhijeet.qa.pages.CheckoutPage;

import com.abhijeet.qa.utils.TestDataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Map;

public class CheckoutTests extends BaseTest{

    //TC-UI-P-006
    @Description("Verify successful completion of the checkout process")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void verifySuccessfulCheckout() {

        //Login
        Map<String, Object> user = TestDataUtil.getUserData("valid_username");
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToLoginPage();
        loginPage.login(user.get("username").toString(),user.get("password").toString());

        CartPage cartPage = new CartPage(page);
        cartPage.addItemToCart(0);

        CheckoutPage checkoutPage = new CheckoutPage(page);
        checkoutPage.goToCart();
        checkoutPage.startCheckout();
        checkoutPage.enterCheckoutInformation("Test", "User", "90210");

        Assert.assertTrue(page.url().contains("checkout-step-two"),
                "User should be on checkout overview page");
    }

    //TC-UI-P-007
    @Description("Verify the checkout completion page displays a success message")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void verifyCheckoutCompletion() {

        //Login
        Map<String, Object> user = TestDataUtil.getUserData("valid_username");
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToLoginPage();
        loginPage.login(user.get("username").toString(),user.get("password").toString());

        CartPage cartPage = new CartPage(page);
        cartPage.addItemToCart(0);

        Map<String, Object> profile = TestDataUtil.getUserData("user_profile");
        CheckoutPage checkoutPage = new CheckoutPage(page);
        checkoutPage.goToCart();
        checkoutPage.startCheckout();
        checkoutPage.enterCheckoutInformation(
                profile.get("first_name").toString(),
                profile.get("last_name").toString(),
                profile.get("zip").toString()
        );
        checkoutPage.finishCheckout();

        String successMsg = checkoutPage.getSuccessMessage();
        System.out.println("Success message displayed: " + successMsg);

        Assert.assertTrue(successMsg.toLowerCase().contains("thank you"),
                "Checkout success message should be displayed");
    }
}
