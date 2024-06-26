package com.example.teamcity.ui;

import com.codeborne.selenide.Configuration;
import com.example.teamcity.BaseTest;
import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.User;
import com.example.teamcity.ui.pages.LoginPage;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeSuite;

public class BaseUiTest extends BaseTest {

    protected final static String REPOSITORY_URL = "https://github.com/AlexPshe/spring-core-for-qa";

    @BeforeSuite
    @Step("Setup UI tests configuration")
    public void setupUiTest() {
        Configuration.baseUrl = "http://" + Config.getProperty("host");
        Configuration.remote = Config.getProperty("remote");
        Configuration.timeout = 10000;

        BrowserSettings.setUp(Config.getProperty("browser"));
    }

    @Step("Create user and login")
    public void loginAsUser(User user) {
        checkedWithSuperUser.getUserRequest().create(user);
        new LoginPage().open().login(user);
    }

    @Step("Create project")
    public void createProject(NewProjectDescription project) {
        checkedWithSuperUser.getProjectRequest().create(project);
    }
}
