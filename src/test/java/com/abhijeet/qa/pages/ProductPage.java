package com.abhijeet.qa.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

import java.util.HashMap;
import java.util.Map;

public class ProductPage {
    private final Page page;

    //Locators
    private final Locator productCards;
    private final Locator productNames;
    private final Locator productDescriptions;
    private final Locator productPrices;

    public ProductPage(Page page) {
        this.page = page;
        this.productCards = page.locator(".inventory_item");
        this.productNames = page.locator(".inventory_item_name");
        this.productDescriptions = page.locator(".inventory_item_desc");
        this.productPrices = page.locator(".inventory_item_price");
    }

    public void navigateToProductPage() {
        page.navigate("https://www.saucedemo.com/inventory.html");
    }

    public int getProductCount() {
        return productCards.count();
    }

    // Return product info for a specific index
    public Map<String, String> getProductInfo(int index) {
        Map<String, String> productInfo = new HashMap<>();
        productInfo.put("name", productNames.nth(index).textContent().trim());
        productInfo.put("description", productDescriptions.nth(index).textContent().trim());
        productInfo.put("price", productPrices.nth(index).textContent().trim());
        return productInfo;
    }

    //Check if info is complete
    public boolean isProductInfoComplete(int index) {
        Map<String, String> info = getProductInfo(index);
        return !info.get("name").isBlank()
                && !info.get("description").isBlank()
                && !info.get("price").isBlank();
    }

    public String getProductPrice (String productName) {
        String productPriceLocator =
                "//div[@class='inventory_item_name ' and text()='" + productName + "']" +
                        "/ancestor::div[@class='inventory_item']" +
                        "//div[@class='inventory_item_price']";


        String priceText = page.textContent(productPriceLocator);

        return priceText.replace("$","").trim();
    }

}
