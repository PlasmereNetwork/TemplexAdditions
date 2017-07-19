package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TPACommand extends TabbableCommand {

    /**
     * The set of currently active requests. This map is threadsafe.
     */
    private final Map<ProxiedPlayer, ProxiedPlayer> requests;

    /**
     * The scheduler which manages request timeouts. Requests will timeout after 10 seconds.
     */
    private final ScheduledExecutorService requestManager;

    public TPACommand() {
        super("tpa", "nonop");
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
                commandSender.sendMessage(new ComponentBuilder("You didn't have any active requests!").color(ChatColor.RED).create());
            } else {
                requests.remove(target);
                try {
                    Daemon.getInstance().submitCommands(Collections.singletonList("/tp " + target.getName() + " " + executor.getName()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return;
        }
        if (requests.containsKey(executor)) {
            commandSender.sendMessage(new ComponentBuilder("You already have an active request.").color(ChatColor.RED).create());
        } else {
            final ProxiedPlayer requested = proxy.getPlayer(strings[0]);
            if (requested == null) {
                commandSender.sendMessage(new ComponentBuilder("Didn't recognize the name " + strings[0] + ".").color(ChatColor.RED).create());
            } else {
                commandSender.sendMessage(new ComponentBuilder("Request sent to " + strings[0]).color(ChatColor.GREEN).create());
                requested.sendMessage(new ComponentBuilder(commandSender.getName() + " requested a TP to you! Type /tpa to accept.").color(ChatColor.GOLD).create());
                requests.put(executor, requested);
                requestManager.schedule(new Runnable() {
                    @Override
                    public void run() {
                        if (requests.remove(executor) != null) {
                            commandSender.sendMessage(new ComponentBuilder("Request timed out.").color(ChatColor.RED).create());
                            requested.sendMessage(new ComponentBuilder("Request from " + commandSender.getName() + " timed out.").color(ChatColor.RED).create());
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