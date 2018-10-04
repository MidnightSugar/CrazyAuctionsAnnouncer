package com.diamonddagger590.caa.events;

import com.diamonddagger590.caa.actionbar.ActionBar;
import com.diamonddagger590.caa.datastorage.AnnouncerLimiter;
import com.diamonddagger590.caa.main.Main;
import me.badbones69.crazyauctions.api.ShopType;
import me.badbones69.crazyauctions.api.events.AuctionNewBidEvent;
import me.badbones69.crazyenchantments.api.CEBook;
import me.badbones69.crazyenchantments.api.CrazyEnchantments;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class BidEvent implements Listener{

	@EventHandler
	public void newBidEvent(AuctionNewBidEvent e) {
		Player p = e.getPlayer();
		ItemStack item = e.getItem();
		long bid = e.getBid();
		String itemType = com.diamonddagger590.caa.util.Methods.convertName(item.getType(), item.getDurability());
		if(Bukkit.getPluginManager().isPluginEnabled("CrazyEnchantments") && CrazyEnchantments.getInstance().isEnchantmentBook(item)) {
			CEBook book = CrazyEnchantments.getInstance().convertToCEBook(item);
			String power = me.badbones69.crazyenchantments.Methods.getPower(book.getPower());
			itemType = book.getEnchantment().getName() + " " + power;
		}
		String serverMessage = Main.color(Main.getListHandler().getConfig().getString("PluginPrefix") + Main.getListHandler().getConfig().getString("Messages.AuctionBid"));
		String displayName;
		if(item.hasItemMeta() && item.getItemMeta().hasDisplayName()){
			displayName = com.diamonddagger590.caa.util.Methods.convertName(item.getType(), item.getDurability());
		}
		else{
			displayName = item.getItemMeta().getDisplayName();
		}
		serverMessage = com.diamonddagger590.caa.util.Methods.translateMessage(serverMessage, p, bid, item.getAmount(), itemType, ShopType.BID.getName(), displayName);
		String discordMessage = Main.getListHandler().getConfig().getString("Discord.Messages.AuctionBid");
		discordMessage = com.diamonddagger590.caa.util.Methods.translateMessage(discordMessage, p, bid, item.getAmount(), itemType, ShopType.BID.getName(), displayName);
		String displayType = Main.getListHandler().getConfig().getString("Settings.MessageSendTo");
		if((!Main.getListHandler().getConfig().getBoolean("Settings.UseAnnouncementLimit")) || AnnouncerLimiter.canAnnounce()) {
			if(displayType.equalsIgnoreCase("both") || displayType.equalsIgnoreCase("server")) {
				if(Main.getListHandler().getConfig().getBoolean("Server.EventEnabler.AuctionBid")) {
					String displayTypeServer = Main.getListHandler().getConfig().getString("Server.DisplayType.AuctionBid");
					for(Player play : Bukkit.getOnlinePlayers()) {
						if(displayTypeServer.equalsIgnoreCase("message")) {
							play.sendMessage(serverMessage);
						}
						else if(displayTypeServer.equalsIgnoreCase("subtitle")){
							ActionBar.sendTitle(play, "", serverMessage, Main.getListHandler().getConfig().getInt("Server.TitleConfig.AuctionBid.FadeInTime") * 20, Main.getListHandler().getConfig().getInt("Server.TitleConfig.AuctionBid.StayTime") * 20, Main.getListHandler().getConfig().getInt("Server.TitleConfig.AuctionBid.FadeOutTime") * 20);
						}
						else if(displayTypeServer.equalsIgnoreCase("title")) {
							ActionBar.sendTitle(play, serverMessage, "", Main.getListHandler().getConfig().getInt("Server.TitleConfig.AuctionBid.FadeInTime") * 20, Main.getListHandler().getConfig().getInt("Server.TitleConfig.AuctionBid.StayTime") * 20, Main.getListHandler().getConfig().getInt("Server.TitleConfig.AuctionBid.FadeOutTime") * 20);
						}
						else if(displayTypeServer.equalsIgnoreCase("action_bar")) {
							ActionBar.sendActionBar(play, serverMessage);
						}
					}	
				}
			}
			if(displayType.equalsIgnoreCase("both") || displayType.equalsIgnoreCase("discord")) {
				String channel = Main.getListHandler().getConfig().getString("Discord.Channels.AuctionBidServer");
				if(Main.getListHandler().getConfig().getBoolean("Discord.EventEnabler.AuctionBid")) {
					com.diamonddagger590.caa.util.Methods.sendDiscordMessage(discordMessage, channel);
				}
			}
		}
	}
}
