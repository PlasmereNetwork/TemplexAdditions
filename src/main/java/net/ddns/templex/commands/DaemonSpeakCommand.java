package net.ddns.templex.commands;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class DaemonSpeakCommand extends TabbableCommand {

    private final BaseComponent[] MESSAGE_NEEDED = new ComponentBuilder("Need a message to send!").color(ChatColor.RED).create();
    private final BaseComponent[] DAEMON_NEEDED = new ComponentBuilder("Daemon is not connected to the server.").color(ChatColor.RED).create();

    public DaemonSpeakCommand() {
        super("daemonspeak", "op", "dsay");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage(MESSAGE_NEEDED);
        } else if (!Daemon.hasInstance()) {
            commandSender.sendMessage(DAEMON_NEEDED);
        }
        StringBuilder messageBuilder = new StringBuilder(strings[0]);
        for (int i = 1; i < strings.length; i++) {
            messageBuilder.append(' ');
            messageBuilder.append(strings[i]);
        }
        TranslatableComponent translatableComponent = new TranslatableComponent("chat.type.text");
        translatableComponent.addWith(Daemon.getInstanceNow().getPlayer().getDisplayName());
        translatableComponent.addWith(messageBuilder.toString());
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            player.sendMessage(translatableComponent);
        }
    }
}
