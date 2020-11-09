package net.vectromc.vnitrogen.listeners;

import net.vectromc.vnitrogen.vNitrogen;
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
        if (player.hasPermission("vnitrogen.groups.owner")) {
            plugin.data.config.set(player.getUniqueId() + ".Rank", "Owner");
        } else if (player.hasPermission("vnitrogen.groups.developer")) {
            plugin.data.config.set(player.getUniqueId() + ".Rank", "Developer");
        } else if (player.hasPermission("vnitrogen.groups.manager")) {
            plugin.data.config.set(player.getUniqueId() + ".Rank", "Manager");
        } else if (player.hasPermission("vnitrogen.groups.admin")) {
            plugin.data.config.set(player.getUniqueId() + ".Rank", "Admin");
        } else if (player.hasPermission("vnitrogen.groups.seniormod")) {
            plugin.data.config.set(player.getUniqueId() + ".Rank", "Senior-Mod");
        } else if (player.hasPermission("vnitrogen.groups.mod")) {
            plugin.data.config.set(player.getUniqueId() + ".Rank", "Mod");
        } else if (player.hasPermission("vnitrogen.groups.trialmod")) {
            plugin.data.config.set(player.getUniqueId() + ".Rank", "Trial-Mod");
        } else if (player.hasPermission("vnitrogen.groups.builder")) {
            plugin.data.config.set(player.getUniqueId() + ".Rank", "Builder");
        } else {
            plugin.data.config.set(player.getUniqueId() + ".Rank", "Default");
        }
        plugin.data.config.set(player.getUniqueId() + ".Name", player.getName());
        plugin.data.saveData();
    }
}
