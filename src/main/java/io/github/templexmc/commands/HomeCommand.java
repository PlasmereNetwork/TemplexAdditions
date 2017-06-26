package io.github.templexmc.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.TabCompleteEvent;

/**
 * TODO
 */
public class HomeCommand extends TabbableCommand {

    public HomeCommand() {
        super("home", "tabbable.home", "h");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (strings.length > 1) {

        } else {
            commandSender.sendMessage(new ComponentBuilder("Incorrect usage!").color(ChatColor.RED).create());
            commandSender.sendMessage(new ComponentBuilder("Usage: /home <home name>").color(ChatColor.RED).create());
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        Util.pushAutocompletePlayers(event);
    }

}
