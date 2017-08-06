package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collections;

public class BedCommand extends TabbableCommand {
    public BedCommand() {
        super("bedpoint", "nonop", "bed");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            return;
        }
        try {
            Daemon.getInstance().submitCommands(Collections.singletonList(String.format("/execute %s ~ ~ ~ spawnpoint @s ~ ~ ~", commandSender.getName())));
            commandSender.sendMessage(new
                    ComponentBuilder("Successfully set your spawnpoint!")
                    .color(ChatColor.GREEN)
                    .create()
            );
            CommandUtil.tellOps(
                    new ComponentBuilder("Bed Point PL ").color(ChatColor.GOLD)
                            .append(": ").color(ChatColor.DARK_GRAY)
                            .append("Successfully set ").color(ChatColor.RED)
                            .append(commandSender.getName()).color(ChatColor.GRAY)
                            .append("'s spawnpoint!").color(ChatColor.RED).create()
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
