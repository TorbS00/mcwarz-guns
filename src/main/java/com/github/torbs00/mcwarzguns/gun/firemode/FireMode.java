package com.github.torbs00.mcwarzguns.gun.firemode;

import com.github.torbs00.mcwarzguns.gun.PlayerGunState;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public interface FireMode {
    void onShoot(PlayerGunState playerGunState, Player shooter, double accuracy);

    default Vector calculateBulletVelocity(Location location, double gunAccuracy, double bulletSpeed) {
        int acc = (int) (gunAccuracy * 1000.0D);
        if (acc <= 0) {
            acc = 1;
        }
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        double dir = (-location.getYaw() - 90.0F);
        double pitch = -location.getPitch();
        double xwep = ((rand.nextInt(acc) - rand.nextInt(acc)) + 0.5D) / 1000.0D;
        double ywep = ((rand.nextInt(acc) - rand.nextInt(acc)) + 0.5D) / 1000.0D;
        double zwep = ((rand.nextInt(acc) - rand.nextInt(acc)) + 0.5D) / 1000.0D;
        double xd = Math.cos(Math.toRadians(dir)) * Math.cos(Math.toRadians(pitch)) + xwep;
        double yd = Math.sin(Math.toRadians(pitch)) + ywep;
        double zd = -Math.sin(Math.toRadians(dir)) * Math.cos(Math.toRadians(pitch)) + zwep;
        Vector vec = new Vector(xd, yd, zd);
        vec.multiply(bulletSpeed);
        return vec;
    }
}
