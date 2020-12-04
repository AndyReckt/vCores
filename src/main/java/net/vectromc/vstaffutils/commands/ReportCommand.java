package net.vectromc.vstaffutils.commands;

import net.vectromc.vbasic.vBasic;
import net.vectromc.vnitrogen.vNitrogen;
import net.vectromc.vstaffutils.utils.Utils;
import net.vectromc.vstaffutils.utils.XMaterial;
import net.vectromc.vstaffutils.vStaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ReportCommand implements CommandExecutor {

    private vStaffUtils plugin;
    private vNitrogen nitrogen;
    private vBasic basic;

    public ReportCommand() {
        plugin = vStaffUtils.getPlugin(vStaffUtils.class);
        nitrogen = vNitrogen.getPlugin(vNitrogen.class);
        basic = vBasic.getPlugin(vBasic.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, plugin.getConfig().getString("MustBePlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
        } else {
            Player player = (Player) sender;
            if (args.length == 0) {
                Utils.sendMessage(player, plugin.getConfig().getString("ReportIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    nitrogen.setPlayerColor(player);
                    nitrogen.setPlayerColor(target);
                    Inventory reportGui = Bukkit.createInventory(player, 18, ChatColor.RED + "Report");
                    ItemStack hacks = XMaterial.DIAMOND_SWORD.parseItem();
                    ItemStack spam = XMaterial.WRITABLE_BOOK.parseItem();
                    ItemStack toxicity = XMaterial.COBWEB.parseItem();
                    ItemStack racism_sexism = XMaterial.RED_WOOL.parseItem();
                    ItemStack exploiting = XMaterial.LADDER.parseItem();
                    ItemStack threats = XMaterial.BEDROCK.parseItem();
                    ItemStack advertising = XMaterial.BOOK.parseItem();
                    ItemStack innapskinname = XMaterial.WHITE_BANNER.parseItem();
                    ItemStack custom = XMaterial.OAK_SIGN.parseItem();
                    ItemStack filler = XMaterial.GRAY_STAINED_GLASS_PANE.parseItem();
                    ItemMeta hacksMeta = hacks.getItemMeta();
                    ItemMeta spamMeta = spam.getItemMeta();
                    ItemMeta toxicityMeta = toxicity.getItemMeta();
                    ItemMeta rsMeta = racism_sexism.getItemMeta();
                    ItemMeta explmeta = exploiting.getItemMeta();
                    ItemMeta threatsMeta = threats.getItemMeta();
                    ItemMeta advertMeta = advertising.getItemMeta();
                    ItemMeta isnMeta = innapskinname.getItemMeta();
                    ItemMeta customMeta = custom.getItemMeta();

                    hacksMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Combat Hacks"));
                    spamMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Spam"));
                    toxicityMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Toxicity"));
                    rsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Racism/Sexism"));
                    explmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Exploit/Bug/Glitch Abuse"));
                    threatsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Threats"));
                    advertMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Advertising"));
                    isnMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Innapropriate Skin/Name"));
                    customMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Custom Reason"));

                    hacks.setItemMeta(hacksMeta);
                    spam.setItemMeta(spamMeta);
                    toxicity.setItemMeta(toxicityMeta);
                    racism_sexism.setItemMeta(rsMeta);
                    exploiting.setItemMeta(explmeta);
                    threats.setItemMeta(threatsMeta);
                    advertising.setItemMeta(advertMeta);
                    innapskinname.setItemMeta(isnMeta);
                    custom.setItemMeta(customMeta);

                    for (int i = 0; i < 9; i++) {
                        reportGui.setItem(i, filler);
                    }
                    reportGui.setItem(9, hacks);
                    reportGui.setItem(10, spam);
                    reportGui.setItem(11, toxicity);
                    reportGui.setItem(12, racism_sexism);
                    reportGui.setItem(13, exploiting);
                    reportGui.setItem(14, threats);
                    reportGui.setItem(15, advertising);
                    reportGui.setItem(16, innapskinname);
                    reportGui.setItem(17, custom);

                    player.openInventory(reportGui);
                    plugin.reporting.add(player.getUniqueId());
                    plugin.report_set.put(player.getUniqueId(), target);
                } else {
                    Utils.sendMessage(player, plugin.getConfig().getString("ReportInvalidPlayer").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
                }
            } else {
                Utils.sendMessage(player, plugin.getConfig().getString("ReportIncorrectUsage").replaceAll("%server_prefix%", plugin.getConfig().getString("ServerPrefix")).replaceAll("%plugin_prefix%", plugin.getConfig().getString("PluginPrefix")));
            }
        }
        return true;
    }
}
