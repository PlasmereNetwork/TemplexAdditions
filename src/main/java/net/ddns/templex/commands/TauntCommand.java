package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.Collections;

public class TauntCommand extends TabbableCommand {
    private final BaseComponent[] SYNTAX = (
            new ComponentBuilder("Syntax:\n/taunt <shulker|enderman>").color(ChatColor.RED)
                    .create()
            );
    private final BaseComponent[] ROAR = (
            new ComponentBuilder("Successfully taunted all nearby surroundings!").color(ChatColor.GREEN)
                    .create()
            );

    public TauntCommand() {super("taunt","special");}

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        final BaseComponent[] TELLOPS = (
                new ComponentBuilder("Taunt PL").color(ChatColor.GOLD)
                        .append(" : ").color(ChatColor.DARK_GRAY)
                        .append(commandSender.getName()).color(ChatColor.GRAY)
                        .append(" just taunted their surroundings!").color(ChatColor.RED)
                        .create()
        );
        if (strings.length == 0) {
            commandSender.sendMessage(SYNTAX);
            return;
        }
        switch (strings[0]) {
            case "shulker":
                try {
                    Daemon.getInstance().submitCommands(Collections.singletonList(String.format("/execute %s ~ ~ ~ execute @a[r=20] ~ ~ ~ playsound minecraft:entity.shulker.ambient master @s",commandSender.getName())));
                    commandSender.sendMessage(ROAR);
                    CommandUtil.tellOps(TELLOPS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            case "enderman":
                try {
                    Daemon.getInstance().submitCommands(Collections.singletonList(String.format("/execute %s ~ ~ ~ execute @a[r=20] ~ ~ ~ playsound minecraft:entity.endermen.stare master @s",commandSender.getName())));
                    commandSender.sendMessage(ROAR);
                    CommandUtil.tellOps(TELLOPS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}
