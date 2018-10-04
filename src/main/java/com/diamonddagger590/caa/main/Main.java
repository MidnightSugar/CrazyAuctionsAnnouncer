package com.diamonddagger590.caa.main;


import com.diamonddagger590.caa.datastorage.AnnouncerLimiter;
import com.diamonddagger590.caa.events.BidEvent;
import com.diamonddagger590.caa.events.BuyEvent;
import com.diamonddagger590.caa.events.NewAuction;
import com.diamonddagger590.caa.events.WinBidEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	private static ListHandler listHandler = ListHandler.getInstance();
	
	public void onEnable(){
		//setup list handler class
		System.out.println(Main.color("&7[&3CrazyAuctionsAnnouncer&7]&6>>&eLoading files..."));
		listHandler.setup(this);
		System.out.println(Main.color(getPluginPrefix() + "&aFiles have been loaded!"));
		System.out.println(Main.color(getPluginPrefix() + "&eRegistering events..."));
		Bukkit.getServer().getPluginManager().registerEvents(new BidEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new BuyEvent(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new NewAuction(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new WinBidEvent(), this);
		System.out.println(Main.color(getPluginPrefix() + "&aEvents have been registered!"));
		System.out.print(Main.color(getPluginPrefix() + "&eStarting timers..."));
		AnnouncerLimiter.startTimer(this);
		System.out.print(Main.color(getPluginPrefix() + "&aTimers started!"));
		System.out.print(Main.color(getPluginPrefix() + "&aHave a blessed day!!!"));
	}
	
	public void onDisable(){
	}
	
	
	public static ListHandler getListHandler() {
		return listHandler;
	}
	
	public static String getPluginPrefix() {
		return getListHandler().getConfig().getString("PluginPrefix");
	}
	
	public static String color(String msg){
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getLabel().equalsIgnoreCase("caa")) {
			if(args.length > 0) {
				if(args[0].equalsIgnoreCase("reload")) {
					if(sender instanceof Player) {
						Player p = (Player) sender;
						if(p.hasPermission("CAA.*") || p.hasPermission("CAA.reload")) {
							listHandler.reloadConfig();
							AnnouncerLimiter.startTimer(this);
							p.sendMessage(color(listHandler.getConfig().getString("PluginPrefix") + listHandler.getConfig().getString("Messages.ConfigReloaded")));
							return true;
						}
						else {
							p.sendMessage(color(listHandler.getConfig().getString("PluginPrefix") + listHandler.getConfig().getString("Messages.NoPerms")));
							return true;
						}
					}
					else {
						listHandler.reloadConfig();
						AnnouncerLimiter.startTimer(this);
						System.out.println(color(listHandler.getConfig().getString("PluginPrefix") + listHandler.getConfig().getString("Messages.ConfigReloaded")));
						return true;
					}
				}
			}
			else {
				sender.sendMessage(color(listHandler.getConfig().getString("PluginPrefix") + listHandler.getConfig().getString("Messages.InvalidCommand")));
				return true;
			}
		}
		return false;
	}

}
