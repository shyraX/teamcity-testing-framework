package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.Page;

import static com.example.teamcity.ui.Selectors.byId;

public class CreateNewProject extends Page {

    private final SelenideElement urlInput = byId("url");
    private final SelenideElement projectNameInput = byId("projectName");
    private final SelenideElement buildTypeNameInput = byId("buildTypeName");

    public CreateNewProject open(String parentProjectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId=" + parentProjectId + "&showMode=createProjectMenu");
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateNewProject createProjectByUrl(String url) {
        urlInput.setValue(url);
        submit();
        return this;
    }

    public void setupProject(String projectName, String buildTypeName) {
        projectNameInput.clear();
        projectNameInput.setValue(projectName);
        buildTypeNameInput.clear();
        buildTypeNameInput.setValue(buildTypeName);
        submit();
    }
}
