package net.vectromc.vstaffutils.listeners.modmode;

import net.vectromc.vstaffutils.utils.Utils;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class ModmodeProhibiters implements Listener {

    private vStaffUtils plugin;

    public ModmodeProhibiters() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (plugin.modmode.contains(player.getUniqueId())) {
            event.setCancelled(true);
            Utils.sendMessage(player, plugin.getConfig().getString("ModmodeBuildErrorMessage"));
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (plugin.modmode.contains(player)) {
            event.setCancelled(true);
            Utils.sendMessage(player, plugin.getConfig().getString("ModmodeBuildErrorMessage"));
        }
    }

    @EventHandler
    public void onPvp(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (plugin.modmode.contains(entity.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onModPvp(EntityDamageByEntityEvent event) {
        Entity entity = event.getDamager();
        if (entity instanceof Player) {
            if (plugin.modmode.contains(entity.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (plugin.modmode.contains(player.getUniqueId())) {
            event.setCancelled(true);
            player.updateInventory();
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if (plugin.modmode.contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) event.getEntity();
            if (plugin.modmode.contains(player.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (plugin.modmode.contains(player.getUniqueId())) {
            player.getInventory().clear();
            player.getInventory().setContents(plugin.staff_inventory.get(player.getUniqueId()));
            player.getInventory().setArmorContents(plugin.staff_armor.get(player.getUniqueId()));
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (plugin.modmode.contains(player.getUniqueId())) {
            plugin.modmode.remove(player.getUniqueId());
            event.getDrops().clear();
            plugin.awaiting_respawn.add(player.getUniqueId());
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (plugin.awaiting_respawn.contains(player.getUniqueId())) {
            player.getInventory().setContents(plugin.staff_inventory.get(player.getUniqueId()));
            player.getInventory().setArmorContents(plugin.staff_armor.get(player.getUniqueId()));
        }
    }
}
