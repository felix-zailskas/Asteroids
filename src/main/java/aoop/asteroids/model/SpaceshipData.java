package aoop.asteroids.model;

import aoop.asteroids.util.SerializeID;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class SpaceshipData implements Serializable {

    private static final long serialVersionUID = SerializeID.SHIP_DATA_ID;

    private final int index;
    private final boolean[] keys;
    private final Point2D.Double pos;
    private final double dir;
    private final double energy;
    private final String name;
    private final Color color;

    public SpaceshipData(Spaceship spaceship, int index) {
        this.index = index;
        this.keys = new boolean[]{spaceship.isAccelerating(),
                spaceship.isTurningLeft(),
                spaceship.isTurningRight(),
                spaceship.isFiring()};
        this.pos = spaceship.getLocation();
        this.dir = spaceship.getDirection();
        this.energy = spaceship.getEnergyPercentage();
        this.name = spaceship.getName();
        this.color = spaceship.getColor();
    }

    public Color getColor() {
        return color;
    }

    public double getEnergy() {
        return energy;
    }

    public String getName() {
        return name;
    }

    public boolean[] getKeys() {
        return keys;
    }

    public Point2D.Double getPos() {
        return pos;
    }

    public double getDir() {
        return dir;
    }

    public int getIndex() {
        return index;
    }
}
