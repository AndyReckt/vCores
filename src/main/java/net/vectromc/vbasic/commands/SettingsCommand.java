package net.vectromc.vbasic.commands;

import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.utils.XMaterial;
import net.vectromc.vbasic.vBasic;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SettingsCommand implements CommandExecutor {

    private vBasic plugin;

    public SettingsCommand() {
        plugin = vBasic.getPlugin(vBasic.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            Player player = (Player) sender;
            Inventory generalSettingsInv = Bukkit.createInventory(player, 9, ChatColor.YELLOW + "Settings");
            ItemStack tpm = XMaterial.WRITABLE_BOOK.parseItem();
            ItemMeta tpmMeta = tpm.getItemMeta();
            if (plugin.tpm.contains(player.getUniqueId())) {
                tpmMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&ePrivate Messages: &cDisabled."));
            } else {
                tpmMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&ePrivate Messages: &aEnabled."));
            }
            ArrayList<String> tpmLore = new ArrayList<>();
            tpmLore.add(ChatColor.GRAY + "Click to select");
            tpmMeta.setLore(tpmLore);
            tpm.setItemMeta(tpmMeta);
            generalSettingsInv.setItem(0, tpm);

            // Global Chat Item
            ItemStack globalchat = XMaterial.PAPER.parseItem();
            ItemMeta gcMeta = globalchat.getItemMeta();
            if (plugin.tgc.contains(player.getUniqueId())) {
                gcMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eGlobal Chat: &cDisabled."));
            } else {
                gcMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eGlobal Chat: &aEnabled."));
            }
            ArrayList<String> gcLore = new ArrayList<>();
            gcLore.add(ChatColor.GRAY + "Click to select");
            gcMeta.setLore(gcLore);
            globalchat.setItemMeta(gcMeta);
            generalSettingsInv.setItem(1, globalchat);

            // Message Sound Item
            ItemStack messagesounds = XMaterial.EGG.parseItem();
            ItemMeta msMeta = globalchat.getItemMeta();
            if (plugin.tms.contains(player.getUniqueId())) {
                msMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eMessaging Sounds: &cDisabled."));
            } else {
                msMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&eMessaging Sounds: &aEnabled."));
            }
            ArrayList<String> msLore = new ArrayList<>();
            msLore.add(ChatColor.GRAY + "Click to select");
            msMeta.setLore(msLore);
            messagesounds.setItemMeta(msMeta);
            generalSettingsInv.setItem(2, messagesounds);

            player.openInventory(generalSettingsInv);
        }
        return true;
    }
}
