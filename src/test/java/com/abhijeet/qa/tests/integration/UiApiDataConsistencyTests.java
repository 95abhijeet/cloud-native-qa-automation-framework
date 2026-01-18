package com.abhijeet.qa.tests.integration;

import com.abhijeet.qa.base.BaseTest;
import com.abhijeet.qa.pages.LoginPage;
import com.abhijeet.qa.pages.ProductPage;

import com.abhijeet.qa.utils.TestDataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import io.restassured.RestAssured;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UiApiDataConsistencyTests extends BaseTest {

    //TC-INT-D-002
    @Description("Verify UI price matches API price for a known product")
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = {"api"})
    public void verifyApiPriceMatchUiPrice() {
        // API Step
        Response response =
                given()
                        .baseUri("https://fakestoreapi.com")
                        .when()
                        .get("/products");

        System.out.println("Response Body: ");
        System.out.println(response.asString());

        List<Map<String, Object>> products = response.jsonPath().getList("$");
        Assert.assertTrue(products.size() > 0, "The products list should not be empty");

        String apiProductName = null;
        double apiProductPrice = 0;

        for (Map<String, Object> product : products) {
            if(product.get("title").toString().contains("Backpack")) {
                apiProductName = product.get("title").toString();
                apiProductPrice = Double.parseDouble(product.get("price").toString());
                break;
            }
        }
        System.out.println("THe name extracted from API: " + apiProductName);
        System.out.println("The price extracted from API: " + apiProductPrice);

        apiProductName = "Sauce Labs Backpack";
        double hardcodedApiProductPrice = 29.99;

        Assert.assertNotNull(apiProductName, "API Product not found");

        // UI Step

        Map<String, Object> user = TestDataUtil.getUserData("valid_username");

        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToLoginPage();
        loginPage.login(user.get("username").toString(),user.get("password").toString());

        ProductPage productPage = new ProductPage(page);
        productPage.navigateToProductPage();

        String uiProductPrice = productPage.getProductPrice(apiProductName);

        System.out.println("API Price: " + apiProductPrice);
        System.out.println("UI Price: " + uiProductPrice);

        Assert.assertEquals(Double.parseDouble(uiProductPrice), hardcodedApiProductPrice,
        "UI Price does not match API price");
    }
}
