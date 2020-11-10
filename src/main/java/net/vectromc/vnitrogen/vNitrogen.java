package net.vectromc.vnitrogen;

import net.vectromc.vnitrogen.chats.AdminChatCommand;
import net.vectromc.vnitrogen.chats.BuildChatCommand;
import net.vectromc.vnitrogen.chats.ManagementChatCommand;
import net.vectromc.vnitrogen.chats.StaffChatCommand;
import net.vectromc.vnitrogen.commands.NitrogenCommand;
import net.vectromc.vnitrogen.commands.SetRankCommand;
import net.vectromc.vnitrogen.commands.punishments.*;
import net.vectromc.vnitrogen.commands.toggles.AdminChatToggle;
import net.vectromc.vnitrogen.commands.toggles.BuildChatToggle;
import net.vectromc.vnitrogen.commands.toggles.ManagementChatToggle;
import net.vectromc.vnitrogen.commands.toggles.StaffChatToggle;
import net.vectromc.vnitrogen.data.PlayerData;
import net.vectromc.vnitrogen.listeners.*;
import net.vectromc.vnitrogen.listeners.chatlisteners.ACToggleListener;
import net.vectromc.vnitrogen.listeners.chatlisteners.BCToggleListener;
import net.vectromc.vnitrogen.listeners.chatlisteners.MCToggleListener;
import net.vectromc.vnitrogen.listeners.chatlisteners.SCToggleListener;
import net.vectromc.vnitrogen.listeners.starterlisteners.ACStarterListener;
import net.vectromc.vnitrogen.listeners.starterlisteners.BCStarterListener;
import net.vectromc.vnitrogen.listeners.starterlisteners.MCStarterListener;
import net.vectromc.vnitrogen.listeners.starterlisteners.SCStarterListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class vNitrogen extends JavaPlugin {

    public PlayerData data;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        registerEvents();
        registerCommands();
        runRunnables();
        registerRanks();
        registerData();
        refreshBans();
        refreshMutes();
        startupAnnouncements();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        shutdownAnnouncements();
    }

    // HashMaps
    public HashMap<String, Integer> ranks = new HashMap<>();

    // ArrayLists
    public ArrayList<UUID> buildchat_toggle = new ArrayList<>();
    public ArrayList<UUID> staffchat_toggle = new ArrayList<>();
    public ArrayList<UUID> adminchat_toggle = new ArrayList<>();
    public ArrayList<UUID> managementchat_toggle = new ArrayList<>();
    public ArrayList<String> muted = new ArrayList<>();
    public ArrayList<String> banned = new ArrayList<>();
    public  ArrayList<String> alts = new ArrayList<>();

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
        manager.registerEvents(new GUIClickListener(), this);
    }

    private void runRunnables() {

    }

    public void registerRanks() {
        System.out.println("[VectroMC] Registering all ranks...");
        this.ranks.put("Owner", 1);
        this.ranks.put("Developer", 2);
        this.ranks.put("Manager", 3);
        this.ranks.put("Admin", 4);
        this.ranks.put("Senior-Mod", 5);
        this.ranks.put("Mod", 6);
        this.ranks.put("Trial-Mod", 7);
        this.ranks.put("Builder", 8);
        this.ranks.put("Default", 9);
        System.out.println("[VectroMC] All ranks registered successfully.");
    }

    public void setPlayerPrefix(Player player) {
        if (player.hasPermission("vnitrogen.groups.owner")) {
            player.setDisplayName(getConfig().getString("Owner.prefix") + player.getName());
        } else if (player.hasPermission("vnitrogen.groups.developer")) {
            player.setDisplayName(getConfig().getString("Developer.prefix") + player.getName());
        } else if (player.hasPermission("vnitrogen.groups.manager")) {
            player.setDisplayName(getConfig().getString("Manager.prefix") + player.getName());
        } else if (player.hasPermission("vnitrogen.groups.admin")) {
            player.setDisplayName(getConfig().getString("Admin.prefix") + player.getName());
        } else if (player.hasPermission("vnitrogen.groups.seniormod")) {
            player.setDisplayName(getConfig().getString("Senior-Mod.prefix") + player.getName());
        } else if (player.hasPermission("vnitrogen.groups.mod")) {
            player.setDisplayName(getConfig().getString("Mod.prefix") + player.getName());
        } else if (player.hasPermission("vnitrogen.groups.trialmod")) {
            player.setDisplayName(getConfig().getString("Trial-Mod.prefix") + player.getName());
        } else if (player.hasPermission("vnitrogen.groups.builder")) {
            player.setDisplayName(getConfig().getString("Builder.prefix") + player.getName());
        } else {
            player.setDisplayName(getConfig().getString("Default.prefix") + player.getName());
        }
    }

    public void setPlayerColor(Player player) {
        if (player.hasPermission("vnitrogen.groups.owner")) {
            player.setDisplayName(getConfig().getString("Owner.color") + player.getName());
        } else if (player.hasPermission("vnitrogen.groups.developer")) {
            player.setDisplayName(getConfig().getString("Developer.color") + player.getName());
        } else if (player.hasPermission("vnitrogen.groups.manager")) {
            player.setDisplayName(getConfig().getString("Manager.color") + player.getName());
        } else if (player.hasPermission("vnitrogen.groups.admin")) {
            player.setDisplayName(getConfig().getString("Admin.color") + player.getName());
        } else if (player.hasPermission("vnitrogen.groups.seniormod")) {
            player.setDisplayName(getConfig().getString("Senior-Mod.color") + player.getName());
        } else if (player.hasPermission("vnitrogen.groups.mod")) {
            player.setDisplayName(getConfig().getString("Mod.color") + player.getName());
        } else if (player.hasPermission("vnitrogen.groups.trialmod")) {
            player.setDisplayName(getConfig().getString("Trial-Mod.color") + player.getName());
        } else if (player.hasPermission("vnitrogen.groups.builder")) {
            player.setDisplayName(getConfig().getString("Builder.color") + player.getName());
        } else {
            player.setDisplayName(getConfig().getString("Default.color") + player.getName());
        }
    }

    public void setTargetPrefix(Player target) {
        if (target.hasPermission("vnitrogen.groups.owner")) {
            target.setDisplayName(getConfig().getString("Owner.prefix") + target.getName());
        } else if (target.hasPermission("vnitrogen.groups.developer")) {
            target.setDisplayName(getConfig().getString("Developer.prefix") + target.getName());
        } else if (target.hasPermission("vnitrogen.groups.manager")) {
            target.setDisplayName(getConfig().getString("Manager.prefix") + target.getName());
        } else if (target.hasPermission("vnitrogen.groups.admin")) {
            target.setDisplayName(getConfig().getString("Admin.prefix") + target.getName());
        } else if (target.hasPermission("vnitrogen.groups.seniormod")) {
            target.setDisplayName(getConfig().getString("Senior-Mod.prefix") + target.getName());
        } else if (target.hasPermission("vnitrogen.groups.mod")) {
            target.setDisplayName(getConfig().getString("Mod.prefix") + target.getName());
        } else if (target.hasPermission("vnitrogen.groups.trialmod")) {
            target.setDisplayName(getConfig().getString("Trial-Mod.prefix") + target.getName());
        } else if (target.hasPermission("vnitrogen.groups.builder")) {
            target.setDisplayName(getConfig().getString("Builder.prefix") + target.getName());
        } else {
            target.setDisplayName(getConfig().getString("Default.prefix") + target.getName());
        }
    }

    public void setTargetColor(Player target) {
        if (target.hasPermission("vnitrogen.groups.owner")) {
            target.setDisplayName(getConfig().getString("Owner.color") + target.getName());
        } else if (target.hasPermission("vnitrogen.groups.developer")) {
            target.setDisplayName(getConfig().getString("Developer.color") + target.getName());
        } else if (target.hasPermission("vnitrogen.groups.manager")) {
            target.setDisplayName(getConfig().getString("Manager.color") + target.getName());
        } else if (target.hasPermission("vnitrogen.groups.admin")) {
            target.setDisplayName(getConfig().getString("Admin.color") + target.getName());
        } else if (target.hasPermission("vnitrogen.groups.seniormod")) {
            target.setDisplayName(getConfig().getString("Senior-Mod.color") + target.getName());
        } else if (target.hasPermission("vnitrogen.groups.mod")) {
            target.setDisplayName(getConfig().getString("Mod.color") + target.getName());
        } else if (target.hasPermission("vnitrogen.groups.trialmod")) {
            target.setDisplayName(getConfig().getString("Trial-Mod.color") + target.getName());
        } else if (target.hasPermission("vnitrogen.groups.builder")) {
            target.setDisplayName(getConfig().getString("Builder.color") + target.getName());
        } else {
            target.setDisplayName(getConfig().getString("Default.color") + target.getName());
        }
    }

    public void setTarget2Prefix(Player target2) {
        if (target2.hasPermission("vnitrogen.groups.owner")) {
            target2.setDisplayName(getConfig().getString("Owner.prefix") + target2.getName());
        } else if (target2.hasPermission("vnitrogen.groups.developer")) {
            target2.setDisplayName(getConfig().getString("Developer.prefix") + target2.getName());
        } else if (target2.hasPermission("vnitrogen.groups.manager")) {
            target2.setDisplayName(getConfig().getString("Manager.prefix") + target2.getName());
        } else if (target2.hasPermission("vnitrogen.groups.admin")) {
            target2.setDisplayName(getConfig().getString("Admin.prefix") + target2.getName());
        } else if (target2.hasPermission("vnitrogen.groups.seniormod")) {
            target2.setDisplayName(getConfig().getString("Senior-Mod.prefix") + target2.getName());
        } else if (target2.hasPermission("vnitrogen.groups.mod")) {
            target2.setDisplayName(getConfig().getString("Mod.prefix") + target2.getName());
        } else if (target2.hasPermission("vnitrogen.groups.trialmod")) {
            target2.setDisplayName(getConfig().getString("Trial-Mod.prefix") + target2.getName());
        } else if (target2.hasPermission("vnitrogen.groups.builder")) {
            target2.setDisplayName(getConfig().getString("Builder.prefix") + target2.getName());
        } else {
            target2.setDisplayName(getConfig().getString("Default.prefix") + target2.getName());
        }
    }

    public void setTarget2Color(Player target2) {
        if (target2.hasPermission("vnitrogen.groups.owner")) {
            target2.setDisplayName(getConfig().getString("Owner.color") + target2.getName());
        } else if (target2.hasPermission("vnitrogen.groups.developer")) {
            target2.setDisplayName(getConfig().getString("Developer.color") + target2.getName());
        } else if (target2.hasPermission("vnitrogen.groups.manager")) {
            target2.setDisplayName(getConfig().getString("Manager.color") + target2.getName());
        } else if (target2.hasPermission("vnitrogen.groups.admin")) {
            target2.setDisplayName(getConfig().getString("Admin.color") + target2.getName());
        } else if (target2.hasPermission("vnitrogen.groups.seniormod")) {
            target2.setDisplayName(getConfig().getString("Senior-Mod.color") + target2.getName());
        } else if (target2.hasPermission("vnitrogen.groups.mod")) {
            target2.setDisplayName(getConfig().getString("Mod.color") + target2.getName());
        } else if (target2.hasPermission("vnitrogen.groups.trialmod")) {
            target2.setDisplayName(getConfig().getString("Trial-Mod.color") + target2.getName());
        } else if (target2.hasPermission("vnitrogen.groups.builder")) {
            target2.setDisplayName(getConfig().getString("Builder.color") + target2.getName());
        } else {
            target2.setDisplayName(getConfig().getString("Default.color") + target2.getName());
        }
    }

    private void registerData() {
        data = new PlayerData();
        data.setupData();
        data.saveData();
        data.reloadData();
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
}
