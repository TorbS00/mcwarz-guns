package com.github.torbs00.mcwarzguns.gun.firemode;

import com.github.torbs00.mcwarzguns.gun.PlayerGunState;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

public class SingleShotFireMode implements FireMode {
    @Override
    public void onShoot(PlayerGunState playerGunState, Player shooter) {
        Snowball snowball = shooter.launchProjectile(Snowball.class);
        snowball.setVelocity(shooter.getLocation().getDirection());
    }
}
