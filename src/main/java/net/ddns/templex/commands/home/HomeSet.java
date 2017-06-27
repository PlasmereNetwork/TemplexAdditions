package net.ddns.templex.commands.home;

import net.ddns.templex.game.CoordinateTriad;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HomeSet extends ConcurrentHashMap<String, CoordinateTriad> {
    @Getter
    private final UUID owner;

    public HomeSet(@NonNull UUID owner) {
        this.owner = owner;
    }
}
