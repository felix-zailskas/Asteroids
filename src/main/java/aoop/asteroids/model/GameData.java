package aoop.asteroids.model;

import aoop.asteroids.util.SerializeID;
//import sun.jvm.hotspot.memory.Space;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class GameData implements Serializable {

    private static final long serialVersionUID = SerializeID.GAME_DATA_ID;

    private final int index;
    private final Collection<Asteroid> asteroids;
    private final ArrayList<SpaceshipData> spaceshipData;

    public GameData(MultiPlayerGame game, int index) {
        this.index = index;
        this.asteroids = game.getAsteroids();
        this.spaceshipData = new ArrayList<>();
        ArrayList<Integer> keys = new ArrayList<>(game.getPlayers().keySet());
        int i = 0;
        for (Spaceship spaceship : game.getPlayers().values()) {
            spaceshipData.add(new SpaceshipData(spaceship, keys.get(i)));
            i++;
        }
    }

    public ArrayList<SpaceshipData> getSpaceshipData() {
        return spaceshipData;
    }

    public Collection<Asteroid> getAsteroids() {
        return this.asteroids;
    }

    public int getIndex() {
        return index;
    }
}
