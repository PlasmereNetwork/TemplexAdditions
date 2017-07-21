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

public class Specials extends ArrayList<Specials.SpecialsEntry> {
    public static void assignSpecialAttributes(TemplexAdditionsPlugin plugin) {
        Specials.SpecialAttribute specialAttribute = new Specials.SpecialAttribute(plugin);
        plugin.getAttributeHandler().getAttributes().putIfAbsent("special", specialAttribute);
        plugin.getAttributeHandler().getAttributes().putIfAbsent("donor", specialAttribute);
    }

    @Value
    public static class SpecialsEntry {
        String name;
    }

    @RequiredArgsConstructor
    public static class SpecialAttribute implements Attribute {

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
                        player.setPermission("special", true);
                        Specials specials = plugin.getConfigHandler().getConfig("special.json", Specials.class);
                        specials.add(new Specials.SpecialsEntry(strings[0]));
                        plugin.getConfigHandler().saveConfig("special.json", specials);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return new ComponentBuilder("Marked ").color(ChatColor.GREEN)
                            .append(player.getDisplayName())
                            .append(" as special. Woo!").color(ChatColor.GREEN).create();
                case "false":
                    try {
                        player.setPermission("special", false);
                        Specials specials = plugin.getConfigHandler().getConfig("special.json", Specials.class);
                        for (int i = 0; i < specials.size(); i++) {
                            if (specials.get(i).getName().equals(player.getName())) {
                                specials.remove(i);
                            }
                        }
                        plugin.getConfigHandler().saveConfig("special.json", specials);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return new ComponentBuilder("Marked ").color(ChatColor.GREEN)
                            .append(player.getDisplayName())
                            .append(" as not so special.").color(ChatColor.GREEN).create();
                default:
                    return BAD_VALUE;
            }
        }
    }
}
