package com.example.teamcity.api;

import com.example.teamcity.api.enums.Role;
import com.example.teamcity.api.generators.TestDataGenerator;
import com.example.teamcity.api.requests.checked.CheckedBuildConfig;
import com.example.teamcity.api.requests.checked.CheckedProject;
import com.example.teamcity.api.requests.uncheked.UncheckedBuildConfig;
import com.example.teamcity.api.requests.uncheked.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

public class RolesTest extends BaseApiTest {

    @Test
    public void unauthorizedUserShouldNotHaveRightToCreateProject() {
        var testData = testDataStorage.addTestData();

        new UncheckedRequests(Specifications.getSpec().unAuthSpec()).getProjectRequest()
                .create(testData.getProject().getId())
                .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED);

        uncheckedWithSuperUser.getProjectRequest()
                .get(testData.getProject().getId())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void systemAdminTestShouldHaveRightsToCreateProject() {
        var testData = testDataStorage.addTestData();

        testData.getUser().setRoles(TestDataGenerator.generateRoles(Role.SYSTEM_ADMIN, "g"));

        checkedWithSuperUser.getUserRequest()
                .create(testData.getUser());
        var project = new CheckedProject(Specifications.getSpec()
                .authSpec(testData.getUser()))
                .create(testData.getProject());
        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    public void projectAdminShouldHaveRightsToCreateBuildConfigToHisProject() {
        var testData = testDataStorage.addTestData();

        checkedWithSuperUser.getProjectRequest()
                .create(testData.getProject());

        testData.getUser().setRoles(TestDataGenerator.generateRoles(Role.PROJECT_ADMIN, "p:" + testData.getProject().getId()));

        checkedWithSuperUser.getUserRequest()
                .create(testData.getUser());

        var buildConfig = new CheckedBuildConfig(Specifications.getSpec().authSpec(testData.getUser()))
                .create(testData.getBuildType());
        softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test
    public void projectAdminShouldNotHaveRightsToCreateBuildConfigToAnotherProject() {
        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();

        checkedWithSuperUser.getProjectRequest().create(firstTestData.getProject());
        checkedWithSuperUser.getProjectRequest().create(secondTestData.getProject());

        firstTestData.getUser().setRoles(TestDataGenerator.generateRoles(Role.PROJECT_ADMIN, "p:" + firstTestData.getProject().getId()));

        checkedWithSuperUser.getUserRequest().create(firstTestData.getUser());

        secondTestData.getUser().setRoles(TestDataGenerator.generateRoles(Role.PROJECT_ADMIN, "p:" + secondTestData.getProject().getId()));

        checkedWithSuperUser.getUserRequest()
                .create(secondTestData.getUser());

        new UncheckedBuildConfig(Specifications.getSpec().authSpec(firstTestData.getUser()))
                .create(secondTestData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
