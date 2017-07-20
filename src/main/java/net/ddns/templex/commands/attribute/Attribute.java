package net.ddns.templex.commands.attribute;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.List;

public interface Attribute {
    void handleTabCompleteEvent(TabCompleteEvent event, List<String> items);

    BaseComponent[] applyValue(ProxiedPlayer player, String[] strings);
}
