package net.vectromc.vstaffutils.listeners;

import net.vectromc.vnitrogen.vNitrogen;
import net.vectromc.vstaffutils.utils.Utils;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VanishUpdater implements Listener {

    private vStaffUtils plugin;
    private vNitrogen nitrogen;

    public VanishUpdater() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            if (plugin.vanished.contains(onlinePlayers.getUniqueId())) {
                if (!player.hasPermission(plugin.getConfig().getString("VanishPermission"))) {
                    player.hidePlayer(onlinePlayers);
                }
            }
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        Entity entity = event.getDamager();
        if (entity instanceof Player) {
            Player player = (Player) event.getDamager();
            if (plugin.vanished.contains(player.getUniqueId())) {
                event.setCancelled(true);
                Utils.sendMessage(player, "&cYou cannot pvp as you are in vanish!");
            }
        }
    }

    @EventHandler
    public void onStaffQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (plugin.vanished.contains(player.getUniqueId())) {
            plugin.vanished.remove(player.getUniqueId());
            plugin.vanish_logged.add(player.getUniqueId());
        }
    }

    @EventHandler
    public void onStaffJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (plugin.vanish_logged.contains(player.getUniqueId())) {
            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                onlinePlayers.hidePlayer(player);
            }
            plugin.vanish_logged.remove(player.getUniqueId());
            plugin.vanished.add(player.getUniqueId());
            Utils.sendMessage(player, plugin.getConfig().getString("VanishLogin"));
        }
    }

    @EventHandler
    public void onMobNotice(EntityTargetEvent event) {
        Entity entity = event.getTarget();
        if (entity instanceof Player) {
            Player player = (Player) event.getTarget();
            if (plugin.vanished.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) event.getEntity();
            if (plugin.vanished.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
}
