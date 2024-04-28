package com.example.teamcity.api.requests.checked;

import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class CheckedRequests {
    private final CheckedUser userRequest;
    private final CheckedProject projectRequest;
    private final CheckedBuildConfig buildConfigRequest;
    private final AuthRequest authRequest;

    public CheckedRequests(RequestSpecification spec) {
        this.userRequest = new CheckedUser(spec);
        this.buildConfigRequest = new CheckedBuildConfig(spec);
        this.projectRequest = new CheckedProject(spec);
        this.authRequest = new AuthRequest(spec);
    }
}
