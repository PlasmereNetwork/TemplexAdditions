package net.ddns.templex.commands.team;

import lombok.RequiredArgsConstructor;
import net.ddns.templex.commands.attribute.Attribute;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.List;

@RequiredArgsConstructor
public class TeamAttribute implements Attribute {

    private final TeamHandler handler;

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event, List<String> items) {
        if (items.size() == 4) {
            for (String team : handler.getTeams()) {
                if (team.startsWith(items.get(3))) {
                    event.getSuggestions().add(team);
                }
            }
        }
    }

    @Override
    public BaseComponent[] applyValue(ProxiedPlayer player, String[] strings) {
        if (strings.length == 2) {
            handler.removeFromTeams(player.getName());
            return new ComponentBuilder(String.format("Removed %s from their team.", player.getDisplayName())).color(ChatColor.GREEN).create();
        } else {
            handler.changeTeam(player.getName(), strings[2]);
            return new ComponentBuilder(String.format("Moved %s to team %s.", player.getDisplayName(), strings[2])).color(ChatColor.GREEN).create();
        }
    }
}
