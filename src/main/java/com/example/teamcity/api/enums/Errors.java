package com.example.teamcity.api.enums;

import lombok.Getter;

@Getter
public enum Errors {

    PROJECT_WITH_SAME_NAME_EXIST("Project with this name already exists: %s"),
    EMPTY_PROJECT_NAME("Project name cannot be empty."),
    EMPTY_PROJECT_ID("Project ID must not be empty."),
    PROJECT_WITH_SAME_ID_EXIST("Project ID \"%s\" is already used by another project"),
    BUILD_CONFIG_WITH_SAME_NAME_EXIST("Build configuration with name \"%s\" already exists in project: \"%s\""),
    BUILD_CONFIG_WITH_SAME_ID_EXIST("The build configuration / template ID \"%s\" "
            + "is already used by another configuration or template"),
    BUILD_CONFIG_WITH_EMPTY_ID("Build configuration or template ID must not be empty."),
    BUILD_CONFIG_WITH_EMPTY_NAME("When creating a build type, non empty name should be provided."),
    UNRECOGNIZED_URL("Cannot create a project using the specified URL. The URL is not recognized.");

    private final String text;

    Errors(String text) {
        this.text = text;
    }
}
