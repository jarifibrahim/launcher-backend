package io.fabric8.launcher.core.impl;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import io.fabric8.launcher.core.api.Boom;
import io.fabric8.launcher.core.api.ImmutableBoom;
import io.fabric8.launcher.core.api.MissionControl;
import io.fabric8.launcher.core.api.events.StatusMessageEventBroker;
import io.fabric8.launcher.core.api.projectiles.ImmutableImportFromGitProjectile;
import io.fabric8.launcher.core.api.projectiles.ImportFromGitProjectile;
import io.fabric8.launcher.core.api.projectiles.context.ImportFromGitProjectileContext;
import io.fabric8.launcher.core.impl.steps.GitSteps;
import io.fabric8.launcher.core.impl.steps.OpenShiftSteps;
import io.fabric8.launcher.service.git.api.GitRepository;
import io.fabric8.launcher.service.openshift.api.OpenShiftProject;

@Dependent
public class ImportFromGitMissionControlImpl implements MissionControl<ImportFromGitProjectileContext, ImportFromGitProjectile> {

    @Inject
    private StatusMessageEventBroker eventBroker;

    @Inject
    private GitSteps gitSteps;

    @Inject
    private OpenShiftSteps openShiftSteps;

    @Override
    public ImportFromGitProjectile prepare(ImportFromGitProjectileContext context) {
        return ImmutableImportFromGitProjectile.builder()
                .gitOrganization(context.getGitOrganization())
                .gitRepositoryName(context.getGitRepository())
                .builderImage(context.getBuilderImage())
                .eventConsumer(eventBroker::send)
                .openShiftProjectName(context.getProjectName())
                .build();
    }

    @Override
    public Boom launch(ImportFromGitProjectile projectile) {
        GitRepository repository = gitSteps.findRepository(projectile.getGitOrganization(), projectile.getGitRepositoryName());
        OpenShiftProject openShiftProject = openShiftSteps.createOpenShiftProject(projectile);
        openShiftSteps.importFromGitRepository(openShiftProject, repository, projectile);
        return ImmutableBoom.builder()
                .createdProject(openShiftProject)
                .createdRepository(repository)
                .build();
    }
}