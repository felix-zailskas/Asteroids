package aoop.asteroids.network;

import java.net.InetAddress;

public class ConnectionDetails {

    private final InetAddress ipAddress;
    private final int port;

    public ConnectionDetails(InetAddress ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }
}
