package com.example.teamcity.ui;

import com.example.teamcity.api.enums.Errors;
import com.example.teamcity.ui.pages.admin.CreateNewProjectPage;
import com.example.teamcity.ui.pages.favorites.FavoriteProjectPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;

@Epic("UI tests")
@Feature("TeamCity project")
public class CreateNewProjectTest extends BaseUiTest {

    @Test
    @Description("Authorized user should be able create new project")
    void authorizedUserShouldBeAbleCreateNewProject() {

        var testData = testDataStorage.addTestData();

        loginAsUser(testData.getUser());

        new CreateNewProjectPage()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(REPOSITORY_URL)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        new FavoriteProjectPage().open()
                .getSubprojects()
                .stream().reduce((first, second) -> second).get()
                .getHeader().shouldHave(text(testData.getProject().getName()));

        checkedWithSuperUser.getProjectRequest().get(testData.getProject().getName());
    }

    @Test
    @Description("Creating projects with same name should not be available")
    void creatingProjectsWithSameNameShouldNotBeAvailable() {

        var testData = testDataStorage.addTestData();

        loginAsUser(testData.getUser());

        new CreateNewProjectPage()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(REPOSITORY_URL)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        new CreateNewProjectPage()
                .open(testData.getProject().getParentProject().getLocator())
                .createProjectByUrl(REPOSITORY_URL)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName())
                .checkErrorText("projectName" ,String.format(Errors.PROJECT_WITH_SAME_NAME_EXIST.getText(),
                        testData.getProject().getName()));
    }
}
