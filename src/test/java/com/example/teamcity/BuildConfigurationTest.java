package com.example.teamcity;

import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.generators.TestDataGenerator;
import com.example.teamcity.api.models.Steps;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Collections;

public class BuildConfigurationTest extends BaseApiTest {

    private static final String BUILD_CONFIG_WITH_SAME_NAME_EXIST_ERROR = "Build configuration with name \"%s\" already exists in project: \"%s\"";
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
                .body(Matchers.containsString(String.format(BUILD_CONFIG_WITH_SAME_NAME_EXIST_ERROR,
                        buildType.getName(), testData.getProject().getName())));
    }
}
