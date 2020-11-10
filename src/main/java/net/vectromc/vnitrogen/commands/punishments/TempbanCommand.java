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

import java.util.Date;

public class TempbanCommand implements CommandExecutor {

    private vNitrogen plugin;
    private Boolean silent;

    public TempbanCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("YouMustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("TempBan.Permission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                if (args.length == 0 || args.length == 1 || args.length == 2) {
                    Utils.sendMessage(player, plugin.getConfig().getString("TempBan.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (plugin.data.config.getConfigurationSection("BannedPlayers").getKeys(false).contains(target.getUniqueId().toString())) {
                            Utils.sendMessage(player, plugin.getConfig().getString("TempBan.PlayerIsMuted").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            plugin.setPlayerColor(player);
                            plugin.setTargetColor(target);
                            String time = args[1];
                            int id = plugin.data.config.getInt(target.getUniqueId().toString() + ".BansAmount") + 1;
                            if (time.contains("s")) {
                                String milliString = time.replaceFirst("s", "");
                                Long mills = Long.parseLong(milliString);
                                Long formattedTime = mills * 1000;
                                Long unmuteTime = formattedTime + System.currentTimeMillis();

                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Temp", "true");
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Duration", unmuteTime);
                            } else if (time.contains("m") && !time.contains("o")) {
                                String milliString = time.replaceFirst("m", "");
                                Long mills = Long.parseLong(milliString);
                                Long formattedTime = 60 * (mills * 1000);
                                Long unmuteTime = formattedTime + System.currentTimeMillis();

                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Temp", "true");
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Duration", unmuteTime);
                            } else if (time.contains("h")) {
                                String milliString = time.replaceFirst("h", "");
                                Long mills = Long.parseLong(milliString);
                                Long formattedTime = 60 * (60 * (mills * 1000));
                                Long unmuteTime = formattedTime + System.currentTimeMillis();

                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Temp", "true");
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Duration", unmuteTime);
                            } else if (time.contains("d")) {
                                String milliString = time.replaceFirst("d", "");
                                Long mills = Long.parseLong(milliString);
                                Long formattedTime = 24 * (60 * (60 * (mills * 1000)));
                                Long unmuteTime = formattedTime + System.currentTimeMillis();

                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Temp", "true");
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Duration", unmuteTime);
                            } else if (time.contains("w")) {
                                String milliString = time.replaceFirst("w", "");
                                Long mills = Long.parseLong(milliString);
                                Long formattedTime = 7 * (24 * (60 * (60 * (mills * 1000))));
                                Long unmuteTime = formattedTime + System.currentTimeMillis();

                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Temp", "true");
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Duration", unmuteTime);
                            } else if (time.contains("m") && time.contains("o")) {
                                String milliString = time.replaceFirst("mo", "");
                                Long mills = Long.parseLong(milliString);
                                Long formattedTime = 30 * (24 * (60 * (60 * (mills * 1000))));
                                Long unmuteTime = formattedTime + System.currentTimeMillis();

                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Temp", "true");
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Duration", unmuteTime);
                            } else if (time.contains("y")) {
                                String milliString = time.replaceFirst("y", "");
                                Long mills = Long.parseLong(milliString);
                                Long formattedTime = 12 * (30 * (24 * (60 * (60 * (mills * 1000)))));
                                Long unmuteTime = formattedTime + System.currentTimeMillis();

                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Temp", "true");
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Duration", unmuteTime);
                            } else {
                                int mills = 1;
                                int formattedTime = 24 * (60 * (60 * (mills * 1000)));
                                Long unmuteTime = formattedTime + System.currentTimeMillis();

                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Temp", "true");
                                plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Duration", unmuteTime);
                            }
                            String reason = "";
                            for (int i = 2; i < args.length; i++) {
                                reason = reason + " " + args[i];
                            }
                            if (reason.contains("-s")) {
                                reason = reason.replaceFirst("-s", "");
                                this.silent = true;
                                reason = reason.replaceFirst(" ", "");
                            } else {
                                this.silent = false;
                            }
                            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                if (!silent) {
                                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("TempBan.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                                } else {
                                    if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("TempBan.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                                    }
                                }
                            }
                            if (!silent) {
                                Utils.sendMessage(player, plugin.getConfig().getString("TempBan.ExecutorResponse").replaceAll("%player%", target.getDisplayName()).replaceAll("%reason%", reason));
                            } else {
                                Utils.sendMessage(player, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("TempBan.ExecutorResponse").replaceAll("%player%", target.getDisplayName()).replaceAll("%reason%", reason));
                            }
                            String expirationDate = Utils.TIME_FORMAT.format(new Date(plugin.data.config.getLong(player.getUniqueId().toString() + ".Bans." + id + ".Duration")));
                            target.kickPlayer(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("TempBan.BanMessage").replaceAll("%reason%", reason).replaceAll("%executor%", player.getDisplayName()).replaceAll("%expiry%", expirationDate)));
                            plugin.banned.add(target.getUniqueId().toString());
                            PlayerManagement playerManagement = new PlayerManagement(target);
                            playerManagement.addBan();
                            plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Executor", player.getUniqueId().toString());
                            plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Reason", reason);
                            plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Silent", silent.toString());
                            plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Server", player.getWorld().getName());
                            plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Date", System.currentTimeMillis());
                            plugin.data.config.set(target.getUniqueId() + ".Bans." + id + ".Status", "Active");
                            plugin.data.config.set("BannedPlayers." + target.getUniqueId().toString() + ".Name", target.getName());
                            plugin.data.saveData();
                        }
                    } else {
                        OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
                        String target2name = target2.getName();
                        String target2color;
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
                        String target2display = target2color + target2name;
                        if (plugin.data.config.getConfigurationSection("BannedPlayers").getKeys(false).contains(target2.getUniqueId().toString())) {
                            Utils.sendMessage(player, plugin.getConfig().getString("TempBan.PlayerIsMuted").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                        } else {
                            plugin.setPlayerColor(player);
                            String time = args[1];
                            int id = plugin.data.config.getInt(target2.getUniqueId().toString() + ".BansAmount") + 1;
                            if (time.contains("s")) {
                                String milliString = time.replaceFirst("s", "");
                                Long mills = Long.parseLong(milliString);
                                Long formattedTime = mills * 1000;
                                Long unmuteTime = formattedTime + System.currentTimeMillis();

                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Temp", "true");
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Duration", unmuteTime);
                            } else if (time.contains("m") && !time.contains("o")) {
                                String milliString = time.replaceFirst("m", "");
                                Long mills = Long.parseLong(milliString);
                                Long formattedTime = 60 * (mills * 1000);
                                Long unmuteTime = formattedTime + System.currentTimeMillis();

                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Temp", "true");
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Duration", unmuteTime);
                            } else if (time.contains("h")) {
                                String milliString = time.replaceFirst("h", "");
                                Long mills = Long.parseLong(milliString);
                                Long formattedTime = 60 * (60 * (mills * 1000));
                                Long unmuteTime = formattedTime + System.currentTimeMillis();

                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Temp", "true");
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Duration", unmuteTime);
                            } else if (time.contains("d")) {
                                String milliString = time.replaceFirst("d", "");
                                Long mills = Long.parseLong(milliString);
                                Long formattedTime = 24 * (60 * (60 * (mills * 1000)));
                                Long unmuteTime = formattedTime + System.currentTimeMillis();

                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Temp", "true");
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Duration", unmuteTime);
                            } else if (time.contains("w")) {
                                String milliString = time.replaceFirst("w", "");
                                Long mills = Long.parseLong(milliString);
                                Long formattedTime = 7 * (24 * (60 * (60 * (mills * 1000))));
                                Long unmuteTime = formattedTime + System.currentTimeMillis();

                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Temp", "true");
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Duration", unmuteTime);
                            } else if (time.contains("m") && time.contains("o")) {
                                String milliString = time.replaceFirst("mo", "");
                                Long mills = Long.parseLong(milliString);
                                Long formattedTime = 30 * (24 * (60 * (60 * (mills * 1000))));
                                Long unmuteTime = formattedTime + System.currentTimeMillis();

                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Temp", "true");
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Duration", unmuteTime);
                            } else if (time.contains("y")) {
                                String milliString = time.replaceFirst("y", "");
                                Long mills = Long.parseLong(milliString);
                                Long formattedTime = 12 * (30 * (24 * (60 * (60 * (mills * 1000)))));
                                Long unmuteTime = formattedTime + System.currentTimeMillis();

                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Temp", "true");
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Duration", unmuteTime);
                            } else {
                                int mills = 1;
                                int formattedTime = 24 * (60 * (60 * (mills * 1000)));
                                Long unmuteTime = formattedTime + System.currentTimeMillis();

                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Temp", "true");
                                plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Duration", unmuteTime);
                            }
                            String reason = "";
                            for (int i = 2; i < args.length; i++) {
                                reason = reason + " " + args[i];
                            }
                            if (reason.contains("-s")) {
                                reason = reason.replaceFirst("-s", "");
                                this.silent = true;
                                reason = reason.replaceFirst(" ", "");
                            } else {
                                this.silent = false;
                            }
                            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                                if (!silent) {
                                    onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("TempBan.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target2display).replaceAll("%reason%", reason)));
                                } else {
                                    if (onlinePlayers.hasPermission(plugin.getConfig().getString("Silent.Notify"))) {
                                        onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("TempBan.GlobalMessage").replaceAll("%executor%", player.getDisplayName()).replaceAll("%target%", target2display).replaceAll("%reason%", reason)));
                                    }
                                }
                            }
                            if (!silent) {
                                Utils.sendMessage(player, plugin.getConfig().getString("TempBan.ExecutorResponse").replaceAll("%player%", target2display).replaceAll("%reason%", reason));
                            } else {
                                Utils.sendMessage(player, plugin.getConfig().getString("Silent.Prefix") + " " + plugin.getConfig().getString("TempBan.ExecutorResponse").replaceAll("%player%", target2display).replaceAll("%reason%", reason));
                            }
                            plugin.banned.add(target2.getUniqueId().toString());
                            PlayerManagement playerManagement = new PlayerManagement(target2);
                            playerManagement.addBan();
                            plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Executor", player.getUniqueId().toString());
                            plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Reason", reason);
                            plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Silent", silent.toString());
                            plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Server", player.getWorld().getName());
                            plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Date", System.currentTimeMillis());
                            plugin.data.config.set(target2.getUniqueId() + ".Bans." + id + ".Status", "Active");
                            plugin.data.config.set("BannedPlayers." + target2.getUniqueId().toString() + ".Name", target2.getName());
                            plugin.data.saveData();
                        }
                    }
                }
            }
        }
        return true;
    }
}
