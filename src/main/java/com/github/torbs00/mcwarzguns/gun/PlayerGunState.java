package com.github.torbs00.mcwarzguns.gun;

import org.bukkit.entity.Player;

public class PlayerGunState {

    private final ImmutableGunData gun;
    private int ammoInClip;
    private long nextShootTime;

    public PlayerGunState(ImmutableGunData gun) {
        this.gun = gun;
        this.ammoInClip = gun.maxClipSize();
        this.nextShootTime = 0;
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

    public long getNextShootTime() {
        return nextShootTime;
    }

    public void setNextShootTime(long nextShootTime) {
        this.nextShootTime = nextShootTime;
    }
}
