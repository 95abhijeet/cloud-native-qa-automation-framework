package com.abhijeet.qa.tests.ui;

import com.abhijeet.qa.base.BaseTest;
import com.abhijeet.qa.pages.LoginPage;
import com.abhijeet.qa.pages.ProductPage;
import com.abhijeet.qa.utils.TestDataUtil;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class ProductTests extends BaseTest {

    // TC-UI-V-003
    @Description("Verify mandatory product info and print values for first 4 products")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void verifyProductCatalog() {

        //Login
        Map<String, Object> user = TestDataUtil.getUserData("valid_username");
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToLoginPage();
        loginPage.login(user.get("username").toString(), user.get("password").toString());

        //Inventory Page
        ProductPage productPage = new ProductPage(page);
        productPage.navigateToProductPage();

        int productCount = productPage.getProductCount();
        Assert.assertTrue(productCount >= 4, "At least 4 products should be displayed");

        //Validate first 4 products and print info
        for (int products = 0; products < 4; products++) {
            Map<String, String> info = productPage.getProductInfo(products);

            System.out.println("Product " + (products + 1) + " info:");
            System.out.println("  Name: " + info.get("name"));
            System.out.println("  Description: " + info.get("description"));
            System.out.println("  Price: " + info.get("price"));
            System.out.println("------------------------------------------");

            Assert.assertTrue(productPage.isProductInfoComplete(products),
                    "Product at index" + products + "should have name, description & price");
        }
    }
}
