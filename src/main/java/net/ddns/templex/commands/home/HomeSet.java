package net.ddns.templex.commands.home;

import lombok.Getter;
import lombok.NonNull;
import net.ddns.templex.game.CoordinateTriad;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HomeSet extends ConcurrentHashMap<String, CoordinateTriad> {
    @Getter
    private final UUID owner;

    public HomeSet(@NonNull UUID owner) {
        this.owner = owner;
    }
}
