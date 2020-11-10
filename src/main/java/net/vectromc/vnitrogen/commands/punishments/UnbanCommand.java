package net.vectromc.vnitrogen.commands.punishments;

import net.vectromc.vnitrogen.management.PlayerManagement;
import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnbanCommand implements CommandExecutor {

    private vNitrogen plugin;
    private Boolean silent;

    public UnbanCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("Unban.Permission"))) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (args.length == 0) {
                Utils.sendMessage(sender, plugin.getConfig().getString("Unban.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        if (!plugin.data.config.contains("BannedPlayers." + target.getUniqueId().toString())) {
                            Utils.sendMessage(player, plugin.getConfig().getString("Unban.PlayerIsNotBanned").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            if (args.length == 1) {
                                this.silent = false;
                                plugin.setPlayerColor(player);
                                plugin.setTargetColor(target);
                                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Unban.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName())));
                                }
                                Utils.sendMessage(player, plugin.getConfig().getString("Unban.ExecutorResponse").replaceAll("%player%", target.getDisplayName()));
                                plugin.banned.remove(target.getUniqueId().toString());
                                plugin.data.config.set("BannedPlayers." + target.getUniqueId(), null);
                                int id = plugin.data.config.getInt(target.getUniqueId().toString() + ".BansAmount");
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Status", "Revoked");
                                plugin.data.saveData();
                            } else {
                                if (!args[1].equalsIgnoreCase("-s")) {
                                    Utils.sendMessage(player, plugin.getConfig().getString("Unban.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                                } else {
                                    this.silent = true;
                                    plugin.setPlayerColor(player);
                                    plugin.setTargetColor(target);
                                    for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                        if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                            onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Unban.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName())));
                                        }
                                    }
                                    Utils.sendMessage(player, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Unban.ExecutorResponse").replaceAll("%player%", target.getDisplayName()));
                                    Utils.sendMessage(target, plugin.getConfig().getString("Unban.TargetResponse").replaceAll("%executor%", player.getDisplayName()));
                                    plugin.banned.remove(target.getUniqueId().toString());
                                    plugin.data.config.set("BannedPlayers." + target.getUniqueId(), null);
                                    int id = plugin.data.config.getInt(target.getUniqueId().toString() + ".BansAmount");
                                    plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Status", "Revoked");
                                    plugin.data.saveData();
                                }
                            }
                        }
                    } else {
                        String consoleName = plugin.getConfig().getString("Console.name");
                        if (!plugin.data.config.contains("BannedPlayers." + target.getUniqueId().toString())) {
                            System.out.println(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Unban.PlayerIsNotBanned").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))));
                        } else {
                            if (args.length == 1) {
                                this.silent = false;
                                plugin.setTargetColor(target);
                                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Unban.GlobalMessage").replaceAll("%executor%", consoleName).replaceAll("%target%", target.getDisplayName())));
                                }
                                System.out.println(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Unban.ExecutorResponse").replaceAll("%player%", target.getDisplayName())));
                                plugin.banned.remove(target.getUniqueId().toString());
                                plugin.data.config.set("BannedPlayers." + target.getUniqueId(), null);
                                int id = plugin.data.config.getInt(target.getUniqueId().toString() + ".BansAmount");
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Status", "Revoked");
                                plugin.data.saveData();
                            } else {
                                if (!args[1].equalsIgnoreCase("-s")) {
                                    System.out.println(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Unban.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))));
                                } else {
                                    this.silent = true;
                                    plugin.setTargetColor(target);
                                    for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                        if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                            onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Unban.GlobalMessage").replaceAll("%executor%", consoleName).replaceAll("%target%", target.getDisplayName())));
                                        }
                                    }
                                    System.out.println(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Unban.ExecutorResponse").replaceAll("%player%", target.getDisplayName())));
                                    Utils.sendMessage(target, plugin.getConfig().getString("Unban.TargetResponse").replaceAll("%executor%", consoleName));
                                    plugin.banned.remove(target.getUniqueId().toString());
                                    plugin.data.config.set("BannedPlayers." + target.getUniqueId(), null);
                                    int id = plugin.data.config.getInt(target.getUniqueId().toString() + ".BansAmount");
                                    plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Status", "Revoked");
                                    plugin.data.saveData();
                                }
                            }
                        }
                    }
                } else {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
                        String target2name = args[0];
                        String target2color;
                        if (args.length == 1) {
                            this.silent = false;
                            if (!plugin.data.config.contains(target2.getUniqueId().toString()) || !plugin.data.config.contains(target2.getUniqueId().toString() + ".Rank")) {
                                target2color = plugin.getConfig().getString("Default.color");
                            } else {
                                if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Owner")) {
                                    target2color = plugin.getConfig().getString("Owner.color");
                                } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Developer")) {
                                    target2color = plugin.getConfig().getString("Developer.color");
                                } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Manager")) {
                                    target2color = plugin.getConfig().getString("Manager.color");
                                } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Admin")) {
                                    target2color = plugin.getConfig().getString("Admin.color");
                                } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Senior-Mod")) {
                                    target2color = plugin.getConfig().getString("Senior-Mod.color");
                                } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Mod")) {
                                    target2color = plugin.getConfig().getString("Mod.color");
                                } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Trial-Mod")) {
                                    target2color = plugin.getConfig().getString("Trial-Mod.color");
                                } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Builder")) {
                                    target2color = plugin.getConfig().getString("Builder.color");
                                } else {
                                    target2color = plugin.getConfig().getString("Default.color");
                                }
                            }
                            String target2display = target2color + target2name;
                            plugin.setPlayerColor(player);
                            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Unban.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target2display)));
                            }
                            Utils.sendMessage(player, plugin.getConfig().getString("Unban.ExecutorResponse").replaceAll("%player%", target2display));
                            plugin.banned.remove(target2.getUniqueId().toString());
                            plugin.data.config.set("BannedPlayers." + target2.getUniqueId(), null);
                            int id = plugin.data.config.getInt(target2.getUniqueId().toString() + ".BansAmount");
                            plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Status", "Revoked");
                            plugin.data.saveData();
                        } else if (args.length == 2) {
                            if (!args[1].equalsIgnoreCase("-s")) {
                                Utils.sendMessage(player, plugin.getConfig().getString("Unban.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                            } else {
                                this.silent = true;
                                if (!plugin.data.config.contains(target2.getUniqueId().toString()) || !plugin.data.config.contains(target2.getUniqueId().toString() + ".Rank")) {
                                    target2color = plugin.getConfig().getString("Default.color");
                                } else {
                                    if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Owner")) {
                                        target2color = plugin.getConfig().getString("Owner.color");
                                    } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Developer")) {
                                        target2color = plugin.getConfig().getString("Developer.color");
                                    } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Manager")) {
                                        target2color = plugin.getConfig().getString("Manager.color");
                                    } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Admin")) {
                                        target2color = plugin.getConfig().getString("Admin.color");
                                    } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Senior-Mod")) {
                                        target2color = plugin.getConfig().getString("Senior-Mod.color");
                                    } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Mod")) {
                                        target2color = plugin.getConfig().getString("Mod.color");
                                    } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Trial-Mod")) {
                                        target2color = plugin.getConfig().getString("Trial-Mod.color");
                                    } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Builder")) {
                                        target2color = plugin.getConfig().getString("Builder.color");
                                    } else {
                                        target2color = plugin.getConfig().getString("Default.color");
                                    }
                                }
                                String target2display = target2color + target2name;
                                plugin.setPlayerColor(player);
                                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                    if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Unban.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target2display)));
                                    }
                                }
                                Utils.sendMessage(player, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Unban.ExecutorResponse").replaceAll("%player%", target2display));
                                plugin.banned.remove(target2.getUniqueId().toString());
                                plugin.data.config.set("BannedPlayers." + target2.getUniqueId(), null);
                                int id = plugin.data.config.getInt(target2.getUniqueId().toString() + ".BansAmount");
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Status", "Revoked");
                                plugin.data.saveData();
                            }
                        }
                    } else {
                        String consoleName = plugin.getConfig().getString("Console.name");
                        OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
                        String target2name = args[0];
                        String target2color;
                        if (args.length == 1) {
                            this.silent = false;
                            if (!plugin.data.config.contains(target2.getUniqueId().toString()) || !plugin.data.config.contains(target2.getUniqueId().toString() + ".Rank")) {
                                target2color = plugin.getConfig().getString("Default.color");
                            } else {
                                if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Owner")) {
                                    target2color = plugin.getConfig().getString("Owner.color");
                                } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Developer")) {
                                    target2color = plugin.getConfig().getString("Developer.color");
                                } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Manager")) {
                                    target2color = plugin.getConfig().getString("Manager.color");
                                } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Admin")) {
                                    target2color = plugin.getConfig().getString("Admin.color");
                                } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Senior-Mod")) {
                                    target2color = plugin.getConfig().getString("Senior-Mod.color");
                                } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Mod")) {
                                    target2color = plugin.getConfig().getString("Mod.color");
                                } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Trial-Mod")) {
                                    target2color = plugin.getConfig().getString("Trial-Mod.color");
                                } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Builder")) {
                                    target2color = plugin.getConfig().getString("Builder.color");
                                } else {
                                    target2color = plugin.getConfig().getString("Default.color");
                                }
                            }
                            String target2display = target2color + target2name;
                            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Unban.GlobalMessage").replaceAll("%executor%", consoleName).replaceAll("%target%", target2display)));
                            }
                            System.out.println(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Unban.ExecutorResponse").replaceAll("%player%", target2display)));
                            plugin.banned.remove(target2.getUniqueId().toString());
                            plugin.data.config.set("BannedPlayers." + target2.getUniqueId(), null);
                            int id = plugin.data.config.getInt(target2.getUniqueId().toString() + ".BansAmount");
                            plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Status", "Revoked");
                            plugin.data.saveData();
                        } else if (args.length == 2) {
                            if (!args[1].equalsIgnoreCase("-s")) {
                                System.out.println(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Unban.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix"))));
                            } else {
                                this.silent = true;
                                if (!plugin.data.config.contains(target2.getUniqueId().toString()) || !plugin.data.config.contains(target2.getUniqueId().toString() + ".Rank")) {
                                    target2color = plugin.getConfig().getString("Default.color");
                                } else {
                                    if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Owner")) {
                                        target2color = plugin.getConfig().getString("Owner.color");
                                    } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Developer")) {
                                        target2color = plugin.getConfig().getString("Developer.color");
                                    } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Manager")) {
                                        target2color = plugin.getConfig().getString("Manager.color");
                                    } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Admin")) {
                                        target2color = plugin.getConfig().getString("Admin.color");
                                    } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Senior-Mod")) {
                                        target2color = plugin.getConfig().getString("Senior-Mod.color");
                                    } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Mod")) {
                                        target2color = plugin.getConfig().getString("Mod.color");
                                    } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Trial-Mod")) {
                                        target2color = plugin.getConfig().getString("Trial-Mod.color");
                                    } else if (plugin.data.config.getString(target2.getUniqueId() + ".Rank").equalsIgnoreCase("Builder")) {
                                        target2color = plugin.getConfig().getString("Builder.color");
                                    } else {
                                        target2color = plugin.getConfig().getString("Default.color");
                                    }
                                }
                                String target2display = target2color + target2name;
                                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                    if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Unban.GlobalMessage").replaceAll("%executor%", consoleName).replaceAll("%target%", target2display)));
                                    }
                                }
                                System.out.println(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("Unban.ExecutorResponse").replaceAll("%player%", target2display)));
                                plugin.banned.remove(target2.getUniqueId().toString());
                                plugin.data.config.set("BannedPlayers." + target2.getUniqueId(), null);
                                int id = plugin.data.config.getInt(target2.getUniqueId().toString() + ".BansAmount");
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Status", "Revoked");
                                plugin.data.saveData();
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
