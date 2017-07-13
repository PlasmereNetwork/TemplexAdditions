package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import lombok.NonNull;
import net.ddns.templex.TemplexAdditionsPlugin;
import net.ddns.templex.game.CoordinateTriad;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.io.IOException;
import java.util.Collections;

/**
 * Tps players to the End on the Templex server.
 */
public class EndCommand extends TabbableCommand {

    private final CoordinateTriad endPortalCoordinates;

    public EndCommand(@NonNull TemplexAdditionsPlugin plugin) {
        super("end", "templex.end");
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
            commandSender.sendMessage(new ComponentBuilder("End portal coordinates were not specified! Contact an administrator.").color(ChatColor.RED).create());
        }
        try {
            Daemon.getInstance().submitCommands(Collections.singletonList("/tp " + commandSender.getName() + " " + endPortalCoordinates));
            Daemon.getInstance().submitCommands(Collections.singletonList("/tellraw @a[tag=OP] [{\"text\":\"\\u00A76TP End \\u00A78: \\u00A7cSuccessfully tped \\u00A77" + commandSender.getName() + " to the End!\",\"color\":\"red\"}]"));
            // TODO Use component builder
            commandSender.sendMessage(new ComponentBuilder("Successfully tped to the End!").color(ChatColor.GREEN).create());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        Util.pushAutocompletePlayers(event);
    }

}
