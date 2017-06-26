package io.github.templexmc.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.Collections;

/**
 * Used for to change someone's gamemode to survival.
 */
public class SurvivalCommand extends TabbableCommand {

    public SurvivalCommand() {
        super("survival", "templex.survival", "s");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        try {
            Daemon.getInstance().submitCommands(Collections.singletonList("/gamemode s " + commandSender.getName()));
            Daemon.getInstance().submitCommands(Collections.singletonList("/tellraw @a[tag=OP] [{\"text\":\"\\u00A76Survival PL \\u00A78: \\u00A7cSet \\u00A77" + commandSender.getName() + "'s gamemode to Survival mode!\",\"color\":\"red\"}]"));
            // TODO Use component builder
            commandSender.sendMessage(new ComponentBuilder("Your gamemode has been updated to Survival mode!").color(ChatColor.GREEN).create());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
