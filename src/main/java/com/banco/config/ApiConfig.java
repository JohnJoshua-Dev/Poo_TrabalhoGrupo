package com.banco.config;

public class ApiConfig {
    private static String baseUrl = "http://localhost:8080/api";
    
    public static String getBaseUrl() {
        return baseUrl;
    }
    
    public static void setBaseUrl(String url) {
        baseUrl = url;
    }
}