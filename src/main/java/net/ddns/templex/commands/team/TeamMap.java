package net.ddns.templex.commands.team;

import lombok.Value;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class TeamMap extends ConcurrentHashMap<String, TeamMap.Team> {
    @Value
    public static class Team {
        String format;
        ArrayList<String> members;
    }
}
