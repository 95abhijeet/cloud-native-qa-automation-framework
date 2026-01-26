package com.abhijeet.qa.tests.ui;

import com.abhijeet.qa.base.BaseTest;
import com.abhijeet.qa.pages.LoginPage;
import com.abhijeet.qa.pages.TransactionQueuePage;
import com.abhijeet.qa.pages.TransactionAuthorizationPage;

import com.abhijeet.qa.utils.AllureUtils;
import com.abhijeet.qa.utils.TestDataUtil;
import com.microsoft.playwright.Page;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.Map;
/**
 * TransactionExecutionTests validates the end-to-end flow of authorizing
 * and committing a financial transaction within the secure portal.
 */

public class TransactionExecutionTests extends BaseTest{

    //TC-UI-P-006
    @Description("Validating the transition to the final review state")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void verifyTransactionAuthorizationFlow() {

        //Login
        Map<String, Object> user = TestDataUtil.getUserData("valid_username");
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToPortal();
        loginPage.login(user.get("username").toString(),user.get("password").toString());

        TransactionQueuePage transactionQueuePage = new TransactionQueuePage(page);
        transactionQueuePage.stageTransaction(0);

        TransactionAuthorizationPage authPage = new TransactionAuthorizationPage(page);
        authPage.goToTransactionSummary();
        authPage.startAuthorization();
        authPage.enterAuthorizationInformation("Test", "User", "90210");

        Assert.assertTrue(page.url().contains("checkout-step-two"),
                "User must be redirected to the Final Review/Audit page before commitment");
    }

    //TC-UI-P-007
    @Description("Ensure the system provides a valid confirmation upon transaction commitment")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void verifyTransactionSettlementCompletion() {

        //Login
        Map<String, Object> user = TestDataUtil.getUserData("valid_username");
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToPortal();
        loginPage.login(user.get("username").toString(),user.get("password").toString());

        TransactionQueuePage transactionQueuePage = new TransactionQueuePage(page);
        transactionQueuePage.stageTransaction(0);

        Map<String, Object> profile = TestDataUtil.getUserData("user_profile");
        TransactionAuthorizationPage authPage = new TransactionAuthorizationPage(page);
        authPage.goToTransactionSummary();
        authPage.startAuthorization();
        authPage.enterAuthorizationInformation(
                profile.get("first_name").toString(),
                profile.get("last_name").toString(),
                profile.get("zip").toString()
        );
        authPage.finishAuthorization();
        byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
        AllureUtils.attachScreenshot("Transaction_Settlement_Confirmation", screenshot);

        String successMsg = authPage.getSuccessMessage();
        System.out.println("[AUDIT] Transaction Settlement Message: " + successMsg);

        Assert.assertTrue(successMsg.toLowerCase().contains("thank you"),
                "The system must display a formal 'Thank You' or 'Success' confirmation upon settlement");
    }
}
