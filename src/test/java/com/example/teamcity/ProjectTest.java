package com.example.teamcity;

import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.generators.TestData;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.Project;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class ProjectTest extends BaseApiTest {

    @Test
    public void createProjectWithEmptyName() {

        var project = NewProjectDescription.builder().name("").parentProject(Project.builder().locator("_Root").build()).id(RandomData.getString()).build();
        var projectTestData = TestData.builder().project(project).build();
        var testData = testDataStorage.addTestData(projectTestData);

        uncheckedWithSuperUser.getProjectRequest()
                .create(testData.getProject())
                .then()
                .assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project name cannot be empty."));
    }

    // Падает с 500 ошибкой???
    @Test
    public void createProjectWithEmptyId() {

        var project = NewProjectDescription.builder().name(RandomData.getString()).parentProject(Project.builder().locator("_Root").build()).id("").build();
        var projectTestData = TestData.builder().project(project).build();
        var testData = testDataStorage.addTestData(projectTestData);

        uncheckedWithSuperUser.getProjectRequest()
                .create(testData.getProject())
                .then()
                .assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Project ID must not be empty."));
    }
}
