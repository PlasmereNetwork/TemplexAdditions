package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.Collections;

/**
 * Created by vtcakavsmoace on 7/15/17.
 */
public class DaemonizeCommand extends TabbableCommand {

    public DaemonizeCommand() {
        super("daemonize", "templex.op", "d");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage(new ComponentBuilder("Must submit a command to execute!").color(ChatColor.RED).create());
            return;
        }
        StringBuilder commandBuilder = new StringBuilder("/execute @s ~ ~ ~");
        for (String item : strings) {
            commandBuilder.append(' ');
            commandBuilder.append(item);
        }
        try {
            Daemon.getInstance().submitCommands(Collections.singletonList(commandBuilder.toString()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
