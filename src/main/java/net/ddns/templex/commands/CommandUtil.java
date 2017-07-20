package net.ddns.templex.commands;

import lombok.NonNull;
import net.ddns.templex.player.PlayerUtil;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandUtil {

    private CommandUtil() {
        throw new UnsupportedOperationException("Instantiation is not permitted.");
    }

    /**
     * Quick little method for pushing all the online players matching the tab complete request.
     *
     * @param event The event which triggered the tab complete request.
     */
    public static void pushAutocompletePlayers(@NonNull TabCompleteEvent event) {
        String filter = event.getCursor().substring(event.getCursor().lastIndexOf(' ') + 1);
        event.getSuggestions().addAll(getOnlinePlayersByFilter(filter));
    }

    /**
     * Returns the names of all online players which have the passed filter as the first characters of their name.
     *
     * @param filter The filter to check against.
     * @return names The names of the players, alphabetically sorted.
     */
    public static List<String> getOnlinePlayersByFilter(@NonNull String filter) {
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

    public static void tellOps(BaseComponent... components) {
        for (ProxiedPlayer op : PlayerUtil.getOnlineOps()) {
            op.sendMessage(components);
        }
    }

}
