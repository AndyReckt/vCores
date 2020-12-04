package net.vectromc.vstaffutils.commands;

import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import net.vectromc.vstaffutils.utils.Utils;
import net.vectromc.vstaffutils.utils.XMaterial;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ModmodeCommand implements CommandExecutor {

    private vStaffUtils plugin;
    private vNitrogen nitrogen;
    private vBasic basic;

    public ModmodeCommand() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
        basic = vBasic.getPlugin(vBasic.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            if (!sender.hasPermission(plugin.getConfig().getString("ModmodePermission"))) {
                Utils.sendMessage(sender, plugin.getConfig().getString("NoPermission").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else {
                Player player = (Player) sender;
                ItemStack teleporter = new ItemStack(Material.COMPASS);
                ItemStack onlineplayers = XMaterial.CLOCK.parseItem();
                ItemStack onlinestaff = new ItemStack(Material.PAPER);
                ItemStack vanishOn = XMaterial.LIME_DYE.parseItem();
                ItemStack vanishOff = XMaterial.GRAY_DYE.parseItem();
                ItemStack inventoryViewer = new ItemStack(Material.BOOK);
                ItemStack freeze = new ItemStack(Material.PACKED_ICE);

                ItemMeta teleporterName = teleporter.getItemMeta();
                ItemMeta onlineplayersName = onlineplayers.getItemMeta();
                ItemMeta onlinestaffName = onlinestaff.getItemMeta();
                ItemMeta vanishOnName = vanishOn.getItemMeta();
                ItemMeta vanishOffName = vanishOff.getItemMeta();
                ItemMeta inventoryViewerName = inventoryViewer.getItemMeta();
                ItemMeta freezeName = freeze.getItemMeta();

                teleporterName.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&2&lTeleporter"));
                onlineplayersName.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lOnline Players"));
                onlinestaffName.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lOnline Staff"));
                vanishOnName.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lBecome Visible"));
                vanishOffName.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&lBecome Invisible"));
                inventoryViewerName.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lInventory Viewer"));
                freezeName.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lFreeze Player"));

                teleporter.setItemMeta(teleporterName);
                onlineplayers.setItemMeta(onlineplayersName);
                onlinestaff.setItemMeta(onlinestaffName);
                vanishOn.setItemMeta(vanishOnName);
                vanishOff.setItemMeta(vanishOffName);
                inventoryViewer.setItemMeta(inventoryViewerName);
                freeze.setItemMeta(freezeName);
                if (args.length == 0) {
                    if (plugin.modmode.contains(player.getUniqueId())) {
                        nitrogen.setPlayerColor(player);
                        plugin.modmode.remove(player.getUniqueId());
                        Utils.sendMessage(player, plugin.getConfig().getString("ModmodeOffSelf"));
                        for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                            if (basic.toggle_staff_alerts.contains(onlineStaff.getUniqueId())) {
                                onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.ModmodeOffSelf").replaceAll("%player%", player.getDisplayName())));
                            }
                        }
                        player.setAllowFlight(false);
                        player.setFlying(false);
                        player.getInventory().clear();
                        player.getInventory().setContents(plugin.staff_inventory.get(player.getUniqueId()));
                        player.getInventory().setArmorContents(plugin.staff_armor.get(player.getUniqueId()));
                        double health = plugin.health_level.get(player.getUniqueId());
                        int food = plugin.food_level.get(player.getUniqueId());
                        player.setHealth(health);
                        player.setFoodLevel(food);
                    } else {
                        nitrogen.setPlayerColor(player);
                        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                        plugin.modmode.add(player.getUniqueId());
                        Utils.sendMessage(player, plugin.getConfig().getString("ModmodeOnSelf"));
                        for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                            if (basic.toggle_staff_alerts.contains(onlineStaff.getUniqueId())) {
                                onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.ModmodeOnSelf").replaceAll("%player%", player.getDisplayName())));
                            }
                        }
                        player.setAllowFlight(true);
                        player.setFlying(true);
                        player.setHealth(20);
                        player.setFoodLevel(20);
                        plugin.staff_inventory.put(player.getUniqueId(), player.getInventory().getContents());
                        plugin.staff_armor.put(player.getUniqueId(), player.getInventory().getArmorContents());
                        plugin.health_level.put(player.getUniqueId(), player.getHealth());
                        plugin.food_level.put(player.getUniqueId(), player.getFoodLevel());
                        player.getInventory().clear();
                        player.getInventory().setHelmet(new ItemStack(Material.AIR));
                        player.getInventory().setChestplate(new ItemStack(Material.AIR));
                        player.getInventory().setLeggings(new ItemStack(Material.AIR));
                        player.getInventory().setBoots(new ItemStack(Material.AIR));
                        Inventory playerInventory = player.getInventory();
                        playerInventory.setItem(0, teleporter);
                        playerInventory.setItem(1, inventoryViewer);
                        playerInventory.setItem(2, onlineplayers);
                        playerInventory.setItem(8, onlinestaff);
                        playerInventory.setItem(6, freeze);
                        if (plugin.vanished.contains(player.getUniqueId())) {
                            playerInventory.setItem(7, vanishOn);
                        } else {
                            playerInventory.setItem(7, vanishOff);
                        }
                    }
                } else if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (plugin.modmode.contains(target.getUniqueId())) {
                            nitrogen.setPlayerColor(player);
                            nitrogen.setPlayerColor(target);
                            plugin.modmode.remove(target.getUniqueId());
                            Utils.sendMessage(player, plugin.getConfig().getString("ModmodeOffOthers").replaceAll("%target%", target.getDisplayName()));
                            Utils.sendTargetMessage(target, plugin.getConfig().getString("ModmodeOffSelf"));
                            for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                                if (basic.toggle_staff_alerts.contains(onlineStaff.getUniqueId())) {
                                    onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.ModmodeOffOthers").replaceAll("%player%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName())));
                                }
                            }
                            target.setAllowFlight(false);
                            target.setFlying(false);
                            target.getInventory().clear();
                            target.getInventory().setContents(plugin.staff_inventory.get(target.getUniqueId()));
                            target.getInventory().setArmorContents(plugin.staff_armor.get(target.getUniqueId()));
                            double health = plugin.health_level.get(target.getUniqueId());
                            int food = plugin.food_level.get(target.getUniqueId());
                            target.setHealth(health);
                            target.setFoodLevel(food);
                        } else {
                            nitrogen.setPlayerColor(player);
                            nitrogen.setPlayerColor(target);
                            target.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                            plugin.modmode.add(target.getUniqueId());
                            Utils.sendMessage(player, plugin.getConfig().getString("ModmodeOnOthers").replaceAll("%target%", target.getDisplayName()));
                            Utils.sendTargetMessage(target, plugin.getConfig().getString("ModmodeOnSelf"));
                            for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                                if (basic.toggle_staff_alerts.contains(onlineStaff.getUniqueId())) {
                                    onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.ModmodeOnOthers").replaceAll("%player%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName())));
                                }
                            }
                            target.setAllowFlight(true);
                            target.setFlying(true);
                            target.setHealth(20);
                            target.setFoodLevel(20);
                            plugin.staff_inventory.put(target.getUniqueId(), target.getInventory().getContents());
                            plugin.staff_armor.put(target.getUniqueId(), target.getInventory().getArmorContents());
                            plugin.health_level.put(target.getUniqueId(), target.getHealth());
                            plugin.food_level.put(target.getUniqueId(), target.getFoodLevel());
                            target.getInventory().clear();
                            target.getInventory().setHelmet(new ItemStack(Material.AIR));
                            target.getInventory().setChestplate(new ItemStack(Material.AIR));
                            target.getInventory().setLeggings(new ItemStack(Material.AIR));
                            target.getInventory().setBoots(new ItemStack(Material.AIR));

                            Inventory targetInventory = target.getInventory();
                            targetInventory.setItem(0, teleporter);
                            targetInventory.setItem(1, inventoryViewer);
                            targetInventory.setItem(2, onlineplayers);
                            targetInventory.setItem(8, onlinestaff);
                            targetInventory.setItem(6, freeze);
                            if (plugin.vanished.contains(target.getUniqueId())) {
                                targetInventory.setItem(7, vanishOn);
                            } else {
                                targetInventory.setItem(7, vanishOff);
                            }
                        }
                    } else {
                        Utils.sendMessage(player, plugin.getConfig().getString("ModmodeInvalidPlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                    }
                } else {
                    Utils.sendMessage(player, plugin.getConfig().getString("ModmodeIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                }
            }
        }
        return true;
    }
}
