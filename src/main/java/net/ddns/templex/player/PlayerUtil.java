package net.ddns.templex.player;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public final class PlayerUtil {

    private PlayerUtil() {
        throw new UnsupportedOperationException("Instantiation is not permitted.");
    }

    public static Collection<ProxiedPlayer> getOnlineOps() {
        ArrayList<ProxiedPlayer> ops = new ArrayList<>();
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            if (player.getPermissions().contains("templex.op")) {
                ops.add(player);
            }
        }
        return Collections.unmodifiableCollection(ops);
    }

}
