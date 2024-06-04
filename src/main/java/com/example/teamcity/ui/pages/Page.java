package com.example.teamcity.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.PageElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.codeborne.selenide.Condition.id;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static com.example.teamcity.ui.Selectors.byDataTest;
import static com.example.teamcity.ui.Selectors.byId;
import static com.example.teamcity.ui.Selectors.byType;

public class Page {

    private final SelenideElement submitButton = byType("submit");
    private final SelenideElement savingWaitingMarker = byId("saving");
    private final SelenideElement pageWaitingMarker = byDataTest("ring-loader");
    private final ElementsCollection errors = $$(".error");
    protected final SelenideElement urlInput = byId("url");
    private final SelenideElement submitAnywayButton = byId("submitAnywayButton");

    /**
     * Нажатие на кнопку подтверждения
     */
    public void submit() {
        submitButton.click();
        waitUntilDataIsSaved();
    }

    /**
     * Нажатие на кнопку подтверждения в предупреждающем модальном окне
     */
    public Page submitAnyway() {
        submitAnywayButton.click();
        waitUntilDataIsSaved();
        return this;
    }

    /**
     * Ожидание полной загрузки страницы
     */
    public void waitUntilPageIsLoaded() {
        pageWaitingMarker.shouldNotBe(visible, Duration.ofSeconds(30));
    }

    /**
     * Ожидание сохранения данных на странице
     */
    public void waitUntilDataIsSaved() {
        savingWaitingMarker.shouldNotBe(visible, Duration.ofSeconds(20));
    }

    /**
     * @param collection - коллекция элементов, которую ожидается найти на странице
     * @param creator - объект класса Function, который используется для создания элемента
     * @param <T> - класс наследник PageElement
     * @return коллекция созданных PageElement
     */
    public <T extends PageElement> List<T> generatePageElements(
            ElementsCollection collection,
            Function<SelenideElement, T> creator) {
        var elements = new ArrayList<T>();
        collection.forEach(webElement -> elements.add(creator.apply(webElement)));
        return elements;
    }

    /**
     * Проверить текст ошибки
     * @param id - элемента содержащего ошибку
     * @param errorText - ожидаемый текст ошибки
     */
    public void checkErrorText(String id, String errorText) {
        errors.findBy(id("error_" + id)).shouldHave(text(errorText));
    }
}
