package net.ddns.templex.commands;

import com.google.common.net.InetAddresses;
import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.net.InetAddress;
import java.util.Collections;

public class BanIPCommand extends TabbableCommand {

    public BanIPCommand() {
        super("ban-ip", "op");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if (strings.length == 0) {
            player.sendMessage(new ComponentBuilder("A player or IP must be specified!").color(ChatColor.RED).create());
            return;
        }
        InetAddress toBan;
        ProxiedPlayer playerToBan = ProxyServer.getInstance().getPlayer(strings[0]);
        if (playerToBan == null) {
            try {
                toBan = InetAddresses.forString(strings[0]);
            } catch (IllegalArgumentException e) {
                player.sendMessage(new ComponentBuilder("Player name was not recognized or IP was malformed.").color(ChatColor.RED).create());
                return;
            }
        } else {
            toBan = playerToBan.getAddress().getAddress();
        }
        try {
            Daemon instance = Daemon.getInstance();
            StringBuilder commandBuilder = new StringBuilder("/execute @s ~ ~ ~ ban-ip "); // Use execute for backend commands.
            commandBuilder.append(toBan.getHostAddress());
            for (int i = 1; i < strings.length; i++) {
                commandBuilder.append(" ");
                commandBuilder.append(strings[i]);
            }
            instance.submitCommands(Collections.singletonList(commandBuilder.toString()));
            for (ProxiedPlayer item : ProxyServer.getInstance().getPlayers()) {
                if (item.getAddress().getAddress().equals(toBan)) {
                    item.disconnect(new TextComponent("Banned by an operator."));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
