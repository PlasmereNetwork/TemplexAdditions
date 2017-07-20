package net.ddns.templex.commands.attribute;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import net.ddns.templex.TemplexAdditionsPlugin;
import net.ddns.templex.commands.CommandUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttributeCommand extends TabbableCommand {

    private BaseComponent[] SYNTAX = new ComponentBuilder("Syntax:\n/attribute <player> <attribute> [value]").color(ChatColor.RED).create();

    private final TemplexAdditionsPlugin plugin;

    public AttributeCommand(TemplexAdditionsPlugin plugin) {
        super("attribute", "op", "attr");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (strings.length < 2) {
            commandSender.sendMessage(SYNTAX);
            return;
        }
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(strings[0]);
        if (player == null) {
            commandSender.sendMessage(new ComponentBuilder(String.format("Couldn't find player %s.", strings[0])).color(ChatColor.RED).create());
            return;
        }
        Attribute attribute = plugin.getAttributeHandler().getAttributes().get(strings[1]);
        if (attribute != null) {
            commandSender.sendMessage(attribute.applyValue(player, strings));
        } else {
            commandSender.sendMessage(new ComponentBuilder(String.format("Attribute %s does not exist.", strings[1])).color(ChatColor.RED).create());
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        List<String> items = Arrays.asList(event.getCursor().split(" "));
        switch (items.size()) {
            case 1:
                break;
            case 2:
                CommandUtil.pushAutocompletePlayers(event);
                break;
            case 3:
                for (String attributeName : plugin.getAttributeHandler().getAttributes().keySet()) {
                    if (attributeName.startsWith(items.get(2))) {
                        event.getSuggestions().add(attributeName);
                    }
                }
                break;
            default:
                Attribute attribute = plugin.getAttributeHandler().getAttributes().get(items.get(2));
                if (attribute != null) {
                    attribute.handleTabCompleteEvent(event, items);
                }
        }
    }

}
