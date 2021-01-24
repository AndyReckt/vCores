package net.vectromc.vbasic.commands.staff;

import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.utils.XMaterial;
import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearCommand implements CommandExecutor {

    private vNitrogen nitrogen;
    private vBasic plugin;

    public ClearCommand() {
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
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
            if (!player.hasPermission(plugin.getConfig().getString("ClearPermission"))) {
                Utils.sendMessage(player, plugin.getConfig().getString("NoPermission")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                if (args.length == 0) {
                    player.getInventory().clear();
                    player.getInventory().setHelmet(XMaterial.AIR.parseItem());
                    player.getInventory().setChestplate(XMaterial.AIR.parseItem());
                    player.getInventory().setLeggings(XMaterial.AIR.parseItem());
                    player.getInventory().setBoots(XMaterial.AIR.parseItem());
                    nitrogen.setPlayerColor(player);
                    Utils.sendMessage(player, plugin.getConfig().getString("ClearMsg")
                            .replace("%player%", player.getDisplayName()));
                } else if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        target.getInventory().clear();
                        target.getInventory().setHelmet(XMaterial.AIR.parseItem());
                        target.getInventory().setChestplate(XMaterial.AIR.parseItem());
                        target.getInventory().setLeggings(XMaterial.AIR.parseItem());
                        target.getInventory().setBoots(XMaterial.AIR.parseItem());
                        nitrogen.setPlayerColor(target);
                        Utils.sendMessage(player, plugin.getConfig().getString("ClearMsg")
                                .replace("%player%", target.getDisplayName()));
                    } else {
                        Utils.sendMessage(player, plugin.getConfig().getString("ClearInvalidPlayer")
                                .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    }
                } else {
                    Utils.sendMessage(player, plugin.getConfig().getString("ClearIncorrectUsage")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                }
            }
        }
        return true;
    }
}
