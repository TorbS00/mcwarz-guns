package com.github.torbs00.mcwarzguns.gun.firemode;

import com.github.torbs00.mcwarzguns.gun.PlayerGunState;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;

import java.util.Random;

public class PumpActionFireMode implements FireMode {

    private final int bullets;
    //TODO: Add bullet velocity.

    public PumpActionFireMode(int bullets) {
        this.bullets = bullets;
    }

    Random random = new Random();

    @Override
    public void onShoot(PlayerGunState playerGunState, Player shooter) {
        for (int i = 0; i < bullets; i++) {
            Snowball snowball = shooter.launchProjectile(Snowball.class);
            snowball.setTicksLived(100);

            // Get shooter's direction
            Vector direction = shooter.getLocation().getDirection();

            // Apply random spread
            double spread = 0.1; // Adjust this value to control the spread
            double offsetX = (random.nextDouble() - 0.5) * spread;
            double offsetY = (random.nextDouble() - 0.5) * spread;
            double offsetZ = (random.nextDouble() - 0.5) * spread;

            Vector spreadVector = direction.clone().add(new Vector(offsetX, offsetY, offsetZ)).normalize();

            snowball.setVelocity(spreadVector.multiply(1.5)); // Adjust speed if needed
        }
    }
}
