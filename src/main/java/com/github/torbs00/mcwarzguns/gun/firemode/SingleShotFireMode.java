package com.github.torbs00.mcwarzguns.gun.firemode;

import com.github.torbs00.mcwarzguns.gun.PlayerGunState;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;

public class SingleShotFireMode implements FireMode {
    @Override
    public void onShoot(PlayerGunState playerGunState, Player shooter, double accuracy) {
        Vector velocity = calculateBulletVelocity(shooter.getLocation(), playerGunState.getGun().accuracy(), playerGunState.getGun().bulletSpeed());
        Snowball snowball = shooter.launchProjectile(Snowball.class);
        snowball.setTicksLived(100);
        snowball.setVelocity(velocity);
    }
}
