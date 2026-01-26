package com.abhijeet.qa.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/** TransactionQueuePage manages the selection and staging of financial services
 * or payment transactions before final authorization */

public class TransactionQueuePage {
    private final Page page;

    //Locators
    private final Locator initiateTransactionButtons; //Maps to "add to cart"
    private final Locator transactionSummaryIcon; //Maps to Cart icon
    private final Locator pendingTransactionBadge; //Maps to cart badge showing number of items
    private final Locator serviceItemNames; //Maps to names of products in catalog


    public TransactionQueuePage(Page page) {
        this.page = page;
        this.initiateTransactionButtons = page.locator(".inventory_item button");
        this.transactionSummaryIcon = page.locator(".shopping_cart_link");
        this.pendingTransactionBadge = page.locator(".shopping_cart_badge");
        this.serviceItemNames = page.locator(".inventory_item_name");
    }

    /** Selects a financial service or transaction item by its index to add to the processing queue.
     * @param index The position of the service in the dashboard list */
    public void stageTransaction(int index) {
        String serviceName = serviceItemNames.nth(index).textContent().trim();
        initiateTransactionButtons.nth(index).click();
        System.out.println("[AUDIT] Service Staged: " + serviceName);
        System.out.println("[AUDIT] Pending Queue Count: "  + getPendingTransactionCount());
        System.out.println("----------------------------");
    }

    /** Retrieves the current count of transactions awaiting authorization.
     * @return count of pending items in the queue */
    public int getPendingTransactionCount() {
        if (pendingTransactionBadge.isVisible()) {
            return Integer.parseInt((pendingTransactionBadge.textContent().trim()));
        } else {
            return 0;
        }
    }

    /** Navigates to the Transaction Review/Summary screen */
    public void navigateToReviewSummary(){
        transactionSummaryIcon.click();
    }


}
