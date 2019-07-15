package com.diamonddagger590.caa.events;

import com.diamonddagger590.caa.datastorage.AnnouncerLimiter;
import com.diamonddagger590.caa.discordsrv.DiscordSRVManager;
import com.diamonddagger590.caa.main.CrazyAuctionsAnnouncer;
import com.diamonddagger590.caa.server.ServerManager;
import com.diamonddagger590.caa.util.AuctionType;
import com.diamonddagger590.caa.util.Methods;
import me.badbones69.crazyauctions.api.ShopType;
import me.badbones69.crazyauctions.api.events.AuctionBuyEvent;
import me.badbones69.crazyauctions.api.events.AuctionListEvent;
import me.badbones69.crazyauctions.api.events.AuctionNewBidEvent;
import me.badbones69.crazyauctions.api.events.AuctionWinBidEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {
    @EventHandler
    public void auctionBuy(AuctionBuyEvent e) {
        if (!AnnouncerLimiter.canAnnounce()) return;

        Player player = e.getPlayer();
        ItemStack item = e.getItem();
        double price = e.getPrice();
        AuctionType type = AuctionType.BUY;

        String message = Methods.translateMessage(type, player, item, price);

        if (ServerManager.canAnnounceServer(type))
            ServerManager.sendServerMessage(message, type, item);

        if (DiscordSRVManager.canAnnounceDiscord(type))
            DiscordSRVManager.sendDiscordMessage(message, type);
    }
    @EventHandler
    public void auctionList(AuctionListEvent e) {
        if (!AnnouncerLimiter.canAnnounce()) return;

        Player player = e.getPlayer();
        ItemStack item = e.getItem();
        double price = e.getPrice();

        if (e.getShopType().equals(ShopType.BID)) {
            AuctionType type = AuctionType.STARTBID;

            String message = Methods.translateMessage(type, player, item, price);

            if (ServerManager.canAnnounceServer(type))
                ServerManager.sendServerMessage(message, type, item);

            if (DiscordSRVManager.canAnnounceDiscord(type))
                DiscordSRVManager.sendDiscordMessage(message, type);
        } else {
            AuctionType type = AuctionType.SELL;

            String message = Methods.translateMessage(type, player, item, price);

            if (ServerManager.canAnnounceServer(type))
                ServerManager.sendServerMessage(message, type, item);

            if (DiscordSRVManager.canAnnounceDiscord(type))
                DiscordSRVManager.sendDiscordMessage(message, type);
        }
    }
    @EventHandler
    public void auctionNewBid(AuctionNewBidEvent e) {
        if (!AnnouncerLimiter.canAnnounce()) return;

        Player player = e.getPlayer();
        ItemStack item = e.getItem();
        double price = e.getBid();
        AuctionType type = AuctionType.BID;

        String message = Methods.translateMessage(type, player, item, price);

        if (ServerManager.canAnnounceServer(type))
            ServerManager.sendServerMessage(message, type, item);

        if (DiscordSRVManager.canAnnounceDiscord(type))
            DiscordSRVManager.sendDiscordMessage(message, type);
    }
    @EventHandler
    public void winBid(AuctionWinBidEvent e) {
        if (!AnnouncerLimiter.canAnnounce()) return;

        Player player = e.getPlayer();
        ItemStack item = e.getItem();
        double price = e.getBid();
        AuctionType type = AuctionType.WINBID;

        String message = Methods.translateMessage(type, player, item, price);

        if (ServerManager.canAnnounceServer(type))
            ServerManager.sendServerMessage(message, type, item);

        if (DiscordSRVManager.canAnnounceDiscord(type))
            DiscordSRVManager.sendDiscordMessage(message, type);
    }
}
