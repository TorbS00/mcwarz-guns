package com.github.torbs00.mcwarzguns;

import com.github.torbs00.mcwarzguns.api.GunAPI;
import com.github.torbs00.mcwarzguns.gun.GunRegistry;
import com.github.torbs00.mcwarzguns.gun.PlayerController;
import com.github.torbs00.mcwarzguns.gun.PlayerGunController;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

@Singleton
public class GunPluginBootstrap implements GunAPI {

    private final JavaPlugin plugin;
    private final Injector injector;

    private GunRegistry gunRegistry;
    private PlayerGunController playerGunController;

    @Inject
    public GunPluginBootstrap(JavaPlugin plugin, Injector injector) {
        this.plugin = plugin;
        this.injector = injector;
    }

    void load() {
        log("Start up works!");

        gunRegistry = injector.getInstance(GunRegistry.class);
        gunRegistry.loadGunsFromJson();

        playerGunController = injector.getInstance(PlayerGunController.class);
        registerListener(PlayerController.class);
    }

    void close() {
        log("Closing works!");
    }

    public void log(String log) {
        plugin.getLogger().log(Level.SEVERE, log);
    }

    private  <T extends Listener> T registerListener(Class<T> listenerClass) {
        T clazz = createInstance(listenerClass);
        plugin.getServer().getPluginManager().registerEvents(clazz, plugin);
        return clazz;
    }

    private  <T extends CommandExecutor> T registerCommand(String command, Class<T> commandClass) {
        T clazz = createInstance(commandClass);
        plugin.getCommand(command).setExecutor(clazz);
        return clazz;
    }

    private  <T> T createInstance(Class<T> clazz) {
        return injector.getInstance(clazz);
    }

}
