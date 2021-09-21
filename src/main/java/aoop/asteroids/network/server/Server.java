package aoop.asteroids.network.server;

import aoop.asteroids.model.MultiPlayerGame;
import aoop.asteroids.network.ConnectionDetails;
import aoop.asteroids.network.PacketHandler;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class Server extends PacketHandler implements Runnable {

    private int threadNumber;
    private boolean spectator;
    private final MultiPlayerGame multiPlayerGame;
    private boolean running;

    public Server() {
        this.threadNumber = 1;
        multiPlayerGame = null;
        this.running = false;
    }

    public Server(MultiPlayerGame game) {
        this.threadNumber = 1;
        this.multiPlayerGame = game;
        this.running = false;
    }

    public ConnectionDetails connect(DatagramSocket ds) {
        // get int from client
        // 1: Player
        // 0: Spectator
        DatagramPacket inital = receive(ds);
        assert inital != null;
        ByteBuffer b = ByteBuffer.wrap(inital.getData());
        spectator = b.getInt() != 1;
        return new ConnectionDetails(inital.getAddress(), inital.getPort());
    }

    @Override
    public void run() {
        try (DatagramSocket ds = new DatagramSocket()) {
            System.out.println(ds.getLocalAddress());
            System.out.println(ds.getLocalPort());
            running = true;
            assert multiPlayerGame != null;
            multiPlayerGame.start();
            while (running) {
                ConnectionDetails details = connect(ds);
                if (!spectator) {
                    String name = receiveString(ds);
                    multiPlayerGame.addNewPlayer(threadNumber, name);
                }
                System.out.println("ADDED PLAYER WITH SPECATE '" + spectator + "' to the Server.");
                Thread t = new Thread(new ThreadedClientHandler(threadNumber, details, multiPlayerGame));
                t.start();
                threadNumber++;
            }
        } catch (SocketException e) {
            //TODO: catch
            e.printStackTrace();
        }
    }
}
