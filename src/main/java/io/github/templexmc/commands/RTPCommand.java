package io.github.templexmc.commands;

import io.github.templexmc.TemplexAdditionsPlugin;
import io.github.templexmc.game.CoordinatePair;
import io.github.templexmc.game.CoordinateTriad;
import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.io.IOException;
import java.util.Collections;

/**
 * Randomly tps players.
 */
public class RTPCommand extends TabbableCommand {

    private final CoordinatePair center;

    public RTPCommand(TemplexAdditionsPlugin plugin) {
        super("rtp", "templex.rtp");
        CoordinatePair center;
        try {
            center = plugin.getConfigHandler().getConfig("end.json", CoordinateTriad.class);
        } catch (IOException e) {
            center = null;
            e.printStackTrace();
        }
        this.center = center;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (center == null) {
            commandSender.sendMessage(new ComponentBuilder("RTP center coordinates were not specified! Contact an administrator.").color(ChatColor.RED).create());
        }
        try {
            // TODO RTP-count checking?
            Daemon.getInstance().submitCommands(Collections.singletonList("/spreadplayers  " + center + " 700000 2000000 false " + commandSender.getName()));
            commandSender.sendMessage(new ComponentBuilder("You have been successfully randomly tped!").color(ChatColor.GREEN).create());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        Util.pushAutocompletePlayers(event);
    }

}
