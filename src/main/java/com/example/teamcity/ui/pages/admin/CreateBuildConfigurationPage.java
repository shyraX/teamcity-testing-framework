package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.Page;

import static com.example.teamcity.ui.Selectors.byId;

public class CreateBuildConfigurationPage extends Page {

    private final String CREATE_BUILD_CONFIG_URL = "/admin/createObjectMenu.html?projectId=%s&showMode=createBuildTypeMenu";
    private final SelenideElement buildTypeNameInput = byId("buildTypeName");

    public CreateBuildConfigurationPage open(String projectId) {
        Selenide.open(String.format(CREATE_BUILD_CONFIG_URL, projectId));
        waitUntilPageIsLoaded();
        return this;
    }

    public CreateBuildConfigurationPage createBuildConfigByUrl(String url) {
        urlInput.setValue(url);
        submit();
        return this;
    }

    public CreateBuildConfigurationPage setupBuildConfig(String buildTypeName) {
        buildTypeNameInput.clear();
        buildTypeNameInput.setValue(buildTypeName);
        submit();
        return this;
    }
}
