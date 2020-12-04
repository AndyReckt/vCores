package net.vectromc.vstaffutils.listeners;

import net.vectromc.vstaffutils.utils.XMaterial;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WorldChangeListener implements Listener {

    private vStaffUtils plugin;

    public WorldChangeListener() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (plugin.modmode.contains(player.getUniqueId())) {
            plugin.modmode.remove(player.getUniqueId());
        }
        ItemStack checkItem = XMaterial.COMPASS.parseItem();
        ItemMeta checkItemMeta = checkItem.getItemMeta();
        checkItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lTeleporter"));
        checkItem.setItemMeta(checkItemMeta);
        if (player.getInventory().contains(checkItem)) {
            player.getInventory().clear();
            if (plugin.staff_inventory.containsKey(player.getUniqueId())) {
                player.getInventory().setContents(plugin.staff_inventory.get(player.getUniqueId()));
            }
            if (plugin.staff_armor.containsKey(player.getUniqueId())) {
                player.getInventory().setArmorContents(plugin.staff_armor.get(player.getUniqueId()));
            }
        }
    }
}
