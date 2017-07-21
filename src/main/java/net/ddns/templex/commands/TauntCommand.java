package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collections;

public class TauntCommand extends TabbableCommand {
    public TauntCommand() {
        super("taunt", "special");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            return;
        }
        try {
            Daemon.getInstance().submitCommands(Collections.singletonList("")); // TODO
            commandSender.sendMessage(new ComponentBuilder("This is currently broken.").color(ChatColor.RED).create());
            CommandUtil.tellOps(
                    new ComponentBuilder("Taunt PL ").color(ChatColor.GOLD)
                            .append(": ").color(ChatColor.DARK_GRAY)
                            .append(commandSender.getName()).color(ChatColor.GREEN)
                            .append(" just taunted their surroundings!").color(ChatColor.RED)
                            .create()
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
