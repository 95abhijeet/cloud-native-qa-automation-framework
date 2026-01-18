package com.abhijeet.qa.tests.ui;

import com.abhijeet.qa.pages.LoginPage;
import com.abhijeet.qa.pages.CartPage;
import com.abhijeet.qa.base.BaseTest;
import com.abhijeet.qa.utils.TestDataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class CartTests extends BaseTest {

    //TC-UI-C-004
    @Description("Verify an item can be added to the shopping cart")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void verifyAddItemToCart() {
        //Login
        Map<String, Object> user = TestDataUtil.getUserData("valid_username");
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToLoginPage();
        loginPage.login(user.get("username").toString(),user.get("password").toString());

        CartPage cartPage = new CartPage(page);

        //Add first product
        cartPage.addItemToCart(0);

        Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart count should be 1");
    }


    //TC-UI-C-005
    @Description("Verify cart icon accurately reflects the number of items")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void verifyCartItemCount() {
        //Login
        Map<String, Object> user = TestDataUtil.getUserData("valid_username");
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToLoginPage();
        loginPage.login(user.get("username").toString(),user.get("password").toString());

        CartPage cartPage = new CartPage(page);

        //Add first 2 items
        cartPage.addItemToCart(0);
        cartPage.addItemToCart(1);

        Assert.assertEquals(cartPage.getCartItemCount(), 2, "Cart count should be 2");
    }
}
