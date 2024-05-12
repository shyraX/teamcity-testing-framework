package com.example.teamcity;

import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.Module;
import com.example.teamcity.api.models.Modules;
import com.example.teamcity.api.models.ServerAuthSettings;
import com.example.teamcity.api.requests.checked.CheckedRequests;
import com.example.teamcity.api.requests.uncheked.UncheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.Collections;

public class BaseApiTest extends BaseTest {

    public TestDataStorage testDataStorage;
    private static final String AUTH_SETTINGS_ENDPOINT = "/app/rest/server/authSettings";
    public CheckedRequests checkedWithSuperUser = new CheckedRequests(Specifications.getSpec().superUserSpec());
    public UncheckedRequests uncheckedWithSuperUser = new UncheckedRequests(Specifications.getSpec().superUserSpec());

    @BeforeMethod
    public void setUpTest() {
        testDataStorage = TestDataStorage.getTestDataStorage();
    }

    @AfterMethod
    public void cleanTest() {
        testDataStorage.delete();
    }

    @BeforeSuite
    public void setup() {
        enableProjectPermissions();
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
