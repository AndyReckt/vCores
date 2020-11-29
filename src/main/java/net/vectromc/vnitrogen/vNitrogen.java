package net.vectromc.vnitrogen;

import net.vectromc.vnitrogen.chats.AdminChatCommand;
import net.vectromc.vnitrogen.chats.BuildChatCommand;
import net.vectromc.vnitrogen.chats.ManagementChatCommand;
import net.vectromc.vnitrogen.chats.StaffChatCommand;
import net.vectromc.vnitrogen.commands.*;
import net.vectromc.vnitrogen.commands.punishments.*;
import net.vectromc.vnitrogen.commands.toggles.AdminChatToggle;
import net.vectromc.vnitrogen.commands.toggles.BuildChatToggle;
import net.vectromc.vnitrogen.commands.toggles.ManagementChatToggle;
import net.vectromc.vnitrogen.commands.toggles.StaffChatToggle;
import net.vectromc.vnitrogen.data.GrantData;
import net.vectromc.vnitrogen.data.PlayerData;
import net.vectromc.vnitrogen.data.PunishmentData;
import net.vectromc.vnitrogen.listeners.*;
import net.vectromc.vnitrogen.listeners.chatlisteners.ACToggleListener;
import net.vectromc.vnitrogen.listeners.chatlisteners.BCToggleListener;
import net.vectromc.vnitrogen.listeners.chatlisteners.MCToggleListener;
import net.vectromc.vnitrogen.listeners.chatlisteners.SCToggleListener;
import net.vectromc.vnitrogen.listeners.starterlisteners.ACStarterListener;
import net.vectromc.vnitrogen.listeners.starterlisteners.BCStarterListener;
import net.vectromc.vnitrogen.listeners.starterlisteners.MCStarterListener;
import net.vectromc.vnitrogen.listeners.starterlisteners.SCStarterListener;
import net.vectromc.vnitrogen.runnables.GrantActivityUpdater;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class vNitrogen extends JavaPlugin {

    public PunishmentData data;
    public GrantData gData;
    public PlayerData pData;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        registerEvents();
        registerCommands();
        registerData();
        runRunnables();
        registerRanks();
        refreshBlacklists();
        refreshBans();
        refreshMutes();
        startupAnnouncements();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        shutdownAnnouncements();
    }

    public HashMap<Player, OfflinePlayer> grantPlayer = new HashMap<>();
    public HashMap<Player, String> grantRank = new HashMap<>();
    public HashMap<Player, String> grantDuration = new HashMap<>();
    public HashMap<Player, String> grantReason = new HashMap<>();

    public HashMap<Player, OfflinePlayer> punishPlayer = new HashMap<>();
    public HashMap<Player, String> punishType = new HashMap<>();
    public HashMap<Player, String> punishDuration = new HashMap<>();
    public HashMap<Player, String> punishReason = new HashMap<>();
    public HashMap<Player, String> punishSilent = new HashMap<>();

    public ArrayList<UUID> buildchat_toggle = new ArrayList<>();
    public ArrayList<UUID> staffchat_toggle = new ArrayList<>();
    public ArrayList<UUID> adminchat_toggle = new ArrayList<>();
    public ArrayList<UUID> managementchat_toggle = new ArrayList<>();

    public ArrayList<String> ranks = new ArrayList<>();
    public ArrayList<String> muted = new ArrayList<>();
    public ArrayList<String> banned = new ArrayList<>();
    public ArrayList<String> blacklisted = new ArrayList<>();
    public ArrayList<String> alts = new ArrayList<>();
    public ArrayList<String> chatMute = new ArrayList<>();

    private void startupAnnouncements() {
        System.out.println("[VectroMC] vNitrogen v1.0 by Yochran is loading...");
        System.out.println("[VectroMC] vNitrogen v1.0 by Yochran has successfully loaded.");
    }

    private void shutdownAnnouncements() {
        System.out.println("[VectroMC] vNitrogen v1.0 by Yochran is unloading...");
        System.out.println("[VectroMC] vNitrogen v1.0 by Yochran has successfully unloaded.");
    }

    private void registerCommands() {
        // Plugin
        getCommand("vNitrogen").setExecutor(new NitrogenCommand());
        getCommand("Setrank").setExecutor(new SetRankCommand());
        getCommand("Grant").setExecutor(new GrantCommand());
        getCommand("Grants").setExecutor(new GrantsCommand());
        getCommand("Ungrant").setExecutor(new UngrantCommand());
        // Chats
        getCommand("BuildChat").setExecutor(new BuildChatCommand());
        getCommand("StaffChat").setExecutor(new StaffChatCommand());
        getCommand("AdminChat").setExecutor(new AdminChatCommand());
        getCommand("ManagementChat").setExecutor(new ManagementChatCommand());
        // Toggles
        getCommand("buildchattoggle").setExecutor(new BuildChatToggle());
        getCommand("staffchattoggle").setExecutor(new StaffChatToggle());
        getCommand("adminchattoggle").setExecutor(new AdminChatToggle());
        getCommand("managementchattoggle").setExecutor(new ManagementChatToggle());
        // Punishments
        getCommand("Warn").setExecutor(new WarnCommand());
        getCommand("Mute").setExecutor(new MuteCommand());
        getCommand("Unmute").setExecutor(new UnmuteCommand());
        getCommand("History").setExecutor(new HistoryCommand());
        getCommand("Kick").setExecutor(new KickCommand());
        getCommand("Ban").setExecutor(new BanCommand());
        getCommand("Unban").setExecutor(new UnbanCommand());
        getCommand("TempMute").setExecutor(new TempmuteCommand());
        getCommand("TempBan").setExecutor(new TempbanCommand());
        getCommand("Alts").setExecutor(new AltsCommand());
        getCommand("MuteChat").setExecutor(new MutechatCommand());
        getCommand("ClearChat").setExecutor(new ClearChatCommand());
        getCommand("Blacklist").setExecutor(new BlacklistCommand());
        getCommand("Unblacklist").setExecutor(new UnblacklistCommand());
        getCommand("ClearHistory").setExecutor(new ClearHistoryCommand());
        getCommand("Punish").setExecutor(new PunishCommand());
    }

    private void registerEvents() {
        PluginManager manager = getServer().getPluginManager();
        // Chat Formatting
        manager.registerEvents(new ChatFormat(), this);
        // Toggles
        manager.registerEvents(new BCToggleListener(), this);
        manager.registerEvents(new SCToggleListener(), this);
        manager.registerEvents(new ACToggleListener(), this);
        manager.registerEvents(new MCToggleListener(), this);
        // Starters
        manager.registerEvents(new BCStarterListener(), this);
        manager.registerEvents(new SCStarterListener(), this);
        manager.registerEvents(new ACStarterListener(), this);
        manager.registerEvents(new MCStarterListener(), this);
        // Login/Logout/World Change
        manager.registerEvents(new PlayerLogEvent(), this);
        manager.registerEvents(new StaffLogEvents(), this);
        manager.registerEvents(new StaffWorldChangeEvents(), this);
        // Punishments
        manager.registerEvents(new MuteChatListener(), this);
        manager.registerEvents(new BanJoinListener(), this);
        manager.registerEvents(new BlacklistJoinListener(), this);
        // GUIs
        manager.registerEvents(new GUIClickListener(), this);
        manager.registerEvents(new GrantCommand(), this);
        manager.registerEvents(new PunishCommand(), this);
    }

    private void runRunnables() {
        new GrantActivityUpdater().runTaskTimer(this, 0, 20);
    }

    public void registerRanks() {
        ranks.clear();
        for (String rank : getConfig().getConfigurationSection("Ranks").getKeys(false)) {
            ranks.add(rank);
        }
        for (String test : this.ranks) {
            String permName = getConfig().getString("Ranks." + test + ".permission");
            System.out.println(test + ", " + permName);
        }
    }

    public void setPlayerPrefix(Player player) {
        for (String rank : this.ranks) {
            if (!pData.config.contains(player.getUniqueId().toString()) || pData.config.getString(player.getUniqueId().toString() + ".Rank").equalsIgnoreCase("DEFAULT")) {
                String prefix = getConfig().getString("Ranks.DEFAULT.prefix");
                player.setDisplayName(prefix + player.getName());
            } else {
                if (pData.config.getString(player.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank.toUpperCase())) {
                    String prefix = getConfig().getString("Ranks." + rank + ".prefix");
                    player.setDisplayName(ChatColor.translateAlternateColorCodes('&', prefix + player.getName()));
                }
            }
        }
    }

    public void setPlayerColor(Player player) {
        for (String rank : this.ranks) {
            if (!pData.config.contains(player.getUniqueId().toString()) || pData.config.getString(player.getUniqueId().toString() + ".Rank").equalsIgnoreCase("DEFAULT")) {
                String color = getConfig().getString("Ranks.DEFAULT.color");
                player.setDisplayName(color + player.getName());
            } else {
                if (pData.config.getString(player.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank.toUpperCase())) {
                    String color = getConfig().getString("Ranks." + rank + ".color");
                    player.setDisplayName(ChatColor.translateAlternateColorCodes('&', color + player.getName()));
                }
            }
        }
    }

    public void setTargetPrefix(Player target) {
        for (String rank : this.ranks) {
            if (!pData.config.contains(target.getUniqueId().toString()) || pData.config.getString(target.getUniqueId().toString() + ".Rank").equalsIgnoreCase("DEFAULT")) {
                String prefix = getConfig().getString("Ranks.DEFAULT.prefix");
                target.setDisplayName(prefix + target.getName());
            } else {
                if (pData.config.getString(target.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank.toUpperCase())) {
                    String prefix = getConfig().getString("Ranks." + rank + ".prefix");
                    target.setDisplayName(ChatColor.translateAlternateColorCodes('&', prefix + target.getName()));
                }
            }
        }
    }

    private void registerData() {
        data = new PunishmentData();
        data.setupData();
        data.saveData();
        data.reloadData();

        gData = new GrantData();
        gData.setupData();
        gData.saveData();
        gData.reloadData();

        pData = new PlayerData();
        pData.setupData();
        pData.saveData();
        pData.reloadData();
    }

    private void refreshMutes() {
        if (data.config.contains("MutedPlayers")) {
            for (String mutedPlayers : data.config.getConfigurationSection("MutedPlayers").getKeys(false)) {
                muted.add(mutedPlayers);
            }
        }
    }

    private void refreshBans() {
        if (data.config.contains("BannedPlayers")) {
            for (String bannedPlayers : data.config.getConfigurationSection("BannedPlayers").getKeys(false)) {
                banned.add(bannedPlayers);
            }
        }
    }

    private void refreshBlacklists() {
        if (data.config.contains("BlacklistedIPs")) {
            for (String uuid : data.config.getConfigurationSection("BlacklistedIPs").getKeys(false)) {
                String ip = data.config.getString("BlacklistedIPs." + uuid + ".IP");
                blacklisted.add(ip);
            }
        }
    }
}
