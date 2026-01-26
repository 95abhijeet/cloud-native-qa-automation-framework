package com.abhijeet.qa.tests.ui;

import com.abhijeet.qa.pages.LoginPage;
import com.abhijeet.qa.pages.TransactionQueuePage;
import com.abhijeet.qa.base.BaseTest;
import com.abhijeet.qa.utils.TestDataUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;
/**
 * TransactionStagingTests validates the functionality of staging financial
 * services and ensuring the pending transaction queue accurately tracks selections.
 */

public class TransactionStagingTests extends BaseTest {

    //TC-UI-C-004
    @Description("Verify Transaction Initiation: Selection of a financial service for staging")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void verifyFinancialServiceStaging() {
        //Login
        Map<String, Object> user = TestDataUtil.getUserData("valid_username");
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToPortal();
        loginPage.login(user.get("username").toString(),user.get("password").toString());

        TransactionQueuePage transactionQueuePage = new TransactionQueuePage(page);

        //Add first product
        transactionQueuePage.stageTransaction(0);

        Assert.assertEquals(transactionQueuePage.getPendingTransactionCount(), 1, "Cart count should be 1");
    }


    //TC-UI-C-005
    @Description("Verify Queue Synchronization: Ensure the badge accurately reflects multiple staged transactions")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void verifyMultipleTransactionQueueCount() {
        //Login
        Map<String, Object> user = TestDataUtil.getUserData("valid_username");
        LoginPage loginPage = new LoginPage(page);
        loginPage.navigateToPortal();
        loginPage.login(user.get("username").toString(),user.get("password").toString());

        TransactionQueuePage transactionQueuePage = new TransactionQueuePage(page);

        //Add first 2 items
        transactionQueuePage.stageTransaction(0);
        transactionQueuePage.stageTransaction(1);

        Assert.assertEquals(transactionQueuePage.getPendingTransactionCount(), 2, "Cart count should be 2");
    }
}
