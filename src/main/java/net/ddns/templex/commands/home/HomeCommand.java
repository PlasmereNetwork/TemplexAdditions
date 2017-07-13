package net.ddns.templex.commands.home;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import lombok.NonNull;
import net.ddns.templex.commands.Util;
import net.ddns.templex.game.CoordinateTriad;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.Collections;

/**
 * TODO
 */
public class HomeCommand extends TabbableCommand {

    private final HomeHandler handler;

    public HomeCommand(@NonNull HomeHandler handler) {
        super("home", "templex.home", "h");
        this.handler = handler;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            return;
        }
        if (strings.length == 1) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            CoordinateTriad coordinateTriad = handler.getActiveHomes().get(player).get(strings[0]);
            if (coordinateTriad != null) {
                try {
                    Daemon.getInstance().submitCommands(Collections.singletonList(String.format("/tp %s %s", player.getName(), coordinateTriad.toString())));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                commandSender.sendMessage(new ComponentBuilder(String.format("You don't have a home called %s", strings[0])).color(ChatColor.RED).create());
            }
        } else {
            commandSender.sendMessage(new ComponentBuilder("Usage: /home <home name>").color(ChatColor.RED).create());
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        Util.pushAutocompletePlayers(event);
    }

}
