package com.example.teamcity;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.Module;
import com.example.teamcity.api.models.Modules;
import com.example.teamcity.api.models.ServerAuthSettings;
import com.example.teamcity.api.requests.checked.CheckedRequests;
import com.example.teamcity.api.requests.uncheked.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import io.restassured.RestAssured;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.Collections;

public class BaseTest {

    protected SoftAssertions softy;
    public TestDataStorage testDataStorage;
    public CheckedRequests checkedWithSuperUser = new CheckedRequests(Specifications.getSpec().superUserSpec());
    public UncheckedRequests uncheckedWithSuperUser = new UncheckedRequests(Specifications.getSpec().superUserSpec());
    private static final String AUTH_SETTINGS_ENDPOINT = "/app/rest/server/authSettings";

    @BeforeSuite
    public void setup() {
        enableProjectPermissions();
    }

    @BeforeMethod
    public void setUpTest() {
        testDataStorage = TestDataStorage.getTestDataStorage();
    }

    @AfterMethod
    public void cleanTest() {
        testDataStorage.delete();
    }

    @BeforeMethod
    public void beforeTest() {
        softy = new SoftAssertions();
    }

    @AfterMethod
    public void afterTest() {
        softy.assertAll();
    }

    private void enableProjectPermissions() {
        var authSettings = ServerAuthSettings.builder()
                .perProjectPermissions(true)
                .modules(Modules.builder()
                        .module(Collections.singletonList(Module.builder()
                                .name("HTTP-Basic")
                                .build()))
                        .build())
                .build();
        RestAssured.given()
                .spec(Specifications.getSpec().superUserSpec())
                .body(authSettings)
                .put(AUTH_SETTINGS_ENDPOINT);
    }

}
