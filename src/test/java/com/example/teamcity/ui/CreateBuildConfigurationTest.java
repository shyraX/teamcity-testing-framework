package com.example.teamcity.ui;


import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Errors;
import com.example.teamcity.ui.pages.admin.CreateBuildConfigurationPage;
import com.example.teamcity.ui.pages.project.ProjectPage;
import org.testng.annotations.Test;

public class CreateBuildConfigurationTest extends BaseUiTest {

    @Test
    public void creatingBuildConfigurationShouldBeAvailable() {
        var testData = testDataStorage.addTestData();

        loginAsUser(testData.getUser());
        createProject(testData.getProject());

        new CreateBuildConfigurationPage().open(testData.getProject().getId())
                .createBuildConfigByUrl(REPOSITORY_URL)
                .setupBuildConfig(testData.getBuildType().getName());

        new ProjectPage().open(testData.getProject().getId())
                .getBuild()
                .stream().reduce((first, second) -> second).get()
                .getBuildName().shouldHave(Condition.text(testData.getBuildType().getName()));
    }

    @Test
    public void creatingBuildConfigurationWithSameNameShouldNotBeAvailable() {
        var testData = testDataStorage.addTestData();

        loginAsUser(testData.getUser());
        createProject(testData.getProject());

        new CreateBuildConfigurationPage().open(testData.getProject().getId())
                .createBuildConfigByUrl(REPOSITORY_URL)
                .setupBuildConfig(testData.getBuildType().getName());

        new CreateBuildConfigurationPage().open(testData.getProject().getId())
                .createBuildConfigByUrl(REPOSITORY_URL)
                .setupBuildConfig(testData.getBuildType().getName())
                .submitAnyway()
                .checkErrorText("buildTypeName", String.format(Errors.BUILD_CONFIG_WITH_SAME_NAME_EXIST.getText(),
                        testData.getBuildType().getName(), testData.getProject().getName()));
    }

}

