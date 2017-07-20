package net.ddns.templex.commands.team;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.ArrayList;

public class TeamCommand extends TabbableCommand {

    private BaseComponent[] SYNTAX = new ComponentBuilder("Syntax:\n/team <add|remove>").color(ChatColor.RED).create();
    private BaseComponent[] SYNTAX_ADD = new ComponentBuilder("Syntax:\n/team add <team name> <format>").color(ChatColor.RED).create();
    private BaseComponent[] SYNTAX_REMOVE = new ComponentBuilder("Syntax:\n/team remove <team name>").color(ChatColor.RED).create();

    private final TeamHandler handler;

    public TeamCommand(TeamHandler handler) {
        super("team", "op");
        this.handler = handler;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage(SYNTAX);
            return;
        }
        switch (strings[0]) {
            case "add":
                if (strings.length < 3) {
                    commandSender.sendMessage(SYNTAX_ADD);
                    return;
                }
                StringBuilder formatBuilder = new StringBuilder(strings[2]);
                for (int i = 3; i < strings.length; i++) {
                    formatBuilder.append(' ');
                    formatBuilder.append(strings[i]);
                }
                handler.addTeam(strings[1], new TeamMap.Team(formatBuilder.toString(), new ArrayList<String>()));
                commandSender.sendMessage(
                        new ComponentBuilder(String.format("Team %s added successfully.\nAdd players with /attr <playername> team <team name>.\nRemove players from their teams with /attr <playername> team", strings[1])).color(ChatColor.GREEN).create()
                );
                return;
            case "remove":
                if (strings.length != 2) {
                    commandSender.sendMessage(SYNTAX_REMOVE);
                    return;
                }
                handler.removeTeam(strings[1]);
                commandSender.sendMessage(
                        new ComponentBuilder(String.format("Team %s removed successfully.", strings[1])).color(ChatColor.GREEN).create()
                );
                return;
            default:
                commandSender.sendMessage(SYNTAX);
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {

    }
}
