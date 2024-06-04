package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.uncheked.UncheckedBuildConfig;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedBuildConfig extends Request implements CrudInterface {

    public CheckedBuildConfig(RequestSpecification spec) {
        super(spec);
    }

    /**
     * @return проверенный на 200 код ответ на создание BuildType, извлеченный как BuildType.class
     */
    @Override
    public BuildType create(Object obj) {
        return new UncheckedBuildConfig(spec).create(obj)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(BuildType.class);
    }

    /**
     * @return проверенный на 200 код ответ на получение BuildType, извлеченный как BuildType.class
     */
    @Override
    public BuildType get(String id) {
        return new UncheckedBuildConfig(spec).get(id)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(BuildType.class);
    }

    /**
     * @return null, т.к. метод не реализован
     */
    @Override
    public Object update(String id) {
        return null;
    }

    /**
     * @return проверенный на 204 код ответ на удаление BuildType, извлеченный как BuildType.class
     */
    @Override
    public String delete(String id) {
        return new UncheckedBuildConfig(spec).delete(id)
                .then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().asString();
    }
}
