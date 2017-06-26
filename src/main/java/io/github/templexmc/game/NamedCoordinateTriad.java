package io.github.templexmc.game;

import lombok.Getter;

public class NamedCoordinateTriad extends CoordinateTriad {

    @Getter
    private final String name;

    public NamedCoordinateTriad(String name, double x, double y, double z) {
        super(x, y, z);
        this.name = name;
    }

}
