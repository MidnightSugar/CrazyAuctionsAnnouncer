package com.diamonddagger590.caa.itemhover;

import com.diamonddagger590.caa.util.Methods;
import de.themoep.ShowItem.api.ItemDataTooLongException;
import de.themoep.ShowItem.api.ShowItem;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public class ItemHover {
    public static TextComponent addItemHover(String msg, ItemStack item) {
        TextComponent json = new TextComponent();

        String[] words = msg.split("%Item%");
        for (String word : words) {
            json.addExtra(Methods.color(word));
            if (!word.equals(words[words.length - 1])) {
                try {
                    json.addExtra(createItemJson(item));
                } catch (ItemDataTooLongException e) {
                    e.printStackTrace();
                }
            }
        }
        return json;
    }

    public static TextComponent createItemJson(ItemStack item) throws ItemDataTooLongException {
        ShowItem showItemPlugin = (ShowItem) Bukkit.getPluginManager().getPlugin("ShowItem");
        String itemJson = showItemPlugin.getItemConverter().itemToJson(item, Level.OFF);

        BaseComponent[] hoverEventComponents = new BaseComponent[]{
                new TextComponent(itemJson)
        };

        TextComponent itemComponent = new TextComponent();
        itemComponent.addExtra(ChatColor.WHITE + "[" + Methods.getDisplayName(item) + "]");
        itemComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, hoverEventComponents));
        return itemComponent;
    }
}
