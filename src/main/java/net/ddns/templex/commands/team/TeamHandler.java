package net.ddns.templex.commands.team;

import lombok.Getter;
import net.ddns.templex.TemplexAdditionsPlugin;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class TeamHandler {

    @Getter
    private final TeamMap.Team defaultTeam = new TeamMap.Team("§aNew Comer §7%s§r", new ArrayList<String>(0));
    private final TeamMap map;
    private final TemplexAdditionsPlugin plugin;

    public TeamHandler(TemplexAdditionsPlugin plugin) {
        this.plugin = plugin;
        TeamMap map;
        try {
            map = plugin.getConfigHandler().getConfig("teams.json", TeamMap.class);
        } catch (IOException e) {
            map = null;
            e.printStackTrace();
        }
        this.map = map;

        plugin.getAttributeHandler().getAttributes().put("team", new TeamAttribute(this));
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

    public TeamMap.Team removeFromTeams(String playerName) {
        for (TeamMap.Team team : map.values()) {
            if (team.getMembers().remove(playerName)) {
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playerName);
                if (player != null) {
                    player.setDisplayName(String.format(defaultTeam.getFormat(), player));
                }
                saveMap();
                return team;
            }
        }
        return null;
    }

    public TeamMap.Team changeTeam(String playerName, String teamName) {
        if (!map.containsKey(teamName)) {
            return null;
        }
        TeamMap.Team targetTeam = map.get(teamName);
        if (targetTeam == null) {
            return null;
        }
        TeamMap.Team previous = removeFromTeams(playerName);
        targetTeam.getMembers().add(playerName);
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playerName);
        if (player != null) {
            player.setDisplayName(String.format(targetTeam.getFormat(), player));
        }
        saveMap();
        return previous;
    }

    public List<String> getTeams() {
        ArrayList<String> teams = new ArrayList<>(map.size());
        Enumeration<String> teamEnum = map.keys();
        while (teamEnum.hasMoreElements()) {
            teams.add(teamEnum.nextElement());
        }
        return teams;
    }

    public TeamMap.Team getTeamByName(String name) {
        return map.get(name);
    }

    public void addTeam(String teamName, TeamMap.Team team) {
        map.putIfAbsent(teamName, team);
        saveMap();
    }

    public void removeTeam(String teamName) {
        TeamMap.Team team = map.remove(teamName);
        if (team != null) {
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                if (team.getMembers().contains(player.getName())) {
                    player.setDisplayName(String.format(defaultTeam.getFormat(), player.getName()));
                }
            }
        }
        saveMap();
    }

    private void saveMap() {
        try {
            plugin.getConfigHandler().saveConfig("teams.json", map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
