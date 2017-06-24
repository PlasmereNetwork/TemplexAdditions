package io.github.templexmc.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.Collections;

/**
 * TODO
 */
public class RTP extends TabbableCommand {

    public RTP() {
        super("rtp","tabbable.rtp");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        try {
            Daemon.getInstance().submitCommands(Collections.singletonList("/spreadplayers ~ ~ 700000 2000000 false " + commandSender.getName()));
            commandSender.sendMessage(new ComponentBuilder("You have been successfully randomly tped!").color(ChatColor.GREEN).create());
         } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {   Util.pushAutocompletePlayers(event);    }

}
