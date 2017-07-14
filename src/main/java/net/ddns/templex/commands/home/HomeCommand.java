package net.ddns.templex.commands.home;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import lombok.NonNull;
import net.ddns.templex.game.CoordinateTriad;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.Collections;

public class HomeCommand extends TabbableCommand {

    private final HomeHandler handler;

    public HomeCommand(@NonNull HomeHandler handler) {
        super("home");
        this.handler = handler;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            return;
        }
        String name;
        if (strings.length == 0) {
            name = "home";
        } else {
            name = strings[0];
        }
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        CoordinateTriad coordinateTriad = handler.getActiveHomes().get(player).getHomes().get(name);
        if (coordinateTriad != null) {
            try {
                Daemon.getInstance().submitCommands(Collections.singletonList(String.format("/tp %s %s", player.getName(), coordinateTriad.toString())));
                player.sendMessage(new ComponentBuilder(String.format("Successfully teleported you to '%s'.", name)).color(ChatColor.GREEN).create());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            commandSender.sendMessage(new ComponentBuilder(String.format("You don't have a home called %s", name)).color(ChatColor.RED).create());
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        if (event.getSender() instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) event.getSender();
            String filter = event.getCursor().substring(event.getCursor().lastIndexOf(' ') + 1);
            for (String home : handler.getActiveHomes().get(player).getHomes().keySet()) {
                if (home.startsWith(filter)) {
                    event.getSuggestions().add(home);
                }
            }
        }
    }

}
