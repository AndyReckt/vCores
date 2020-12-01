package me.yochran.vbungee.commands;

import me.yochran.vbungee.utils.Utils;
import me.yochran.vbungee.vbungee;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GListCommand implements CommandExecutor {

    private vbungee plugin;
    private vNitrogen nitrogen;

    public GListCommand() {
        plugin = vbungee.getPlugin(vbungee.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(plugin.getConfig().getString("GList.Permission"))) {
            Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (args.length == 0) {
                int online = Bukkit.getOnlinePlayers().size();
                Utils.sendMessage(sender, plugin.getConfig().getString("GList.RegularFormat")
                        .replace("%online%", "" + online));
            } else {
                if (args.length != 1) {
                    Utils.sendMessage(sender, plugin.getConfig().getString("GList.IncorrectUsage")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    if (args[0].equalsIgnoreCase("current")) {
                        if (!(sender instanceof Player)) {
                            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer")
                                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            Player player = (Player) sender;
                            World world = player.getWorld();
                            int serverOnline = 0;
                            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                if (onlinePlayers.getWorld() == world) {
                                    serverOnline++;
                                }
                            }
                            List<String> players = new ArrayList<>();
                            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                nitrogen.setPlayerColor(onlinePlayers);
                                players.add(onlinePlayers.getDisplayName());
                            }
                            String playerMessage = "";
                            for (String playerList : players) {
                                if (playerMessage.length() == 0) {
                                    playerMessage = playerList;
                                } else {
                                    playerMessage = playerMessage + "&f, " + playerList;
                                }
                            }
                            Utils.sendMessage(player, plugin.getConfig().getString("GList.CurrentFormat")
                                    .replace("%server%", world.getName())
                                    .replace("%server_online%", serverOnline + "")
                                    .replace("%list%", playerMessage));
                        }
                    } else if (args[0].equalsIgnoreCase("showall")) {
                        String finalMsg = "";
                        List<String> serverList = new ArrayList<>();
                        List<String> servers = new ArrayList<>();
                        for (World world : Bukkit.getServer().getWorlds()) {
                            servers.add(world.getName());
                        }
                        for (String world : servers) {
                            List<String> worldPlayers = new ArrayList<>();
                            for (Player player : Bukkit.getWorld(world).getPlayers()) {
                                nitrogen.setPlayerColor(player);
                                worldPlayers.add(player.getDisplayName());
                            }
                            int onlinecount = Bukkit.getServer().getWorld(world).getPlayers().size();
                            String serverMsg = "&6&l" + world + "&7 (" + onlinecount + ")&e:\n";
                            String playerMsg = "";
                            for (String loopPlayers : worldPlayers) {
                                if (playerMsg.length() == 0) {
                                    playerMsg = loopPlayers;
                                } else {
                                    playerMsg = playerMsg + "&f, " + loopPlayers;
                                }
                            }
                            serverMsg = serverMsg + playerMsg + "\n\n";
                            serverList.add(serverMsg + "\n\n");
                        }
                        int loopnumber = 0;
                        for (String finalLoop : serverList) {
                            loopnumber++;
                            if (loopnumber == 1) {
                                finalMsg = finalLoop;
                            } else {
                                finalMsg = finalMsg + finalLoop;
                            }
                        }
                        Utils.sendMessage(sender, plugin.getConfig().getString("GList.ShowallFormat")
                                .replace("%servers%", finalMsg));
                    } else {
                        Utils.sendMessage(sender, plugin.getConfig().getString("GList.IncorrectUsage")
                                .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    }
                }
            }
        }
        return true;
    }
}
