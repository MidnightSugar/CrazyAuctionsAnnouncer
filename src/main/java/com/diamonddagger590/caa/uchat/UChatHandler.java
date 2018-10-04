package com.diamonddagger590.caa.uchat;

import br.net.fabiozumbi12.UltimateChat.Bukkit.UCChannel;
import br.net.fabiozumbi12.UltimateChat.Bukkit.UChat;
import org.bukkit.Bukkit;

public class UChatHandler {

	@SuppressWarnings("deprecation")
	public static void sendMessage(String chan, String msg) {
		if(Bukkit.getPluginManager().isPluginEnabled("UChat") || Bukkit.getPluginManager().isPluginEnabled("UltimateChat")){
			for(UCChannel channel : UChat.get().getAPI().getChannels()) {
				if(channel.getDiscordChannelID().equals(chan)) {
					channel.sendMessage(Bukkit.getConsoleSender(), msg);
					return;
				}
			}
		}
	}
	
}
