package com.example.teamcity.api;

import com.example.teamcity.api.generators.RandomData;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Errors.EMPTY_PROJECT_ID;
import static com.example.teamcity.api.enums.Errors.EMPTY_PROJECT_NAME;
import static com.example.teamcity.api.enums.Errors.PROJECT_WITH_SAME_ID_EXIST;
import static com.example.teamcity.api.enums.Errors.PROJECT_WITH_SAME_NAME_EXIST;

@Epic("API tests")
@Feature("TeamCity project")
public class ProjectTest extends BaseApiTest {

    @Test
    @Description("Creating project should be available")
    void creatingProjectShouldBeAvailable() {
        var testData = testDataStorage.addTestData();
        var project = checkedWithSuperUser.getProjectRequest().create(testData.getProject());

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    @Description("Creating project with empty name should not be available")
    void creatingProjectWithEmptyNameShouldNotBeAvailable() {

        var testData = testDataStorage.addTestData();
        testData.getProject().setName("");

        uncheckedWithSuperUser.getProjectRequest().create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString(EMPTY_PROJECT_NAME.getText()));
    }

    // Падает с 500 ошибкой???
    @Test
    @Description("Creating project with empty id should not be available")
    void creatingProjectWithEmptyIdShouldNotBeAvailable() {

        var testData = testDataStorage.addTestData();
        testData.getProject().setId("");

        uncheckedWithSuperUser.getProjectRequest().create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString(EMPTY_PROJECT_ID.getText()));
    }

    @Test
    @Description("Creating projects with same name should not be available")
    void creatingProjectsWithSameNameShouldNotBeAvailable() {
        var testData = testDataStorage.addTestData();

        checkedWithSuperUser.getProjectRequest().create(testData.getProject());

        var project = testData.getProject();
        project.setId(RandomData.getString());

        uncheckedWithSuperUser.getProjectRequest().create(project)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString(String.format(PROJECT_WITH_SAME_NAME_EXIST.getText(), project.getName())));
    }

    @Test
    @Description("Creating projects with same id should not be available")
    void creatingProjectsWithSameIdShouldNotBeAvailable() {
        var testData = testDataStorage.addTestData();

        checkedWithSuperUser.getProjectRequest().create(testData.getProject());

        var project = testData.getProject();
        project.setName(RandomData.getString());

        uncheckedWithSuperUser.getProjectRequest().create(project)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString(String.format(PROJECT_WITH_SAME_ID_EXIST.getText(), project.getId())));
    }

    @Test
    @Description("Creating project in another project should be available")
    void creatingProjectInAnotherProjectShouldBeAvailable() {
        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();
        var parentProject = checkedWithSuperUser.getProjectRequest().create(firstTestData.getProject());

        secondTestData.getProject().getParentProject().setLocator(parentProject.getId());

        var project = checkedWithSuperUser.getProjectRequest().create(secondTestData.getProject());

        softy.assertThat(project.getParentProjectId()).isEqualTo(parentProject.getId());
    }

    @Test
    @Description("Creating project with same name in different parents should be available")
    void creatingProjectWithSameNameInDifferentParentsShouldBeAvailable() {
        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();
        var thirdTestData = testDataStorage.addTestData();

        var firstProject = checkedWithSuperUser.getProjectRequest().create(firstTestData.getProject());
        var secondProject = checkedWithSuperUser.getProjectRequest().create(secondTestData.getProject());

        thirdTestData.getProject().getParentProject().setLocator(secondProject.getId());
        thirdTestData.getProject().setName(firstProject.getName());

        var thirdProject = checkedWithSuperUser.getProjectRequest().create(thirdTestData.getProject());

        softy.assertThat(thirdProject.getName()).isEqualTo(firstProject.getName());
    }
}
