package io.github.templexmc.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.Collections;

/**
 * Command which makes the Daemon say hello. :D
 */
public class HelloCommand extends TabbableCommand {
    public HelloCommand() {
        super("hello", "templex.hello");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        StringBuilder response = new StringBuilder("Hello");
        if (strings.length != 0) {
            response.append(", ");
            response.append(strings[0]);
        }
        response.append('!');
        try {
            Daemon.getInstance().submitCommands(Collections.singletonList(response.toString()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        Util.pushAutocompletePlayers(event);
    }
}
