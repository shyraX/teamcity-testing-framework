package com.example.teamcity.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private final static String CONFIG_PROPERTIES = "config.properties";
    private static Config config;
    private final Properties properties;

    private Config() {
        properties = new Properties();
        loadProperties(CONFIG_PROPERTIES);
    }

    private static Config getConfig() {
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public void loadProperties(String fileName) {
        try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                System.out.println("File not found " + fileName);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println("Error during file reading " + fileName);
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return getConfig().properties.getProperty(key);
    }
}
