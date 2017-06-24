package io.github.templexmc.commands;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Util {

    private Util() {
        throw new UnsupportedOperationException("Instantiation is not permitted.");
    }

    /**
     * Quick little method for pushing all the online players matching the tab complete request.
     *
     * @param event The event which triggered the tab complete request.
     */
    public static void pushAutocompletePlayers(TabCompleteEvent event) {
        String filter = event.getCursor().substring(event.getCursor().lastIndexOf(' ') + 1);
        event.getSuggestions().addAll(getOnlinePlayersByFilter(filter));
    }

    /**
     * Returns the names of all online players which have the passed filter as the first characters of their name.
     *
     * @param filter The filter to check against.
     * @return names The names of the players, alphabetically sorted.
     */
    public static List<String> getOnlinePlayersByFilter(String filter) {
        filter = filter.toLowerCase();
        List<String> names = new ArrayList<>();
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            if (player.getName().toLowerCase().startsWith(filter)) {
                names.add(player.getName());
            }
        }
        Collections.sort(names);
        return names;
    }

}
