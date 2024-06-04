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

    /**
     * @return проверенный на 200 код ответ на создание Project, извлеченный как Project.class
     */
    @Override
    public Project create(Object obj) {
        return new UncheckedProject(spec).create(obj)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Project.class);
    }

    /**
     * @return проверенный на 200 код ответ на получение Project, извлеченный как Project.class
     */
    @Override
    public Project get(String id) {
        return new UncheckedProject(spec)
                .get(id)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Project.class);
    }

    /**
     * @return null, т.к. метод не реализован
     */
    @Override
    public Object update(String id) {
        return null;
    }

    /**
     * @return проверенный на 200 код ответ на удаление Project, извлеченный как String
     */
    @Override
    public String delete(String id) {
        return new UncheckedProject(spec)
                .delete(id)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();
    }
}
