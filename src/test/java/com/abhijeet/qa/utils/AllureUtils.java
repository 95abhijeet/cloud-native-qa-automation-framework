package com.abhijeet.qa.utils;

import io.qameta.allure.Allure;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayInputStream;

public class AllureUtils {

    public static void attachText(String name, String content) {
        Allure.addAttachment(name, content);
    }

    public static void attachJson(String name, String jsonContent) {
        Allure.addAttachment(name, "application/json", jsonContent, ".json");
    }

    public static void attachScreenshot(String name, byte[] screenshot) {
        Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
    }
}
