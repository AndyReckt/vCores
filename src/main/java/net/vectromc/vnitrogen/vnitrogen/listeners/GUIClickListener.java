package net.vectromc.vnitrogen.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIClickListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (event.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&', "&8History")) || event.getView().getTitle().contains(ChatColor.translateAlternateColorCodes('&', "&8Grants"))) {
            event.setCancelled(true);
        }
    }
}
