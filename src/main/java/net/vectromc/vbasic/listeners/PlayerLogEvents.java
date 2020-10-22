package net.vectromc.vbasic.listeners;

import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLogEvents implements Listener {

    private vBasic plugin;
    private vNitrogen nitrogen;

    public PlayerLogEvents() {
        plugin = vBasic.getPlugin(vBasic.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("JoinMessage")) {
            nitrogen.setPlayerColor(player);
            event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("JoinMsg").replaceAll("%player%", player.getDisplayName())));
        } else {
            event.setJoinMessage("");
        }
    }

    @EventHandler
    public void onJoin(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("LeaveMessage")) {
            nitrogen.setPlayerColor(player);
            event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("LeaveMsg").replaceAll("%player%", player.getDisplayName())));
        } else {
            event.setQuitMessage("");
        }
    }
}
