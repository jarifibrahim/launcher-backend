package io.fabric8.launcher.core.api.projectiles.context;

import io.fabric8.launcher.core.api.ProjectileContext;
import org.immutables.value.Value;

@Value.Immutable
public interface ImportFromGitProjectileContext extends ProjectileContext, ProjectNameCapable, GitCapable {
    String getBuilderImage();
}
