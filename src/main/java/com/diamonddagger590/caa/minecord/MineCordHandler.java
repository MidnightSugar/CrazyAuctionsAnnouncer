package com.diamonddagger590.caa.minecord;

import org.bukkit.Bukkit;
import us.cyrien.minecordbot.HookContainer;
import us.cyrien.minecordbot.Minecordbot;
import us.cyrien.minecordbot.chat.Messenger;
import us.cyrien.minecordbot.chat.exception.IllegalTextChannelException;

public class MineCordHandler {

	public static void sendMessage(String chan, String msg) {
		if(Bukkit.getPluginManager().isPluginEnabled("MineCordBot")){
			Minecordbot mcb = HookContainer.getMcbHook().getPlugin();
			Messenger msger = mcb.getMessenger();
			try {

			    msger.sendMessageToDiscordByID(chan, msg);
			} catch(IllegalTextChannelException tcE) {
				tcE.printStackTrace();
			    //do something here
			}
		}
	}
	
}
