package net.vectromc.vbasic.commands;

import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.utils.XSound;
import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MsgCommand implements CommandExecutor {

    private vBasic plugin;
    private vNitrogen nitrogen;

    public MsgCommand() {
        plugin = vBasic.getPlugin(vBasic.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("message")) {
            if (!(sender instanceof Player)) {
                Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                if (args.length == 0 || args.length == 1) {
                    Utils.sendMessage(player, plugin.getConfig().getString("MsgIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        nitrogen.setPlayerColor(player);
                        nitrogen.setTargetColor(target);
                        if (plugin.tpm.contains(player.getUniqueId())) {
                            Utils.sendMessage(player, plugin.getConfig().getString("TpmToError"));
                        } else if (plugin.tpm.contains(target.getUniqueId())) {
                            Utils.sendMessage(player, plugin.getConfig().getString("TpmFromError"));
                        } else {
                            String str = "";
                            for (int i = 1; i < args.length; i++) {
                                str = str + " " + args[i];
                            }
                            plugin.reply.remove(target.getName());
                            plugin.reply.put(target.getName(), player.getName());
                            if (!plugin.tms.contains(target.getUniqueId())) {
                                target.playSound(target.getLocation(), XSound.ENTITY_EXPERIENCE_ORB_PICKUP.parseSound(), 100, 1);
                            }
                            Utils.sendMessage(player, plugin.getConfig().getString("MsgToFormat").replaceAll("%target%", target.getDisplayName()).replaceAll("%message%", str));
                            Utils.sendMessage(target, plugin.getConfig().getString("MsgFromFormat").replaceAll("%player%", player.getDisplayName()).replaceAll("%message%", str));
                        }
                    } else {
                        Utils.sendMessage(player, plugin.getConfig().getString("PlayerNotFound"));
                    }
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("reply")) {
            if (!(sender instanceof Player)) {
                Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                if (args.length == 0) {
                    Utils.sendMessage(player, plugin.getConfig().getString("ReplyIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    if (plugin.reply.containsKey(player.getName())) {
                        String p3 = plugin.reply.get(player.getName());
                        if (Bukkit.getPlayer(p3) != null) {
                            StringBuilder str = new StringBuilder();
                            for (int i = 0; i < args.length; ++i) {
                                str.append(" ").append(args[i]);
                            }
                            String message = str.substring(1);
                            player.performCommand("msg " + p3 + " " + message);
                        } else {
                            Utils.sendMessage(player, plugin.getConfig().getString("ReplyNotMessaging"));
                        }
                    }
                }
            }
        }
        return true;
    }
}
