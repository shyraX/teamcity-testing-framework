package com.example.teamcity.ui.pages.favorites;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.Page;

import java.time.Duration;

import static com.example.teamcity.ui.Selectors.byClass;

public class FavoritesPage extends Page {
    private final SelenideElement header = byClass("ring-heading-heading");

    public void waitUntilFavoritePageIsLoaded() {
        waitUntilPageIsLoaded();
        header.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }
}
