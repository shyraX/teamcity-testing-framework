package com.example.teamcity.setup;

import com.example.teamcity.ui.BaseUiTest;
import com.example.teamcity.ui.pages.StartUpPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;

@Epic("Setup infra tests")
@Feature("TeamCity server")
public class TeamcitySetupTest extends BaseUiTest {

    @Test
    @Description("Startup TeamCity server and accept license")
    public void startUpTest() {
        new StartUpPage()
                .open()
                .startUpTeamcityServer()
                .getHeader().shouldHave(text("Create Administrator Account"));
    }
}
