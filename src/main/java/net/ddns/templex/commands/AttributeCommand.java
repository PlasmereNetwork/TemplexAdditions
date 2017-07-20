package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import net.ddns.templex.TemplexAdditionsPlugin;
import net.ddns.templex.player.config.Specials;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AttributeCommand extends TabbableCommand {

    private final TemplexAdditionsPlugin plugin;

    private final List<String> attributes = Collections.unmodifiableList(Arrays.asList(
            "special",
            "donor"
    ));

    public AttributeCommand(TemplexAdditionsPlugin plugin) {
        super("attribute", "op", "attr");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (strings.length != 3) {
            commandSender.sendMessage(new ComponentBuilder("Syntax:\n/attribute <player> <attribute> [value]").color(ChatColor.RED).create());
            return;
        }
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(strings[0]);
        if (player == null) {
            commandSender.sendMessage(new ComponentBuilder(String.format("Couldn't find player %s.", strings[0])).color(ChatColor.RED).create());
            return;
        }
        switch (strings[1]) {
            case "special":
            case "donor":
                switch (strings[2]) {
                    case "true":
                        try {
                            player.setPermission("special", true);
                            Specials specials = plugin.getConfigHandler().getConfig("special.json", Specials.class);
                            specials.add(new Specials.SpecialsEntry(strings[0]));
                            plugin.getConfigHandler().saveConfig("special.json", specials);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
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
                        break;
                    default:
                        commandSender.sendMessage(new ComponentBuilder("This attribute requires a boolean value.").color(ChatColor.RED).create());
                        return;
                }
                break;
            default:
                commandSender.sendMessage(new ComponentBuilder(String.format("Attribute %s does not exist.", strings[1])).color(ChatColor.RED).create());
                return;
        }
        commandSender.sendMessage(new ComponentBuilder(String.format("Set attribute %s of player %s to %s.", strings[1], strings[0], strings[2])).color(ChatColor.GREEN).create());
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        ArrayList<String> items = new ArrayList<>();
        int last = 0;
        for (int i = 0; i < event.getCursor().length(); i++) {
            if (event.getCursor().charAt(i) == ' ') {
                items.add(event.getCursor().substring(last, i));
                last = i + 1;
            }
        }
        switch (items.size()) {
            case 2:
                CommandUtil.pushAutocompletePlayers(event);
                break;
            case 3:
                for (String attribute : attributes) {
                    if (attribute.startsWith(items.get(2))) {
                        event.getSuggestions().add(attribute);
                    }
                }
                break;
            default:
                try {
                    Attribute attribute = Attribute.valueOf(items.get(2).toUpperCase());
                    attribute.handleTabCompleteEvent(event, items);
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ignored) {
                }
        }
    }

    private enum Attribute {
        SPECIAL {
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
        },
        DONOR(SPECIAL),
        TEAM {

        };

        private final Attribute alias;

        Attribute() {
            this(null);
        }

        Attribute(Attribute alias) {
            this.alias = alias;
        }

        public void handleTabCompleteEvent(TabCompleteEvent event, List<String> items) {
            if (alias != null) {
                alias.handleTabCompleteEvent(event, items);
            }
        }
    }
}
