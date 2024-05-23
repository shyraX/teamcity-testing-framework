package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import lombok.Getter;

import java.time.Duration;

import static com.codeborne.selenide.Condition.enabled;

public class StartUpPage extends Page {
    @Getter
    private final SelenideElement header = Selectors.byId("header");
    private final SelenideElement proceedButton = Selectors.byId("proceedButton");
    private final SelenideElement acceptLicense = Selectors.byId("accept");

    public StartUpPage open() {
        Selenide.open("/mnt");
        return this;
    }

    public StartUpPage startUpTeamcityServer() {
        waitUntilPageIsLoaded();
        proceedButton.click();
        waitUntilPageIsLoaded();
        proceedButton.click();
        waitUntilPageIsLoaded();
        acceptLicense.shouldBe(enabled, Duration.ofMinutes(5));
        acceptLicense.scrollTo();
        acceptLicense.click();
        submit();
        return this;
    }
}
