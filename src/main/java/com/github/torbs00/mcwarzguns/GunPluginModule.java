package com.github.torbs00.mcwarzguns;

import com.github.torbs00.mcwarzguns.api.GunAPI;
import com.github.torbs00.mcwarzguns.gun.GunRegistry;
import com.github.torbs00.mcwarzguns.gun.PlayerGunController;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.bukkit.plugin.java.JavaPlugin;

public class GunPluginModule extends AbstractModule {
    private final JavaPlugin plugin;

    public GunPluginModule(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(JavaPlugin.class).toInstance(plugin);
        bind(GunAPI.class).to(GunPluginBootstrap.class).in(Singleton.class);
        bind(GunRegistry.class).in(Singleton.class);
        bind(PlayerGunController.class).in(Singleton.class);
    }
}
