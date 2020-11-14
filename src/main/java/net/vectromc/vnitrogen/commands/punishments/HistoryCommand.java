package net.vectromc.vnitrogen.commands.punishments;

import net.vectromc.vnitrogen.utils.Utils;
import net.vectromc.vnitrogen.utils.XMaterial;
import net.vectromc.vnitrogen.vNitrogen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Date;

public class HistoryCommand implements CommandExecutor {

    private vNitrogen plugin;

    public HistoryCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("YouMustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("History.Permission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                String consoleName = plugin.getConfig().getString("Console.name");
                Player player = (Player) sender;
                if (args.length != 1) {
                    Utils.sendMessage(player, plugin.getConfig().getString("History.IncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
                    if (!plugin.pData.config.contains(target2.getUniqueId().toString())) {
                        Utils.sendMessage(player, plugin.getConfig().getString("History.InvalidPlayer")
                                .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else {
                        String target2color = "";
                        String target2name = target2.getName();
                        for (String rank : plugin.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                            if (plugin.pData.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                                target2color = plugin.getConfig().getString("Ranks." + rank + ".color");
                            }
                        }
                        String target2display = target2color + target2name;
                        Inventory histInv = Bukkit.createInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&8History of " + target2display + "&8:"));
                        ItemStack filler = XMaterial.GRAY_STAINED_GLASS_PANE.parseItem();
                        for (int i = 0; i < 9; i++) {
                            histInv.setItem(i, filler);
                        }
                        int Mutes = 0;
                        int Bans = 0;
                        int Blacklists = 0;
                        int Kicks = 0;
                        int Warns = 0;
                        String executorName = null;
                        String date = null;
                        String type = null;
                        String reason = null;
                        String duration = null;
                        String server = null;
                        String silent;

                        if (plugin.data.config.contains(target2.getUniqueId().toString() + ".Blacklists")) {
                            for (String blacklistNumber : plugin.data.config.getConfigurationSection(target2.getUniqueId().toString() + ".Blacklists").getKeys(false)) {
                                Blacklists++;
                                type = "&8Blacklist";
                                String exeUuid = plugin.data.config.getString(target2.getUniqueId().toString() + ".Blacklists." + Blacklists + ".Executor");
                                if (exeUuid.equalsIgnoreCase("Console")) {
                                    executorName = consoleName;
                                } else {
                                    for (String rank : plugin.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                                        if (plugin.pData.config.getString(exeUuid + ".Rank").equalsIgnoreCase(rank.toUpperCase())) {
                                            executorName = plugin.getConfig().getString("Ranks." + rank + ".color") + plugin.pData.config.getString(exeUuid + ".Name");
                                        }
                                    }
                                }
                                reason = plugin.data.config.getString(target2.getUniqueId().toString() + ".Blacklists." + Blacklists + ".Reason");
                                duration = "Permanent";
                                server = plugin.data.config.getString(target2.getUniqueId().toString() + ".Blacklists." + Blacklists + ".Server");
                                silent = plugin.data.config.getString(target2.getUniqueId().toString() + ".Blacklists." + Blacklists + ".Silent");
                                date = Utils.DATE_FORMAT.format(new Date(plugin.data.config.getLong(target2.getUniqueId().toString() + ".Blacklists." + Blacklists + ".Date")));

                                ItemStack blacklistItem = XMaterial.LIME_WOOL.parseItem();
                                ItemMeta blacklistMeta = blacklistItem.getItemMeta();

                                if (plugin.data.config.getString(target2.getUniqueId().toString() + ".Blacklists." + Blacklists + ".Status").equalsIgnoreCase("Active")) {
                                    String status = "&a&lActive";
                                    blacklistItem = XMaterial.LIME_WOOL.parseItem();
                                    blacklistMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', status + "&a&l, (" + date + "&a&l)"));
                                } else if (plugin.data.config.getString(target2.getUniqueId().toString() + ".Blacklists." + Blacklists + ".Status").equalsIgnoreCase("Revoked")) {
                                    String status = "&c&lRevoked";
                                    blacklistItem = XMaterial.RED_WOOL.parseItem();
                                    blacklistMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', status + "&c&l, (" + date + "&c&l)"));
                                } else if (plugin.data.config.getString(target2.getUniqueId().toString() + ".Blacklists." + Blacklists + ".Status").equalsIgnoreCase("Expired")) {
                                    String status = "&6&lExpired";
                                    blacklistItem = XMaterial.ORANGE_WOOL.parseItem();
                                    blacklistMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', status + "&6&l, (" + date + "&6&l)"));
                                } else {
                                    String status = "&7&lUnknown";
                                    blacklistItem = XMaterial.GRAY_WOOL.parseItem();
                                    blacklistMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', status + "&7&l, (" + date + "&7&l)"));
                                }

                                ArrayList<String> blacklistLore = new ArrayList<>();
                                blacklistLore.add(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------"));
                                blacklistLore.add(ChatColor.translateAlternateColorCodes('&', "&eType: " + type));
                                blacklistLore.add(ChatColor.translateAlternateColorCodes('&', "&eExecutor: " + executorName));
                                blacklistLore.add(ChatColor.translateAlternateColorCodes('&', "&eReason:&6" + reason));
                                blacklistLore.add(ChatColor.translateAlternateColorCodes('&', "&eDuration: &6" + duration));
                                blacklistLore.add(ChatColor.translateAlternateColorCodes('&', "&a "));
                                blacklistLore.add(ChatColor.translateAlternateColorCodes('&', "&eServer: &6" + server));
                                blacklistLore.add(ChatColor.translateAlternateColorCodes('&', "&eSilent: &6" + silent));
                                blacklistLore.add(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------"));

                                blacklistMeta.setLore(blacklistLore);
                                blacklistItem.setItemMeta(blacklistMeta);
                                histInv.addItem(blacklistItem);
                            }
                        }

                        if (plugin.data.config.contains(target2.getUniqueId().toString() + ".Bans")) {
                            for (String banNumber : plugin.data.config.getConfigurationSection(target2.getUniqueId().toString() + ".Bans").getKeys(false)) {
                                Bans++;
                                type = "&4Ban";
                                String exeUuid = plugin.data.config.getString(target2.getUniqueId().toString() + ".Bans." + Bans + ".Executor");
                                if (exeUuid.equalsIgnoreCase("Console")) {
                                    executorName = consoleName;
                                } else {
                                    for (String rank : plugin.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                                        if (plugin.pData.config.getString(exeUuid + ".Rank").equalsIgnoreCase(rank.toUpperCase())) {
                                            executorName = plugin.getConfig().getString("Ranks." + rank + ".color") + plugin.pData.config.getString(exeUuid + ".Name");
                                        }
                                    }
                                }
                                reason = plugin.data.config.getString(target2.getUniqueId().toString() + ".Bans." + Bans + ".Reason");

                                if (plugin.data.config.getString(target2.getUniqueId().toString() + ".Bans." + Bans + ".Duration").equalsIgnoreCase("Permanent")) {
                                    duration = "Permanent";
                                } else {
                                    duration = Utils.DATE_FORMAT.format(new Date(plugin.data.config.getLong(target2.getUniqueId().toString() + ".Bans." + Bans + ".Duration")));
                                }

                                server = plugin.data.config.getString(target2.getUniqueId().toString() + ".Bans." + Bans + ".Server");
                                silent = plugin.data.config.getString(target2.getUniqueId().toString() + ".Bans." + Bans + ".Silent");
                                date = Utils.DATE_FORMAT.format(new Date(plugin.data.config.getLong(target2.getUniqueId().toString() + ".Bans." + Bans + ".Date")));

                                ItemStack banItem = XMaterial.LIME_WOOL.parseItem();
                                ItemMeta banMeta = banItem.getItemMeta();

                                if (plugin.data.config.getString(target2.getUniqueId().toString() + ".Bans." + Bans + ".Status").equalsIgnoreCase("Active")) {
                                    String status = "&a&lActive";
                                    banItem = XMaterial.LIME_WOOL.parseItem();
                                    banMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', status + "&a&l, (" + date + "&a&l)"));
                                } else if (plugin.data.config.getString(target2.getUniqueId().toString() + ".Bans." + Bans + ".Status").equalsIgnoreCase("Revoked")) {
                                    String status = "&c&lRevoked";
                                    banItem = XMaterial.RED_WOOL.parseItem();
                                    banMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', status + "&c&l, (" + date + "&c&l)"));
                                } else if (plugin.data.config.getString(target2.getUniqueId().toString() + ".Bans." + Bans + ".Status").equalsIgnoreCase("Expired")) {
                                    String status = "&6&lExpired";
                                    banItem = XMaterial.ORANGE_WOOL.parseItem();
                                    banMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', status + "&6&l, (" + date + "&6&l)"));
                                } else {
                                    String status = "&7&lUnknown";
                                    banItem = XMaterial.GRAY_WOOL.parseItem();
                                    banMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', status + "&7&l, (" + date + "&7&l)"));
                                }

                                ArrayList<String> banLore = new ArrayList<>();
                                banLore.add(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------"));
                                banLore.add(ChatColor.translateAlternateColorCodes('&',"&eType: " + type));
                                banLore.add(ChatColor.translateAlternateColorCodes('&',"&eExecutor: " + executorName));
                                banLore.add(ChatColor.translateAlternateColorCodes('&', "&eReason:&6" + reason));
                                banLore.add(ChatColor.translateAlternateColorCodes('&',"&eDuration: &6" + duration));
                                banLore.add(ChatColor.translateAlternateColorCodes('&',"&a "));
                                banLore.add(ChatColor.translateAlternateColorCodes('&',"&eServer: &6" + server));
                                banLore.add(ChatColor.translateAlternateColorCodes('&',"&eSilent: &6" + silent));
                                banLore.add(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------"));

                                banMeta.setLore(banLore);
                                banItem.setItemMeta(banMeta);
                                histInv.addItem(banItem);
                            }
                        }

                        if (plugin.data.config.contains(target2.getUniqueId() + ".Mutes")) {
                            for (String muteNumber : plugin.data.config.getConfigurationSection(target2.getUniqueId().toString() + ".Mutes").getKeys(false)) {
                                Mutes++;
                                type = "&6Mute";
                                String exeUuid = plugin.data.config.getString(target2.getUniqueId().toString() + ".Mutes." + Mutes + ".Executor");
                                if (exeUuid.equalsIgnoreCase("Console")) {
                                    executorName = consoleName;
                                } else {
                                    for (String rank : plugin.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                                        if (plugin.pData.config.getString(exeUuid + ".Rank").equalsIgnoreCase(rank.toUpperCase())) {
                                            executorName = plugin.getConfig().getString("Ranks." + rank + ".color") + plugin.pData.config.getString(exeUuid + ".Name");
                                        }
                                    }
                                }
                                reason = plugin.data.config.getString(target2.getUniqueId().toString() + ".Mutes." + Mutes + ".Reason");

                                if (plugin.data.config.getString(target2.getUniqueId().toString() + ".Mutes." + Mutes + ".Duration").equalsIgnoreCase("Permanent")) {
                                    duration = "Permanent";
                                } else {
                                    duration = Utils.DATE_FORMAT.format(new Date(plugin.data.config.getLong(target2.getUniqueId().toString() + ".Mutes." + Mutes + ".Duration")));
                                }

                                server = plugin.data.config.getString(target2.getUniqueId().toString() + ".Mutes." + Mutes + ".Server");
                                silent = plugin.data.config.getString(target2.getUniqueId().toString() + ".Mutes." + Mutes + ".Silent");
                                date = Utils.DATE_FORMAT.format(new Date(plugin.data.config.getLong(target2.getUniqueId().toString() + ".Mutes." + Mutes + ".Date")));

                                ItemStack muteItem = XMaterial.LIME_WOOL.parseItem();
                                ItemMeta muteMeta = muteItem.getItemMeta();

                                if (plugin.data.config.getString(target2.getUniqueId().toString() + ".Mutes." + Mutes + ".Status").equalsIgnoreCase("Active")) {
                                    String status = "&a&lActive";
                                    muteItem = XMaterial.LIME_WOOL.parseItem();
                                    muteMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', status + "&a&l, (" + date + "&a&l)"));
                                } else if (plugin.data.config.getString(target2.getUniqueId().toString() + ".Mutes." + Mutes + ".Status").equalsIgnoreCase("Revoked")) {
                                    String status = "&c&lRevoked";
                                    muteItem = XMaterial.RED_WOOL.parseItem();
                                    muteMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', status + "&c&l, (" + date + "&c&l)"));
                                } else if (plugin.data.config.getString(target2.getUniqueId().toString() + ".Mutes." + Mutes + ".Status").equalsIgnoreCase("Expired")) {
                                    String status = "&6&lExpired";
                                    muteItem = XMaterial.ORANGE_WOOL.parseItem();
                                    muteMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', status + "&6&l, (" + date + "&6&l)"));
                                } else {
                                    String status = "&7&lUnknown";
                                    muteItem = XMaterial.GRAY_WOOL.parseItem();
                                    muteMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', status + "&7&l, (" + date + "&7&l)"));
                                }

                                ArrayList<String> muteLore = new ArrayList<>();
                                muteLore.add(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------"));
                                muteLore.add(ChatColor.translateAlternateColorCodes('&',"&eType: " + type));
                                muteLore.add(ChatColor.translateAlternateColorCodes('&',"&eExecutor: " + executorName));
                                muteLore.add(ChatColor.translateAlternateColorCodes('&', "&eReason:&6" + reason));
                                muteLore.add(ChatColor.translateAlternateColorCodes('&',"&eExpire(s/d) on: &6" + duration));
                                muteLore.add(ChatColor.translateAlternateColorCodes('&',"&a "));
                                muteLore.add(ChatColor.translateAlternateColorCodes('&',"&eServer: &6" + server));
                                muteLore.add(ChatColor.translateAlternateColorCodes('&',"&eSilent: &6" + silent));
                                muteLore.add(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------"));

                                muteMeta.setLore(muteLore);
                                muteItem.setItemMeta(muteMeta);
                                histInv.addItem(muteItem);
                            }
                        }

                        if (plugin.data.config.contains(target2.getUniqueId() + ".Kicks")) {
                            for (String kickNumber : plugin.data.config.getConfigurationSection(target2.getUniqueId().toString() + ".Kicks").getKeys(false)) {
                                Kicks++;
                                type = "&cKick";
                                String exeUuid = plugin.data.config.getString(target2.getUniqueId().toString() + ".Kicks." + Kicks + ".Executor");
                                if (exeUuid.equalsIgnoreCase("Console")) {
                                    executorName = consoleName;
                                } else {
                                    for (String rank : plugin.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                                        if (plugin.pData.config.getString(exeUuid + ".Rank").equalsIgnoreCase(rank.toUpperCase())) {
                                            executorName = plugin.getConfig().getString("Ranks." + rank + ".color") + plugin.pData.config.getString(exeUuid + ".Name");
                                        }
                                    }
                                }
                                reason = plugin.data.config.getString(target2.getUniqueId().toString() + ".Kicks." + Kicks + ".Reason");
                                server = plugin.data.config.getString(target2.getUniqueId().toString() + ".Kicks." + Kicks + ".Server");
                                silent = plugin.data.config.getString(target2.getUniqueId().toString() + ".Kicks." + Kicks + ".Silent");
                                date = Utils.DATE_FORMAT.format(new Date(plugin.data.config.getLong(target2.getUniqueId().toString() + ".Kicks." + Kicks + ".Date")));

                                ItemStack kickItem = XMaterial.YELLOW_WOOL.parseItem();
                                ItemMeta kickMeta = kickItem.getItemMeta();
                                kickMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&l(" + date + "&e&l)"));

                                ArrayList<String> kickLore = new ArrayList<>();
                                kickLore.add(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------"));
                                kickLore.add(ChatColor.translateAlternateColorCodes('&',"&eType: " + type));
                                kickLore.add(ChatColor.translateAlternateColorCodes('&',"&eExecutor: " + executorName));
                                kickLore.add(ChatColor.translateAlternateColorCodes('&', "&eReason:&6" + reason));
                                kickLore.add(ChatColor.translateAlternateColorCodes('&',"&a "));
                                kickLore.add(ChatColor.translateAlternateColorCodes('&',"&eServer: &6" + server));
                                kickLore.add(ChatColor.translateAlternateColorCodes('&',"&eSilent: &6" + silent));
                                kickLore.add(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------"));

                                kickMeta.setLore(kickLore);
                                kickItem.setItemMeta(kickMeta);
                                histInv.addItem(kickItem);
                            }
                        }

                        if (plugin.data.config.contains(target2.getUniqueId() + ".Warns")) {
                            for (String warnNumber : plugin.data.config.getConfigurationSection(target2.getUniqueId().toString() + ".Warns").getKeys(false)) {
                                Warns++;
                                type = "&eWarn";
                                String exeUuid = plugin.data.config.getString(target2.getUniqueId().toString() + ".Warns." + Warns + ".Executor");
                                if (exeUuid.equalsIgnoreCase("Console")) {
                                    executorName = consoleName;
                                } else {
                                    for (String rank : plugin.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                                        if (plugin.pData.config.getString(exeUuid + ".Rank").equalsIgnoreCase(rank.toUpperCase())) {
                                            executorName = plugin.getConfig().getString("Ranks." + rank + ".color") + plugin.pData.config.getString(exeUuid + ".Name");
                                        }
                                    }
                                }
                                reason = plugin.data.config.getString(target2.getUniqueId().toString() + ".Warns." + Warns + ".Reason");
                                server = plugin.data.config.getString(target2.getUniqueId().toString() + ".Warns." + Warns + ".Server");
                                silent = plugin.data.config.getString(target2.getUniqueId().toString() + ".Warns." + Warns + ".Silent");
                                date = Utils.DATE_FORMAT.format(new Date(plugin.data.config.getLong(target2.getUniqueId().toString() + ".Warns." + Warns + ".Date")));

                                ItemStack warnItem = XMaterial.YELLOW_WOOL.parseItem();
                                ItemMeta warnMeta = warnItem.getItemMeta();
                                warnMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&l(" + date + "&e&l)"));

                                ArrayList<String> warnLore = new ArrayList<>();
                                warnLore.add(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------"));
                                warnLore.add(ChatColor.translateAlternateColorCodes('&',"&eType: " + type));
                                warnLore.add(ChatColor.translateAlternateColorCodes('&',"&eExecutor: " + executorName));
                                warnLore.add(ChatColor.translateAlternateColorCodes('&', "&eReason:&6" + reason));
                                warnLore.add(ChatColor.translateAlternateColorCodes('&',"&a "));
                                warnLore.add(ChatColor.translateAlternateColorCodes('&',"&eServer: &6" + server));
                                warnLore.add(ChatColor.translateAlternateColorCodes('&',"&eSilent: &6" + silent));
                                warnLore.add(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------"));

                                warnMeta.setLore(warnLore);
                                warnItem.setItemMeta(warnMeta);
                                histInv.addItem(warnItem);
                            }
                        }

                        player.openInventory(histInv);
                    }
                }
            }
        }
        return true;
    }
}
