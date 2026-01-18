package com.abhijeet.qa.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


public class CheckoutPage {

    private final Page page;

    //Locators
    private final Locator cartIcon;
    private final Locator checkoutButton;

    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator zipCodeInput;
    private final Locator continueButton;

    private final Locator finishButton;
    private final Locator successMessage;

    public CheckoutPage(Page page){
        this.page = page;
        this.cartIcon = page.locator(".shopping_cart_link");
        this.checkoutButton = page.locator("#checkout");

        this.firstNameInput = page.locator("#first-name");
        this.lastNameInput = page.locator("#last-name");
        this.zipCodeInput = page.locator("#postal-code");
        this.continueButton = page.locator("#continue");

        this.finishButton = page.locator("#finish");
        this.successMessage = page.locator(".complete-header");
    }

    public void goToCart(){
        cartIcon.click();
        System.out.println("Navigated to Cart page");
    }

    public void startCheckout(){
        checkoutButton.click();
        System.out.println("Checkout started");
    }

    public void enterCheckoutInformation(String firstName, String lastName, String zip){
        firstNameInput.fill(firstName);
        lastNameInput.fill(lastName);
        zipCodeInput.fill(zip);
        continueButton.click();

        System.out.println("Entered checkout information: "
                + firstName + " " + lastName + ", " + zip);
    }

    public void finishCheckout(){
        finishButton.click();
        System.out.println("Checkout completed");
    }

    public String getSuccessMessage() {
        return successMessage.textContent().trim();
    }

}
