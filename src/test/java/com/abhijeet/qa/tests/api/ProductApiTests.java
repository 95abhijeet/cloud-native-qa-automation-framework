package com.abhijeet.qa.tests.api;

import com.abhijeet.qa.utils.AllureUtils;
import io.qameta.allure.Allure;
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

public class ProductApiTests {
    //TC-API-H-001
    @Description("Verify the GET /products endpoint returns HTTP 200 OK")
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = {"api"})
    public void verifyProductsEndpointIsAccessible() {

        Response response =
                given()
                        .baseUri("https://fakestoreapi.com")
                .when()
                        .get("/products");

        System.out.println("Response body: ");
        System.out.println(response.asString());

        AllureUtils.attachText("API Response", response.asPrettyString());

        response.then().statusCode(200);
    }

    //TC-API-V-003
    @Description("Verify the API product response contains mandatory fields")
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = {"api"})
    public void verifyProductResponseMandatoryFields(){

        Response response =
                given()
                        .baseUri("https://fakestoreapi.com")
                        .when()
                        .get("/products");

        System.out.println("Response Body: ");
        System.out.println(response.asString());

        AllureUtils.attachText("API Response", response.asPrettyString());
        List<Map<String, Object>> products = response.jsonPath().getList("$");

        Assert.assertTrue(products.size() > 0, "Products list should not be empty");

        for (Map<String, Object> product : products) {

            Assert.assertNotNull(product.get("id"), "Product ID should not ne Null");
            Assert.assertNotNull(product.get("title"), "Product title should not be Null");
            Assert.assertNotNull(product.get("price"), "Product price should not be Null");

            double price = Double.parseDouble(product.get("price").toString());
            Assert.assertTrue(price > 0, "Product price should be greater than zero");
        }
    }


    //TC-API-E-004
    @Description("Verify the API returns 404 for a nonexistent endpoint")
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = {"api"})
    public void verifyReturnCodeNonExistentEndpoint() {

        Response response =
                given()
                        .baseUri("https://fakestoreapi.com")
                        .when()
                        .get("/nonexistent");

        System.out.println("Response Body: ");
        System.out.println(response.asString());

        response.then().statusCode(404);

    }


    //TC-API-P-005
    @Description("Measure API response time for the GET /products endpoint")
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = {"api"})
    public void verifyResponseTime() {
        Response response =
                given()
                        .baseUri("https://fakestoreapi.com")
                        .when()
                        .get("/products");

        long responseTime = response.time();
        System.out.println("The response time is " + responseTime + "ms");

        Assert.assertTrue(responseTime <=1000,"The response time should be <= 500ms");
    }
}
