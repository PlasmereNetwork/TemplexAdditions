package net.ddns.templex.ping;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class PingListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    @SuppressWarnings("deprecation")
    public void onProxyPingEvent(ProxyPingEvent event) {
        ServerPing ping = event.getResponse();
        ping.setDescription("§7>> §3Templex §7<< §7- §6Powered by commands §8- §8[§41§7.§412§7.§4x§8]\n§4Hard §7§lVanilla §bSurvival §7- §b§o[Completely Vanilla]");
    }

}
