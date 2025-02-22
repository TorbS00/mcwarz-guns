package com.github.torbs00.mcwarzguns;

import com.github.torbs00.mcwarzguns.api.GunAPI;
import com.github.torbs00.mcwarzguns.gun.GunRegistry;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class GunPlugin extends JavaPlugin {

    private Injector injector;
    private GunPluginBootstrap gunPluginBootstrap;

    @Override
    public void onEnable() {
        injector = Guice.createInjector(new GunPluginModule(this));
        gunPluginBootstrap = injector.getInstance(GunPluginBootstrap.class);
        gunPluginBootstrap.load();

        Bukkit.getServicesManager().register(GunAPI.class, gunPluginBootstrap, this, ServicePriority.Normal);
    }

    @Override
    public void onDisable() {
        gunPluginBootstrap.close();

        Bukkit.getServicesManager().unregister(GunAPI.class, gunPluginBootstrap);
    }

    public GunAPI getGunAPI() {
        return gunPluginBootstrap;
    }

}
