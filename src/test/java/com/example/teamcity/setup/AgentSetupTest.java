package com.example.teamcity.setup;

import com.example.teamcity.api.BaseApiTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

@Epic("Setup infra tests")
@Feature("TeamCity agent")
public class AgentSetupTest extends BaseApiTest {

    @Test
    @Description("Setup agent")
    public void setupAgentTest() {
        var agents = checkedWithSuperUser.getCheckedAgents().get();

        if (!agents.getAgent().isEmpty()) {
            checkedWithSuperUser.getCheckedAgents().put(agents.getAgent().get(0).getId(), "true");
        }
        else {
            System.out.println("Teamcity Agent not authorized");
        }
    }
}
