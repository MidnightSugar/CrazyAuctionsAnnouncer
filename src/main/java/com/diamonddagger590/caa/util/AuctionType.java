package com.diamonddagger590.caa.util;

public enum AuctionType {
    BUY ("AuctionBuy"),
    SELL ("AuctionSell"),
    STARTBID ("AuctionStartBid"),
    BID ("AuctionBid"),
    WINBID ("AuctionWinBid");

    private final String name;
    AuctionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
