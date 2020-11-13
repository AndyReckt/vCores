package net.vectromc.vnitrogen.listeners;

import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.utils.XMaterial;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GrantChatListener implements Listener {

    private vNitrogen plugin;

    String targetName = "";
    String targetColor = "";
    String targetDisplay = "";

    public GrantChatListener() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (plugin.grantCustomReason.contains(player.getUniqueId())) {
            OfflinePlayer target = plugin.grantPlayer.get(player);
            targetName = target.getName();
            for (String rank : plugin.ranks) {
                if (plugin.pData.config.getString(target.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank.toUpperCase())) {
                    targetColor = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                }
            }
            targetDisplay = targetColor + targetName;
            event.setCancelled(true);
            String message = event.getMessage();
            plugin.grantReason.put(player, message);
            Inventory confirmInv = Bukkit.createInventory(player, 45, ChatColor.DARK_GRAY + "Confirmation");
            ItemStack yesItem = XMaterial.GREEN_TERRACOTTA.parseItem();
            ItemStack noItem = XMaterial.RED_TERRACOTTA.parseItem();
            ItemMeta yesItemMeta = yesItem.getItemMeta();
            ItemMeta noItemMeta = noItem.getItemMeta();
            String rank = plugin.grantRank.get(player);
            String duration = plugin.grantDuration.get(player);
            String yesDisplay = ChatColor.translateAlternateColorCodes('&', "&2&lConfirm Grant");
            String noDisplay = ChatColor.translateAlternateColorCodes('&', "&c&lCancel Grant");
            yesItemMeta.setDisplayName(yesDisplay);
            noItemMeta.setDisplayName(noDisplay);
            List<String> lore = new ArrayList<>();
            for (String itemLore : plugin.getConfig().getStringList("Grant.ConfirmationMenu.Lore")) {
                String newLore = itemLore
                        .replace("%player%", targetDisplay)
                        .replace("%rank%", rank)
                        .replace("%duration%", duration)
                        .replace("%reason%", message);
                lore.add(ChatColor.translateAlternateColorCodes('&', newLore));
            }
            yesItemMeta.setLore(lore);
            noItemMeta.setLore(lore);
            yesItem.setItemMeta(yesItemMeta);
            noItem.setItemMeta(noItemMeta);
            for (int i = 10; i < 13; i++) {
                confirmInv.setItem(i, yesItem);
            }
            for (int i = 19; i < 22; i++) {
                confirmInv.setItem(i, yesItem);
            }
            for (int i = 28; i < 31; i++) {
                confirmInv.setItem(i, yesItem);
            }
            for (int i = 14; i < 17; i++) {
                confirmInv.setItem(i, noItem);
            }
            for (int i = 23; i < 26; i++) {
                confirmInv.setItem(i, noItem);
            }
            for (int i = 32; i < 35; i++) {
                confirmInv.setItem(i, noItem);
            }
            player.openInventory(confirmInv);

            plugin.grantCustomReason.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (plugin.grantCustomReason.contains(player.getUniqueId())) {
            event.setCancelled(true);
            Utils.sendMessage(player, "&cReasons cannot be a command.");
        }
    }
}
