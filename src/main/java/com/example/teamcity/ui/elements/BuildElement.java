package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.example.teamcity.ui.Selectors.byClass;

@Getter
public class BuildElement extends PageElement {

    private final SelenideElement buildName;

    public BuildElement(SelenideElement element) {
        super(element);
        this.buildName = byClass("MiddleEllipsis__searchable--uZ");
    }
}
