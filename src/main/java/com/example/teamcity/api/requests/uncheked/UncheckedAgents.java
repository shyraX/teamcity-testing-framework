package com.example.teamcity.api.requests.uncheked;

import com.example.teamcity.api.requests.Request;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UncheckedAgents extends Request {

    private static final String AGENTS_ENDPOINT = "/app/rest/agents";

    public UncheckedAgents(RequestSpecification spec) {
        super(spec);
    }

    /**
     * @return ответ от GET запроса на поиск агента
     */
    public Response get() {
        return given()
                .spec(spec)
                .get(AGENTS_ENDPOINT + "?locator=authorized:any");
    }

    /**
     * @return ответ от PUT запроса на добавление агента
     */
    public Response put(String id, String body) {
        return given()
                .spec(spec)
                .body(body)
                .contentType("text/plain")
                .accept("text/plain")
                .put(AGENTS_ENDPOINT + "/id:" + id + "/authorized");
    }
}
