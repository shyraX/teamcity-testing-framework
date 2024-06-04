package com.example.teamcity.ui;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;

public class BrowserSettings {

    private BrowserSettings() {

    }

    public static void setUp(String browser) {
        Configuration.browser = browser;

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", new HashMap<String, Object>() {{
            put("enableVNC", true);
            put("enableLog", true);
        }});
        Configuration.browserCapabilities = capabilities;
    }
}
