package net.vectromc.vstaffutils.listeners;

import net.vectromc.vstaffutils.utils.Utils;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BuildModeListener implements Listener {

    private vStaffUtils plugin;

    public BuildModeListener() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (plugin.buildmode.contains(player.getUniqueId())) {
            event.setCancelled(true);
            Utils.sendMessage(player, plugin.getConfig().getString("BuildErrorMessage"));
        }
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (plugin.buildmode.contains(player.getUniqueId())) {
            event.setCancelled(true);
            Utils.sendMessage(player, plugin.getConfig().getString("BuildErrorMessage"));
        }
    }
}
