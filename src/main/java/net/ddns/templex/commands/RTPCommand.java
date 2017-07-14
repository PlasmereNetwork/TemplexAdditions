package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import lombok.NonNull;
import net.ddns.templex.TemplexAdditionsPlugin;
import net.ddns.templex.game.CoordinatePair;
import net.ddns.templex.game.CoordinateTriad;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.io.IOException;
import java.util.Collections;

/**
 * Randomly tps players.
 */
public class RTPCommand extends TabbableCommand {

    private final CoordinatePair center;

    public RTPCommand(@NonNull TemplexAdditionsPlugin plugin) {
        super("rtp");
        CoordinatePair center;
        try {
            center = plugin.getConfigHandler().getConfig("rtp.json", CoordinateTriad.class);
        } catch (IOException e) {
            center = null;
            e.printStackTrace();
        }
        this.center = center;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            return;
        }
        if (center == null) {
            commandSender.sendMessage(new ComponentBuilder("RTP center coordinates were not specified! Contact an administrator.").color(ChatColor.RED).create());
            return;
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
