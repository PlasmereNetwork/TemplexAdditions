package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Arrays;

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
            Daemon.getInstance().submitCommands(Arrays.asList(
                    String.format("/gamemode s %s", commandSender.getName()),
                    String.format("/tellraw @a[tag=OP] [{\"text\":\"\\u00A76Survival PL \\u00A78: \\u00A7cSet \\u00A77%s's gamemode to Survival mode!\",\"color\":\"red\"}]", commandSender.getName())));
            commandSender.sendMessage(new ComponentBuilder("Your gamemode has been updated to Survival mode!").color(ChatColor.GREEN).create());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
