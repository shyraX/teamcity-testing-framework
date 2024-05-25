package com.example.teamcity.api;

import com.example.teamcity.BaseTest;
import com.example.teamcity.api.models.Module;
import com.example.teamcity.api.models.Modules;
import com.example.teamcity.api.models.ServerAuthSettings;
import com.example.teamcity.api.spec.Specifications;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

import java.util.Collections;

public class BaseApiTest extends BaseTest {

    private static final String AUTH_SETTINGS_ENDPOINT = "/app/rest/server/authSettings";

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
