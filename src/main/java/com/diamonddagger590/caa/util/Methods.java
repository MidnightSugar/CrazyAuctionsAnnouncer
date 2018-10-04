package com.diamonddagger590.caa.util;

import com.diamonddagger590.caa.discordsrv.DiscordSRVManager;
import com.diamonddagger590.caa.main.Main;
import com.diamonddagger590.caa.minecord.MineCordHandler;
import com.diamonddagger590.caa.uchat.UChatHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Methods {

	public static void sendDiscordMessage(String discordMessage, String channel) {
		if(Bukkit.getPluginManager().isPluginEnabled("MineCordBot")){
			MineCordHandler.sendMessage(channel, discordMessage);
		}
		else if(Bukkit.getPluginManager().isPluginEnabled("UChat") || Bukkit.getPluginManager().isPluginEnabled("UltimateChat")){
			UChatHandler.sendMessage(channel, discordMessage);
		}
		else if(Bukkit.getPluginManager().isPluginEnabled("DiscordSRV")) {
			DiscordSRVManager.sendMessage(channel, discordMessage);
		}
	}

	public static String translateMessage(String message, Player p, long bid, int amount, String itemType, String aucType, String displayName) {
		for(String s : Main.getListHandler().getConfig().getConfigurationSection("Settings.BannedDisplayNameWords").getKeys(false)){
			if(message.contains(s)){
				message = message.replace("s", "***");
			}
		}
		return message.replace("%Player%", p.getDisplayName()).replace("%Money%", Long.toString(bid)).replace("%Amount%", Integer.toString(amount)).replace("%Item%", itemType)
				.replace("%AuctionType%", aucType).replace("%DisplayName%", displayName);
	}

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
	
}
