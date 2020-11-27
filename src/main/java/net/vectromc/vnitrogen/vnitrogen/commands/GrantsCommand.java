package net.vectromc.vnitrogen.commands;

import net.vectromc.vnitrogen.management.GrantManagement;
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

public class GrantsCommand implements CommandExecutor {

    private vNitrogen plugin;

    public GrantsCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("YouMustBePlayer")
                    .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                    .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("Grants.Permission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                if (args.length != 1) {
                    Utils.sendMessage(player, plugin.getConfig().getString("Grants.IncorrectUsage")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    OfflinePlayer target2 = Bukkit.getOfflinePlayer(args[0]);
                    String target2name = target2.getName();
                    String target2color = "";
                    for (String rank : plugin.ranks) {
                        if (plugin.pData.config.getString(target2.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank)) {
                            target2color = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                        }
                    }
                    String target2display = target2color + target2name;
                    plugin.setPlayerColor(player);
                    Inventory grantsInv = Bukkit.createInventory(player, 54, ChatColor.translateAlternateColorCodes('&', "&8Grants of: " + target2display));
                    ItemStack filler = XMaterial.GRAY_STAINED_GLASS_PANE.parseItem();
                    for (int i = 0; i < 9; i++) {
                        grantsInv.setItem(i, filler);
                    }

                    String executorName = "";
                    String grant = "";
                    String date = "";
                    String reason = "";
                    String duration = "";
                    String server = "";
                    int Grants = 0;

                    if (plugin.gData.config.contains(target2.getUniqueId().toString() + ".Grants")) {
                        for (String userGrants : plugin.gData.config.getConfigurationSection(target2.getUniqueId().toString() + ".Grants").getKeys(false)) {
                            Grants++;
                            String exeUuid = plugin.gData.config.getString(target2.getUniqueId().toString() + ".Grants." + Grants + ".Executor");
                            for (String rank : plugin.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                                if (plugin.pData.config.getString(exeUuid + ".Rank").equalsIgnoreCase(rank)) {
                                    executorName = plugin.getConfig().getString("Ranks." + rank + ".color") + plugin.pData.config.getString(exeUuid + ".Name");
                                }
                            }
                            reason = plugin.gData.config.getString(target2.getUniqueId().toString() + ".Grants." + Grants + ".Reason");
                            grant = plugin.gData.config.getString(target2.getUniqueId().toString() + ".Grants." + Grants + ".Rank");

                            if (plugin.gData.config.get(target2.getUniqueId().toString() + ".Grants." + Grants + ".Duration").equals("Permanent")) {
                                duration = "Permanent";
                            } else {
                                duration = Utils.DATE_FORMAT.format(new Date(plugin.gData.config.getLong(target2.getUniqueId().toString() + ".Grants." + Grants + ".Duration")));
                            }

                            date = Utils.DATE_FORMAT.format(new Date(plugin.gData.config.getLong(target2.getUniqueId().toString() + ".Grants." + Grants + ".Date")));
                            server = plugin.gData.config.getString(target2.getUniqueId().toString() + ".Grants." + Grants + ".Server");

                            ItemStack grantItem = XMaterial.LIME_WOOL.parseItem();
                            ItemMeta grantMeta = grantItem.getItemMeta();

                            if (plugin.gData.config.getString(target2.getUniqueId().toString() + ".Grants." + Grants + ".Status").equalsIgnoreCase("Active")) {
                                String status = "&a&lActive";
                                grantItem = XMaterial.LIME_WOOL.parseItem();
                                grantMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', status + "&a&l, (" + date + "&a&l)"));
                            } else if (plugin.gData.config.getString(target2.getUniqueId().toString() + ".Grants." + Grants + ".Status").equalsIgnoreCase("Revoked")) {
                                String status = "&c&lRevoked";
                                grantItem = XMaterial.RED_WOOL.parseItem();
                                grantMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', status + "&c&l, (" + date + "&c&l)"));
                            } else if (plugin.gData.config.getString(target2.getUniqueId().toString() + ".Grants." + Grants + ".Status").equalsIgnoreCase("Expired")) {
                                String status = "&6&lExpired";
                                grantItem = XMaterial.ORANGE_WOOL.parseItem();
                                grantMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', status + "&6&l, (" + date + "&6&l)"));
                            } else {
                                String status = "&7&lUnknown";
                                grantItem = XMaterial.GRAY_WOOL.parseItem();
                                grantMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', status + "&7&l, (" + date + "&7&l)"));
                            }

                            ArrayList<String> grantLore = new ArrayList<>();
                            grantLore.add(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------"));
                            grantLore.add(ChatColor.translateAlternateColorCodes('&',"&eExecutor: " + executorName));
                            grantLore.add(ChatColor.translateAlternateColorCodes('&', "&eReason: &6" + reason));
                            grantLore.add(ChatColor.translateAlternateColorCodes('&',"&eDuration: &6" + duration));
                            grantLore.add(ChatColor.translateAlternateColorCodes('&', "&eRank: &6" + grant));
                            grantLore.add(ChatColor.translateAlternateColorCodes('&', "&e "));
                            grantLore.add(ChatColor.translateAlternateColorCodes('&', "&eServer: &6" + server));
                            grantLore.add(ChatColor.translateAlternateColorCodes('&', "&eID: &6" + Grants));
                            grantLore.add(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------"));

                            grantMeta.setLore(grantLore);
                            grantItem.setItemMeta(grantMeta);
                            grantsInv.addItem(grantItem);
                        }
                    }
                    player.openInventory(grantsInv);
                }
            }
        }
        return true;
    }
}
