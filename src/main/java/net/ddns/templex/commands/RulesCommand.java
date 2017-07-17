package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class RulesCommand extends TabbableCommand {

    private final BaseComponent[] rules;

    public RulesCommand() {
        super("rules", "templex.nonop");
        this.rules =
                new ComponentBuilder("------- ").color(ChatColor.GRAY).append("RULES ").color(ChatColor.RED).append("-------").color(ChatColor.GRAY).append("\n")
                        .append("- ").color(ChatColor.LIGHT_PURPLE).append("No griefing/raiding. Don't even try.").color(ChatColor.DARK_GREEN).append("\n")
                        .append("- ").color(ChatColor.LIGHT_PURPLE).append("Do not swear. If you do so repeatedly, you may be banned.").color(ChatColor.DARK_GREEN).append("\n")
                        .append("- ").color(ChatColor.LIGHT_PURPLE).append("Do not spam.").color(ChatColor.DARK_GREEN).append("\n")
                        .append("- ").color(ChatColor.LIGHT_PURPLE).append("Respect the server staff members! If you offend them, they may kick or even ban you!").color(ChatColor.DARK_GREEN).append("\n")
                        .append("- ").color(ChatColor.LIGHT_PURPLE).append("Do not ask for ranks, items, op, or favors from ops!").color(ChatColor.DARK_GREEN).append("\n")
                        .append("- ").color(ChatColor.LIGHT_PURPLE).append("Be appropriate! We have young players here.").color(ChatColor.DARK_GREEN).append("\n")
                        .append("- ").color(ChatColor.LIGHT_PURPLE).append("Do not use mods, hacks, or ").color(ChatColor.DARK_GREEN).append("any resource pack ").color(ChatColor.RED).bold(true).reset().append("that gives you an advantage over other players!").color(ChatColor.DARK_GREEN).append("\n")
                        .append("- ").color(ChatColor.LIGHT_PURPLE).append("Do not spam kill or die!").color(ChatColor.DARK_GREEN).append("\n")
                        .append("- ").color(ChatColor.LIGHT_PURPLE).append("Do not PVP without consent!").color(ChatColor.DARK_GREEN).append("\n")
                        .append("- ").color(ChatColor.LIGHT_PURPLE).append("Respect everyone!").color(ChatColor.DARK_GREEN).append("\n")
                        .append("-------------------").color(ChatColor.GRAY)
                        .create();

    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        commandSender.sendMessage(rules);
    }
}
