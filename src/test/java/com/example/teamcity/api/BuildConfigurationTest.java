package com.example.teamcity.api;

import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.generators.TestDataGenerator;
import com.example.teamcity.api.models.Steps;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Collections;

import static com.example.teamcity.api.enums.Errors.BUILD_CONFIG_WITH_EMPTY_ID;
import static com.example.teamcity.api.enums.Errors.BUILD_CONFIG_WITH_EMPTY_NAME;
import static com.example.teamcity.api.enums.Errors.BUILD_CONFIG_WITH_SAME_ID_EXIST;
import static com.example.teamcity.api.enums.Errors.BUILD_CONFIG_WITH_SAME_NAME_EXIST;

public class BuildConfigurationTest extends BaseApiTest {


    private static final String BUILD_CONFIG_STEP_NAME = "print";
    private static final String BUILD_CONFIG_STEP_TYPE = "simpleRunner";
    private static final String BUILD_CONFIG_PROPERTY_NAME = "script.content";
    private static final String BUILD_CONFIG_PROPERTY_VALUE = "echo 'Hello, World!'";

    @Test
    void creatingBuildConfigurationShouldBeAvailable() {

        var testData = testDataStorage.addTestData();
        checkedWithSuperUser.getProjectRequest().create(testData.getProject());
        var buildConfig = checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());

        softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test
    void creatingBuildConfigurationWithStepsShouldBeAvailable() {

        var testData = testDataStorage.addTestData();
        checkedWithSuperUser.getProjectRequest().create(testData.getProject());
        testData.getBuildType().setSteps(Steps.builder()
                .step(Collections.singletonList(TestDataGenerator.generateBuildConfigurationSteps(BUILD_CONFIG_STEP_NAME, BUILD_CONFIG_STEP_TYPE,
                        Collections.singletonList(TestDataGenerator.generateStepProperty(BUILD_CONFIG_PROPERTY_NAME, BUILD_CONFIG_PROPERTY_VALUE)))))
                .build());
        var buildConfig = checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());
        var actualStepName = buildConfig.getSteps().getStep().get(0).getName();
        var expectedStepName = testData.getBuildType().getSteps().getStep().get(0).getName();

        softy.assertThat(actualStepName).isEqualTo(expectedStepName);
    }

    @Test
    void creatingBuildConfigurationWithSameNameShouldNotBeAvailable() {

        var testData = testDataStorage.addTestData();
        var buildType = testData.getBuildType();
        checkedWithSuperUser.getProjectRequest().create(testData.getProject());
        checkedWithSuperUser.getBuildConfigRequest().create(buildType);

        buildType.setId(RandomData.getString());
        uncheckedWithSuperUser.getBuildConfigRequest().create(buildType)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString(String.format(BUILD_CONFIG_WITH_SAME_NAME_EXIST.getText(),
                        buildType.getName(), testData.getProject().getName())));
    }

    @Test
    void creatingBuildConfigurationWithSameIdShouldNotBeAvailable() {

        var testData = testDataStorage.addTestData();
        var buildType = testData.getBuildType();
        checkedWithSuperUser.getProjectRequest().create(testData.getProject());
        checkedWithSuperUser.getBuildConfigRequest().create(buildType);

        buildType.setName(RandomData.getString());
        uncheckedWithSuperUser.getBuildConfigRequest().create(buildType)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString(String.format(BUILD_CONFIG_WITH_SAME_ID_EXIST.getText(), buildType.getId())));
    }

    // 500 error?
    @Test
    void creatingBuildConfigurationWithEmptyIdShouldNotBeAvailable() {

        var testData = testDataStorage.addTestData();
        checkedWithSuperUser.getProjectRequest().create(testData.getProject());
        testData.getBuildType().setId("");

        uncheckedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString(BUILD_CONFIG_WITH_EMPTY_ID.getText()));

    }

    @Test
    void creatingBuildConfigurationWithEmptyNameShouldNotBeAvailable() {

        var testData = testDataStorage.addTestData();
        checkedWithSuperUser.getProjectRequest().create(testData.getProject());
        testData.getBuildType().setName("");

        uncheckedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString(BUILD_CONFIG_WITH_EMPTY_NAME.getText()));

    }
}