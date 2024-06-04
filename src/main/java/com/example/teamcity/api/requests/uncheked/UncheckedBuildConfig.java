package com.example.teamcity.api.requests.uncheked;

import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedBuildConfig extends Request implements CrudInterface {

    private static final String BUILD_CONFIG_ENDPOINT = "/app/rest/buildTypes";

    public UncheckedBuildConfig(RequestSpecification spec) {
        super(spec);
    }

    /**
     * @return ответ от POST запроса на создание билд конфигурации
     */
    @Override
    public Response create(Object obj) {
        return given().spec(spec).body(obj)
                .post(BUILD_CONFIG_ENDPOINT);
    }

    /**
     * @return ответ от GET запроса на получение билд конфигурации
     */
    @Override
    public Response get(String name) {
        return given().spec(spec).get(BUILD_CONFIG_ENDPOINT + "/name:" + name);
    }

    /**
     * @return ответ от PUT запроса на изменение билд конфигурации
     */
    @Override
    public Object update(String id) {
        return null;
    }

    /**
     * @return ответ от DELETE запроса на удаление билд конфигурации
     */
    @Override
    public Response delete(String id) {
        return given().spec(spec).delete(BUILD_CONFIG_ENDPOINT + "/id:" + id);
    }
}
