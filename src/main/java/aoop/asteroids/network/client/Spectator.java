package aoop.asteroids.network.client;

import aoop.asteroids.model.*;
import aoop.asteroids.network.ConnectionDetails;
import aoop.asteroids.network.PacketHandler;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collection;

public class Spectator extends PacketHandler implements Runnable {


    private final MultiPlayerGame multiPlayerGame;
    private GameData gameData;
    private int threadNumber;
    private DatagramSocket ds;
    private boolean running;
    private final int port;

    public Spectator(MultiPlayerGame multiPlayerGame, int port) {
        this.multiPlayerGame = multiPlayerGame;
        this.port = port;
        this.running = false;
        try {
            this.ds = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        initializeConnection();
        receiveInitialState();
        running = true;
        multiPlayerGame.start();
        while (running) {
            receiveGameInfo();
        }
    }

    public boolean canConnectToCurrentPort() {
        boolean result = false;
        try {
            DatagramSocket socket = new DatagramSocket(port);
            socket.close();
        } catch(IOException e) {
            result = true;
        }
        return result;
    }

    private void initializeConnection() {
        try {
            this.details = new ConnectionDetails(InetAddress.getLocalHost(), port);
            // initialize to server
            sendInt(ds, 0, details);
            DatagramPacket dp = receive(ds);
            // now we can send to the server
            this.details = new ConnectionDetails(dp.getAddress(), dp.getPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void receiveInitialState() {
        this.gameData = receiveGameData(ds);
        this.threadNumber = gameData.getIndex();
        updateShips();
        updateAsteroids();
    }

    private void receiveGameInfo() {
        GameData receivedData = receiveGameData(ds);
        if (receivedData != null) gameData = receivedData;
        updateShips();
        updateAsteroids();
    }

    private void updateShips() {
        ArrayList<SpaceshipData> spaceshipData = gameData.getSpaceshipData();
        int playerSize = spaceshipData.size();
        for (SpaceshipData currData : spaceshipData) {
            int index = currData.getIndex();
            if (index == threadNumber) continue;
            Spaceship currPlayer = multiPlayerGame.getPlayers().get(index);
            if (currPlayer == null) {
                multiPlayerGame.addNewPlayer(index, new Spaceship());
            }
            multiPlayerGame.getPlayers().get(index).updateValues(currData);
        }
    }


    private synchronized void updateAsteroids() {
        Collection<Asteroid> asteroids = gameData.getAsteroids();
        if (asteroids != null) multiPlayerGame.setAsteroids(asteroids);
    }

}
