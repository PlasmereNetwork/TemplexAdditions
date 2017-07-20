package net.ddns.templex.commands.team;

import net.ddns.templex.TemplexAdditionsPlugin;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class TeamHandler {

    private static final TeamMap.Team defaultTeam = new TeamMap.Team("§aNew Comer §7%s§r", new ArrayList<String>(0));
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

    public void removeFromTeams(String playerName) {
        for (TeamMap.Team team : map.values()) {
            if (team.getMembers().remove(playerName)) {
                return;
            }
        }
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playerName);
        if (player != null) {
            player.setDisplayName(String.format(defaultTeam.getFormat(), player));
        }
        saveMap();
    }

    public void changeTeam(String playerName, String teamName) {
        if (!map.containsKey(teamName)) {
            return;
        }
        TeamMap.Team targetTeam = map.get(teamName);
        if (targetTeam == null) {
            return;
        }
        removeFromTeams(playerName);
        targetTeam.getMembers().add(playerName);
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playerName);
        if (player != null) {
            player.setDisplayName(String.format(targetTeam.getFormat(), player));
        }
        saveMap();
    }

    public List<String> getTeams() {
        ArrayList<String> teams = new ArrayList<>(map.size());
        Enumeration<String> teamEnum = map.keys();
        while (teamEnum.hasMoreElements()) {
            teams.add(teamEnum.nextElement());
        }
        return teams;
    }

    public void addTeam(String teamName, TeamMap.Team team) {
        map.put(teamName, team);
        saveMap();
    }

    public void removeTeam(String teamName) {
        map.remove(teamName);
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
