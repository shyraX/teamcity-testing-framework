package com.example.teamcity.api.requests.uncheked;

import io.restassured.specification.RequestSpecification;
import lombok.Getter;

@Getter
public class UncheckedRequests {
    private final UncheckedUser userRequest;
    private final UncheckedProject projectRequest;
    private final UncheckedBuildConfig buildConfigRequest;
    private final UncheckedAgents uncheckedAgent;

    public UncheckedRequests(RequestSpecification spec) {
        this.userRequest = new UncheckedUser(spec);
        this.buildConfigRequest = new UncheckedBuildConfig(spec);
        this.projectRequest = new UncheckedProject(spec);
        this.uncheckedAgent = new UncheckedAgents(spec);
    }
}
