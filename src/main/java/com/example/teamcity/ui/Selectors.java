package com.example.teamcity.ui;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class Selectors {

    private Selectors() {

    }

    public static SelenideElement byId(String value) {
        return $("[id='" + value + "']");
    }

    public static SelenideElement byType(String value) {
        return $("[type='" + value + "']");
    }

    public static SelenideElement byDataTest(String value) {
        return $("[data-test='" + value + "']");
    }

    public static SelenideElement byClass(String value) {
        return $("." + value);
    }
}
