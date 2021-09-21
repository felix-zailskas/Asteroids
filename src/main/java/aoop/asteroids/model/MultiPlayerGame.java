package aoop.asteroids.model;

import aoop.asteroids.control.MultiPlayerGameUpdater;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class MultiPlayerGame extends Game {

    private HashMap<Integer, Spaceship> players;
    private final Spaceship mainPlayer;
    private int mainPlayerIndex;

    public MultiPlayerGame(int playerIndex) {
        super();
        this.mainPlayer = new Spaceship();
        mainPlayer.reset();
        this.mainPlayerIndex = playerIndex;
        initializeGameData();
        System.out.println("SIZE END OF GAME CONSTRUCTOR: " + players.size());
    }

    public int getPlayerAmount() {
        return players.size();
    }

    @Override
    public Spaceship getSpaceship() {
        return mainPlayer;
    }

    @Override
    public void initializeGameData() {
        this.bullets = new CopyOnWriteArrayList<>();
        this.asteroids = new CopyOnWriteArrayList<>();
        this.players = new HashMap<>();
        players.put(mainPlayerIndex, mainPlayer);
        //TODO: RESET MAIN PLAYER SHIP (GIVES NULL POINTER EXCEPTION???)
//        System.out.println(mainPlayer.getEnergyPercentage());
    }

    public void adjustMainPlayer(int playerIndex) {
        players.remove(mainPlayerIndex);
        this.mainPlayerIndex = playerIndex;
        players.put(mainPlayerIndex, mainPlayer);
    }

    public void addNewPlayer(int playerIndex) {
        players.put(playerIndex, new Spaceship());
    }

    public void addNewPlayer(int playerIndex, String name) {
        Spaceship spaceship = new Spaceship();
        spaceship.setName(name);
        players.put(playerIndex, spaceship);
    }

    public void addNewPlayer(int playerIndex, Spaceship spaceship) {
        players.put(playerIndex, spaceship);
    }

    public void removePlayer(int playerIndex) {
        players.remove(playerIndex);
    }

    public HashMap<Integer, Spaceship> getPlayers() {
        return players;
    }

    /**
     * Using this game's current model, spools up a new game updater thread to begin a game loop and start processing
     * user input and physics updates. Only if the game isn't currently running, that is.
     */
    @Override
    public void start() {
        if (!this.running) {
            this.running = true;
            this.gameUpdaterThread = new Thread(new MultiPlayerGameUpdater(this));
            this.gameUpdaterThread.start();
        }
    }

    @Override
    public boolean isGameOver() {
        try {
            for (Spaceship ship : players.values()) {
                if (!ship.isDestroyed()) return false;
            }
        } catch (ConcurrentModificationException e) {
            return false;
        }
        return true;
    }
}
