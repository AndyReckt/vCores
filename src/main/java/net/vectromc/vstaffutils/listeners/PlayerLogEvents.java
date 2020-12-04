package net.vectromc.vstaffutils.listeners;

import net.vectromc.vnitrogen.vNitrogen;
import net.vectromc.vstaffutils.utils.Utils;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerLogEvents implements Listener {

    private vStaffUtils plugin;
    private vNitrogen nitrogen;

    public PlayerLogEvents() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("JoinMessage")) {
            if (!nitrogen.banned.contains(player.getUniqueId().toString()) && !nitrogen.blacklisted.contains(player.getAddress().getAddress().getHostAddress())) {
                if (!plugin.vanished.contains(player.getUniqueId()) && !plugin.vanish_logged.contains(player.getUniqueId())) {
                    nitrogen.setPlayerColor(player);
                    event.setJoinMessage("");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (Player onlinePlayers : player.getWorld().getPlayers()) {
                                Utils.sendMessage(onlinePlayers, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("JoinFormat").replace("%player%", player.getDisplayName())));
                            }
                        }
                    }.runTaskLater(plugin, 10);
                } else {
                    event.setJoinMessage("");
                }
            } else {
                event.setJoinMessage("");
            }
        } else {
            event.setJoinMessage("");
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("LeaveMessage")) {
            if (!nitrogen.banned.contains(player.getUniqueId().toString()) && !nitrogen.blacklisted.contains(player.getAddress().getAddress().getHostAddress())) {
                if (!plugin.vanished.contains(player.getUniqueId()) && !plugin.vanish_logged.contains(player.getUniqueId())) {
                    nitrogen.setPlayerColor(player);
                    event.setQuitMessage("");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (Player onlinePlayers : player.getWorld().getPlayers()) {
                                Utils.sendMessage(onlinePlayers, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("LeaveFormat").replace("%player%", player.getDisplayName())));
                            }
                        }
                    }.runTaskLater(plugin, 10);
                } else {
                    event.setQuitMessage("");
                }
            } else {
                event.setQuitMessage("");
            }
        } else {
            event.setQuitMessage("");
        }
    }
}
