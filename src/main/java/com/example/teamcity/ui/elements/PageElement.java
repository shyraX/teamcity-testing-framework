package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;

public abstract class PageElement {
    private final SelenideElement element;

    public PageElement(SelenideElement element) {
        this.element = element;
    }

    /**
     * Поиск элемента
     * @param value - тип локатора для поиска
     * @return найденный элемент
     */
    public SelenideElement findElement(String value) {
        return element.find(value);
    }

}
