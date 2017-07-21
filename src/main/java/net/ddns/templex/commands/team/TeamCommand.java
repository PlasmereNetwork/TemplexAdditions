package net.ddns.templex.commands.team;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TeamCommand extends TabbableCommand {

    private BaseComponent[] SYNTAX = new ComponentBuilder("Syntax:\n/team <add|remove> ...").color(ChatColor.RED).create();
    private BaseComponent[] SYNTAX_ADD = new ComponentBuilder("Syntax:\n/team add <team name> <format>").color(ChatColor.RED).create();
    private BaseComponent[] SYNTAX_REMOVE = new ComponentBuilder("Syntax:\n/team remove <team name>").color(ChatColor.RED).create();

    private final List<String> options = Collections.unmodifiableList(Arrays.asList(
            "add",
            "remove",
            "list"
    ));

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
        switch (strings[0].toLowerCase()) {
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
                handler.addTeam(strings[1], new TeamMap.Team(formatBuilder.toString().replace('&', '§'), new ArrayList<String>()));
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
            case "list":
                if (strings.length == 1) {
                    ComponentBuilder builder = new ComponentBuilder("Current teams:").color(ChatColor.GREEN);
                    for (String name : handler.getTeams()) {
                        TeamMap.Team team = handler.getTeamByName(name);
                        builder.append("\n - ").color(ChatColor.WHITE)
                                .append(name).color(ChatColor.GREEN)
                                .append("\n   - ").color(ChatColor.WHITE)
                                .append(Integer.toString(team.getMembers().size())).color(ChatColor.GRAY)
                                .append(String.format(" player%s", (team.getMembers().size() != 1) ? "" : "s")).color(ChatColor.GRAY)
                                .append("\n   - ").color(ChatColor.WHITE)
                                .append("Format: ").color(ChatColor.GRAY)
                                .append(String.format(team.getFormat(), "Player"));
                    }
                    commandSender.sendMessage(builder.create());
                } else {
                    String name = strings[1];
                    TeamMap.Team team = handler.getTeamByName(name);
                    if (team != null) {
                        ComponentBuilder builder = new ComponentBuilder(String.format("Players of team %s:", name)).color(ChatColor.GREEN);
                        for (String player : team.getMembers()) {
                            builder.append("\n - ").color(ChatColor.WHITE)
                                    .append(String.format(team.getFormat(), player));
                        }
                        commandSender.sendMessage(builder.create());
                    }
                }
                return;
            default:
                commandSender.sendMessage(SYNTAX);
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        String[] items = event.getCursor().split(" ");
        switch (items.length) {
            case 2:
                for (String option : options) {
                    if (option.startsWith(items[1].toLowerCase())) {
                        event.getSuggestions().add(option);
                    }
                }
                return;
            case 3:
                switch (items[1]) {
                    case "remove":
                    case "list":
                        for (String name : handler.getTeams()) {
                            if (name.startsWith(items[2])) {
                                event.getSuggestions().add(name);
                            }
                        }
                }
        }
    }
}
