package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collections;

public class FireworkCommand extends TabbableCommand {

    public FireworkCommand() {
        super("firework", "special", "fw");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            return;
        }
        Daemon instance = Daemon.getInstanceNow();
        if (instance == null) {
            CommandUtil.daemonNotFound(commandSender);
            return;
        }
        instance.submitCommands(Collections.singletonList(String.format("/execute %s ~ ~ ~ summon fireworks_rocket ~ ~ ~ {LifeTime:40,Damage:0s}", commandSender.getName())));
        CommandUtil.tellOps(
                new ComponentBuilder("Firework PL ").color(ChatColor.GOLD)
                        .append(": ").color(ChatColor.DARK_GRAY)
                        .append(commandSender.getName()).color(ChatColor.GREEN)
                        .append(" just sent off a rocket!").color(ChatColor.RED)
                        .create()
        );
    }

}
