package com.github.torbs00.mcwarzguns.gun;

import com.google.inject.Inject;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerController implements Listener {

    private final PlayerGunController playerGunController;

    @Inject
    public PlayerController(PlayerGunController playerGunController) {
        this.playerGunController = playerGunController;
    }

    @EventHandler
    public void onPlayerShoot(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Action action = event.getAction();
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        Material material = item.getType();

        if (playerGunController.isGun(material)) {
            if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
                playerGunController.shootGun(event.getPlayer(), material);
            } else if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                playerGunController.toggleAim(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onHotbarSlotChange(PlayerItemHeldEvent event) {
        int newSlot = event.getNewSlot();
        ItemStack item = event.getPlayer().getInventory().getItem(newSlot);

        if (item == null || !playerGunController.isGun(item.getType())) {
            playerGunController.removeAim(event.getPlayer());
        }
    }
}
