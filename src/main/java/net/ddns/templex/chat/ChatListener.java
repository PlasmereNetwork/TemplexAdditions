package net.ddns.templex.chat;

import io.github.trulyfree.va.command.commands.TabbableCommand;
import io.github.trulyfree.va.daemon.Daemon;
import lombok.RequiredArgsConstructor;
import net.ddns.templex.TemplexAdditionsPlugin;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

@RequiredArgsConstructor
public class ChatListener implements Listener {

    private final TemplexAdditionsPlugin plugin;

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChatEvent(final ChatEvent event) {
        if (event.getSender() instanceof ProxiedPlayer
                && !event.getSender().equals(Daemon.getInstanceNow().getPlayer())) {
            if (event.isCommand()) {
                int firstSpace = event.getMessage().indexOf(" ");
                String name;
                if (firstSpace == -1) {
                    name = event.getMessage().substring(1);
                } else {
                    name = event.getMessage().substring(1, firstSpace);
                }
                for (TabbableCommand command : plugin.getAddedCommands()) {
                    if (command.getName().equals(name)) {
                        return;
                    }
                    for (String alias : command.getAliases()) {
                        if (alias.equals(name)) {
                            return;
                        }
                    }
                }
                ProxyServer.getInstance().getLogger().info(
                        String.format(
                                "%s executed command: %s",
                                event.getSender(),
                                event.getMessage()
                        )
                );
            } else {
                ProxyServer.getInstance().getLogger().info(
                        String.format(
                                "<%s> %s",
                                ((ProxiedPlayer) event.getSender()).getName(),
                                event.getMessage()
                        )
                );
            }
        }
    }

}
