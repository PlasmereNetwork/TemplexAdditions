package io.github.templexmc.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.Collections;

/**
 * Tps players to Spawn.
 */
public class Spawn extends TabbableCommand {

    public Spawn() {
        super("spawn","tabbable.spawn","sp");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        try {
            Daemon.getInstance().submitCommands(Collections.singletonList("/tp " + commandSender.getName() + " 721855 29 -5710"));
            commandSender.sendMessage(new ComponentBuilder("You have been successfully tped to Spawn!").color(ChatColor.GREEN).create());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {   Util.pushAutocompletePlayers(event);    }

}
