package io.github.templexmc.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.Collections;

/**
 * TODO
 */
public class Home extends TabbableCommand {

    public Home() {
        super("home","tabbable.home","h");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
// TODO: 6/22/2017          if (strings[1] ==  ){
// TODO: 6/22/2017
// TODO: 6/22/2017          } else {
// TODO: 6/22/2017              commandSender.sendMessage(new ComponentBuilder("Incorrect usage!").color(ChatColor.RED).create());
// TODO: 6/22/2017              commandSender.sendMessage(new ComponentBuilder("Usage: /home <home name>").color(ChatColor.RED).create());
// TODO: 6/22/2017          }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {   Util.pushAutocompletePlayers(event);    }

}
