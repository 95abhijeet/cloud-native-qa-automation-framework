package com.abhijeet.qa.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.ArrayList;
import java.util.List;

public class CartPage {
    private final Page page;

    //Locators
    private final Locator addToCartButtons; //All "add to cart" buttons
    private final Locator cartIcon; //Cart icon
    private final Locator cartBadge; //Badge showing number of items
    private final Locator productNames; //Names of products in catalog


    public CartPage(Page page) {
        this.page = page;
        this.addToCartButtons = page.locator(".inventory_item button");
        this.cartIcon = page.locator(".shopping_cart_link");
        this.cartBadge = page.locator(".shopping_cart_badge");
        this.productNames = page.locator(".inventory_item_name");
    }

    //Add item by index
    public void addItemToCart(int index) {
        String name = productNames.nth(index).textContent().trim();
        addToCartButtons.nth(index).click();
        System.out.println("Added to cart: " + name);
        System.out.println("Cart count now: "  + getCartItemCount());
        System.out.println("----------------------------");
    }

    //Get cart item count
    public int getCartItemCount() {
        if (cartBadge.isVisible()) {
            return Integer.parseInt((cartBadge.textContent().trim()));
        } else {
            return 0;
        }
    }

    //Navigate to cart page
    public void navigateToCart(){
        cartIcon.click();
    }


}
