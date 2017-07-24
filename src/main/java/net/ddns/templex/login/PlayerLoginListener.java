package net.ddns.templex.login;

import com.google.common.net.InetAddresses;
import io.github.trulyfree.va.command.commands.TabbableCommand;
import net.ddns.templex.TemplexAdditionsPlugin;
import net.ddns.templex.commands.SpawnCommand;
import net.ddns.templex.player.config.BannedIPs;
import net.ddns.templex.player.config.OPs;
import net.ddns.templex.player.config.Specials;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerLoginListener implements Listener {

    private final TemplexAdditionsPlugin plugin;

    private final List<String> joined;

    private SpawnCommand spawnCommand = null;

    @SuppressWarnings("unchecked")
    public PlayerLoginListener(TemplexAdditionsPlugin plugin) {
        this.plugin = plugin;
        List<String> joined;
        try {
            List<String> temp = plugin.getConfigHandler().getConfig("joined.json", List.class);
            if (temp == null) {
                throw new IOException("No joined.json file was found.");
            }
            joined = Collections.synchronizedList(temp);
        } catch (IOException e) {
            e.printStackTrace();
            joined = Collections.synchronizedList(new ArrayList<String>());
        }
        this.joined = joined;
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PlayerLoginListener.this.plugin.getConfigHandler().saveConfig("joined.json", PlayerLoginListener.this.joined);
                } catch (IOException ignored) {
                }
            }
        }));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPreLoginEvent(PreLoginEvent event) {
        try {
            BannedIPs bannedIPs = plugin.getConfigHandler().getConfig("banned-ips.json", BannedIPs.class);
            for (BannedIPs.BannedIPsEntry entry : bannedIPs) {
                if (event.getConnection().getAddress().getAddress().equals(InetAddresses.forString(entry.getIp()))) {
                    event.setCancelled(true);
                    event.setCancelReason(entry.getReason());
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            event.setCancelled(true);
            event.setCancelReason("Could not determine IP validity.");
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPostLoginEvent(PostLoginEvent event) {
        final ProxiedPlayer player = event.getPlayer();
        plugin.getBackgroundExecutor().submit(new Runnable() {
            public void run() {
                plugin.getTeamHandler().assignTeam(player);
                establishOp(player);
                establishSpecial(player);
                player.setPermission("nonop", true);
                if (!joined.contains(player)) {
                    joined.add(player.getName());
                    if (spawnCommand == null) {
                        for (TabbableCommand command : plugin.getAddedCommands()) {
                            if (command instanceof SpawnCommand) {
                                spawnCommand = (SpawnCommand) command;
                            }
                        }
                    }
                    spawnCommand.execute(player, new String[0]);
                }
            }
        });
    }

    private void establishOp(ProxiedPlayer player) {
        try {
            OPs ops = plugin.getConfigHandler().getConfig("ops.json", OPs.class);
            player.setPermission("op", false);
            for (OPs.OPsEntry entry : ops) {
                if (player.getName().equals(entry.getName())) {
                    player.setPermission("op", true);
                    plugin.getLogger().info(String.format("Marked %s as an operator.", player.getName()));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void establishSpecial(ProxiedPlayer player) {
        try {
            Specials specials = plugin.getConfigHandler().getConfig("special.json", Specials.class);
            player.setPermission("special", false);
            for (Specials.SpecialsEntry entry : specials) {
                if (player.getName().equals(entry.getName())) {
                    player.setPermission("special", true);
                    plugin.getLogger().info(String.format("Marked %s as a special player.", player.getName()));
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}