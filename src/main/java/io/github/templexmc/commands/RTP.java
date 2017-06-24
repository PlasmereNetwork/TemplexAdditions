package io.github.templexmc.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.Collections;

/**
 * Randomly tps players.
 */
public class RTP extends TabbableCommand {

    public RTP() {
        super("rtp","tabbable.rtp");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
// TODO: 6/23/2017          if (strings[1] == null) {
            try {
                Daemon.getInstance().submitCommands(Collections.singletonList("/spreadplayers ~ ~ 700000 2000000 false " + commandSender.getName()));
                commandSender.sendMessage(new ComponentBuilder("You have been successfully randomly tped!").color(ChatColor.GREEN).create());
            } catch (InterruptedException e) {
                e.printStackTrace();
// TODO: 6/23/2017              }
// TODO: 6/23/2017          } else {
// TODO: 6/23/2017              try {
// TODO: 6/23/2017                  Daemon.getInstance().submitCommands(Collections.singletonList("/spreadplayers ~ ~ 700000 2000000 false " + strings[1]));
// TODO: 6/23/2017                  commandSender.sendMessage(new ComponentBuilder("You have been successfully randomly tped!").color(ChatColor.GREEN).create());
// TODO: 6/23/2017              } catch (InterruptedException e) {
// TODO: 6/23/2017                  e.printStackTrace();
// TODO: 6/23/2017              }
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {   Util.pushAutocompletePlayers(event);    }

}
