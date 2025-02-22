package com.github.torbs00.mcwarzguns.gun;

import com.github.torbs00.mcwarzguns.gun.firemode.SingleShotFireMode;
import com.google.inject.Singleton;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class GunRegistry {

    private final Map<Material, ImmutableGunData> immutableGunsByMaterial = new HashMap<>();
    private final Map<String, ImmutableGunData> immutableGunsByID = new HashMap<>();

    public void loadGunsFromJson() {
        ImmutableGunData immutableGunData = new ImmutableGunData(
                Material.DIAMOND_PICKAXE,
                "barret50cal",
                "Barret 50.cal",
                Material.CLAY,
                10,
                20,
                5,
                new SingleShotFireMode());
        System.out.println(immutableGunData.name() + " added to the memory!");
        addToMemory(immutableGunData);
    }

    private void addToMemory(ImmutableGunData immutableGunData) {
        immutableGunsByID.put(immutableGunData.gunID().toLowerCase(), immutableGunData);
        immutableGunsByMaterial.put(immutableGunData.gunMaterial(), immutableGunData);
    }

    public ImmutableGunData getImmutableGunByMaterial(Material material) {
        return immutableGunsByMaterial.get(material);
    }

    public ImmutableGunData getImmutableGunByID(String gunID) {
        return immutableGunsByID.get(gunID.toLowerCase());
    }
}
