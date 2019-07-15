package com.diamonddagger590.caa.discordsrv;

import com.diamonddagger590.caa.main.CrazyAuctionsAnnouncer;
import com.diamonddagger590.caa.minecord.MineCordHandler;
import com.diamonddagger590.caa.uchat.UChatHandler;
import com.diamonddagger590.caa.util.AuctionType;
import github.scarsz.discordsrv.util.DiscordUtil;
import org.bukkit.Bukkit;

public class DiscordSRVManager {

	public static boolean canAnnounceDiscord(AuctionType type) {
		if (!CrazyAuctionsAnnouncer.getConfigFile().getBoolean("Discord.EventEnabler." + type.getName())) return false;

		if (CrazyAuctionsAnnouncer.getConfigFile().getString("Settings.MessageSendTo").equals("Both")) return true;
		if (CrazyAuctionsAnnouncer.getConfigFile().getString("Settings.MessageSendTo").equals("Discord")) return true;

		return false;
	}

	public static void sendDiscordMessage(String message, AuctionType type) {
		String channel = CrazyAuctionsAnnouncer.getConfigFile().getString("Discord.Channel." + type.getName());

		if(Bukkit.getPluginManager().isPluginEnabled("MineCordBot")){
			MineCordHandler.sendMessage(channel, message);
		}
		else if(Bukkit.getPluginManager().isPluginEnabled("UChat") || Bukkit.getPluginManager().isPluginEnabled("UltimateChat")){
			UChatHandler.sendMessage(channel, message);
		}
		else if(Bukkit.getPluginManager().isPluginEnabled("DiscordSRV")) {
			DiscordSRVManager.sendMessage(channel, message);
		}
	}

	public static void sendMessage(String channel, String message) {
		DiscordUtil.sendMessage(DiscordUtil.getTextChannelById(channel), message);
	}
}
