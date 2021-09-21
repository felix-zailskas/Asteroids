package aoop.asteroids.network;

import aoop.asteroids.model.GameData;
import aoop.asteroids.model.SpaceshipData;
//import javafx.util.Pair;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;

public abstract class PacketHandler {
    // keep data packets as small as possible
    // only send data that you really need
    // ensure DatagramPackets are processed in the right order (use extension of DatagramPacket)

    //TODO: reduce to appropriate size
    private final static int MAX_SIZE = 64000;
    protected ConnectionDetails details;

    public byte[] getByteData(int value) {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.putInt(value);
        return b.array();
    }

    public void sendInt(DatagramSocket socket, int value, ConnectionDetails details) {
        byte[] data = getByteData(value);
        try {
            socket.send(new DatagramPacket(data, data.length, details.getIpAddress(), details.getPort()));
        } catch (IOException e) {
            //TODO: catch
            e.printStackTrace();
        }
    }

    public void sendString(DatagramSocket socket, String s, ConnectionDetails details) {
        byte[] data = s.getBytes();
        try {
            socket.send(new DatagramPacket(data, data.length, details.getIpAddress(), details.getPort()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendSpaceshipData(DatagramSocket socket, SpaceshipData sData, ConnectionDetails details) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(sData);
            outputStream.flush();
            outputStream.close();
            byte[] data = byteArrayOutputStream.toByteArray();
            socket.send(new DatagramPacket(data, data.length, details.getIpAddress(), details.getPort()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendGameData(DatagramSocket socket, GameData pair, ConnectionDetails details) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(pair);
            outputStream.flush();
            outputStream.close();
            byte[] data = byteArrayOutputStream.toByteArray();
            socket.send(new DatagramPacket(data, data.length, details.getIpAddress(), details.getPort()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public DatagramPacket receive(DatagramSocket socket) {
        byte[] data = new byte[MAX_SIZE];
        //create packet to receive in
        DatagramPacket packet = new DatagramPacket(data, data.length);
        try {
            socket.receive(packet);
            return packet;
        } catch (IOException e) {
            //TODO:catch
            e.printStackTrace();
        }
        return null;
    }

    public SpaceshipData receiveSpaceshipData(DatagramSocket socket) {
        DatagramPacket packet = receive(socket);
        SpaceshipData data = null;
        assert packet != null;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
            data = (SpaceshipData) inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    public GameData receiveGameData(DatagramSocket socket) {
        DatagramPacket packet = receive(socket);
        GameData pair = null;
        assert packet != null;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
            pair = (GameData) inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return pair;
    }

    public String receiveString(DatagramSocket socket) {
        DatagramPacket packet = receive(socket);
        String s;
        assert packet != null;
        s = new String(packet.getData(), 0, packet.getLength());
        return s;
    }

}
