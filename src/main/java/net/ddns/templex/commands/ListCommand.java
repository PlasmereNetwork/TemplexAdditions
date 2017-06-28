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
 * Shows players a list of players currently connected to the server.
 */
public class ListCommand extends TabbableCommand {

    public ListCommand() { super("ls","templex.ls"); }

    private String all;
    private String staff;
    private String nstaff;

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            return;
        }
        all = "@a[name=!VADaemon]";
        staff = "@a[name=!VADaemon,tag=OP]";
        nstaff = "@a[name=!VADaemon,tag=!OP]";
        if (strings[1] == null) {
            try{
                Daemon.getInstance().submitCommands(Collections.singletonList("/scoreboard players reset Z List"));
                Daemon.getInstance().submitCommands(Collections.singletonList("/execute " + all + " ~ ~ ~ scoreboard players add Z List 1"));
                Daemon.getInstance().submitCommands(Collections.singletonList("/tellraw " + commandSender + " [{\"text\":\"There are currently\",\"color\":\"green\"},{\"text\":\": \",\"color\":\"dark_gray\"},{\"score\":{\"name\":\"Z\",\"objective\":\"List\"},\"color\":\"gold\"},{\"text\":\" players on \",\"color\":\"green\"},{\"text\":\"Templex\",\"color\":\"dark_aqua\"},{\"text\":\"!\",\"color\":\"dark_gray\"},{\"text\":\"\\nPlayers online\":\"color\":\"green\"},{\"text\":\": \",\"color\":\"dark_gray\"},{\"selector\":\"" + all + "\"}]"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (strings[1] == "all") {
            try{
                Daemon.getInstance().submitCommands(Collections.singletonList("/scoreboard players reset Z List"));
                Daemon.getInstance().submitCommands(Collections.singletonList("/execute " + all + " ~ ~ ~ scoreboard players add Z List 1"));
                Daemon.getInstance().submitCommands(Collections.singletonList("/tellraw " + commandSender + " [{\"text\":\"There are currently\",\"color\":\"green\"},{\"text\":\": \",\"color\":\"dark_gray\"},{\"score\":{\"name\":\"Z\",\"objective\":\"List\"},\"color\":\"gold\"},{\"text\":\" players on \",\"color\":\"green\"},{\"text\":\"Templex\",\"color\":\"dark_aqua\"},{\"text\":\"!\",\"color\":\"dark_gray\"},{\"text\":\"\\nPlayers online\":\"color\":\"green\"},{\"text\":\": \",\"color\":\"dark_gray\"},{\"selector\":\"" + all + "\"}]"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (strings[1] == "staff") {
            try {
                Daemon.getInstance().submitCommands(Collections.singletonList("/scoreboard players reset Z List"));
                Daemon.getInstance().submitCommands(Collections.singletonList("/execute " + staff + " ~ ~ ~ scoreboard players add Z List 1"));
                Daemon.getInstance().submitCommands(Collections.singletonList("/tellraw " + commandSender + " [{\"text\":\"There are currently\",\"color\":\"green\"},{\"text\":\": \",\"color\":\"dark_gray\"},{\"score\":{\"name\":\"Z\",\"objective\":\"List\"},\"color\":\"gold\"},{\"text\":\" players on \",\"color\":\"green\"},{\"text\":\"Templex\",\"color\":\"dark_aqua\"},{\"text\":\"!\",\"color\":\"dark_gray\"},{\"text\":\"\\nPlayers online\":\"color\":\"green\"},{\"text\":\": \",\"color\":\"dark_gray\"},{\"selector\":\"" + staff + "\"}]"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (strings[1] == "nonstaff") {
            try{
                Daemon.getInstance().submitCommands(Collections.singletonList("/scoreboard players reset Z List"));
                Daemon.getInstance().submitCommands(Collections.singletonList("/execute " + nstaff + " ~ ~ ~ scoreboard players add Z List 1"));
                Daemon.getInstance().submitCommands(Collections.singletonList("/tellraw " + commandSender + " [{\"text\":\"There are currently\",\"color\":\"green\"},{\"text\":\": \",\"color\":\"dark_gray\"},{\"score\":{\"name\":\"Z\",\"objective\":\"List\"},\"color\":\"gold\"},{\"text\":\" players on \",\"color\":\"green\"},{\"text\":\"Templex\",\"color\":\"dark_aqua\"},{\"text\":\"!\",\"color\":\"dark_gray\"},{\"text\":\"\\nPlayers online\":\"color\":\"green\"},{\"text\":\": \",\"color\":\"dark_gray\"},{\"selector\":\"" + nstaff + "\"}]"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            commandSender.sendMessage(new ComponentBuilder("Incorrect usage!").color(ChatColor.RED).create());
            commandSender.sendMessage(new ComponentBuilder("Usage: /ls <all | staff | nonstaff>").color(ChatColor.RED).create());
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) { Util.pushAutocompletePlayers(event); }

}
