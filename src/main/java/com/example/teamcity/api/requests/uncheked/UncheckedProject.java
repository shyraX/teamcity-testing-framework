package com.example.teamcity.api.requests.uncheked;

import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedProject extends Request implements CrudInterface {

    private static final String PROJECT_ENDPOINT = "/app/rest/projects";

    public UncheckedProject(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Response create(Object obj) {
        return given()
                .spec(spec)
                .body(obj)
                .post(PROJECT_ENDPOINT);
    }

    @Override
    public Response get(String name) {
        return given().spec(spec).get(PROJECT_ENDPOINT + "/name:" + name);
    }

    @Override
    public Object update(String id) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return given()
                .spec(spec)
                .delete(PROJECT_ENDPOINT + "/id:" + id);
    }
}
