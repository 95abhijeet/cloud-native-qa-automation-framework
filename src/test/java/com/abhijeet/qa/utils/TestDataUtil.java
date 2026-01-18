package com.abhijeet.qa.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Map;


public class TestDataUtil {

    private static Map<String, Object> data;

    static{
        try{
            ObjectMapper mapper = new ObjectMapper();

            data = mapper.readValue(
                    new File("src/test/resources/testData.json"),
                    Map.class
            );
        } catch (Exception e) {
            throw  new RuntimeException("Failed to load test data");
        }
    }

    public static Map<String, Object> getUserData(String key) {
        return (Map<String, Object>) data.get(key);
    }
}
