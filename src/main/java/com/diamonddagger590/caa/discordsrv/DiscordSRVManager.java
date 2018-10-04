package com.diamonddagger590.caa.discordsrv;

import github.scarsz.discordsrv.util.DiscordUtil;

public class DiscordSRVManager {

	public static void sendMessage(String channel, String message) {
		DiscordUtil.sendMessage(DiscordUtil.getTextChannelById(channel), message);
	}
}
