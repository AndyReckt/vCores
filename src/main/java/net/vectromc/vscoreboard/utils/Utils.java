package net.vectromc.vscoreboard.utils;

import net.vectromc.vbasic.management.EconomyManagement;
import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import net.vectromc.vscoreboard.vScoreboard;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class Utils {

    private vStaffUtils staffutils;
    private vNitrogen nitrogen;
    private vScoreboard plugin;
    private vBasic basic;

    public Utils() {
        plugin = vScoreboard.getPlugin(vScoreboard.class);
        staffutils = vStaffUtils.getPlugin(vStaffUtils.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
        basic = vBasic.getPlugin(vBasic.class);
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }


    public static void liner(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m----------------------"));
    }

    public static void liner(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&m----------------------"));
    }

    public static void spacer(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7 "));
    }

    public static void spacer(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7 "));
    }

    public String replace(String message, Player player) {
        String vanish;
        if (staffutils.vanished.contains(player.getUniqueId())) {
            vanish = "&aYes";
        } else {
            vanish = "&cNo";
        }
        int online;
        int staffonline;
        String rank = "";
        int vanished = staffutils.vanished.size();
        online = Bukkit.getOnlinePlayers().size() - vanished;
        staffonline = 0;
        for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
            if (onlineStaff.hasPermission(plugin.getConfig().getString("StaffScoreboard.Permission"))) {
                staffonline++;
            }
        }
        String defaultRank = "";
        for (String rankLoop : nitrogen.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
            if (nitrogen.getConfig().getBoolean("Ranks." + rankLoop + ".default")) {
                defaultRank = rankLoop;
            }
        }
        for (String rankLoop : nitrogen.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
            if (!nitrogen.pData.config.contains(player.getUniqueId().toString()) || !nitrogen.pData.config.contains(player.getUniqueId().toString() + ".Rank")) {
                rank = nitrogen.getConfig().getString("Ranks." + defaultRank + ".display");
            } else {
                if (nitrogen.pData.config.getString(player.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rankLoop)) {
                    rank = nitrogen.getConfig().getString("Ranks." + rankLoop + ".display");
                }
            }
        }

        DecimalFormat df = new DecimalFormat("###,###,###,###,###,###.##");
        String server = player.getWorld().getName();

        String kills = df.format(basic.stats.config.getInt(player.getUniqueId().toString() + "." + server + ".Kills"));
        String deaths = df.format(basic.stats.config.getInt(player.getUniqueId().toString() + "." + server + ".Deaths"));
        String kdr = df.format(basic.stats.config.getDouble(player.getUniqueId().toString() + "." + server + ".KDR"));
        String balance = df.format(basic.economy.config.getDouble(player.getUniqueId().toString() + "." + server + ".Balance"));
        String streak = df.format(basic.stats.config.getInt(player.getUniqueId().toString() + "." + server + ".Streak"));

        String bounty = "";
        EconomyManagement economyManagement = new EconomyManagement();
        if (!economyManagement.isBountied(server, player)) {
            bounty = "None";
        } else {
            double amount = economyManagement.getBountyAmount(server, player);
            bounty = "$" + df.format(amount);
        }

        return message
                .replace("%online%", "" + online)
                .replace("%rank%", rank)
                .replace("%name%", player.getName())
                .replace("%displayname%", player.getDisplayName())
                .replace("%vanish%", vanish)
                .replace("%onlinestaff%", "" + staffonline)
                .replace("%world%", player.getWorld().getName())

                .replace("%kills%", kills)
                .replace("%deaths%", deaths)
                .replace("%kdr%", kdr)
                .replace("%balance%", balance)
                .replace("%streak%", streak)
                .replace("%bounty%", bounty);
    }
}
