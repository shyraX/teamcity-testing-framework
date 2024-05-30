package com.example.teamcity.api;

import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.generators.TestDataGenerator;
import com.example.teamcity.api.models.Steps;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Collections;

import static com.example.teamcity.api.enums.Errors.BUILD_CONFIG_WITH_EMPTY_ID;
import static com.example.teamcity.api.enums.Errors.BUILD_CONFIG_WITH_EMPTY_NAME;
import static com.example.teamcity.api.enums.Errors.BUILD_CONFIG_WITH_SAME_ID_EXIST;
import static com.example.teamcity.api.enums.Errors.BUILD_CONFIG_WITH_SAME_NAME_EXIST;

@Epic("Api tests")
@Feature("TeamCity build configuration")
public class BuildConfigurationTest extends BaseApiTest {

    private static final String BUILD_CONFIG_STEP_NAME = "print";
    private static final String BUILD_CONFIG_STEP_TYPE = "simpleRunner";
    private static final String BUILD_CONFIG_PROPERTY_NAME = "script.content";
    private static final String BUILD_CONFIG_PROPERTY_VALUE = "echo 'Hello, World!'";

    @Test
    @Description("Creating build configuration should be available")
    void creatingBuildConfigurationShouldBeAvailable() {

        var testData = testDataStorage.addTestData();
        checkedWithSuperUser.getProjectRequest().create(testData.getProject());
        var buildConfig = checkedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType());

        softy.assertThat(buildConfig.getId()).isEqualTo(testData.getBuildType().getId());
    }

    @Test
    @Description("Creating build configuration with steps should be available")
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
    @Description("Creating build configuration with same name should not be available")
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
    @Description("Creating build configuration with same id should not be available")
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

    @Test
    @Description("Creating build configuration with empty id should not be available")
    void creatingBuildConfigurationWithEmptyIdShouldNotBeAvailable() {

        var testData = testDataStorage.addTestData();
        checkedWithSuperUser.getProjectRequest().create(testData.getProject());
        testData.getBuildType().setId("");

        uncheckedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString(BUILD_CONFIG_WITH_EMPTY_ID.getText()));

    }

    @Test
    @Description("Creating build configuration with empty name should not be available")
    void creatingBuildConfigurationWithEmptyNameShouldNotBeAvailable() {

        var testData = testDataStorage.addTestData();
        checkedWithSuperUser.getProjectRequest().create(testData.getProject());
        testData.getBuildType().setName("");

        uncheckedWithSuperUser.getBuildConfigRequest().create(testData.getBuildType())
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString(BUILD_CONFIG_WITH_EMPTY_NAME.getText()));

    }
}
