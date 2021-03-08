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

public class PunishCommand implements CommandExecutor, Listener {

    private vNitrogen plugin;

    String targetName = "";
    String targetColor = "";
    String targetDisplay = "";

    String clickedType = "";
    String clickedDuration = "";
    String clickedReason = "";
    String clickedSilent = "";

    public PunishCommand() {
        plugin = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == XMaterial.AIR.parseMaterial()) {
            return;
        } else {
            if (!event.getCurrentItem().getItemMeta().hasLore()) {
                event.setCancelled(true);
            } else {
                if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Choose A Punishment Type")) {
                    clickedType = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
                    for (String type : plugin.getConfig().getConfigurationSection("Punish.Types").getKeys(false)) {
                        if (plugin.getConfig().getString("Punish.Types." + type + ".ID").equals(clickedType)) {
                            Player player = (Player) event.getWhoClicked();
                            if (player.hasPermission(plugin.getConfig().getString("Punish.Types." + type + ".Permission"))) {
                                plugin.punishType.put(player, clickedType);
                                OfflinePlayer target = plugin.punishPlayer.get(player);
                                String warnID = plugin.getConfig().getString("Punish.Types.Warn.ID");
                                String kickID = plugin.getConfig().getString("Punish.Types.Kick.ID");
                                String blacklistID = plugin.getConfig().getString("Punish.Types.Blacklist.ID");
                                if (clickedType.equalsIgnoreCase(warnID) || clickedType.equalsIgnoreCase(kickID) || clickedType.equalsIgnoreCase(blacklistID)) {
                                    plugin.punishDuration.put(player, "Permanent");
                                    openReasonGUI(player, target);
                                } else {
                                    openDurationGUI(player, target);
                                }
                            } else {
                                event.setCancelled(true);
                            }
                        }
                    }
                } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Choose A Punishment Duration")) {
                    clickedDuration = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
                    for (String duration : plugin.getConfig().getConfigurationSection("Punish.Durations.Items").getKeys(false)) {
                        if (plugin.getConfig().getString("Punish.Durations.Items." + duration + ".ID").equals(clickedDuration)) {
                            Player player = (Player) event.getWhoClicked();
                            OfflinePlayer target = plugin.punishPlayer.get(player);
                            plugin.punishDuration.put(player, clickedDuration);
                            openReasonGUI(player, target);
                        }
                    }
                } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Choose A Punishment Reason")) {
                    clickedReason = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
                    for (String reason : plugin.getConfig().getConfigurationSection("Punish.Reasons.Items").getKeys(false)) {
                        if (plugin.getConfig().getString("Punish.Reasons.Items." + reason + ".ID").equals(clickedReason)) {
                            Player player = (Player) event.getWhoClicked();
                            OfflinePlayer target = plugin.punishPlayer.get(player);
                            plugin.punishReason.put(player, clickedReason);
                            openSilentGUI(player, target);
                        }
                    }
                } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Will It Be Silent?")) {
                    clickedSilent = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
                    for (String silent : plugin.getConfig().getConfigurationSection("Punish.Silent").getKeys(false)) {
                        if (plugin.getConfig().getString("Punish.Silent." + silent + ".ID").equals(clickedSilent)) {
                            Player player = (Player) event.getWhoClicked();
                            OfflinePlayer target = plugin.punishPlayer.get(player);
                            plugin.punishSilent.put(player, clickedSilent);
                            openConfirmationGUI(player, target);
                        }
                    }
                } else if (event.getView().getTitle().equalsIgnoreCase(ChatColor.DARK_GRAY + "Confirm This Punishment")) {
                    Player player = (Player) event.getWhoClicked();
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&2&lConfirm Punishment"))) {
                        OfflinePlayer target = plugin.punishPlayer.get(player);
                        String targetName = target.getName();
                        String targetColor = "";
                        plugin.setPlayerColor(player);
                        String type = plugin.punishType.get(player);
                        String duration = plugin.punishDuration.get(player);
                        String fnlDura = "";
                        switch (duration.toLowerCase()) {
                            case "10 seconds":
                                fnlDura = "10s";
                                break;
                            case "1 minute":
                                fnlDura = "1m";
                                break;
                            case "5 minutes":
                                fnlDura = "5m";
                                break;
                            case "30 minutes":
                                fnlDura = "30m";
                                break;
                            case "1 hour":
                                fnlDura = "1h";
                                break;
                            case "3 hours":
                                fnlDura = "3h";
                                break;
                            case "1 day":
                                fnlDura = "1d";
                                break;
                            case "1 week":
                                fnlDura = "1w";
                                break;
                            case "1 month":
                                fnlDura = "1mo";
                                break;
                            case "1 year":
                                fnlDura = "1y";
                                break;
                        }
                        String reason = plugin.punishReason.get(player);
                        String silent = plugin.punishSilent.get(player);
                        for (String ranks : plugin.getConfig().getConfigurationSection("Ranks").getKeys(false)) {
                            if (plugin.pData.config.getString(target.getUniqueId().toString() + ".Rank").equalsIgnoreCase(ranks)) {
                                targetColor = plugin.getConfig().getString("Ranks." + ranks + ".color");
                            }
                        }
                        targetDisplay = targetColor + targetName;
                        Utils.sendMessage(player, plugin.getConfig().getString("Punish.ConfirmPunishment")
                                .replace("%player%", targetDisplay)
                                .replace("%type%", type)
                                .replace("%duration%", duration)
                                .replace("%reason%", reason));
                        player.closeInventory();
                        plugin.punishPlayer.remove(player);
                        plugin.punishType.remove(player);
                        plugin.punishDuration.remove(player);
                        plugin.punishReason.remove(player);
                        plugin.punishSilent.remove(player);
                        if (duration.equalsIgnoreCase("Permanent")) {
                            if (silent.equalsIgnoreCase("Yes")) {
                                player.performCommand(type + " " + targetName + " -s " + reason);
                            } else {
                                player.performCommand(type + " " + targetName + " " + reason);
                            }
                        } else {
                            String newType = "";
                            if (type.equalsIgnoreCase("Ban")) {
                                newType = "Tempban";
                            } else if (type.equalsIgnoreCase("Mute")) {
                                newType = "Tempmute";
                            }
                            if (silent.equalsIgnoreCase("Yes")) {
                                player.performCommand(newType + " " + targetName + " " + fnlDura + " -s " + reason);
                            } else {
                                player.performCommand(newType + " " + targetName + " " + fnlDura + " " + reason);
                            }
                        }
                    } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&c&lCancel Punishment"))) {
                        Utils.sendMessage(player, plugin.getConfig().getString("Punish.CancelPunishment"));
                        player.closeInventory();
                        plugin.punishPlayer.remove(player);
                        plugin.punishType.remove(player);
                        plugin.punishDuration.remove(player);
                        plugin.punishReason.remove(player);
                        plugin.punishSilent.remove(player);
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
            if (!sender.hasPermission(plugin.getConfig().getString("Punish.Permission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission")
                        .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                        .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                if (args.length != 1) {
                    Utils.sendMessage(player, plugin.getConfig().getString("Punish.IncorrectUsage")
                            .replace("%server_prefix%", plugin.getConfig().getString("ServerPrefix"))
                            .replace("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                } else {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    plugin.punishPlayer.remove(player);
                    plugin.punishType.remove(player);
                    plugin.punishDuration.remove(player);
                    plugin.punishReason.remove(player);
                    plugin.punishSilent.remove(player);
                    openTypeGUI(player, target);
                    plugin.punishPlayer.put(player, target);
                }
            }
        }
        return true;
    }

    public ItemStack getMaterialFromConfig(String name) {
        Optional<XMaterial> mat = XMaterial.matchXMaterial(name);
        return mat.map(XMaterial::parseItem).orElse(null);
    }

    public void openTypeGUI(Player player, OfflinePlayer target) {
        targetName = target.getName();
        for (String rank : plugin.ranks) {
            if (plugin.pData.config.getString(target.getUniqueId().toString() + ".Rank").equalsIgnoreCase(rank.toUpperCase())) {
                targetColor = plugin.getConfig().getString("Ranks." + rank.toUpperCase() + ".color");
            }
        }
        targetDisplay = targetColor + targetName;
        Inventory typeInv = Bukkit.createInventory(player, 18, ChatColor.DARK_GRAY + "Choose A Punishment Type");
        ItemStack typeItem = XMaterial.BEDROCK.parseItem();
        ItemStack filler = XMaterial.GRAY_STAINED_GLASS_PANE.parseItem();
        for (int i = 0; i < 9; i++) {
            typeInv.setItem(i, filler);
        }
        ItemMeta typeItemMeta = typeItem.getItemMeta();
        for (String type : plugin.getConfig().getConfigurationSection("Punish.Types").getKeys(false)) {
            String configItem = plugin.getConfig().getString("Punish.Types." + type + ".Item");
            typeItem = getMaterialFromConfig(configItem);
            String displayName = plugin.getConfig().getString("Punish.Types." + type + ".Name");
            typeItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
            List<String> lore = new ArrayList<>();
            String newLore;
            for (String itemLore : plugin.getConfig().getStringList("Punish.Types." + type + ".Lore")) {
                newLore = itemLore
                        .replace("%player%", targetDisplay)
                        .replace("%availability%", "&a&lClick to select this type.");
                lore.add(ChatColor.translateAlternateColorCodes('&', newLore));
            }
            typeItemMeta.setLore(lore);
            typeItem.setItemMeta(typeItemMeta);
            int slot = plugin.getConfig().getInt("Punish.Types." + type + ".Slot");
            typeInv.setItem(slot, typeItem);
        }
        player.openInventory(typeInv);
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
                Inventory durInv = Bukkit.createInventory(player, 27, ChatColor.DARK_GRAY + "Choose A Punishment Duration");
                ItemStack durItem = XMaterial.BEDROCK.parseItem();
                ItemStack filler = XMaterial.GRAY_STAINED_GLASS_PANE.parseItem();
                for (int i = 0; i < 9; i++) {
                    durInv.setItem(i, filler);
                }
                ItemMeta durItemMeta = durItem.getItemMeta();
                for (String duration : plugin.getConfig().getConfigurationSection("Punish.Durations.Items").getKeys(false)) {
                    String configItem = plugin.getConfig().getString("Punish.Durations.Items." + duration + ".Item");
                    durItem = getMaterialFromConfig(configItem);
                    String displayName = plugin.getConfig().getString("Punish.Durations.Items." + duration + ".Name");
                    durItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
                    List<String> lore = new ArrayList<>();
                    String newLore;
                    for (String itemLore : plugin.getConfig().getStringList("Punish.Durations.Lore")) {
                        newLore = itemLore
                                .replace("%player%", targetDisplay)
                                .replace("%time%", ChatColor.translateAlternateColorCodes('&', displayName));
                        lore.add(ChatColor.translateAlternateColorCodes('&', newLore));
                    }
                    durItemMeta.setLore(lore);
                    durItem.setItemMeta(durItemMeta);
                    int slot = plugin.getConfig().getInt("Punish.Durations.Items." + duration + ".Slot");
                    durInv.setItem(slot, durItem);
                }
                player.openInventory(durInv);
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
                Inventory rsInv = Bukkit.createInventory(player, 18, ChatColor.DARK_GRAY + "Choose A Punishment Reason");
                ItemStack rsItem = XMaterial.BEDROCK.parseItem();
                ItemStack filler = XMaterial.GRAY_STAINED_GLASS_PANE.parseItem();
                for (int i = 0; i < 9; i++) {
                    rsInv.setItem(i, filler);
                }
                ItemMeta rsItemMeta = rsItem.getItemMeta();
                for (String reason : plugin.getConfig().getConfigurationSection("Punish.Reasons.Items").getKeys(false)) {
                    String configItem = plugin.getConfig().getString("Punish.Reasons.Items." + reason + ".Item");
                    rsItem = getMaterialFromConfig(configItem);
                    String displayName = plugin.getConfig().getString("Punish.Reasons.Items." + reason + ".Name");
                    rsItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
                    List<String> lore = new ArrayList<>();
                    String newLore;
                    for (String itemLore : plugin.getConfig().getStringList("Punish.Reasons.Lore")) {
                        newLore = itemLore
                                .replace("%player%", targetDisplay)
                                .replace("%reason%", ChatColor.translateAlternateColorCodes('&', displayName));
                        lore.add(ChatColor.translateAlternateColorCodes('&', newLore));
                    }
                    rsItemMeta.setLore(lore);
                    rsItem.setItemMeta(rsItemMeta);
                    int slot = plugin.getConfig().getInt("Punish.Reasons.Items." + reason + ".Slot");
                    rsInv.setItem(slot, rsItem);
                }
                player.openInventory(rsInv);
            }
        }.runTaskLater(plugin, 5);
    }

    public void openSilentGUI(Player player, OfflinePlayer target) {
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
                Inventory sInv = Bukkit.createInventory(player, 18, ChatColor.DARK_GRAY + "Will It Be Silent?");
                ItemStack sItem = XMaterial.BEDROCK.parseItem();
                ItemStack filler = XMaterial.GRAY_STAINED_GLASS_PANE.parseItem();
                for (int i = 0; i < 9; i++) {
                    sInv.setItem(i, filler);
                }
                ItemMeta sItemMeta = sItem.getItemMeta();
                for (String silent : plugin.getConfig().getConfigurationSection("Punish.Silent").getKeys(false)) {
                    String configItem = plugin.getConfig().getString("Punish.Silent." + silent + ".Item");
                    sItem = getMaterialFromConfig(configItem);
                    String displayName = plugin.getConfig().getString("Punish.Silent." + silent + ".Name");
                    sItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
                    List<String> lore = new ArrayList<>();
                    String newLore;
                    for (String itemLore : plugin.getConfig().getStringList("Punish.Silent." + silent + ".Lore")) {
                        newLore = itemLore
                                .replace("%player%", targetDisplay)
                                .replace("%reason%", ChatColor.translateAlternateColorCodes('&', displayName));
                        lore.add(ChatColor.translateAlternateColorCodes('&', newLore));
                    }
                    sItemMeta.setLore(lore);
                    sItem.setItemMeta(sItemMeta);
                    int slot = plugin.getConfig().getInt("Punish.Silent." + silent + ".Slot");
                    sInv.setItem(slot, sItem);
                }
                player.openInventory(sInv);
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
                Inventory confirmInv = Bukkit.createInventory(player, 45, ChatColor.DARK_GRAY + "Confirm This Punishment");
                ItemStack yesItem = XMaterial.GREEN_TERRACOTTA.parseItem();
                ItemStack noItem = XMaterial.RED_TERRACOTTA.parseItem();
                ItemMeta yesItemMeta = yesItem.getItemMeta();
                ItemMeta noItemMeta = noItem.getItemMeta();
                String type = plugin.punishType.get(player);
                String duration = plugin.punishDuration.get(player);
                String reason = plugin.punishReason.get(player);
                String silent = plugin.punishSilent.get(player);
                String silentStr;
                if (silent.equalsIgnoreCase("Yes")) {
                    silentStr = "&aYes";
                } else {
                    silentStr = "&cNo";
                }
                String yesDisplay = ChatColor.translateAlternateColorCodes('&', "&2&lConfirm Punishment");
                String noDisplay = ChatColor.translateAlternateColorCodes('&', "&c&lCancel Punishment");
                yesItemMeta.setDisplayName(yesDisplay);
                noItemMeta.setDisplayName(noDisplay);
                List<String> lore = new ArrayList<>();
                for (String itemLore : plugin.getConfig().getStringList("Punish.Confirmation.Lore")) {
                    String newLore = itemLore
                            .replace("%player%", targetDisplay)
                            .replace("%type%", type)
                            .replace("%duration%", duration)
                            .replace("%reason%", reason)
                            .replace("%silent%", silentStr);
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
