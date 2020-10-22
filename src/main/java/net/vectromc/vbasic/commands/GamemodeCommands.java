package net.vectromc.vbasic.commands;

import net.vectromc.vbasic.utils.Utils;
import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommands implements CommandExecutor {

    private vBasic plugin;
    private vNitrogen nitrogen;

    public GamemodeCommands() {
        plugin = vBasic.getPlugin(vBasic.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gamemode")) {
            if (!(sender instanceof Player)) {
                Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                if (!sender.hasPermission(plugin.getConfig().getString("GamemodePermission"))) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player player = (Player) sender;
                    if (args.length == 0) {
                        Utils.sendMessage(player, plugin.getConfig().getString("GamemodeIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else if (args.length == 1) {
                        if (args[0] != null) {
                            if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("1")) {
                                player.setGameMode(GameMode.CREATIVE);
                                Utils.sendMessage(player, plugin.getConfig().getString("CreativeSelf"));
                            } else if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("0")) {
                                player.setGameMode(GameMode.SURVIVAL);
                                Utils.sendMessage(player, plugin.getConfig().getString("SurvivalSelf"));
                            } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("3")) {
                                player.setGameMode(GameMode.SPECTATOR);
                                Utils.sendMessage(player, plugin.getConfig().getString("SpectatorSelf"));
                            } else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("2")) {
                                player.setGameMode(GameMode.ADVENTURE);
                                Utils.sendMessage(player, plugin.getConfig().getString("AdventureSelf"));
                            }
                        } else {
                            Utils.sendMessage(player, plugin.getConfig().getString("GamemodeIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        }
                    } else if (args.length == 2) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (args[0] != null) {
                            if (target != null) {
                                nitrogen.setTargetColor(target);
                                if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("1")) {
                                    target.setGameMode(GameMode.CREATIVE);
                                    Utils.sendTargetMessage(target, plugin.getConfig().getString("CreativeSelf"));
                                    Utils.sendMessage(player, plugin.getConfig().getString("CreativeOther").replaceAll("%target%", target.getDisplayName()));
                                } else if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("0")) {
                                    target.setGameMode(GameMode.SURVIVAL);
                                    Utils.sendTargetMessage(target, plugin.getConfig().getString("SurvivalSelf"));
                                    Utils.sendMessage(player, plugin.getConfig().getString("SurvivalOther").replaceAll("%target%", target.getDisplayName()));
                                } else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("sp") || args[0].equalsIgnoreCase("3")) {
                                    target.setGameMode(GameMode.SPECTATOR);
                                    Utils.sendTargetMessage(target, plugin.getConfig().getString("SpectatorSelf"));
                                    Utils.sendMessage(player, plugin.getConfig().getString("SpectatorOther").replaceAll("%target%", target.getDisplayName()));
                                } else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("2")) {
                                    target.setGameMode(GameMode.ADVENTURE);
                                    Utils.sendTargetMessage(target, plugin.getConfig().getString("AdventureSelf"));
                                    Utils.sendMessage(player, plugin.getConfig().getString("AdventureOther").replaceAll("%target%", target.getDisplayName()));
                                }
                            } else {
                                Utils.sendMessage(player, plugin.getConfig().getString("GamemodeInvalidPlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                            }
                        } else {
                            Utils.sendMessage(player, plugin.getConfig().getString("GamemodeIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        }
                    } else if (args.length > 2) {
                        Utils.sendMessage(player, plugin.getConfig().getString("GamemodeIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    }
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("gmc")) {
            if (!(sender instanceof Player)) {
                Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                if (!sender.hasPermission(plugin.getConfig().getString("GamemodePermission"))) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player player = (Player) sender;
                    if (args.length == 0) {
                        player.setGameMode(GameMode.CREATIVE);
                        Utils.sendMessage(player, plugin.getConfig().getString("CreativeSelf"));
                    } else if (args.length == 1) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            nitrogen.setTargetColor(target);
                            target.setGameMode(GameMode.CREATIVE);
                            Utils.sendTargetMessage(target, plugin.getConfig().getString("CreativeSelf"));
                            Utils.sendMessage(player, plugin.getConfig().getString("CreativeOther").replaceAll("%target%", target.getDisplayName()));
                        } else {
                            Utils.sendMessage(player, plugin.getConfig().getString("GamemodeInvalidPlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        }
                    } else if (args.length > 1) {
                        Utils.sendMessage(player, plugin.getConfig().getString("GamemodeIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    }
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("gms")) {
            if (!(sender instanceof Player)) {
                Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                if (!sender.hasPermission(plugin.getConfig().getString("GamemodePermission"))) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player player = (Player) sender;
                    if (args.length == 0) {
                        player.setGameMode(GameMode.SURVIVAL);
                        Utils.sendMessage(player, plugin.getConfig().getString("SurvivalSelf"));
                    } else if (args.length == 1) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            nitrogen.setTargetColor(target);
                            target.setGameMode(GameMode.SURVIVAL);
                            Utils.sendTargetMessage(target, plugin.getConfig().getString("SurvivalSelf"));
                            Utils.sendMessage(player, plugin.getConfig().getString("SurvivalOther").replaceAll("%target%", target.getDisplayName()));
                        } else {
                            Utils.sendMessage(player, plugin.getConfig().getString("GamemodeInvalidPlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        }
                    } else if (args.length > 1) {
                        Utils.sendMessage(player, plugin.getConfig().getString("GamemodeIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    }
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("gmsp")) {
            if (!(sender instanceof Player)) {
                Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                if (!sender.hasPermission(plugin.getConfig().getString("GamemodePermission"))) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player player = (Player) sender;
                    if (args.length == 0) {
                        player.setGameMode(GameMode.SPECTATOR);
                        Utils.sendMessage(player, plugin.getConfig().getString("SpectatorSelf"));
                    } else if (args.length == 1) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            nitrogen.setTargetColor(target);
                            target.setGameMode(GameMode.SPECTATOR);
                            Utils.sendTargetMessage(target, plugin.getConfig().getString("SpectatorSelf"));
                            Utils.sendMessage(player, plugin.getConfig().getString("SpectatorOther").replaceAll("%target%", target.getDisplayName()));
                        } else {
                            Utils.sendMessage(player, plugin.getConfig().getString("GamemodeInvalidPlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        }
                    } else if (args.length > 1) {
                        Utils.sendMessage(player, plugin.getConfig().getString("GamemodeIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    }
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("gma")) {
            if (!(sender instanceof Player)) {
                Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                if (!sender.hasPermission(plugin.getConfig().getString("GamemodePermission"))) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player player = (Player) sender;
                    if (args.length == 0) {
                        player.setGameMode(GameMode.ADVENTURE);
                        Utils.sendMessage(player, plugin.getConfig().getString("AdventureSelf"));
                    } else if (args.length == 1) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            nitrogen.setTargetColor(target);
                            target.setGameMode(GameMode.ADVENTURE);
                            Utils.sendTargetMessage(target, plugin.getConfig().getString("AdventureSelf"));
                            Utils.sendMessage(player, plugin.getConfig().getString("AdventureOther").replaceAll("%target%", target.getDisplayName()));
                        } else {
                            Utils.sendMessage(player, plugin.getConfig().getString("GamemodeInvalidPlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        }
                    } else if (args.length > 1) {
                        Utils.sendMessage(player, plugin.getConfig().getString("GamemodeIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    }
                }
            }
        }
        return true;
    }
}
