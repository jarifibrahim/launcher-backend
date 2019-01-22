package io.fabric8.launcher.web.endpoints.inputs;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;

import io.fabric8.launcher.core.api.projectiles.context.ImportFromGitProjectileContext;

import static io.fabric8.launcher.service.git.api.GitService.GIT_NAME_REGEXP;
import static io.fabric8.launcher.service.openshift.api.OpenShiftService.PROJECT_NAME_REGEX;
import static io.fabric8.launcher.service.openshift.api.OpenShiftService.PROJECT_NAME_VALIDATION_MESSAGE;

public class ImportFromGitProjectileInput implements ImportFromGitProjectileContext {

    @FormParam("gitOrganization")
    @Pattern(message = "gitOrganization should follow consist only of alphanumeric characters, '-', '_' or '.' .",
            regexp = GIT_NAME_REGEXP)
    private String gitOrganization;

    @FormParam("gitRepository")
    @NotNull(message = "gitRepository is required")
    @Pattern(message = "gitRepository should follow consist only of alphanumeric characters, '-', '_' or '.' .",
            regexp = GIT_NAME_REGEXP)
    private String gitRepository;

    @FormParam("projectName")
    @NotNull(message = "projectName is required")
    @Pattern(message = PROJECT_NAME_VALIDATION_MESSAGE,
            regexp = PROJECT_NAME_REGEX)
    private String projectName;

    @FormParam("builderImage")
    @NotNull(message = "builderImage is required")
    private String builderImage;

    @Nullable
    @Override
    public String getGitOrganization() {
        return gitOrganization;
    }

    @Override
    public String getGitRepository() {
        return gitRepository;
    }

    @Override
    public String getProjectName() {
        return projectName;
    }

    @Override
    public String getBuilderImage() {
        return builderImage;
    }
}
