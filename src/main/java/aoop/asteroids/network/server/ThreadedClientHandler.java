package aoop.asteroids.network.server;

import aoop.asteroids.game_observer.GameUpdateListener;
import aoop.asteroids.model.*;
import aoop.asteroids.network.ConnectionDetails;
import aoop.asteroids.network.PacketHandler;

import java.net.DatagramSocket;
import java.net.SocketException;

public class ThreadedClientHandler extends PacketHandler implements Runnable, GameUpdateListener {

    private final int threadNumber;
    private DatagramSocket ds;
    private boolean running;
    private final MultiPlayerGame multiPlayerGame;

    public ThreadedClientHandler(int threadNumber, ConnectionDetails details, MultiPlayerGame multiPlayerGame) {
        this.threadNumber = threadNumber;
        this.details = details;
        this.multiPlayerGame = multiPlayerGame;
        this.running = false;
        try {
            this.ds = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        multiPlayerGame.addListener(this);
        sendInt(ds, 0, details);
    }

    @Override
    public void onGameUpdated(long timeSinceLastTick) {
        sendGameData(ds, new GameData(multiPlayerGame, threadNumber), details);
    }

    @Override
    public void run() {
        synchronized (this.multiPlayerGame.getGameUpdaterThread()) {
            sendClientInitialData();
        }
        running = true;
        while (running) {
            receiveAndUpdatePlayerInput();
        }
    }

    private void receiveAndUpdatePlayerInput() {
        SpaceshipData data = receiveSpaceshipData(ds);
        multiPlayerGame.getPlayers().get(data.getIndex()).updateValues(data);
    }

    private void sendClientInitialData() {
        sendGameData(ds, new GameData(multiPlayerGame, threadNumber), details);
    }

}
