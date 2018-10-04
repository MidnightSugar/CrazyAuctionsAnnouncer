package com.diamonddagger590.caa.events;

import com.diamonddagger590.caa.actionbar.ActionBar;
import com.diamonddagger590.caa.datastorage.AnnouncerLimiter;
import com.diamonddagger590.caa.main.CrazyAuctionsAnnouncer;
import me.badbones69.crazyauctions.api.ShopType;
import me.badbones69.crazyauctions.api.events.AuctionListEvent;
import me.badbones69.crazyenchantments.Methods;
import me.badbones69.crazyenchantments.api.CEBook;
import me.badbones69.crazyenchantments.api.CrazyEnchantments;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class NewAuction implements Listener{

	@EventHandler
	public void newAuction(AuctionListEvent e) {
		Player p = e.getPlayer();
		ItemStack item = e.getItem();
		long bid = e.getPrice();
		String auctionType = e.getShopType().getName();
		String itemType = com.diamonddagger590.caa.util.Methods.convertName(item.getType());
		if(Bukkit.getPluginManager().isPluginEnabled("CrazyEnchantments") && CrazyEnchantments.getInstance().isEnchantmentBook(item)) {
			CEBook book = CrazyEnchantments.getInstance().convertToCEBook(item);
			String power = Methods.getPower(book.getPower());
			itemType = book.getEnchantment().getName() + " " + power;
		}
		String serverMessage = com.diamonddagger590.caa.util.Methods.color(CrazyAuctionsAnnouncer.getPluginPrefix() + CrazyAuctionsAnnouncer.getConfigFile().getString("Messages.AuctionStart"));
		String displayName;
		if(item.hasItemMeta() && item.getItemMeta().hasDisplayName()){
			displayName = com.diamonddagger590.caa.util.Methods.convertName(item.getType());
		}
		else{
			displayName = item.getItemMeta().getDisplayName();
		}
		serverMessage = com.diamonddagger590.caa.util.Methods.translateMessage(serverMessage, p, bid, item.getAmount(), itemType, ShopType.BID.getName(), displayName);
		String discordMessage = CrazyAuctionsAnnouncer.getConfigFile().getString("Discord.Messages.AuctionStart");
		discordMessage = com.diamonddagger590.caa.util.Methods.translateMessage(discordMessage, p, bid, item.getAmount(), itemType, ShopType.BID.getName(), displayName);
		String displayType = CrazyAuctionsAnnouncer.getConfigFile().getString("Settings.MessageSendTo");
		if((!CrazyAuctionsAnnouncer.getConfigFile().getBoolean("Settings.UseAnnouncementLimit")) || AnnouncerLimiter.canAnnounce()) {
			if(displayType.equalsIgnoreCase("both") || displayType.equalsIgnoreCase("server")) {
				if(CrazyAuctionsAnnouncer.getConfigFile().getBoolean("Server.EventEnabler.AuctionStart")) {
					String displayTypeServer = CrazyAuctionsAnnouncer.getConfigFile().getString("Server.DisplayType.AuctionStart");
					for(Player play : Bukkit.getOnlinePlayers()) {
						if(displayTypeServer.equalsIgnoreCase("message")) {
							play.sendMessage(serverMessage);
						}
						else if(displayTypeServer.equalsIgnoreCase("subtitle")){
							ActionBar.sendTitle(play, "", serverMessage, CrazyAuctionsAnnouncer.getConfigFile().getInt("Server.TitleConfig.AuctionStart.FadeInTime") * 20,
								CrazyAuctionsAnnouncer.getConfigFile().getInt("Server.TitleConfig.AuctionStart.StayTime") * 20,
								CrazyAuctionsAnnouncer.getConfigFile().getInt("Server.TitleConfig.AuctionStart.FadeOutTime") * 20);
						}
						else if(displayTypeServer.equalsIgnoreCase("title")) {
							ActionBar.sendTitle(play, serverMessage, "", CrazyAuctionsAnnouncer.getConfigFile().getInt("Server.TitleConfig.AuctionStart.FadeInTime") * 20,
								CrazyAuctionsAnnouncer.getConfigFile().getInt("Server.TitleConfig.AuctionStart.StayTime") * 20,
								CrazyAuctionsAnnouncer.getConfigFile().getInt("Server.TitleConfig.AuctionStart.FadeOutTime") * 20);
						}
						else if(displayTypeServer.equalsIgnoreCase("action_bar")) {
							ActionBar.sendActionBar(play, serverMessage);
						}
					}
				}
			}
			if(displayType.equalsIgnoreCase("both") || displayType.equalsIgnoreCase("discord")) {
				String channel = CrazyAuctionsAnnouncer.getConfigFile().getString("Discord.Channels.AuctionStartServer");
				if(CrazyAuctionsAnnouncer.getConfigFile().getBoolean("Discord.EventEnabler.AuctionStart")) {
					com.diamonddagger590.caa.util.Methods.sendDiscordMessage(discordMessage, channel);
                }
			}
		}
	}
	
}
