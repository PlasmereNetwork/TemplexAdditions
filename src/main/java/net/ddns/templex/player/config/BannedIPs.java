package net.ddns.templex.player.config;

import lombok.Value;

import java.util.ArrayList;

public class BannedIPs extends ArrayList<BannedIPs.BannedIPsEntry> {
    @Value
    public static class BannedIPsEntry {
        String ip;
        String created;
        String source;
        String expires;
        String reason;
    }
}
