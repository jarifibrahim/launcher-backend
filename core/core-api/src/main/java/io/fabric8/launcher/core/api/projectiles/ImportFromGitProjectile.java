package io.fabric8.launcher.core.api.projectiles;

import java.nio.file.Path;

import javax.annotation.Nullable;

import org.immutables.value.Value;

@Value.Immutable
public interface ImportFromGitProjectile extends ImportProjectile {

    @Override
    @Nullable
    Path getProjectLocation();

    String getBuilderImage();
}
