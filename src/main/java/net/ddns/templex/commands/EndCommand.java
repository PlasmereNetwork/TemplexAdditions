package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import lombok.NonNull;
import net.ddns.templex.TemplexAdditionsPlugin;
import net.ddns.templex.world.CoordinateTriad;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.io.IOException;
import java.util.Collections;

/**
 * Tps players to the End on the Templex server.
 */
public class EndCommand extends TabbableCommand {

    private final BaseComponent[] NO_COORDS = new ComponentBuilder("End coordinates were not specified! Contact an administrator.").color(ChatColor.RED).create();

    private final CoordinateTriad endPortalCoordinates;

    public EndCommand(@NonNull TemplexAdditionsPlugin plugin) {
        super("end", "nonop");
        CoordinateTriad endPortalCoordinates;
        try {
            endPortalCoordinates = plugin.getConfigHandler().getConfig("end.json", CoordinateTriad.class);
        } catch (IOException e) {
            endPortalCoordinates = null;
            e.printStackTrace();
        }
        this.endPortalCoordinates = endPortalCoordinates;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            return;
        }
        if (endPortalCoordinates == null) {
            commandSender.sendMessage(NO_COORDS);
            return;
        }
        Daemon instance = Daemon.getInstanceNow();
        if (instance == null) {
            CommandUtil.daemonNotFound(commandSender);
            return;
        }
        instance.submitCommands(Collections.singletonList(String.format("/tp %s %s", commandSender.getName(), endPortalCoordinates)));
        CommandUtil.tellOps(
                new ComponentBuilder("TP End ").color(ChatColor.GOLD)
                        .append(": ").color(ChatColor.DARK_GRAY)
                        .append("Successfully TPed ").color(ChatColor.RED)
                        .append(commandSender.getName()).color(ChatColor.GRAY)
                        .append(" to the End!").color(ChatColor.RED).create()
        );
        commandSender.sendMessage(new ComponentBuilder("Successfully teleported to the End!").color(ChatColor.GREEN).create());
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        CommandUtil.pushAutocompletePlayers(event);
    }

}
