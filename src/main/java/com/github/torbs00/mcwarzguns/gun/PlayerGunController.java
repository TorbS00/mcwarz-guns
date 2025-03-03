package com.github.torbs00.mcwarzguns.gun;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Singleton
public class PlayerGunController {
    private final GunRegistry gunRegistry;
    private final Map<UUID, Map<Material, PlayerGunState>> playerGuns;
    private final Cache<UUID, Long> shootCooldowns;

    private final Cache<UUID, Boolean> aiming;

    @Inject
    public PlayerGunController(GunRegistry gunRegistry) {
        this.gunRegistry = gunRegistry;
        this.playerGuns = new HashMap<>();
        this.shootCooldowns = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build();
        this.aiming = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build();
    }

    public void createGun(Player player, String gunId) {
        ImmutableGunData gun = gunRegistry.getImmutableGunByID(gunId);
        if (gun == null) {
            player.sendMessage("Gun not found: " + gunId);
            return;
        }
        playerGuns.computeIfAbsent(player.getUniqueId(), x -> new HashMap<>())
                .putIfAbsent(gun.gunMaterial(), new PlayerGunState(gun));
        player.sendMessage("You received a " + gun.name());
    }

    public void shootGun(Player player, Material material) {
        PlayerGunState pGun = getPlayerGun(player, material);
        if(canShoot(player.getUniqueId(), pGun.getGun().delay())) {
            double accuracy = pGun.getGun().accuracy();
            if(aiming.asMap().containsKey(player.getUniqueId())) {
                accuracy = pGun.getGun().accuracyAimed();
            }
            pGun.shoot(player, accuracy);
            player.playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, 2, 2.0F);
        }
    }

    public boolean canShoot(UUID playerId, long delayInMS) {
        Long lastShotTime = shootCooldowns.getIfPresent(playerId);
        long currentTime = System.currentTimeMillis();

        if (lastShotTime == null || (currentTime - lastShotTime >= delayInMS)) {
            shootCooldowns.put(playerId, currentTime);
            return true;
        }
        return false;
    }

    public void reloadGun(Player player, Material material) {
        PlayerGunState pGun = getPlayerGun(player, material);
        if (pGun != null) {
            pGun.reload(player);
        }
    }

    private PlayerGunState getPlayerGun(Player player, Material material) {
        UUID playerUUID = player.getUniqueId();
        Map<Material, PlayerGunState> guns = playerGuns.computeIfAbsent(playerUUID, uuid -> new HashMap<>());

        PlayerGunState playerGunState = guns.get(material);
        ImmutableGunData gun = gunRegistry.getImmutableGunByMaterial(material);

        if(playerGunState == null) {
            playerGunState = new PlayerGunState(gun);
            guns.put(material, playerGunState);
        }
        return playerGunState;
    }

    public boolean isGun(Material material) {
        return gunRegistry.getImmutableGunByMaterial(material) != null;
    }

    public void toggleAim(Player player) {
        UUID uuid = player.getUniqueId();
        boolean exists = aiming.asMap().containsKey(uuid);
        if(exists) {
            player.removePotionEffect(PotionEffectType.SLOWNESS);
            aiming.invalidate(uuid);
        } else {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 12000, 4));
            aiming.put(uuid, Boolean.TRUE);
        }
    }

    public void removeAim(Player player) {
        UUID uuid = player.getUniqueId();
        if(aiming.asMap().containsKey(uuid)) {
            player.removePotionEffect(PotionEffectType.SLOWNESS);
            aiming.invalidate(uuid);
        }
    }
}
