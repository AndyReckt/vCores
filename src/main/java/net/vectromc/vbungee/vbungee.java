package me.yochran.vbungee;

import me.yochran.vbungee.commands.*;
import me.yochran.vbungee.listeners.ChatListener;
import me.yochran.vbungee.listeners.PlayerLogListeners;
import me.yochran.vbungee.runnables.PlayerSeparatorRunnable;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class vbungee extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        runRunnables();
        registerListeners();
        registerCommands();
        startupAnnouncements();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        shutdownAnnouncements();
    }

    private void startupAnnouncements() {
        System.out.println("[VectroMC] vBungee v1.0 by Yochran is loading...");
        System.out.println("[VectroMC] vBungee v1.0 by Yochran has successfully loaded.");
    }

    private void shutdownAnnouncements() {
        System.out.println("[VectroMC] vBungee v1.0 by Yochran is unloading...");
        System.out.println("[VectroMC] vBungee v1.0 by Yochran has successfully unloaded.");
    }

    private void registerCommands() {
        getCommand("Find").setExecutor(new FindCommand());
        getCommand("GList").setExecutor(new GListCommand());
        getCommand("Send").setExecutor(new SendCommand());
        getCommand("Server").setExecutor(new ServerCommand());
        getCommand("Hub").setExecutor(new HubCommand());
        getCommand("vBungee").setExecutor(new BungeeCommand());
    }

    private void registerListeners() {
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new PlayerLogListeners(), this);
        manager.registerEvents(new ChatListener(), this);
    }

    private void runRunnables() {
        new PlayerSeparatorRunnable().runTaskTimer(this, 0, 5);
    }
}
