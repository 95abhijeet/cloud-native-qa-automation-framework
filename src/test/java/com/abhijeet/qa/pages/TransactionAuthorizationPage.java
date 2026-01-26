package com.abhijeet.qa.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * TransactionAuthorizationPage models the final review and commitment stage
 * of a financial transaction, including summary verification and final submission.
 */
public class TransactionAuthorizationPage {

    private final Page page;

    //Locators
    private final Locator transactionSummaryIcon;
    private final Locator authorizeButton;

    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator zipCodeInput;
    private final Locator continueButton;

    private final Locator finishButton;
    private final Locator successMessage;

    public TransactionAuthorizationPage(Page page){
        this.page = page;
        this.transactionSummaryIcon = page.locator(".shopping_cart_link");
        this.authorizeButton = page.locator("#checkout");

        this.firstNameInput = page.locator("#first-name");
        this.lastNameInput = page.locator("#last-name");
        this.zipCodeInput = page.locator("#postal-code");
        this.continueButton = page.locator("#continue");

        this.finishButton = page.locator("#finish");
        this.successMessage = page.locator(".complete-header");
    }

    public void goToTransactionSummary(){
        transactionSummaryIcon.click();
        System.out.println("Navigated to Transaction Summary page");
    }

    public void startAuthorization(){
        authorizeButton.click();
        System.out.println("Checkout started");
    }

    public void enterAuthorizationInformation(String firstName, String lastName, String zip){
        firstNameInput.fill(firstName);
        lastNameInput.fill(lastName);
        zipCodeInput.fill(zip);
        continueButton.click();

        System.out.println("Entered checkout information: "
                + firstName + " " + lastName + ", " + zip);
    }

    public void finishAuthorization(){
        finishButton.click();
        System.out.println("Authorization completed");
    }

    public String getSuccessMessage() {
        return successMessage.textContent().trim();
    }

}
