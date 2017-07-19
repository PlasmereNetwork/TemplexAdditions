package net.ddns.templex.player.config;

import lombok.Value;

import java.util.ArrayList;

public class OPs extends ArrayList<OPs.OPsEntry> {
    @Value
    public class OPsEntry {
        String uuid;
        String name;
        int level;
        boolean bypassesPlayerLimit;
    }
}
