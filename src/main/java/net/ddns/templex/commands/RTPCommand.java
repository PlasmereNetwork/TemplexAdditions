package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.Collections;

/**
 * Randomly tps players.
 */
public class RTPCommand extends TabbableCommand {

    public RTPCommand() {
        super("rtp", "templex.nonop");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            return;
        }
        try {
            Daemon.getInstance().submitCommands(Collections.singletonList("/spreadplayers  ~ ~ 700000 2000000 false " + commandSender.getName()));
            CommandUtil.tellOps(
                    new ComponentBuilder("RTP PL ").color(ChatColor.GOLD)
                            .append(": ").color(ChatColor.DARK_GRAY)
                            .append("Successfully RTPed ").color(ChatColor.RED)
                            .append(commandSender.getName()).color(ChatColor.GRAY)
                            .append("!").color(ChatColor.RED).create()
            );
            commandSender.sendMessage(new ComponentBuilder("You have been successfully randomly teleported!").color(ChatColor.GREEN).create());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        CommandUtil.pushAutocompletePlayers(event);
    }

}
