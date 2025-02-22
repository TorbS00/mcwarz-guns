package com.github.torbs00.mcwarzguns.gun;

import org.bukkit.entity.Player;

public class PlayerGunState {

    private final ImmutableGunData gun;
    private int ammoInClip;

    public PlayerGunState(ImmutableGunData gun) {
        this.gun = gun;
        this.ammoInClip = gun.maxClipSize();
    }

    public void shoot(Player shooter) {
        gun.fireMode().onShoot(this, shooter);
    }

    public void reload(Player player) {
        ammoInClip = gun.maxClipSize();
        player.sendMessage("Reloaded your " + gun.name());
    }

    public ImmutableGunData getGun() {
        return gun;
    }

    public int getAmmoInClip() {
        return ammoInClip;
    }

    public void setAmmoInClip(int ammoInClip) {
        this.ammoInClip = ammoInClip;
    }

}
