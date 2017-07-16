package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collections;

/**
 * Used for to change someone's gamemode to survival.
 */
public class SurvivalCommand extends TabbableCommand {

    public SurvivalCommand() {
        super("survival");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            return;
        }
        try {
            Daemon.getInstance().submitCommands(Collections.singletonList(String.format("/gamemode s %s", commandSender.getName())));
            CommandUtil.tellOps(
                    new ComponentBuilder("Survival PL ").color(ChatColor.GOLD)
                            .append(": ").color(ChatColor.DARK_GRAY)
                            .append("Successfully changed ").color(ChatColor.RED)
                            .append(commandSender.getName()).color(ChatColor.GRAY)
                            .append("'s gamemode to survival!").color(ChatColor.RED).create()
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
