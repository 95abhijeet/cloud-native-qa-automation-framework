package com.abhijeet.qa.tests.ui;

import com.abhijeet.qa.base.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest{
    @Test
    public void verifyLoginPageLoads() {
        page.navigate("https://www.saucedemo.com/");
        assertTrue(page.title().contains("Swag Labs"),
                "Login page did not load correctly");
    }

}
