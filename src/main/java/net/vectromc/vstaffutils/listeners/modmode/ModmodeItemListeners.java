package net.vectromc.vstaffutils.listeners.modmode;

import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import net.vectromc.vstaffutils.utils.Utils;
import net.vectromc.vstaffutils.utils.XMaterial;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ModmodeItemListeners implements Listener {

    private vStaffUtils plugin;
    private vNitrogen nitrogen;
    private vBasic basic;

    public ModmodeItemListeners() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
        basic = vBasic.getPlugin(vBasic.class);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (plugin.modmode.contains(player.getUniqueId())) {
            if (player.getInventory().getItemInHand().getType() == XMaterial.COMPASS.parseMaterial()) {
                double X = player.getLocation().getDirection().getX() * 7.0;
                double Z = player.getLocation().getDirection().getZ() * 7.0;
                double height = 1.35;
                org.bukkit.util.Vector unitVector = new org.bukkit.util.Vector(X, height, Z);
                unitVector = unitVector.normalize();
                player.setVelocity(unitVector.multiply(1.65));
                player.setSprinting(true);
            }
        }
    }

    public ArrayList<String> player_lore = new ArrayList<>();

    @EventHandler
    public void onlinePlayers(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (plugin.modmode.contains(player.getUniqueId())) {
            if (player.getInventory().getItemInHand().getType() == XMaterial.CLOCK.parseMaterial()) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                    plugin.player_list.clear();
                    plugin.player_list.addAll(Bukkit.getOnlinePlayers());
                    Inventory onlinePlayers = Bukkit.createInventory(player, 54, ChatColor.YELLOW + "Online Players");
                    for (int i = 0; i < plugin.player_list.size(); i++) {
                        Player target = plugin.player_list.get(i);
                        nitrogen.setPlayerColor(target);
                        ItemStack skull = XMaterial.PLAYER_HEAD.parseItem();
                        ItemMeta skullMeta = skull.getItemMeta();
                        skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', target.getDisplayName()));
                        player_lore.add(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------------"));
                        nitrogen.setPlayerPrefix(target);
                        player_lore.add(ChatColor.translateAlternateColorCodes('&', "&eDisplay: " + target.getDisplayName()));
                        player_lore.add(ChatColor.translateAlternateColorCodes('&', "&eServer: &6" + target.getWorld().getName()));
                        player_lore.add(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------------"));
                        nitrogen.setPlayerColor(target);
                        skullMeta.setLore(player_lore);
                        skull.setItemMeta(skullMeta);
                        onlinePlayers.addItem(skull);
                        onlinePlayers.setItem(i, skull);
                        player_lore.clear();
                    }
                    player.openInventory(onlinePlayers);
                }
            }
        }
    }

    @EventHandler
    public void onListClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.YELLOW + "Online Players")) {
            if (event.getCurrentItem() == null) {
                return;
            } else {
                event.setCancelled(true);
            }
        }
    }

    ArrayList<String> staff_lore = new ArrayList<>();

    @EventHandler
    public void onlineStaff(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (plugin.modmode.contains(player.getUniqueId())) {
            if (player.getInventory().getItemInHand().getType() == XMaterial.PAPER.parseMaterial()) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                    Inventory onlinePlayers = Bukkit.createInventory(player, 54, ChatColor.YELLOW + "Online Staff");
                    for (Player target : Bukkit.getOnlinePlayers()) {
                        if (target.hasPermission(plugin.getConfig().getString("OnlineStaffPermission"))) {
                            nitrogen.setPlayerColor(target);
                            ItemStack skull = XMaterial.PLAYER_HEAD.parseItem();
                            ItemMeta skullMeta = skull.getItemMeta();
                            skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', target.getDisplayName()));
                            staff_lore.add(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------------"));
                            if (plugin.vanished.contains(target.getUniqueId())) {
                                staff_lore.add(ChatColor.translateAlternateColorCodes('&', "&eVanished: &aYes"));
                            } else {
                                staff_lore.add(ChatColor.translateAlternateColorCodes('&', "&eVanished: &cNo"));
                            }
                            if (plugin.modmode.contains(target.getUniqueId())) {
                                staff_lore.add(ChatColor.translateAlternateColorCodes('&', "&eModmoded: &aYes"));
                            } else {
                                staff_lore.add(ChatColor.translateAlternateColorCodes('&', "&eModmoded: &cNo"));
                            }
                            staff_lore.add(ChatColor.translateAlternateColorCodes('&', "&7 "));
                            nitrogen.setPlayerPrefix(target);
                            staff_lore.add(ChatColor.translateAlternateColorCodes('&', "&eDisplay: " + target.getDisplayName()));
                            staff_lore.add(ChatColor.translateAlternateColorCodes('&', "&eServer: &6" + target.getWorld().getName()));
                            staff_lore.add(ChatColor.translateAlternateColorCodes('&', "&7&m---------------------------"));
                            nitrogen.setPlayerColor(target);
                            skullMeta.setLore(staff_lore);
                            skull.setItemMeta(skullMeta);
                            onlinePlayers.addItem(skull);
                            staff_lore.clear();
                        }
                    }
                    player.openInventory(onlinePlayers);
                }
            }
        }
    }

    @EventHandler
    public void onStaffClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.YELLOW + "Online Staff")) {
            if (event.getCurrentItem() == null) {
                return;
            } else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void vanish(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (plugin.modmode.contains(player.getUniqueId())) {
            Inventory playerInventory = player.getInventory();

            ItemStack vanishOn = XMaterial.LIME_DYE.parseItem();
            ItemStack vanishOff = XMaterial.GRAY_DYE.parseItem();

            ItemMeta vanishOnName = vanishOn.getItemMeta();
            ItemMeta vanishOffName = vanishOff.getItemMeta();

            vanishOnName.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&a&lBecome Visible"));
            vanishOffName.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&lBecome Invisible"));

            vanishOn.setItemMeta(vanishOnName);
            vanishOff.setItemMeta(vanishOffName);

            nitrogen.setPlayerColor(player);

            if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                if (player.getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&7&lBecome Invisible"))) {
                    plugin.vanished.add(player.getUniqueId());
                    playerInventory.setItem(7, vanishOn);
                    Utils.sendMessage(player, plugin.getConfig().getString("VanishOnSelf"));
                    for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                        if (!onlinePlayers.hasPermission(plugin.getConfig().getString("VanishPermission"))) {
                            onlinePlayers.hidePlayer(player);
                        }
                        if (basic.toggle_staff_alerts.contains(onlinePlayers.getUniqueId())) {
                            onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.VanishOnSelf").replaceAll("%player%", player.getDisplayName())));
                        }
                    }
                } else if (player.getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&a&lBecome Visible"))) {
                    plugin.vanished.remove(player.getUniqueId());
                    playerInventory.setItem(7, vanishOff);
                    Utils.sendMessage(player, plugin.getConfig().getString("VanishOffSelf"));
                    for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                        onlinePlayers.showPlayer(player);
                        if (basic.toggle_staff_alerts.contains(onlinePlayers.getUniqueId())) {
                            onlinePlayers.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("StaffAlerts.VanishOffSelf").replaceAll("%player%", player.getDisplayName())));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInvView(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (plugin.modmode.contains(player.getUniqueId())) {
            Player target = (Player) event.getRightClicked();
            if (player.getInventory().getItemInHand().getType() == XMaterial.BOOK.parseMaterial()) {
                plugin.invsee_inventory.put(target.getUniqueId(), target.getInventory().getContents());
                plugin.invsee_armor.put(target.getUniqueId(), target.getInventory().getArmorContents());
                Inventory targetInv = Bukkit.createInventory(player, 45, ChatColor.GREEN + "Inventory View");
                targetInv.setContents(target.getInventory().getContents());
                targetInv.setItem(36, target.getInventory().getHelmet());
                targetInv.setItem(37, target.getInventory().getChestplate());
                targetInv.setItem(38, target.getInventory().getLeggings());
                targetInv.setItem(39, target.getInventory().getBoots());
                targetInv.setItem(40, target.getInventory().getItemInHand());
                player.openInventory(targetInv);
            }
        }
    }

    @EventHandler
    public void onGuiClick(InventoryClickEvent event) {
        Entity player = event.getWhoClicked();
        if (player instanceof Player) {
            if (event.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Inventory View")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFreeze(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (plugin.modmode.contains(player.getUniqueId())) {
            Player target = (Player) event.getRightClicked();
            if (player.getInventory().getItemInHand().getType() == XMaterial.PACKED_ICE.parseMaterial()) {
                player.performCommand("freeze " + target.getName());
            }
        }
    }
}
