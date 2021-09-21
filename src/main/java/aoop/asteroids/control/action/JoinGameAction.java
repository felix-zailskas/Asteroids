package aoop.asteroids.control.action;

import aoop.asteroids.model.MultiPlayerGame;
import aoop.asteroids.network.client.Client;
import aoop.asteroids.util.PanelEvents;
import aoop.asteroids.util.Port;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;

public class JoinGameAction extends AbstractAction {

    private final PropertyChangeSupport changeSupport;
    private final MultiPlayerGame game;
    private final JTextField port;

    public JoinGameAction(PropertyChangeSupport changeSupport, MultiPlayerGame game, JTextField port) {
        super("Join Game");
        this.changeSupport = changeSupport;
        this.game = game;
        this.port = port;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Port.validPort(port.getText())) {
            PanelEvents.firePanelSwitchEvent(changeSupport, PanelEvents.INVALID_PORT, this);
            return;
        }
        Client client = new Client(game, Integer.parseInt(port.getText()));
        if (!client.canConnectToCurrentPort()) {
            PanelEvents.firePanelSwitchEvent(changeSupport, PanelEvents.INVALID_PORT, this);
            return;
        }
        Thread t = new Thread(client);
        t.start();
        PanelEvents.firePanelSwitchEvent(changeSupport, PanelEvents.MULTI_PLAYER, this);
    }
}
