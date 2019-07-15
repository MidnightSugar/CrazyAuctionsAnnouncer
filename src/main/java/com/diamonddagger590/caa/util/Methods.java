package com.diamonddagger590.caa.util;

import com.diamonddagger590.caa.discordsrv.DiscordSRVManager;
import com.diamonddagger590.caa.main.CrazyAuctionsAnnouncer;
import com.diamonddagger590.caa.minecord.MineCordHandler;
import com.diamonddagger590.caa.uchat.UChatHandler;
import me.badbones69.crazyenchantments.api.CEBook;
import me.badbones69.crazyenchantments.api.CrazyEnchantments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Methods {

	private static String getMinecraftVersion() {
		return Bukkit.getServer().getVersion();
	}
	
	public static String convertName(Material m) {
		String[] chars = m.toString().split("");
		String itemType = "";
		boolean first = true;
		boolean capNext = false;
		for(String s : chars) {
			if(first) {
				itemType += s;
				first = false;
				continue;
			}
			else {
				if(s.equals("_")) {
					itemType += " ";
					capNext = true;
					continue;
				}
				if(capNext) {
					s = s.toUpperCase();
					capNext = false;
				}
				else {
					s = s.toLowerCase();
				}
				itemType += s;
				continue;
			}
		}
		return itemType;
	}

    public static String color(String msg){
	return ChatColor.translateAlternateColorCodes('&', msg);
  }

	public static String getItemType(ItemStack item) {
		String itemType = com.diamonddagger590.caa.util.Methods.convertName(item.getType());
		if(Bukkit.getPluginManager().isPluginEnabled("CrazyEnchantments") && CrazyEnchantments.getInstance().isEnchantmentBook(item)) {
			CEBook book = CrazyEnchantments.getInstance().convertToCEBook(item);
			String power = me.badbones69.crazyenchantments.Methods.getPower(book.getPower());
			itemType = book.getEnchantment().getName() + " " + power;
		}
		return itemType;
	}

	public static String getDisplayName(ItemStack item) {
		if(item.getItemMeta().hasDisplayName()){
			return item.getItemMeta().getDisplayName();
		}
		else if(item.getItemMeta().hasLocalizedName()){
			return item.getItemMeta().getLocalizedName();
		}
		else{
			return Methods.convertName(item.getType());
		}
	}

	// Doesn't translate %Item% in this method, since %Item% could be translated into normal formatting, or hover item.
	public static String translateMessage(AuctionType type, Player player, ItemStack item, double money) {

		String displayName = Methods.getDisplayName(item);
		int amount = item.getAmount();

		String message = CrazyAuctionsAnnouncer.getConfigFile().getString("Messages." + type.getName());

		message = CrazyAuctionsAnnouncer.getPluginPrefix() + message;

		message = message.replace("%Player%", player.getDisplayName())
				.replace("%Money%", CrazyAuctionsAnnouncer.economy.format(money))
				.replace("%Amount%", Integer.toString(amount))
				.replace("%DisplayName%", displayName);

		return message;
	}

}
