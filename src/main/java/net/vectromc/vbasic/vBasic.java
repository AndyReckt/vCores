package net.vectromc.vbasic;

import net.vectromc.vbasic.commands.*;
import net.vectromc.vbasic.commands.economy.BalanceCommand;
import net.vectromc.vbasic.commands.economy.BountyCommand;
import net.vectromc.vbasic.commands.economy.PayCommand;
import net.vectromc.vbasic.commands.economy.staff.EconomyCommand;
import net.vectromc.vbasic.commands.economy.staff.UnbountyCommand;
import net.vectromc.vbasic.commands.staff.*;
import net.vectromc.vbasic.commands.staff.BroadcastCommand;
import net.vectromc.vbasic.commands.staff.FeedCommand;
import net.vectromc.vbasic.commands.staff.GamemodeCommands;
import net.vectromc.vbasic.commands.staff.HealCommand;
import net.vectromc.vbasic.commands.staff.TeleportCommands;
import net.vectromc.vbasic.commands.staff.ToggleStaffAlertsCommand;
import net.vectromc.vbasic.commands.stats.StatsCommand;
import net.vectromc.vbasic.commands.stats.staff.ResetStatsCommand;
import net.vectromc.vbasic.data.EconomyData;
import net.vectromc.vbasic.data.StatData;
import net.vectromc.vbasic.listeners.ChatListener;
import net.vectromc.vbasic.listeners.PlayerJoinListener;
import net.vectromc.vbasic.listeners.SettingsClickListeners;
import net.vectromc.vbasic.listeners.WorldChangeListener;
import net.vectromc.vbasic.listeners.economy.PlayerKillListener;
import net.vectromc.vbasic.listeners.stats.PlayerDeathListener;
import net.vectromc.vbasic.management.EconomyManagement;
import net.vectromc.vbasic.management.StatManagement;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class vBasic extends JavaPlugin {

    public EconomyData economy;
    public StatData stats;

    public StatManagement statManagement = new StatManagement();
    public EconomyManagement economyManagement = new EconomyManagement();

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        registerData();
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
    public ArrayList<UUID> tpm = new ArrayList<>();
    public ArrayList<UUID> tms = new ArrayList<>();
    public ArrayList<UUID> tgc = new ArrayList<>();
    public ArrayList<UUID> fly = new ArrayList<>();
    public HashMap<UUID, ItemStack> hat = new HashMap<>();
    public HashMap<String, String> reply = new HashMap<>();
    public HashMap<Player, Location> back = new HashMap<>();

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
        getCommand("message").setExecutor(new MsgCommand());
        getCommand("reply").setExecutor(new MsgCommand());
        getCommand("toggleglobalchat").setExecutor(new ToggleGlobalChat());
        getCommand("togglemessagesounds").setExecutor(new ToggleMessageSounds());
        getCommand("togglemessages").setExecutor(new ToggleMessages());
        getCommand("settings").setExecutor(new SettingsCommand());
        getCommand("Back").setExecutor(new BackCommand());
        getCommand("Fly").setExecutor(new FlyCommand());
        getCommand("speed").setExecutor(new SpeedCommand());
        getCommand("seen").setExecutor(new SeenCommand());
        getCommand("hat").setExecutor(new HatCommand());
        getCommand("Sudo").setExecutor(new SudoCommand());
        getCommand("Balance").setExecutor(new BalanceCommand());
        getCommand("Pay").setExecutor(new PayCommand());
        getCommand("Economy").setExecutor(new EconomyCommand());
        getCommand("Bounty").setExecutor(new BountyCommand());
        getCommand("Unbounty").setExecutor(new UnbountyCommand());
        getCommand("Stats").setExecutor(new StatsCommand());
        getCommand("ResetStats").setExecutor(new ResetStatsCommand());
    }

    private void registerEvents() {
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new ChatListener(), this);
        manager.registerEvents(new SettingsClickListeners(), this);
        manager.registerEvents(new PlayerJoinListener(), this);
        manager.registerEvents(new WorldChangeListener(), this);
        manager.registerEvents(new PlayerKillListener(), this);
        manager.registerEvents(new PlayerDeathListener(), this);
    }

    private void registerData() {
        economy = new EconomyData();
        economy.setupData();
        economy.saveData();
        economy.reloadData();

        stats = new StatData();
        stats.setupData();
        stats.saveData();
        stats.reloadData();
    }
}
