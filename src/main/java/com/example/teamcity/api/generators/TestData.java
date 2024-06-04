package com.example.teamcity.api.generators;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.NewProjectDescription;
import com.example.teamcity.api.models.ServerAuthSettings;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.uncheked.UncheckedProject;
import com.example.teamcity.api.requests.uncheked.UncheckedUser;
import com.example.teamcity.api.spec.Specifications;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TestData {

    private User user;
    private NewProjectDescription project;
    private BuildType buildType;
    private ServerAuthSettings authSettings;


    public final void delete() {
        var spec = Specifications.getSpec().superUserSpec();

        if (project != null && !(project.getId().isEmpty()) && !(project.getName().isEmpty())) {
            new UncheckedProject(spec).delete(project.getId());
        }
        if (user != null && !(user.getUsername().isEmpty())) {
            new UncheckedUser(spec).delete(user.getUsername());
        }
    }
}
