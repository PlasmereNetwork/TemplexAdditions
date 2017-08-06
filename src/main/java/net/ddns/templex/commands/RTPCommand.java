package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collections;

/**
 * Randomly tps players.
 */
public class RTPCommand extends TabbableCommand {

    private final BaseComponent[] SUCCESS = new ComponentBuilder("You have been randomly teleported successfully!").color(ChatColor.GREEN).create();

    public RTPCommand() {
        super("rtp", "nonop");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            return;
        }
        Daemon instance = Daemon.getInstanceNow();
        if (instance == null) {
            CommandUtil.daemonNotFound(commandSender);
            return;
        }
        instance.submitCommands(Collections.singletonList(String.format("/spreadplayers  ~ ~ 700000 2000000 false %s", commandSender.getName())));
        CommandUtil.tellOps(
                new ComponentBuilder("RTP PL ").color(ChatColor.GOLD)
                        .append(": ").color(ChatColor.DARK_GRAY)
                        .append("Successfully RTPed ").color(ChatColor.RED)
                        .append(commandSender.getName()).color(ChatColor.GRAY)
                        .append("!").color(ChatColor.RED).create()
        );
        commandSender.sendMessage(SUCCESS);
    }

}
