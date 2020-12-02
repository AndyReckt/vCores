package net.vectromc.vstaffutils.listeners;

import net.vectromc.vnitrogen.vNitrogen;
import net.vectromc.vstaffutils.utils.Utils;
import net.vectromc.vstaffutils.utils.XMaterial;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ReportGuiClickListener implements Listener {

    private vStaffUtils plugin;
    private vNitrogen nitrogen;

    public ReportGuiClickListener() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
    }

    @EventHandler
    public void onGuiClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equalsIgnoreCase(ChatColor.RED + "Report")) {
            if (plugin.reporting.contains(player.getUniqueId())) {
                if (event.getCurrentItem() == null) {
                    event.setCancelled(true);
                } else {
                    Player target = plugin.report_set.get(player.getUniqueId());
                    String world = player.getWorld().getName();
                    String reason;
                    if (event.getCurrentItem().getType() == XMaterial.DIAMOND_SWORD.parseMaterial()) {
                        nitrogen.setPlayerColor(player);
                        nitrogen.setTargetColor(target);
                        reason = "Hacking";
                        player.closeInventory();
                        Utils.sendMessage(player, plugin.getConfig().getString("ReportFormat.PlayerFormat").replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason));
                        for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                            if (onlineStaff.hasPermission(plugin.getConfig().getString("ReportNotifyPermission"))) {
                                onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ReportFormat.StaffFormat").replaceAll("%world%", world).replaceAll("%player%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                            }
                        }
                        plugin.report_set.remove(player.getUniqueId());
                        plugin.reporting.remove(player.getUniqueId());
                    } else if (event.getCurrentItem().getType() == XMaterial.WRITABLE_BOOK.parseMaterial()) {
                        reason = "Spam";
                        player.closeInventory();
                        Utils.sendMessage(player, plugin.getConfig().getString("ReportFormat.PlayerFormat").replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason));
                        for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                            if (onlineStaff.hasPermission(plugin.getConfig().getString("ReportNotifyPermission"))) {
                                onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ReportFormat.StaffFormat").replaceAll("%world%", world).replaceAll("%player%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                            }
                        }
                        plugin.report_set.remove(player.getUniqueId());
                        plugin.reporting.remove(player.getUniqueId());
                    } else if (event.getCurrentItem().getType() == XMaterial.COBWEB.parseMaterial()) {
                        reason = "Toxicity";
                        player.closeInventory();
                        Utils.sendMessage(player, plugin.getConfig().getString("ReportFormat.PlayerFormat").replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason));
                        for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                            if (onlineStaff.hasPermission(plugin.getConfig().getString("ReportNotifyPermission"))) {
                                onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ReportFormat.StaffFormat").replaceAll("%world%", world).replaceAll("%player%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                            }
                        }
                        plugin.report_set.remove(player.getUniqueId());
                        plugin.reporting.remove(player.getUniqueId());
                    } else if (event.getCurrentItem().getType() == XMaterial.RED_WOOL.parseMaterial()) {
                        reason = "Racism/Sexism";
                        player.closeInventory();
                        Utils.sendMessage(player, plugin.getConfig().getString("ReportFormat.PlayerFormat").replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason));
                        for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                            if (onlineStaff.hasPermission(plugin.getConfig().getString("ReportNotifyPermission"))) {
                                onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ReportFormat.StaffFormat").replaceAll("%world%", world).replaceAll("%player%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                            }
                        }
                        plugin.report_set.remove(player.getUniqueId());
                        plugin.reporting.remove(player.getUniqueId());
                    } else if (event.getCurrentItem().getType() == XMaterial.LADDER.parseMaterial()) {
                        reason = "Exploit/Bug/Glitch abuse";
                        player.closeInventory();
                        Utils.sendMessage(player, plugin.getConfig().getString("ReportFormat.PlayerFormat").replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason));
                        for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                            if (onlineStaff.hasPermission(plugin.getConfig().getString("ReportNotifyPermission"))) {
                                onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ReportFormat.StaffFormat").replaceAll("%world%", world).replaceAll("%player%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                            }
                        }
                        plugin.report_set.remove(player.getUniqueId());
                        plugin.reporting.remove(player.getUniqueId());
                    } else if (event.getCurrentItem().getType() == XMaterial.BEDROCK.parseMaterial()) {
                        reason = "Threats";
                        player.closeInventory();
                        Utils.sendMessage(player, plugin.getConfig().getString("ReportFormat.PlayerFormat").replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason));
                        for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                            if (onlineStaff.hasPermission(plugin.getConfig().getString("ReportNotifyPermission"))) {
                                onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ReportFormat.StaffFormat").replaceAll("%world%", world).replaceAll("%player%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                            }
                        }
                        plugin.report_set.remove(player.getUniqueId());
                        plugin.reporting.remove(player.getUniqueId());
                    } else if (event.getCurrentItem().getType() == XMaterial.BOOK.parseMaterial()) {
                        reason = "Advertising";
                        player.closeInventory();
                        Utils.sendMessage(player, plugin.getConfig().getString("ReportFormat.PlayerFormat").replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason));
                        for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                            if (onlineStaff.hasPermission(plugin.getConfig().getString("ReportNotifyPermission"))) {
                                onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ReportFormat.StaffFormat").replaceAll("%world%", world).replaceAll("%player%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                            }
                        }
                        plugin.report_set.remove(player.getUniqueId());
                        plugin.reporting.remove(player.getUniqueId());
                    } else if (event.getCurrentItem().getType() == XMaterial.WHITE_BANNER.parseMaterial()) {
                        reason = "Innapropriate Name/Skin";
                        player.closeInventory();
                        Utils.sendMessage(player, plugin.getConfig().getString("ReportFormat.PlayerFormat").replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason));
                        for (Player onlineStaff : Bukkit.getOnlinePlayers()) {
                            if (onlineStaff.hasPermission(plugin.getConfig().getString("ReportNotifyPermission"))) {
                                onlineStaff.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("ReportFormat.StaffFormat").replaceAll("%world%", world).replaceAll("%player%", player.getDisplayName()).replaceAll("%target%", target.getDisplayName()).replaceAll("%reason%", reason)));
                            }
                        }
                        plugin.report_set.remove(player.getUniqueId());
                        plugin.reporting.remove(player.getUniqueId());
                    } else if (event.getCurrentItem().getType() == XMaterial.OAK_SIGN.parseMaterial()) {
                        player.closeInventory();
                        Utils.sendMessage(player, "&ePlease enter a &6custom &ereason in chat:");
                        plugin.custom_reason.add(player.getUniqueId());
                    }
                }
            }
        }
    }
}
