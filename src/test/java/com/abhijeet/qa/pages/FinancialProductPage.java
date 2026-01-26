package com.abhijeet.qa.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

import java.util.HashMap;
import java.util.Map;

/**
 * FinancialProductPage models the catalog of banking services,
 * investment instruments, or account types available to the client.
 */

public class FinancialProductPage {
    private final Page page;

    //Locators
    private final Locator instrumentCards; //Maps to product cards
    private final Locator instrumentNames; //Maps to product names
    private final Locator instrumentDescriptions; //Maps to product descriptions
    private final Locator instrumentRates; //Maps to product prices

    public FinancialProductPage(Page page) {
        this.page = page;
        this.instrumentCards = page.locator(".inventory_item");
        this.instrumentNames = page.locator(".inventory_item_name");
        this.instrumentDescriptions = page.locator(".inventory_item_desc");
        this.instrumentRates = page.locator(".inventory_item_price");
    }

    public void navigateToDashboard() {
        page.navigate("https://www.saucedemo.com/inventory.html");
    }

    public int getInstrumentCount() {
        return instrumentCards.count();
    }

    /** Retrieves data for a specific financial instrument by index.
     * Maps to account/service details in a banking context */
    public Map<String, String> getInstrumentDetails(int index) {
        Map<String, String> details = new HashMap<>();
        details.put("name", instrumentNames.nth(index).textContent().trim());
        details.put("description", instrumentDescriptions.nth(index).textContent().trim());
        details.put("price", instrumentRates.nth(index).textContent().trim());
        return details;
    }

    /** Validates that all mandatory financial data fields are populated.
     * Crucial for regulatory data integrity checks */
    public boolean isInformationDataValid(int index) {
        Map<String, String> info = getInstrumentDetails(index);
        return !info.get("name").isBlank()
                && !info.get("description").isBlank()
                && !info.get("price").isBlank();
    }

    /** Fetches the specific rate or fee for a financial product based on its name.
     * Uses dynamic XPath for precise data extraction */
    public String getInstrumentRate (String instrumentName) {
        String rateLocator =
                "//div[@class='inventory_item_name ' and text()='" + instrumentName + "']" +
                        "/ancestor::div[@class='inventory_item']" +
                        "//div[@class='inventory_item_price']";


        String rateText = page.textContent(rateLocator);

        // Standardizing numeric data for financial reconciliation
        return rateText.replace("$","").trim();
    }

}
