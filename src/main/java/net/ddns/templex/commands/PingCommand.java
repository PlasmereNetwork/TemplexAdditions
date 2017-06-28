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
 * Allows players to ping staff (or Nitrate1 specifically) if they need help.
 */
public class PingCommand extends TabbableCommand {

    public PingCommand() {  super("ping","templex.ping");    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            return;
        }
        if (strings[1] == null) {
            strings[1] = "owner";
        }
        if (strings[1] == "owner") {
            try {
                commandSender.sendMessage(new ComponentBuilder("Pong! Just pinged the owner!").color(ChatColor.BLUE).create());
                Daemon.getInstance().submitCommands(Collections.singletonList("/execute @a[team=Owner] ~ ~ ~ playsound minecraft:block.note.flute master @s"));
                wait(1);
                Daemon.getInstance().submitCommands(Collections.singletonList("/execute @a[team=Owner] ~ ~ ~ playsound minecraft:block.note.flute master @s"));
                wait(1);
                Daemon.getInstance().submitCommands(Collections.singletonList("/execute @a[team=Owner] ~ ~ ~ playsound minecraft:block.note.flute master @s"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (strings[1] == "staff") {
            try {
                commandSender.sendMessage(new ComponentBuilder("Pong! Just pinged all staff!").color(ChatColor.BLUE).create());
                Daemon.getInstance().submitCommands(Collections.singletonList("/execute @a[tag=OP] ~ ~ ~ playsound minecraft:block.note.flute master @s"));
                wait(1);
                Daemon.getInstance().submitCommands(Collections.singletonList("/execute @a[tag=OP] ~ ~ ~ playsound minecraft:block.note.flute master @s"));
                wait(1);
                Daemon.getInstance().submitCommands(Collections.singletonList("/execute @a[tag=OP] ~ ~ ~ playsound minecraft:block.note.flute master @s"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            commandSender.sendMessage(new ComponentBuilder("Incorrect usage!").color(ChatColor.RED).create());
            commandSender.sendMessage(new ComponentBuilder("Usage: /ping <staff | owner>").color(ChatColor.RED).create()); // More teams to be added at a later date.
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {    Util.pushAutocompletePlayers(event);    }
}
