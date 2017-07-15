package net.ddns.templex.world;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoordinatePair {
    @Getter
    private final double x;
    @Getter
    private final double z;

    @Override
    public String toString() {
        return String.format("%s %s", getX(), getZ());
    }

}
