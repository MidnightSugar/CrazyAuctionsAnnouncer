package com.diamonddagger590.caa.main;

import com.diamonddagger590.caa.datastorage.AnnouncerLimiter;
import com.diamonddagger590.caa.datastorage.FileManager;
import com.diamonddagger590.caa.events.BidEvent;
import com.diamonddagger590.caa.events.BuyEvent;
import com.diamonddagger590.caa.events.NewAuction;
import com.diamonddagger590.caa.events.WinBidEvent;
import com.diamonddagger590.caa.util.Methods;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CrazyAuctionsAnnouncer extends JavaPlugin {

  @Getter
  private static FileManager fileManager = FileManager.getInstance();

  public void onEnable(){
	//setup list handler class
	System.out.println(Methods.color("&7[&3CrazyAuctionsAnnouncer&7]&6>>&eLoading files..."));
	fileManager.setup(this);
	System.out.println(Methods.color(getPluginPrefix() + "&aFiles have been loaded!"));
	System.out.println(Methods.color(getPluginPrefix() + "&eRegistering events..."));
	Bukkit.getServer().getPluginManager().registerEvents(new BidEvent(), this);
	Bukkit.getServer().getPluginManager().registerEvents(new BuyEvent(), this);
	Bukkit.getServer().getPluginManager().registerEvents(new NewAuction(), this);
	Bukkit.getServer().getPluginManager().registerEvents(new WinBidEvent(), this);
	System.out.println(Methods.color(getPluginPrefix() + "&aEvents have been registered!"));
	System.out.print(Methods.color(getPluginPrefix() + "&eStarting timers..."));
	AnnouncerLimiter.startTimer(this);
	System.out.print(Methods.color(getPluginPrefix() + "&aTimers started!"));
	System.out.print(Methods.color(getPluginPrefix() + "&aHave a blessed day!!!"));
  }

  public void onDisable(){
  }


  public static String getPluginPrefix(){
	return getConfigFile().getString("PluginPrefix");
  }

  public static FileConfiguration getConfigFile() {
    return getFileManager().getFile(FileManager.Files.CONFIG);
  }

  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
	if(cmd.getLabel().equalsIgnoreCase("caa")){
	  if(args.length > 0){
		if(args[0].equalsIgnoreCase("reload")){
		  if(sender instanceof Player){
			Player p = (Player) sender;
			if(p.hasPermission("CAA.*") || p.hasPermission("CAA.reload")){
			  getFileManager().reloadAllFiles();
			  AnnouncerLimiter.startTimer(this);
			  p.sendMessage(Methods.color(getPluginPrefix() + getConfigFile().getString("Messages.ConfigReloaded")));
			  return true;
			}
			else{
			  p.sendMessage(Methods.color(getPluginPrefix() + getConfigFile().getString("Messages.NoPerms")));
			  return true;
			}
		  }
		  else{
			getFileManager().reloadAllFiles();
			AnnouncerLimiter.startTimer(this);
			System.out.println(Methods.color(getPluginPrefix() + getConfigFile().getString("Messages.ConfigReloaded")));
			return true;
		  }
		}
	  }
	  else{
		sender.sendMessage(Methods.color(getPluginPrefix() + getConfigFile().getString("Messages.InvalidCommand")));
		return true;
	  }
	}
	return false;
  }

}
