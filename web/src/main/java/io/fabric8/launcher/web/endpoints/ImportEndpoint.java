package io.fabric8.launcher.web.endpoints;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import io.fabric8.launcher.core.api.MissionControl;
import io.fabric8.launcher.core.api.events.LauncherStatusEventKind;
import io.fabric8.launcher.core.api.projectiles.ImportFromGitProjectile;
import io.fabric8.launcher.core.api.projectiles.context.ImportFromGitProjectileContext;
import io.fabric8.launcher.web.endpoints.inputs.ImportFromGitProjectileInput;

import static java.util.Arrays.asList;

@Path("/launcher/import")
@RequestScoped
public class ImportEndpoint extends AbstractLaunchEndpoint {

    @Inject
    private MissionControl<ImportFromGitProjectileContext, ImportFromGitProjectile> importFromGitMissionControl;

    @POST
    @Path("/git")
    @Produces(MediaType.APPLICATION_JSON)
    public void importFromGit(@Valid @BeanParam ImportFromGitProjectileInput input,
                              @Context HttpServletResponse response,
                              @Suspended AsyncResponse asyncResponse) throws IOException {
        ImportFromGitProjectile projectile = importFromGitMissionControl.prepare(input);
        try {
            doLaunch(projectile, importFromGitMissionControl::launch, asList(LauncherStatusEventKind.OPENSHIFT_CREATE,
                                                                             LauncherStatusEventKind.OPENSHIFT_PIPELINE),
                     response, asyncResponse);
        } finally {
            reaper.delete(projectile.getProjectLocation());
        }
    }
}