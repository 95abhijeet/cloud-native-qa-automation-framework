package com.abhijeet.qa.utils;

import io.qameta.allure.Allure;

public class AllureUtils {

    public static void attachText(String name, String content) {
        Allure.addAttachment(name, content);
    }
}
