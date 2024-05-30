package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.requests.Request;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class AuthRequest extends Request {

    private static final String AUTH_SETTINGS = "/app/rest/server/authSettings";

    public AuthRequest(RequestSpecification spec) {
        super(spec);
    }

    /**
     * @return возвращает строку с csrf токеном
     */
    public String getCsrfToken() {
        return RestAssured
                .given().spec(spec)
                .get("/authenticationTest.html?csrf")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();
    }
}
