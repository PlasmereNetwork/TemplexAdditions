package net.ddns.templex.commands.team;

import net.ddns.templex.TemplexAdditionsPlugin;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.IOException;
import java.util.ArrayList;

public class TeamHandler {

    private final TeamMap map;
    private static final TeamMap.Team defaultTeam = new TeamMap.Team("§aNew Comer %s§r", new ArrayList<String>(0));

    public TeamHandler(TemplexAdditionsPlugin plugin) {
        TeamMap map;
        try {
            map = plugin.getConfigHandler().getConfig("teams.json", TeamMap.class);
        } catch (IOException e) {
            map = null;
            e.printStackTrace();
        }
        this.map = map;
    }

    public void assignTeam(ProxiedPlayer player) {
        for (TeamMap.Team team : map.values()) {
            if (team.getMembers().contains(player.getName())) {
                player.setDisplayName(String.format(team.getFormat(), player));
                return;
            }
        }
        player.setDisplayName(String.format(defaultTeam.getFormat(), player));
    }

    public boolean changeTeam(String playerName, String teamName) {
        if (!map.containsKey(teamName)) {
            return false;
        }
        for (TeamMap.Team team : map.values()) {
            if (team.getMembers().remove(playerName)) {
                break;
            }
        }
        TeamMap.Team team = map.get(teamName);
        team.getMembers().add(playerName);
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playerName);
        if (player != null) {
            player.setDisplayName(String.format(team.getFormat(), player));
        }
        return true;
    }

    public void addTeam(String teamName, TeamMap.Team team) {
        map.put(teamName, team);
    }

    public void removeTeam(String teamName) {
        map.remove(teamName);
    }

}
