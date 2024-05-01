package com.example.teamcity;

import com.example.teamcity.api.generators.RandomData;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class ProjectTest extends BaseApiTest {

    private static final String EMPTY_PROJECT_NAME_ERROR = "Project name cannot be empty.";
    private static final String EMPTY_PROJECT_ID_ERROR = "Project ID must not be empty.";
    private static final String PROJECT_WITH_SAME_NAME_EXIST_ERROR = "Project with this name already exists: %s";
    private static final String PROJECT_WITH_SAME_ID_EXIST_ERROR = "Project ID \"%s\" is already used by another project";

    @Test
    void creatingProjectShouldBeAvailable() {
        var testData = testDataStorage.addTestData();
        var project = checkedWithSuperUser.getProjectRequest().create(testData.getProject());

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    void creatingProjectWithEmptyNameShouldNotBeAvailable() {

        var testData = testDataStorage.addTestData();
        testData.getProject().setName("");

        uncheckedWithSuperUser.getProjectRequest().create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString(EMPTY_PROJECT_NAME_ERROR));
    }

    // Падает с 500 ошибкой???
    @Test
    void creatingProjectWithEmptyIdShouldNotBeAvailable() {

        var testData = testDataStorage.addTestData();
        testData.getProject().setId("");

        uncheckedWithSuperUser.getProjectRequest().create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString(EMPTY_PROJECT_ID_ERROR));
    }

    @Test
    void creatingProjectsWithSameNameShouldNotBeAvailable() {
        var testData = testDataStorage.addTestData();

        checkedWithSuperUser.getProjectRequest().create(testData.getProject());

        var project = testData.getProject();
        project.setId(RandomData.getString());

        uncheckedWithSuperUser.getProjectRequest().create(project)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString(String.format(PROJECT_WITH_SAME_NAME_EXIST_ERROR, project.getName())));
    }

    @Test
    void creatingProjectsWithSameIdShouldNotBeAvailable() {
        var testData = testDataStorage.addTestData();

        checkedWithSuperUser.getProjectRequest().create(testData.getProject());

        var project = testData.getProject();
        project.setName(RandomData.getString());

        uncheckedWithSuperUser.getProjectRequest().create(project)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString(String.format(PROJECT_WITH_SAME_ID_EXIST_ERROR, project.getId())));
    }

    @Test
    void creatingProjectInAnotherProjectShouldBeAvailable() {
        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();
        var parentProject = checkedWithSuperUser.getProjectRequest().create(firstTestData.getProject());

        secondTestData.getProject().getParentProject().setLocator(parentProject.getId());

        var project = checkedWithSuperUser.getProjectRequest().create(secondTestData.getProject());

        softy.assertThat(project.getParentProjectId()).isEqualTo(parentProject.getId());
    }

    @Test
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
