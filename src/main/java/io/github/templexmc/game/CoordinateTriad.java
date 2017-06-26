package io.github.templexmc.game;

import lombok.Getter;

public class CoordinateTriad extends CoordinatePair {
    @Getter
    private final double y;

    public CoordinateTriad(double x, double y, double z) {
        super(x, z);
        this.y = y;
    }

    @Override
    public String toString() {
        return getX() + " " + getY() + " " + getZ();
    }

}
