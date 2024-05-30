package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.Page;

import static com.example.teamcity.ui.Selectors.byId;

public class CreateBuildConfigurationPage extends Page {

    private final String createBuildConfigUrl = "/admin"
            + "/createObjectMenu.html?projectId=%s&showMode=createBuildTypeMenu";
    private final SelenideElement buildTypeNameInput = byId("buildTypeName");

    /**
     * Переход на страницу
     * @return текущий экземпляр CreateBuildConfigurationPage
     */
    public CreateBuildConfigurationPage open(String projectId) {
        Selenide.open(String.format(createBuildConfigUrl, projectId));
        waitUntilPageIsLoaded();
        return this;
    }

    /**
     * Создание BuildConfiguration для репозитория
     * @param url - ссылка на репозиторий
     * @return текущий экземпляр CreateBuildConfigurationPage
     */
    public CreateBuildConfigurationPage createBuildConfigByUrl(String url) {
        urlInput.setValue(url);
        submit();
        return this;
    }

    /**
     * Настройка BuildConfiguration
     * @param buildTypeName - название билда
     * @return текущий экземпляр CreateBuildConfigurationPage
     */
    public CreateBuildConfigurationPage setupBuildConfig(String buildTypeName) {
        buildTypeNameInput.clear();
        buildTypeNameInput.setValue(buildTypeName);
        submit();
        return this;
    }
}
