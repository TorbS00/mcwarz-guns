package com.github.torbs00.mcwarzguns.gun;

import com.github.torbs00.mcwarzguns.gun.firemode.FireMode;
import org.bukkit.Material;
import java.util.Objects;
public record ImmutableGunData(
        Material gunMaterial,
        String gunID,
        String name,
        Material ammoMaterial,
        long delay,
        int maxClipSize,
        double damagePerBullet,
        long reloadTimeMs,
        double bulletSpeed,
        double accuracy,
        double accuracyAimed,
        FireMode fireMode
) {
    public ImmutableGunData {
        Objects.requireNonNull(gunMaterial);
        Objects.requireNonNull(gunID);
        Objects.requireNonNull(name);
        Objects.requireNonNull(ammoMaterial);
        Objects.requireNonNull(fireMode);
    }
}
