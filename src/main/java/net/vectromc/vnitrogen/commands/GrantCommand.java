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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GrantCommand implements CommandExecutor, Listener {

    private vNitrogen plugin;

    public GrantCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    String clickedRank = "";
    String clickedDuration = "";
    String clickedReason = "";

    String targetName = "";
    String targetDisplay = "";
    String targetColor = "";

    @EventHandler
    public void guiClick(InventoryClickEvent event) {
        Player clicker = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == XMaterial.AIR.parseMaterial()) {
            return;
        } else {
            if (!event.getCurrentItem().getItemMeta().hasLore()) {
                event.setCancelled(true);
            } else {
                if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Select A Rank")) {
                    clickedRank = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
                    for (String rank : plugin.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                        if (plugin.getConfig().getString("Ranks." + rank + ".name").equalsIgnoreCase(clickedRank)) {
                            Player player = clicker;
                            if (player.hasPermission(plugin.getConfig().getString("Ranks." + rank + ".grantPermission"))) {
                                OfflinePlayer target = plugin.grantPlayer.get(player);
                                plugin.grantRank.put(player, clickedRank);
                                openDurationGUI(player, target);
                            } else {
                                event.setCancelled(true);
                            }
                        }
                    }
                } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Select A Duration")) {
                    clickedDuration = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
                    for (String duration : plugin.getConfig().getConfigurationSection("Grant.DurationMenu.Items").getKeys(false)) {
                        if (plugin.getConfig().getString("Grant.DurationMenu.Items." + duration + ".ID").equalsIgnoreCase(clickedDuration)) {
                            Player player = clicker;
                            OfflinePlayer target = plugin.grantPlayer.get(player);
                            plugin.grantDuration.put(player, clickedDuration);
                            openReasonGUI(player, target);
                        }
                    }
                } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Select A Reason")) {
                    clickedReason = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
                    for (String reason : plugin.getConfig().getConfigurationSection("Grant.ReasonMenu.Items").getKeys(false)) {
                        if (plugin.getConfig().getString("Grant.ReasonMenu.Items." + reason + ".ID").equalsIgnoreCase(clickedReason)) {
                            Player player = clicker;
                            OfflinePlayer target = plugin.grantPlayer.get(player);
                            plugin.grantReason.put(player, clickedReason);
                            openConfirmationGUI(player, target);
                        }
                    }
                } else if (event.getView().getTitle().equals(ChatColor.DARK_GRAY + "Confirmation")) {
                    Player player = clicker;
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&2&lConfirm Grant"))) {
                        OfflinePlayer target = plugin.grantPlayer.get(player);
                        plugin.setPlayerColor(player);
                        String rank = plugin.grantRank.get(player);
                        String duration = plugin.grantDuration.get(player);
                        String reason = plugin.grantReason.get(player);
                        String rankBefore = "";
                        for (String ranks : plugin.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                            if (plugin.pData.config.getString(target.getUniqueId().toString() + ".Rank").equalsIgnoreCase(ranks)) {
                                rankBefore = plugin.getConfig().getString("Ranks." + ranks + ".name");
                            }
                        }
                        Utils.sendMessage(player, plugin.getConfig().getString("Grant.ConfirmedGrant")
                                .replace("%player%", targetDisplay)
                                .replace("%rank%", rank)
                                .replace("%duration%", duration)
                                .replace("%reason%", reason));
                        GrantManagement grantManagement = new GrantManagement(target);
                        int id = plugin.gData.config.getInt(target.getUniqueId().toString() + ".GrantsAmount") + 1;
                        if (duration.equalsIgnoreCase(plugin.getConfig().getString("Grant.DurationMenu.Items.Permanent.ID"))) {
                            plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Temp", "false");
                            plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Duration", "Permanent");
                        } else {
                            plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Temp", "true");

                            long formattedTime;
                            Long expireTime;
                            switch (duration.toLowerCase()) {
                                case "10 seconds":
                                    formattedTime = 10 * 1000;
                                    expireTime = formattedTime + System.currentTimeMillis();

                                    plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Duration", expireTime);
                                    break;
                                case "1 minute":
                                    formattedTime = 60 * 1000;
                                    expireTime = formattedTime + System.currentTimeMillis();

                                    plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Duration", expireTime);
                                    break;
                                case "5 minutes":
                                    formattedTime = 5 * (60 * 1000);
                                    expireTime = formattedTime + System.currentTimeMillis();

                                    plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Duration", expireTime);
                                    break;
                                case "30 minutes":
                                    formattedTime = 30 * (60 * 1000);
                                    expireTime = formattedTime + System.currentTimeMillis();

                                    plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Duration", expireTime);
                                    break;
                                case "1 hour":
                                    formattedTime = 60 * (60 * 1000);
                                    expireTime = formattedTime + System.currentTimeMillis();

                                    plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Duration", expireTime);
                                    break;
                                case "3 hours":
                                    formattedTime = 3 * (60 * (60 * 1000));
                                    expireTime = formattedTime + System.currentTimeMillis();

                                    plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Duration", expireTime);
                                    break;
                                case "1 day":
                                    formattedTime = 24 * (60 * (60 * 1000));
                                    expireTime = formattedTime + System.currentTimeMillis();

                                    plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Duration", expireTime);
                                    break;
                                case "1 week":
                                    formattedTime = 7 * (24 * (60 * (60 * 1000)));
                                    expireTime = formattedTime + System.currentTimeMillis();

                                    plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Duration", expireTime);
                                    break;
                                case "1 month":
                                    formattedTime = 31 * (24 * (60 * (60 * 1000)));
                                    expireTime = formattedTime + System.currentTimeMillis();

                                    plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Duration", expireTime);
                                    break;
                                case "1 year":
                                    formattedTime = 12 * (31 * (24 * (60 * (60 * 1000))));
                                    expireTime = formattedTime + System.currentTimeMillis();

                                    plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Duration", expireTime);
                                    break;
                            }
                        }
                        player.closeInventory();
                        plugin.grantPlayer.remove(player);
                        plugin.grantReason.remove(player);
                        plugin.grantDuration.remove(player);
                        plugin.grantRank.remove(player);
                        grantManagement.addGrant();
                        plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Rank", rank);
                        plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Executor", player.getUniqueId().toString());
                        plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Reason", reason);
                        plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Server", player.getWorld().getName());
                        plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Date", System.currentTimeMillis());
                        plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".PrevRank", rankBefore);
                        plugin.gData.config.set(target.getUniqueId() + ".Grants." + id + ".Status", "Active");
                        plugin.gData.saveData();
                        plugin.gData.reloadData();
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "setrank " + target.getName() + " " + rank);
                    } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&c&lCancel Grant"))) {
                        Utils.sendMessage(player, plugin.getConfig().getString("Grant.CancelledGrant"));
                        player.closeInventory();
                        plugin.grantPlayer.remove(player);
                        plugin.grantReason.remove(player);
                        plugin.grantDuration.remove(player);
                        plugin.grantRank.remove(player);
                    }
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("YouMustBePlayer")
            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("Grant.Permission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                if (args.length != 1) {
                    Utils.sendMessage(player, plugin.getConfig().getString("Grant.IncorrectUsage")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    if (!plugin.pData.config.contains(target.getUniqueId().toString())) {
                        Utils.sendMessage(player, plugin.getConfig().getString("Grant.InvalidPlayer")
                                .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                                .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    } else {
                        targetName = target.getName();
                        for (String rank : plugin.ranks) {
                            if (plugin.pData.config.getString(target.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank.toUpperCase())) {
                                targetColor = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                            }
                        }
                        plugin.grantPlayer.remove(player);
                        plugin.grantDuration.remove(player);
                        plugin.grantReason.remove(player);
                        plugin.grantRank.remove(player);
                        targetDisplay = targetColor + targetName;
                        plugin.grantPlayer.put(player, target);
                        plugin.setPlayerColor(player);
                        Inventory grantInv = Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY + "Select A Rank");
                        ItemStack rankItem = XMaterial.BEDROCK.parseItem();
                        ItemStack filler = XMaterial.GRAY_STAINED_GLASS_PANE.parseItem();
                        for (int i = 0; i < 9; i++) {
                            grantInv.setItem(i, filler);
                        }
                        ItemMeta rankItemMeta = rankItem.getItemMeta();
                        for (String rank : plugin.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                            String grantItem = plugin.getConfig().getString("Ranks." + rank + ".grantItem");
                            rankItem = getMaterialFromConfig(grantItem);
                            String rankDisplay = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Ranks." + rank + ".display"));
                            rankItemMeta.setDisplayName(rankDisplay);
                            List<String> lore = new ArrayList<>();
                            for (String itemLore : plugin.getConfig().getStringList("Grant.GrantMenu.Lore")) {
                                String newLore = "";
                                if (player.hasPermission(plugin.getConfig().getString("Ranks." + rank + ".grantPermission"))) {
                                    newLore = itemLore
                                            .replace("%player%", targetDisplay)
                                            .replace("%rank%", rankDisplay)
                                            .replace("%availability%", "&a&lClick to grant this rank.");
                                } else {
                                    newLore = itemLore
                                            .replace("%player%", targetDisplay)
                                            .replace("%rank%", rankDisplay)
                                            .replace("%availability%", "&c&lYou do not have permission to grant this rank.");
                                }
                                lore.add(ChatColor.translateAlternateColorCodes('&', newLore));
                            }
                            rankItemMeta.setLore(lore);
                            rankItem.setItemMeta(rankItemMeta);
                            grantInv.addItem(rankItem);
                            player.openInventory(grantInv);
                        }
                    }
                }
            }
        }
        return true;
    }

    public ItemStack getMaterialFromConfig(String name) {
        Optional<XMaterial> mat = XMaterial.matchXMaterial(name);
        return mat.map(XMaterial::parseItem).orElse(null);
    }

    public void openDurationGUI(Player player, OfflinePlayer target) {
        player.closeInventory();
        new BukkitRunnable() {
            public void run() {
                targetName = target.getName();
                for (String rank : plugin.ranks) {
                    if (plugin.pData.config.getString(target.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank.toUpperCase())) {
                        targetColor = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                    }
                }
                targetDisplay = targetColor + targetName;
                Inventory durationInv = Bukkit.createInventory(player, 27, ChatColor.DARK_GRAY + "Select A Duration");
                ItemStack filler = XMaterial.GRAY_STAINED_GLASS_PANE.parseItem();
                ItemStack durItem = XMaterial.BEDROCK.parseItem();
                for (int i = 0; i < 9; i++) {
                    durationInv.setItem(i, filler);
                }
                ItemMeta durItemMeta = durItem.getItemMeta();
                for (String duration : plugin.getConfig().getConfigurationSection("Grant.DurationMenu.Items").getKeys(false)) {
                    String configItem = plugin.getConfig().getString("Grant.DurationMenu.Items." + duration + ".Item");
                    durItem = getMaterialFromConfig(configItem);
                    String durDisplay =  ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Grant.DurationMenu.Items." + duration + ".Name"));
                    durItemMeta.setDisplayName(durDisplay);
                    List<String> lore = new ArrayList<>();
                    for (String itemLore : plugin.getConfig().getStringList("Grant.DurationMenu.Lore")) {
                        String newLore = itemLore
                                .replace("%player%", targetDisplay)
                                .replace("%time%", durDisplay);
                        lore.add(ChatColor.translateAlternateColorCodes('&', newLore));
                    }
                    durItemMeta.setLore(lore);
                    durItem.setItemMeta(durItemMeta);
                    durationInv.setItem(plugin.getConfig().getInt("Grant.DurationMenu.Items." + duration + ".Slot"), durItem);
                    player.openInventory(durationInv);
                }
            }
        }.runTaskLater(plugin, 5);
    }

    public void openReasonGUI(Player player, OfflinePlayer target) {
        player.closeInventory();
        new BukkitRunnable() {
            public void run() {
                targetName = target.getName();
                for (String rank : plugin.ranks) {
                    if (plugin.pData.config.getString(target.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank.toUpperCase())) {
                        targetColor = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                    }
                }
                targetDisplay = targetColor + targetName;
                Inventory reasonInv = Bukkit.createInventory(player, 18, ChatColor.DARK_GRAY + "Select A Reason");
                ItemStack filler = XMaterial.GRAY_STAINED_GLASS_PANE.parseItem();
                ItemStack rsItem = XMaterial.BEDROCK.parseItem();
                for (int i = 0; i < 9; i++) {
                    reasonInv.setItem(i, filler);
                }
                ItemMeta rsItemMeta = rsItem.getItemMeta();
                for (String reason : plugin.getConfig().getConfigurationSection("Grant.ReasonMenu.Items").getKeys(false)) {
                    String configItem = plugin.getConfig().getString("Grant.ReasonMenu.Items." + reason + ".Item");
                    rsItem = getMaterialFromConfig(configItem);
                    String rsDisplay = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Grant.ReasonMenu.Items." + reason + ".Name"));
                    rsItemMeta.setDisplayName(rsDisplay);
                    List<String> lore = new ArrayList<>();
                    for (String itemLore : plugin.getConfig().getStringList("Grant.ReasonMenu.Lore")) {
                        String newLore = itemLore
                                .replace("%player%", targetDisplay)
                                .replace("%reason%", rsDisplay);
                        lore.add(ChatColor.translateAlternateColorCodes('&', newLore));
                    }
                    rsItemMeta.setLore(lore);
                    rsItem.setItemMeta(rsItemMeta);
                    reasonInv.setItem(plugin.getConfig().getInt("Grant.ReasonMenu.Items." + reason + ".Slot"), rsItem);
                    player.openInventory(reasonInv);
                }
            }
        }.runTaskLater(plugin, 5);
    }

    public void openConfirmationGUI(Player player, OfflinePlayer target) {
        player.closeInventory();
        new BukkitRunnable() {
            public void run() {
                targetName = target.getName();
                for (String rank : plugin.ranks) {
                    if (plugin.pData.config.getString(target.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank.toUpperCase())) {
                        targetColor = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
                    }
                }
                targetDisplay = targetColor + targetName;
                Inventory confirmInv = Bukkit.createInventory(player, 45, ChatColor.DARK_GRAY + "Confirmation");
                ItemStack yesItem = XMaterial.GREEN_TERRACOTTA.parseItem();
                ItemStack noItem = XMaterial.RED_TERRACOTTA.parseItem();
                ItemMeta yesItemMeta = yesItem.getItemMeta();
                ItemMeta noItemMeta = noItem.getItemMeta();
                String rank = plugin.grantRank.get(player);
                String duration = plugin.grantDuration.get(player);
                String reason = plugin.grantReason.get(player);
                String yesDisplay = ChatColor.translateAlternateColorCodes('&', "&2&lConfirm Grant");
                String noDisplay = ChatColor.translateAlternateColorCodes('&', "&c&lCancel Grant");
                yesItemMeta.setDisplayName(yesDisplay);
                noItemMeta.setDisplayName(noDisplay);
                List<String> lore = new ArrayList<>();
                for (String itemLore : plugin.getConfig().getStringList("Grant.ConfirmationMenu.Lore")) {
                    String newLore = itemLore
                            .replace("%player%", targetDisplay)
                            .replace("%rank%", rank)
                            .replace("%duration%", duration)
                            .replace("%reason%", reason);
                    lore.add(ChatColor.translateAlternateColorCodes('&', newLore));
                }
                yesItemMeta.setLore(lore);
                noItemMeta.setLore(lore);
                yesItem.setItemMeta(yesItemMeta);
                noItem.setItemMeta(noItemMeta);
                for (int i = 10; i < 13; i++) {
                    confirmInv.setItem(i, yesItem);
                }
                for (int i = 19; i < 22; i++) {
                    confirmInv.setItem(i, yesItem);
                }
                for (int i = 28; i < 31; i++) {
                    confirmInv.setItem(i, yesItem);
                }
                for (int i = 14; i < 17; i++) {
                    confirmInv.setItem(i, noItem);
                }
                for (int i = 23; i < 26; i++) {
                    confirmInv.setItem(i, noItem);
                }
                for (int i = 32; i < 35; i++) {
                    confirmInv.setItem(i, noItem);
                }
                player.openInventory(confirmInv);
            }
        }.runTaskLater(plugin, 5);
    }
}
