package com.example.teamcity.ui.pages.project;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.Selectors;
import com.example.teamcity.ui.elements.BuildElement;
import com.example.teamcity.ui.pages.Page;

import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class ProjectPage extends Page {

    private static final String PROJECT_URL = "/project/%s";
    private final ElementsCollection builds = $$(".BuildTypes__item--UX");
    private final SelenideElement ringTab = Selectors.byDataTest("ring-tab");

    public ProjectPage open(String projectId) {
        Selenide.open(String.format(PROJECT_URL, projectId));
        waitUntilPageIsLoaded();
        ringTab.shouldBe(visible);
        return this;
    }

    public List<BuildElement> getBuild(){
        return generatePageElements(builds, BuildElement::new);
    }
}
