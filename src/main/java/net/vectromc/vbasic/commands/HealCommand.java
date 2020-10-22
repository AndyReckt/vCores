package net.vectromc.vbasic.commands;

import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class HealCommand implements CommandExecutor {

    private vBasic plugin;
    private vNitrogen nitrogen;

    public HealCommand() {
        plugin = vBasic.getPlugin(vBasic.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("HealPermission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                if (args.length == 0) {
                    nitrogen.setPlayerColor(player);
                    Utils.sendMessage(player, plugin.getConfig().getString("HealSelfMsg"));
                    player.setHealth(20.0);
                    player.setFoodLevel(20);
                    for (PotionEffect effect : player.getActivePotionEffects()) {
                        player.removePotionEffect(effect.getType());
                    }
                    for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                        if (plugin.toggle_staff_alerts.contains(onlineStaff.getUniqueId())) {
                            onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.HealSelf").replaceAll("%player%", player.getDisplayName())));
                        }
                    }
                } else if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        nitrogen.setTargetColor(target);
                        nitrogen.setPlayerColor(player);
                        Utils.sendMessage(player, plugin.getConfig().getString("HealOtherMsg").replaceAll("%target%", target.getDisplayName()));
                        Utils.sendTargetMessage(target, plugin.getConfig().getString("HealSelfMsg"));
                        target.setHealth(20.0);
                        target.setFoodLevel(20);
                        for (PotionEffect effect : target.getActivePotionEffects()) {
                            target.removePotionEffect(effect.getType());
                        }
                        for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                            if (plugin.toggle_staff_alerts.contains(onlineStaff.getUniqueId())) {
                                onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.HealOther").replaceAll("%player%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName())));
                            }
                        }
                    } else {
                        Utils.sendMessage(player, plugin.getConfig().getString("HealInvalidPlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    }
                }
            }
        }
        return true;
    }
}
