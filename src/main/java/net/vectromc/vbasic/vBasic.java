package net.vectromc.vbasic;

import net.vectromc.vbasic.commands.*;
import net.vectromc.vbasic.listeners.PlayerLogEvents;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

public final class vBasic extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        registerCommands();
        registerEvents();
        startupAnnouncements();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        shutdownAnnouncements();
    }

    public ArrayList<UUID> toggle_staff_alerts = new ArrayList<>();

    private void startupAnnouncements() {
        System.out.println("[VectroMC] vBasic v1.0 by Yochran is loading...");
        System.out.println("[VectroMC] vBasic v1.0 by Yochran has successfully loaded.");
    }

    private void shutdownAnnouncements() {
        System.out.println("[VectroMC] vBasic v1.0 by Yochran is unloading...");
        System.out.println("[VectroMC] vBasic v1.0 by Yochran has successfully unloaded.");
    }

    private void registerCommands() {
        getCommand("vbasic").setExecutor(new BasicCommand());
        getCommand("teleport").setExecutor(new TeleportCommands());
        getCommand("teleporthere").setExecutor(new TeleportCommands());
        getCommand("teleportall").setExecutor(new TeleportCommands());
        getCommand("ToggleStaffAlerts").setExecutor(new ToggleStaffAlertsCommand());
        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("gamemode").setExecutor(new GamemodeCommands());
        getCommand("gmc").setExecutor(new GamemodeCommands());
        getCommand("gms").setExecutor(new GamemodeCommands());
        getCommand("gmsp").setExecutor(new GamemodeCommands());
        getCommand("gma").setExecutor(new GamemodeCommands());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("feed").setExecutor(new FeedCommand());
    }

    private void registerEvents() {
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new PlayerLogEvents(), this);
    }
}
