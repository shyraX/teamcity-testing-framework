package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.uncheked.UncheckedProject;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedProject extends Request implements CrudInterface {

    public CheckedProject(RequestSpecification spec) {
        super(spec);
    }

    @Override
    public Project create(Object obj) {
        return new UncheckedProject(spec).create(obj)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Project.class);
    }

    @Override
    public Project get(String id) {
        return new UncheckedProject(spec)
                .get(id)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Project.class);
    }

    @Override
    public Object update(String id) {
        return null;
    }

    @Override
    public String delete(String id) {
        return new UncheckedProject(spec)
                .delete(id)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();
    }
}
