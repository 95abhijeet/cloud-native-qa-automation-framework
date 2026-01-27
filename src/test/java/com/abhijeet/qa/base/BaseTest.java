package com.abhijeet.qa.base;

import com.microsoft.playwright.*;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;
import io.qameta.allure.Allure;
import java.nio.file.Paths;

@Listeners({AllureTestNg.class})
public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeMethod
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(true)
                        .setSlowMo(50) //Slight delay helps stability in complex banking UIs
        );
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1280, 720)); //Setting a viewport size for consistency across environments

        // Start Playwright Tracing (Essential for Banking Compliance/Audit trails)
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        page = context.newPage();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        if (result.getStatus() == ITestResult.FAILURE && page != null) {
            // 1. Capture screenshot for Allure
            byte[] screenshot = page.screenshot();
            Allure.getLifecycle().addAttachment(
                    "Failure Screenshot",
                    "image/png",
                    "png",
                    screenshot
            );
            // 2. Export Trace File
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("traces/traces/" + testName + "_trace.zip")));
        }
        else {
            context.tracing().stop();
        }


        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
