package net.vectromc.vnitrogen.listeners;

import net.vectromc.vnitrogen.management.PlayerManagement;
import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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
            PlayerManagement playerManagement = new PlayerManagement(player);
            if (plugin.data.config.getString(player.getUniqueId().toString() + ".Bans." + playerManagement.getBansAmount() + ".Temp").equalsIgnoreCase("true")) {
                if (System.currentTimeMillis() >= plugin.data.config.getLong(player.getUniqueId().toString() + ".Bans." + playerManagement.getBansAmount() + ".Duration")) {
                    plugin.banned.remove(player.getUniqueId().toString());
                    plugin.data.config.set("BannedPlayers." + player.getUniqueId(), null);
                    plugin.data.config.set(player.getUniqueId().toString() + ".Bans." + playerManagement.getBansAmount() + ".Status", "Expired");
                    plugin.data.saveData();
                } else {
                    String reason = plugin.data.config.getString(player.getUniqueId().toString() + ".Bans." + playerManagement.getBansAmount() + ".Reason");
                    String expirationDate = Utils.TIME_FORMAT.format(new Date(plugin.data.config.getLong(player.getUniqueId().toString() + ".Bans." + playerManagement.getBansAmount() + ".Duration")));
                    player.kickPlayer(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("TempBan.BanMessage").replaceAll("%reason%", reason).replaceAll("%expiry%", expirationDate)));
                    plugin.data.saveData();
                }
            } else {
                String reason = plugin.data.config.getString(player.getUniqueId().toString() + ".Bans." + playerManagement.getBansAmount() + ".Reason");
                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Ban.BanMessage").replaceAll("%reason%", reason)));
                plugin.data.saveData();
            }
        }
    }
}
