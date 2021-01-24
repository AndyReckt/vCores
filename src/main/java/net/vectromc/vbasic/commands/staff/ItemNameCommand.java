package net.vectromc.vbasic.commands.staff;

import com.google.common.base.Joiner;
import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.utils.XMaterial;
import net.vectromc.vbasic.vBasic;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemNameCommand implements CommandExecutor {

    private vBasic plugin;

    public ItemNameCommand() {
        plugin = vBasic.getPlugin(vBasic.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer")
                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            Player player = (Player) sender;
            if (!player.hasPermission(plugin.getConfig().getString("ItemNamePermission"))) {
                Utils.sendMessage(player, plugin.getConfig().getString("NoPermission")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                if (args.length < 1) {
                    Utils.sendMessage(player, plugin.getConfig().getString("ItemNameIncorrectUsage")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    if (player.getInventory().getItemInHand().getType() == XMaterial.AIR.parseMaterial()) {
                        Utils.sendMessage(player, plugin.getConfig().getString("ItemNameInvalidItem")
                                .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else {
                        ItemStack item = player.getInventory().getItemInHand();
                        ItemMeta itemMeta = item.getItemMeta();
                        String name = Joiner.on(" ").join(args);
                        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
                        item.setItemMeta(itemMeta);
                        int slot = player.getInventory().getHeldItemSlot();
                        player.getInventory().setItem(slot, item);
                        Utils.sendMessage(player, plugin.getConfig().getString("ItemNameMsg")
                                .replace("%name%", name));
                    }
                }
            }
        }
        return true;
    }
}
