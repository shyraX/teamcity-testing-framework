package com.example.teamcity.api.requests.uncheked;

import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedUser extends Request implements CrudInterface {


    private final String userEndpoint = "/app/rest/users";

    public UncheckedUser(RequestSpecification spec) {
        super(spec);
    }

    /**
     * @return ответ от POST запроса на создание пользователя
     */
    @Override
    public Response create(Object obj) {
        return given()
                .spec(spec)
                .body(obj)
                .post(userEndpoint);
    }

    /**
     * @return ответ от GET запроса на получение пользователя
     */
    @Override
    public Object get(String id) {
        return null;
    }

    /**
     * @return ответ от PUT запроса на изменение пользователя
     */
    @Override
    public Object update(String id) {
        return null;
    }

    /**
     * @return ответ от DELETE запроса на удаление пользователя
     */
    @Override
    public Response delete(String id) {
        return given()
                .spec(spec)
                .delete(userEndpoint + "/username:" + id);
    }
}
