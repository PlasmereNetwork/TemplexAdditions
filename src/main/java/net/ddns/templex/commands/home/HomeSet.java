package net.ddns.templex.commands.home;

import lombok.Getter;
import lombok.NonNull;
import net.ddns.templex.world.CoordinateTriad;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HomeSet {
    @Getter
    private final UUID owner;

    @Getter
    private final ConcurrentHashMap<String, CoordinateTriad> homes;

    public HomeSet(@NonNull UUID owner) {
        this(owner, new ConcurrentHashMap<String, CoordinateTriad>());
    }

    public HomeSet(@NonNull UUID owner, ConcurrentHashMap<String, CoordinateTriad> homes) {
        this.owner = owner;
        this.homes = homes;
    }

    public boolean equals(Object anObject) {
        return anObject instanceof UUID && anObject.equals(owner);
    }
}
