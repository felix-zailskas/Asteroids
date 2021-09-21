package aoop.asteroids.network.client;

import aoop.asteroids.game_observer.GameUpdateListener;
import aoop.asteroids.model.*;
import aoop.asteroids.network.ConnectionDetails;
import aoop.asteroids.network.PacketHandler;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collection;

public class Client extends PacketHandler implements Runnable, GameUpdateListener {

    private final MultiPlayerGame multiPlayerGame;
    private GameData gameData;
    private int threadNumber;
    private DatagramSocket ds;
    private boolean running;
    private final int port;

    public Client(MultiPlayerGame multiPlayerGame, int port) {
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
            sendSpaceshipData(ds, new SpaceshipData(multiPlayerGame.getSpaceship(), threadNumber), details);
        }
    }

    public boolean canConnectToCurrentPort() {
        boolean result = true;
        try {
            DatagramSocket socket = new DatagramSocket(port);
            result = false;
            socket.close();
        } catch(IOException e) {
            System.out.println("CANNOT CONNECT TO THE PORT!");
        }
            return result;
    }

    private void initializeConnection() {
        try {
            this.details = new ConnectionDetails(InetAddress.getLocalHost(), port);
            System.out.println(details.getIpAddress());
            // initialize to server
            sendInt(ds, 1, details);
            System.out.println(multiPlayerGame.getSpaceship().getName());
            sendString(ds, multiPlayerGame.getSpaceship().getName(), details);
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
        this.multiPlayerGame.adjustMainPlayer(threadNumber);
        updateShips();
        updateAsteroids();
    }

    private void receiveGameInfo() {
        GameData receivedData = receiveGameData(ds);
        if (receivedData != null) gameData = receivedData;
        updateShips();
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


    @Override
    public void onGameUpdated(long timeSinceLastTick) {
        updateAsteroids();
    }
}
