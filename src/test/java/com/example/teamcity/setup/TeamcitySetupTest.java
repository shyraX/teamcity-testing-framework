package com.example.teamcity.setup;

import com.example.teamcity.ui.BaseUiTest;
import com.example.teamcity.ui.pages.StartUpPage;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;

public class TeamcitySetupTest extends BaseUiTest {

    @Test
    public void startUpTest() {
        new StartUpPage()
                .open()
                .startUpTeamcityServer()
                .getHeader().shouldHave(text("Create Administrator Account"));
    }
}
