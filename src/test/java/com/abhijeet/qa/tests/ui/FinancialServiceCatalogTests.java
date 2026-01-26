package com.abhijeet.qa.tests.ui;

import com.abhijeet.qa.base.BaseTest;
import com.abhijeet.qa.pages.LoginPage;
import com.abhijeet.qa.pages.FinancialProductPage;
import com.abhijeet.qa.utils.TestDataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;
/**
 * FinancialServiceCatalogTests validates the availability and data integrity
 * of the digital service offerings presented to the customer.
 */

public class FinancialServiceCatalogTests extends BaseTest {

    // TC-UI-V-003
    @Description("Verify mandatory service data and instrument rates for the top-tier catalog")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void verifyFinancialServiceCatalog() {

        //Login
        Map<String, Object> user = TestDataUtil.getUserData("valid_username");
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToPortal();
        loginPage.login(user.get("username").toString(), user.get("password").toString());

        //Inventory Page
        FinancialProductPage financialProductPage = new FinancialProductPage(page);
        financialProductPage.navigateToDashboard();

        int instrumentCount = financialProductPage.getInstrumentCount();
        Assert.assertTrue(instrumentCount >= 4, "Service catalog must display at least 4 active financial instruments");

        //Validate first 4 products and print info
        for (int instrument = 0; instrument < 4; instrument++) {
            Map<String, String> details = financialProductPage.getInstrumentDetails(instrument);

            System.out.println("[AUDIT LOG] Instrument " + (instrument + 1) + " info:");
            System.out.println("  Service Name: " + details.get("name"));
            System.out.println("  Description: " + details.get("description"));
            System.out.println("  Current Rate: " + details.get("rate"));
            System.out.println("------------------------------------------");

            Assert.assertTrue(financialProductPage.isInformationDataValid(instrument),
                    "Instrument at index" + instrument + "failed data integrity check (Missing name, description, or rate)");
        }
    }
}
