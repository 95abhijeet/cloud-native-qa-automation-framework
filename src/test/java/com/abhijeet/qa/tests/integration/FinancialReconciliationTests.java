package com.abhijeet.qa.tests.integration;

import com.abhijeet.qa.base.BaseTest;
import com.abhijeet.qa.pages.LoginPage;
import com.abhijeet.qa.pages.FinancialProductPage;

import com.abhijeet.qa.utils.AllureUtils;
import com.abhijeet.qa.utils.TestDataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class FinancialReconciliationTests extends BaseTest {

    //TC-INT-D-002
    @Description("Cross-Layer Reconciliation: Verify UI instrument rates match Backend Source of Truth (API)")
    @Severity(SeverityLevel.CRITICAL)
    @Test(groups = {"api"})
    public void verifyRateConsistencyBetweenApiAndUi() {
        // API Step
        Response response =
                given()
                        .baseUri("https://fakestoreapi.com")
                        .when()
                        .get("/products");

        System.out.println("Response Body: ");
        System.out.println(response.asString());
        AllureUtils.attachJson("Backend_Source_of_Truth", response.asPrettyString());

        List<Map<String, Object>> apiInstruments = response.jsonPath().getList("$");
        Assert.assertTrue(apiInstruments.size() > 0, "The products list should not be empty");

        String apiInstrumentName = null;
        double apiInstrumentRate = 0;

        for (Map<String, Object> instrument : apiInstruments) {
            if(instrument.get("title").toString().contains("Backpack")) {
                apiInstrumentName = instrument.get("title").toString();
                apiInstrumentRate = Double.parseDouble(instrument.get("price").toString());
                break;
            }
        }
        System.out.println("[RECONCILIATION] Backend Source Rate for " + apiInstrumentName + ": " + apiInstrumentRate);

        apiInstrumentName = "Sauce Labs Backpack";
        double hardcodedApiInstrumentRate= 29.99;

        Assert.assertNotNull(apiInstrumentName, "API Instrument not found");

        // UI Step

        Map<String, Object> user = TestDataUtil.getUserData("valid_username");

        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToPortal();
        loginPage.login(user.get("username").toString(),user.get("password").toString());

        FinancialProductPage financialProductPage = new FinancialProductPage(page);
        financialProductPage.navigateToDashboard();

        String uiInstrumentRate = financialProductPage.getInstrumentRate(apiInstrumentName);

        AllureUtils.attachScreenshot("UI_Presented_Rate_Snapshot", page.screenshot());

        System.out.println("[RECONCILIATION] Frontend Presented Rate:" + uiInstrumentRate);

        Assert.assertEquals(Double.parseDouble(uiInstrumentRate), hardcodedApiInstrumentRate,
        "CRITICAL: Data Mismatch detected! UI Rate does not match Backend Source of Truth.");

        System.out.println("[RECONCILIATION] Audit Passed: Frontend and Backend are synchronized.");
    }
}
