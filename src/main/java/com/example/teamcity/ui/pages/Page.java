package com.example.teamcity.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.PageElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.codeborne.selenide.Condition.visible;
import static com.example.teamcity.ui.Selectors.byDataTest;
import static com.example.teamcity.ui.Selectors.byId;
import static com.example.teamcity.ui.Selectors.byType;

public class Page {

    private final SelenideElement submitButton = byType("submit");
    private final SelenideElement savingWaitingMarker = byId("saving");
    private final SelenideElement pageWaitingMarker = byDataTest("ring-loader");

    public void submit() {
        submitButton.click();
        waitUntilDataIsSaved();
    }

    public void waitUntilPageIsLoaded() {
        pageWaitingMarker.shouldNotBe(visible, Duration.ofSeconds(30));
    }

    public void waitUntilDataIsSaved() {
        savingWaitingMarker.shouldNotBe(visible, Duration.ofSeconds(20));
    }

    public <T extends PageElement> List<T> generatePageElements(
            ElementsCollection collection,
            Function<SelenideElement, T> creator) {
        var elements = new ArrayList<T>();
        collection.forEach(webElement -> elements.add(creator.apply(webElement)));
        return elements;
    }
}
