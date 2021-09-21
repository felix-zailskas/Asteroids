package aoop.asteroids.control.action;

import aoop.asteroids.model.MultiPlayerGame;
import aoop.asteroids.network.client.Spectator;
import aoop.asteroids.util.PanelEvents;
import aoop.asteroids.util.Port;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;

public class SpectateAction extends AbstractAction {

    private final PropertyChangeSupport changeSupport;
    private final MultiPlayerGame game;
    private final JTextField port;

    public SpectateAction(PropertyChangeSupport changeSupport, MultiPlayerGame game, JTextField port) {
        super("Spectate Game");
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
        Spectator spectator = new Spectator(game, Integer.parseInt(port.getText()));
        if (!spectator.canConnectToCurrentPort()) {
            PanelEvents.firePanelSwitchEvent(changeSupport, PanelEvents.INVALID_PORT, this);
            return;
        }
        Thread t = new Thread(spectator);
        t.start();
        PanelEvents.firePanelSwitchEvent(changeSupport, PanelEvents.MULTI_PLAYER, this);
    }
}
