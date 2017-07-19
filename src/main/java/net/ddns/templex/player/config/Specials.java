package net.ddns.templex.player.config;

import lombok.Value;

import java.util.ArrayList;

public class Specials extends ArrayList<Specials.SpecialsEntry> {
    @Value
    public static class SpecialsEntry {
        String name;
    }
}
