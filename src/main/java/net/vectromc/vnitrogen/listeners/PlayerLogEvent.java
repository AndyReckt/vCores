package net.vectromc.vnitrogen.listeners;

import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerLogEvent implements Listener {

    private vNitrogen plugin;

    public PlayerLogEvent() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for (String rank : plugin.ranks) {
            String permName = plugin.getConfig().getString("Ranks." + rank + ".permission");
            if (player.hasPermission(permName)) {
                plugin.pData.config.set(player.getUniqueId().toString() + ".Rank", rank);
            }
        }
        plugin.pData.config.set(player.getUniqueId() + ".Name", player.getName());
        plugin.pData.saveData();

        String ip = player.getAddress().getAddress().getHostAddress();
        plugin.data.config.set("IPs." + player.getUniqueId().toString() + ".IP", ip);
        plugin.data.config.set("IPs." + player.getUniqueId().toString() + ".Name", player.getName());
        plugin.data.saveData();
        int amount = 0;
        for (String allUUIDS : plugin.data.config.getConfigurationSection("IPs").getKeys(false)) {
            if (plugin.data.config.getString("IPs." + player.getUniqueId().toString() + ".IP").equals(plugin.data.config.get("IPs." + allUUIDS + ".IP")) && !allUUIDS.equals(player.getUniqueId().toString())) {
                amount++;
                Player altPlayer = Bukkit.getPlayer(plugin.data.config.getString("IPs." + allUUIDS + ".Name"));
                String altStatus = "";
                if (altPlayer != null) {
                    if (plugin.data.config.getConfigurationSection("MutedPlayers").getKeys(false).contains(altPlayer.getUniqueId().toString())) {
                        altStatus = "&6" + altPlayer.getName();
                    } else {
                        altStatus = "&a" + altPlayer.getName();
                    }
                } else {
                    OfflinePlayer altOfflinePlayer = Bukkit.getOfflinePlayer(plugin.data.config.getString("IPs." + allUUIDS + ".Name"));
                    if (plugin.data.config.getConfigurationSection("BlacklistedIPs").getKeys(false).contains(altOfflinePlayer.getUniqueId().toString())) {
                        if (plugin.data.config.getString("BlacklistedIPs." + altOfflinePlayer.getUniqueId().toString() + ".IP").equalsIgnoreCase(plugin.data.config.getString("IPs." + altOfflinePlayer.getUniqueId().toString() + ".IP"))) {
                            altStatus = "&4" + altOfflinePlayer.getName();
                        }
                    } else if (plugin.data.config.getConfigurationSection("BannedPlayers").getKeys(false).contains(altOfflinePlayer.getUniqueId().toString())) {
                        altStatus = "&c" + altOfflinePlayer.getName();
                    } else if (plugin.data.config.getConfigurationSection("MutedPlayers").getKeys(false).contains(altOfflinePlayer.getUniqueId().toString())) {
                        altStatus = "&6" + altOfflinePlayer.getName();
                    } else {
                        altStatus = "&7" + altOfflinePlayer.getName();
                    }
                }
                plugin.alts.add(altStatus);
            }
        }
        if (amount >= 1) {
            plugin.setPlayerColor(player);
            String amountStr = "" + amount;
            for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                if (onlineStaff.hasPermission(plugin.getConfig().getString("Alts.Permission"))) {
                    String str = "";
                    for (String alt : plugin.alts) {
                        if (str.length() == 0) {
                            str = alt;
                        } else {
                            str = str + " " + alt;
                        }
                    }
                    onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Alts.JoinMessageDetected").replaceAll("%player%", player.getDisplayName()).replaceAll("%amount%", amountStr)));
                    plugin.alts.clear();
                }
            }
        }
    }
}
