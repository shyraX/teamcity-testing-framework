package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.Page;

import static com.example.teamcity.ui.Selectors.byId;

public class CreateNewProjectPage extends Page {

    private final SelenideElement projectNameInput = byId("projectName");
    private final SelenideElement buildTypeNameInput = byId("buildTypeName");

    public CreateNewProjectPage open(String parentProjectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId=" + parentProjectId + "&showMode=createProjectMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateNewProjectPage createProjectByUrl(String url) {
        urlInput.setValue(url);
        submit();
        return this;
    }

    public CreateNewProjectPage setupProject(String projectName, String buildTypeName) {
        projectNameInput.clear();
        projectNameInput.setValue(projectName);
        buildTypeNameInput.clear();
        buildTypeNameInput.setValue(buildTypeName);
        submit();
        return this;
    }

}
