package net.vectromc.vbasic.listeners;

import net.vectromc.vbasic.utils.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SettingsClickListeners implements Listener {

    @EventHandler
    public void onGuiClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.YELLOW + "Settings")) {
            if (event.getCurrentItem() == null) {
                return;
            } else {
                if (event.getCurrentItem().getType() == XMaterial.WRITABLE_BOOK.parseMaterial()) {
                    player.closeInventory();
                    player.performCommand("tpm");
                } else if (event.getCurrentItem().getType() == XMaterial.PAPER.parseMaterial()) {
                    player.closeInventory();
                    player.performCommand("tgc");
                } else if (event.getCurrentItem().getType() == XMaterial.EGG.parseMaterial()) {
                    player.closeInventory();
                    player.performCommand("tms");
                }
            }
        }
    }
}
