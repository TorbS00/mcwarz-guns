package com.github.torbs00.mcwarzguns.gun;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class PlayerGunController {
    private final GunRegistry gunRegistry;
    private final Map<UUID, Map<Material, PlayerGunState>> playerGuns = new HashMap<>();

    @Inject
    public PlayerGunController(GunRegistry gunRegistry) {
        this.gunRegistry = gunRegistry;
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
        pGun.shoot(player);
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

}
