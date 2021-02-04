package me.yochran.vbungee;

import me.yochran.vbungee.commands.*;
import me.yochran.vbungee.data.ServerData;
import me.yochran.vbungee.listeners.*;
import me.yochran.vbungee.runnables.PlayerSeparatorRunnable;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class vbungee extends JavaPlugin {

    public ServerData data;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        registerData();
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
        getCommand("Spawn").setExecutor(new SpawnCommand());
    }

    private void registerListeners() {
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new PlayerLogListeners(), this);
        manager.registerEvents(new ChatListener(), this);
        manager.registerEvents(new CommandListener(), this);
        manager.registerEvents(new WorldChangeListener(), this);
        manager.registerEvents(new RespawnListener(), this);
    }

    private void runRunnables() {
        new PlayerSeparatorRunnable().runTaskTimer(this, 0, 5);
    }

    private void registerData() {
        data = new ServerData();
        data.setupData();
        data.saveData();
        data.reloadData();

        for (World world : Bukkit.getServer().getWorlds()) {
            if (!data.config.contains("Servers." + world.getName())) {
                data.config.set("Servers." + world.getName() + ".WorldName", world.getName());
                data.config.set("Servers." + world.getName() + ".Enabled", true);
                data.config.set("Servers." + world.getName() + ".Spawn.X", 0.5);
                data.config.set("Servers." + world.getName() + ".Spawn.Y", 60);
                data.config.set("Servers." + world.getName() + ".Spawn.Z", 0.5);
                data.config.set("Servers." + world.getName() + ".Spawn.Pitch", 0.5);
                data.config.set("Servers." + world.getName() + ".Spawn.Yaw", 0.5);
            }
        }
        data.saveData();
    }
}
