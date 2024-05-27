package com.example.teamcity.setup;

import com.example.teamcity.api.BaseApiTest;
import org.testng.annotations.Test;

public class AgentSetupTest extends BaseApiTest {

    @Test
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
