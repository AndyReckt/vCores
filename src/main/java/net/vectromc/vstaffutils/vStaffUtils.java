package net.vectromc.vstaffutils;

import net.vectromc.vstaffutils.commands.*;
import net.vectromc.vstaffutils.listeners.*;
import net.vectromc.vstaffutils.listeners.modmode.ModmodeItemListeners;
import net.vectromc.vstaffutils.listeners.modmode.ModmodeProhibiters;
import net.vectromc.vstaffutils.runnables.FreezeCooldown;
import net.vectromc.vstaffutils.runnables.VanishTicker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class vStaffUtils extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        registerEvents();
        registerCommands();
        runRunnables();
        startupAnnouncements();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        shutdownAnnouncements();
    }

    public ArrayList<UUID> vanished = new ArrayList<>();
    public ArrayList<UUID> modmode = new ArrayList<>();
    public ArrayList<UUID> buildmode = new ArrayList<>();
    public ArrayList<UUID> awaiting_respawn = new ArrayList<>();
    public ArrayList<UUID> frozen = new ArrayList<>();
    public ArrayList<UUID> reporting = new ArrayList<>();
    public ArrayList<UUID> custom_reason = new ArrayList<>();
    public ArrayList<UUID> vanish_logged = new ArrayList<>();
    public ArrayList<Player> player_list = new ArrayList<>();
    public HashMap<UUID, Player> report_set = new HashMap<>();
    public HashMap<UUID, ItemStack[]> staff_inventory = new HashMap<>();
    public HashMap<UUID, ItemStack[]> staff_armor = new HashMap<>();
    public HashMap<UUID, ItemStack[]> invsee_inventory = new HashMap<>();
    public HashMap<UUID, ItemStack[]> invsee_armor = new HashMap<>();
    public HashMap<UUID, Double> health_level = new HashMap<>();
    public HashMap<UUID, Integer> food_level = new HashMap<>();
    public HashMap<UUID, Double> freeze_cooldown = new HashMap<>();

    private void startupAnnouncements() {
        System.out.println("[VectroMC] vStaffUtils v1.0 by Yochran is loading...");
        System.out.println("[VectroMC] vStaffUtils v1.0 by Yochran has successfully loaded.");
    }

    private void shutdownAnnouncements() {
        System.out.println("[VectroMC] vStaffUtils v1.0 by Yochran is unloading...");
        System.out.println("[VectroMC] vStaffUtils v1.0 by Yochran has successfully unloaded.");
    }

    private void registerCommands() {
        getCommand("vStaffUtils").setExecutor(new StaffUtilsCommand());
        getCommand("Vanish").setExecutor(new VanishCommand());
        getCommand("BuildMode").setExecutor(new BuildModeCommand());
        getCommand("Modmode").setExecutor(new ModmodeCommand());
        getCommand("Freeze").setExecutor(new FreezeCommand());
        getCommand("report").setExecutor(new ReportCommand());
        getCommand("Invsee").setExecutor(new InvseeCommand());
        getCommand("OnlineStaff").setExecutor(new OnlineStaffCommand());
    }

    private void registerEvents() {
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new VanishUpdater(), this);
        manager.registerEvents(new BuildModeListener(), this);
        manager.registerEvents(new ModmodeItemListeners(), this);
        manager.registerEvents(new ModmodeProhibiters(), this);
        manager.registerEvents(new FreezeListener(), this);
        manager.registerEvents(new ReportGuiClickListener(), this);
        manager.registerEvents(new ReportCustomChatReason(), this);
        manager.registerEvents(new PlayerLogEvents(), this);
        manager.registerEvents(new WorldChangeListener(), this);
    }

    private void runRunnables() {
        new VanishTicker().runTaskTimer(this, 0, 10);
        new FreezeCooldown().runTaskTimer(this, 0, 5);
    }
}
