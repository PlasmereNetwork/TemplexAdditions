package net.ddns.templex.world;

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
        return String.format("%s %s %s", getX(), getY(), getZ());
    }

}
