package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.Page;

import static com.example.teamcity.ui.Selectors.byId;

public class CreateNewProjectPage extends Page {

    private final SelenideElement projectNameInput = byId("projectName");
    private final SelenideElement buildTypeNameInput = byId("buildTypeName");

    /**
     * Переход на страницу
     * @param parentProjectId - проект, в котором будет создан новый проект
     * @return текущий экземпляр CreateNewProjectPage
     */
    public CreateNewProjectPage open(String parentProjectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId="
                + parentProjectId + "&showMode=createProjectMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    /**
     * Создание проекта
     * @param url - ссылка на репозиторий
     * @return текущий экземпляр CreateNewProjectPage
     */
    public CreateNewProjectPage createProjectByUrl(String url) {
        urlInput.setValue(url);
        submit();
        return this;
    }

    /**
     * Настройка проекта
     * @param projectName - название проекта
     * @param buildTypeName - название buildType
     * @return текущий экземпляр CreateNewProjectPage
     */
    public CreateNewProjectPage setupProject(String projectName, String buildTypeName) {
        projectNameInput.clear();
        projectNameInput.setValue(projectName);
        buildTypeNameInput.clear();
        buildTypeNameInput.setValue(buildTypeName);
        submit();
        return this;
    }

}
