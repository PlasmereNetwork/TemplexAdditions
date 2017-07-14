package net.ddns.templex.ping;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class PingListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onProxyPingEvent(ProxyPingEvent event) {
        ServerPing ping = event.getResponse();
        ping.setDescriptionComponent(
                new TextComponent("Templex: Where snapshot innovation is forefront.")
        );
    }

}
