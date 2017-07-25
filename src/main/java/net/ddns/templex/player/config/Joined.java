package net.ddns.templex.player.config;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import net.ddns.templex.TemplexAdditionsPlugin;
import net.ddns.templex.commands.attribute.Attribute;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Joined extends ArrayList<Joined.JoinedEntry> {
    public static void assignJoinedAttributes(TemplexAdditionsPlugin plugin) {
        Joined.JoinedAttribute joinedAttribute = new Joined.JoinedAttribute(plugin);
        plugin.getAttributeHandler().getAttributes().putIfAbsent("joined", joinedAttribute);
    }

    @Value
    public static class JoinedEntry {
        String name;
    }

    @RequiredArgsConstructor
    public static class JoinedAttribute implements Attribute {

        private final BaseComponent[] BAD_VALUE = new ComponentBuilder("This attribute requires a boolean value.").color(ChatColor.RED).create();

        private final TemplexAdditionsPlugin plugin;

        @Override
        public void handleTabCompleteEvent(TabCompleteEvent event, List<String> items) {
            if (items.size() == 4) {
                String value = items.get(3).toLowerCase();
                if ("true".startsWith(value)) {
                    event.getSuggestions().add("true");
                } else if ("false".startsWith(value)) {
                    event.getSuggestions().add("false");
                } else {
                    event.getSuggestions().add("true");
                    event.getSuggestions().add("false");
                }
            }
        }

        public BaseComponent[] applyValue(ProxiedPlayer player, String[] strings) {
            switch (strings[2].toLowerCase()) {
                case "true":
                    try {
                        Joined joined = plugin.getConfigHandler().getConfig("joined.json", Joined.class);
                        joined.add(new Joined.JoinedEntry(strings[0]));
                        plugin.getConfigHandler().saveConfig("joined.json", joined);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return new ComponentBuilder("Added ").color(ChatColor.GREEN)
                            .append(player.getDisplayName())
                            .append(" to the joined list. Yay!").color(ChatColor.GREEN).create();
                case "false":
                    try {
                        Joined joined = plugin.getConfigHandler().getConfig("joined.json", Joined.class);
                        for (int i = 0; i < joined.size(); i++) {
                            if (joined.get(i).getName().equals(player.getName())) {
                                joined.remove(i);
                            }
                        }
                        plugin.getConfigHandler().saveConfig("joined.json", joined);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return new ComponentBuilder("Removed ").color(ChatColor.GREEN)
                            .append(player.getDisplayName())
                            .append(" from the joined list.").color(ChatColor.GREEN).create();
                default:
                    return BAD_VALUE;
            }
        }
    }
}
