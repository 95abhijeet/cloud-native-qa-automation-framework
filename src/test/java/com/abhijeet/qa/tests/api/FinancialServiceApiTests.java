package com.abhijeet.qa.tests.api;

import com.abhijeet.qa.utils.AllureUtils;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * FinancialServiceApiTests validates the integrity, availability, and schema
 * compliance of the core banking service catalog APIs.
 */

public class FinancialServiceApiTests {
    //TC-API-H-001
    @Description("Verify Service Availability: GET /products (Banking Catalog) endpoint returns HTTP 200 OK")
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = {"api"})
    public void verifyFinancialServiceAvailability() {

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
    @Description("Contract Validation: Verify mandatory fields in the Financial Instrument response")
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = {"api"})
    public void verifyInstrumentSchemaIntegrity(){

        Response response =
                given()
                        .baseUri("https://fakestoreapi.com")
                        .when()
                        .get("/products");

        System.out.println("Response Body: ");
        System.out.println(response.asString());

        AllureUtils.attachJson("Financial_Instrument_Payload", response.asPrettyString());
        List<Map<String, Object>> instruments = response.jsonPath().getList("$");

        Assert.assertTrue(instruments.size() > 0, "Service catalog should contain active instruments");

        for (Map<String, Object> instrument : instruments) {

            Assert.assertNotNull(instrument.get("id"), "Instrument ID (Primary Key) missing");
            Assert.assertNotNull(instrument.get("title"), "Instrument Title missing");
            Assert.assertNotNull(instrument.get("price"), "Market Rate/Price missing");

            double price = Double.parseDouble(instrument.get("price").toString());
            Assert.assertTrue(price > 0, "Financial rate must be a positive non-zero value");
        }
    }


    //TC-API-E-004
    @Description("Negative Testing: Verify API resilience and error handling for invalid endpoints")
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = {"api"})
    public void verifyApiErrorHandlingResilience() {

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
    @Description("Performance SLA: Measure response latency for high-frequency service calls")
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = {"api"})
    public void verifyServiceResponseSLA() {
        Response response =
                given()
                        .baseUri("https://fakestoreapi.com")
                        .when()
                        .get("/products");

        long latency = response.time();
        System.out.println("[PERF-LOG] Service Latency: " + latency + "ms");

        Assert.assertTrue(latency <=1000,"API response latency exceeds the 1000ms SLA threshold");
    }
}
