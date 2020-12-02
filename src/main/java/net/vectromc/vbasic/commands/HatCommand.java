package net.vectromc.vbasic.commands;

import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.utils.XMaterial;
import net.vectromc.vbasic.vBasic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HatCommand implements CommandExecutor {

    private vBasic plugin;

    public HatCommand() {
        plugin = vBasic.getPlugin(vBasic.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer")
                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("Hat.Permission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                if (player.getItemInHand().getType() == XMaterial.AIR.parseMaterial()) {
                    Utils.sendMessage(player, plugin.getConfig().getString("Hat.InvalidItem")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    ItemStack item = player.getItemInHand();
                    plugin.hat.put(player.getUniqueId(), player.getInventory().getHelmet());
                    int slot = player.getInventory().getHeldItemSlot();
                    player.getInventory().setHelmet(item);
                    player.getInventory().setItemInHand(null);
                    player.getInventory().setItem(slot, plugin.hat.get(player.getUniqueId()));
                    Utils.sendMessage(player, plugin.getConfig().getString("Hat.Message"));
                }
            }
        }
        return true;
    }
}
