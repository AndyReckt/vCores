package net.vectromc.vstaffutils.listeners;

import net.vectromc.vnitrogen.vNitrogen;
import net.vectromc.vstaffutils.utils.Utils;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ReportCustomChatReason implements Listener {

    private vStaffUtils plugin;
    private vNitrogen nitrogen;

    public ReportCustomChatReason() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.custom_reason.contains(player.getUniqueId())) {
            event.setCancelled(true);
            Player target = plugin.report_set.get(player.getUniqueId());
            nitrogen.setPlayerColor(target);
            nitrogen.setPlayerColor(player);
            String reason = event.getMessage();
            String world = player.getWorld().getName();
            Utils.sendMessage(player, plugin.getConfig().getString("ReportFormat.PlayerFormat").replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason));
            for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                if (onlineStaff.hasPermission(plugin.getConfig().getString("ReportNotifyPermission"))) {
                    onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ReportFormat.StaffFormat").replaceAll("%world%", world).replaceAll("%player%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                }
            }
            plugin.custom_reason.remove(player.getUniqueId());
            plugin.report_set.remove(player.getUniqueId());
            plugin.reporting.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (plugin.custom_reason.contains(player.getUniqueId())) {
            event.setCancelled(true);
            Utils.sendMessage(player, "&cYou cannot use commands while writing a custom reason.");
        }
    }
}
