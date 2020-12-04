package net.vectromc.vnitrogen.listeners;

import net.vectromc.vnitrogen.management.PunishmentManagement;
import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;

public class BanJoinListener implements Listener {

    private vNitrogen plugin;

    public BanJoinListener() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (plugin.banned.contains(player.getUniqueId().toString())) {
            PunishmentManagement punishmentManagement = new PunishmentManagement(player);
            if (plugin.data.config.getString(player.getUniqueId().toString() + ".Bans." + punishmentManagement.getBansAmount() + ".Temp").equalsIgnoreCase("true")) {
                String reason = plugin.data.config.getString(player.getUniqueId().toString() + ".Bans." + punishmentManagement.getBansAmount() + ".Reason");
                String expirationDate = Utils.TIME_FORMAT.format(new Date(plugin.data.config.getLong(player.getUniqueId().toString() + ".Bans." + punishmentManagement.getBansAmount() + ".Duration")));
                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("TempBan.BanMessage").replaceAll("%reason%", reason).replaceAll("%expiry%", expirationDate)));
                plugin.data.saveData();
                event.setJoinMessage("");
            } else {
                String reason = plugin.data.config.getString(player.getUniqueId().toString() + ".Bans." + punishmentManagement.getBansAmount() + ".Reason");
                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Ban.BanMessage").replaceAll("%reason%", reason)));
                plugin.data.saveData();
                event.setJoinMessage("");
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (plugin.banned.contains(player.getUniqueId().toString()) || plugin.blacklisted.contains(player.getAddress().getAddress().getHostAddress())) {
            event.setQuitMessage("");
        }
    }
}
