package com.example.teamcity.api.enums;

import lombok.Getter;

@Getter
public enum Role {

    SYSTEM_ADMIN("SYSTEM_ADMIN"),
    PROJECT_VIEWER("PROJECT_VIEWER"),
    PROJECT_DEVELOPER("PROJECT_DEVELOPER"),
    PROJECT_ADMIN("PROJECT_ADMIN"),
    AGENT_MANAGER("AGENT_MANAGER"),
    TOOLS_INTEGRATION("TOOLS_INTEGRATION");

    private final String text;

    Role(String text) {
        this.text = text;
    }

}
