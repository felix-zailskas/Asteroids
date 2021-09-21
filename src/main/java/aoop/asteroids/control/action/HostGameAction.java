package aoop.asteroids.control.action;

import aoop.asteroids.model.MultiPlayerGame;
import aoop.asteroids.network.server.Server;
import aoop.asteroids.util.PanelEvents;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;

public class HostGameAction extends AbstractAction {

    private final PropertyChangeSupport changeSupport;
    private final MultiPlayerGame game;

    public HostGameAction(PropertyChangeSupport changeSupport, MultiPlayerGame game) {
        super("Host Game");
        this.changeSupport = changeSupport;
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PanelEvents.firePanelSwitchEvent(changeSupport, PanelEvents.MULTI_PLAYER, this);
        game.quit();
        game.initializeGameData();
        game.getSpaceship().reset();
        Thread t = new Thread(new Server(game));
        t.start();
    }
}
