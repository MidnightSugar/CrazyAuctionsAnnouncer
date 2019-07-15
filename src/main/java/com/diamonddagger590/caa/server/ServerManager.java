package com.diamonddagger590.caa.server;

import com.diamonddagger590.caa.actionbar.ActionBar;
import com.diamonddagger590.caa.itemhover.ItemHover;
import com.diamonddagger590.caa.main.CrazyAuctionsAnnouncer;
import com.diamonddagger590.caa.util.AuctionType;
import com.diamonddagger590.caa.util.Methods;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ServerManager {
    public static void sendServerMessage(String message, AuctionType type, ItemStack item){
        String displayType = CrazyAuctionsAnnouncer.getConfigFile().getString("Server.DisplayType." + type.getName());
        for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.hasPermission("caa.notify")) continue; //Temporary permission check. Need to add toggle command.
            if (displayType.equalsIgnoreCase("message")) {
                if (CrazyAuctionsAnnouncer.getConfigFile().getBoolean("Settings.ItemHover")) {
                    TextComponent json = ItemHover.addItemHover(message, item);
                    onlinePlayer.spigot().sendMessage(json);
                    return;
                }
                String itemType = Methods.getItemType(item);
                message = Methods.color(message);
                message = message.replace("%Item%", itemType);

                onlinePlayer.sendMessage(message);
                return;
            }

            String itemType = Methods.getItemType(item);
            message = Methods.color(message);
            message = message.replace("%Item%", itemType);

            if (displayType.equalsIgnoreCase("subtitle")) {
                ActionBar.sendTitle(onlinePlayer, "", message,
                        CrazyAuctionsAnnouncer.getConfigFile().getInt("Server.TitleConfig." + type.getName() + ".FadeInTime") * 20,
                        CrazyAuctionsAnnouncer.getConfigFile().getInt("Server.TitleConfig." + type.getName() + ".StayTime") * 20,
                        CrazyAuctionsAnnouncer.getConfigFile().getInt("Server.TitleConfig." + type.getName() + ".FadeOutTime") * 20);
                return;
            }
            if (displayType.equalsIgnoreCase("title")) {
                ActionBar.sendTitle(onlinePlayer, message, "",
                        CrazyAuctionsAnnouncer.getConfigFile().getInt("Server.TitleConfig." + type.getName() + ".FadeInTime") * 20,
                        CrazyAuctionsAnnouncer.getConfigFile().getInt("Server.TitleConfig." + type.getName() + ".StayTime") * 20,
                        CrazyAuctionsAnnouncer.getConfigFile().getInt("Server.TitleConfig." + type.getName() + ".FadeOutTime") * 20);
            }
        }
    }

    public static boolean canAnnounceServer(AuctionType type) {
        if (!CrazyAuctionsAnnouncer.getConfigFile().getBoolean("Server.EventEnabler." + type.getName())) return false;

        if (CrazyAuctionsAnnouncer.getConfigFile().getString("Settings.MessageSendTo").equals("Both")) return true;
        if (CrazyAuctionsAnnouncer.getConfigFile().getString("Settings.MessageSendTo").equals("Server")) return true;

        return false;
    }
}
