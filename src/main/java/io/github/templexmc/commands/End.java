package io.github.templexmc.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.Collections;

/**
 * Tps players to the End on the Templex server.
 */
public class End extends TabbableCommand {

    public End() {
        super("end","tabbable.end");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        try {
            Daemon.getInstance().submitCommands(Collections.singletonList("/tp " + commandSender.getName() + " -18970 35 -13209"));
            Daemon.getInstance().submitCommands(Collections.singletonList("/tellraw @a[tag=OP] [{\"text\":\"\\u00A76TP End \\u00A78: \\u00A7cSuccessfully tped \\u00A77" + commandSender.getName() + " to the End!\",\"color\":\"red\"}]"));
            commandSender.sendMessage(new ComponentBuilder("Successfully tped to the End!").color(ChatColor.GREEN).create());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {   Util.pushAutocompletePlayers(event);    }

}
