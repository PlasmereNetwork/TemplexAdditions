package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TPAHereCommand extends TabbableCommand {

    private final BaseComponent[] NO_ACTIVE_REQUESTS = new ComponentBuilder("You didn't have any active requests!").color(ChatColor.RED).create();
    private final BaseComponent[] ALREADY_ACTIVE_REQUEST = new ComponentBuilder("You already have an active request!").color(ChatColor.RED).create();
    private final BaseComponent[] TIMEOUT = new ComponentBuilder("Request timed out.").color(ChatColor.RED).create();

    /**
     * The set of currently active requests. This map is threadsafe.
     */
    private final Map<ProxiedPlayer, ProxiedPlayer> requests;

    /**
     * The scheduler which manages request timeouts. Requests will timeout after 10 seconds.
     */
    private final ScheduledExecutorService requestManager;

    public TPAHereCommand() {
        super("tpahere", "nonop");
        this.requests = new ConcurrentHashMap<>();
        requestManager = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void execute(final CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            return;
        }
        ProxyServer proxy = ProxyServer.getInstance();
        final ProxiedPlayer executor = (ProxiedPlayer) commandSender;
        if (strings.length == 0) {
            ProxiedPlayer target = null;
            for (Map.Entry<ProxiedPlayer, ProxiedPlayer> entry : requests.entrySet()) {
                if (entry.getValue().equals(executor)) {
                    target = entry.getKey();
                    break;
                }
            }
            if (target == null) {
                commandSender.sendMessage(NO_ACTIVE_REQUESTS);
            } else {
                requests.remove(target);
                Daemon instance = Daemon.getInstanceNow();
                if (instance == null) {
                    CommandUtil.daemonNotFound(commandSender);
                    return;
                }
                instance.submitCommands(Collections.singletonList(String.format("/tp %s %s", executor.getName(), target.getName())));
            }
            return;
        }
        if (requests.containsKey(executor)) {
            commandSender.sendMessage(ALREADY_ACTIVE_REQUEST);
        } else {
            final ProxiedPlayer requested = proxy.getPlayer(strings[0]);
            if (requested == null) {
                commandSender.sendMessage(new ComponentBuilder(String.format("Didn't recognize the name %s.", strings[0])).color(ChatColor.RED).create());
            } else {
                commandSender.sendMessage(new ComponentBuilder(String.format("Request sent to %s", strings[0])).color(ChatColor.GREEN).create());
                requested.sendMessage(new ComponentBuilder(String.format("%s requested that you TP to them! Type /tpahere to accept.", commandSender.getName())).color(ChatColor.GOLD).create());
                requests.put(executor, requested);
                requestManager.schedule(new Runnable() {
                    @Override
                    public void run() {
                        if (requests.remove(executor) != null) {
                            commandSender.sendMessage(TIMEOUT);
                            requested.sendMessage(new ComponentBuilder(String.format("Request from %s timed out.", commandSender.getName())).color(ChatColor.RED).create());
                        }
                    }
                }, 10, TimeUnit.SECONDS);
            }
        }
    }

    @Override
    public void handleTabCompleteEvent(TabCompleteEvent event) {
        CommandUtil.pushAutocompletePlayers(event);
    }
}
