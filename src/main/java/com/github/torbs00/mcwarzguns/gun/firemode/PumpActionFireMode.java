package com.github.torbs00.mcwarzguns.gun.firemode;

import com.github.torbs00.mcwarzguns.gun.PlayerGunState;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;

import java.util.Random;

public class PumpActionFireMode implements FireMode {

    private final int bullets;

    public PumpActionFireMode(int bullets) {
        this.bullets = bullets;
    }
    @Override
    public void onShoot(PlayerGunState playerGunState, Player shooter, double accuracy) {
        for (int i = 0; i < bullets; i++) {
            Vector velocity = calculateBulletVelocity(shooter.getLocation(), playerGunState.getGun().accuracy(), playerGunState.getGun().bulletSpeed());
            Snowball snowball = shooter.launchProjectile(Snowball.class);
            snowball.setTicksLived(100);
            snowball.setVelocity(velocity);
        }
    }
}
