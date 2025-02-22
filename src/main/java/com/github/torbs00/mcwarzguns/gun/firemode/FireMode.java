package com.github.torbs00.mcwarzguns.gun.firemode;

import com.github.torbs00.mcwarzguns.gun.PlayerGunState;
import org.bukkit.entity.Player;

public interface FireMode {
    void onShoot(PlayerGunState playerGunState, Player shooter);
}
