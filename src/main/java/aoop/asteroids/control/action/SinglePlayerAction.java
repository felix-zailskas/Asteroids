package aoop.asteroids.control.action;

import aoop.asteroids.model.Game;
import aoop.asteroids.util.PanelEvents;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;

public class SinglePlayerAction extends AbstractAction {

    private final Game game;
    private final PropertyChangeSupport changeSupport;

    public SinglePlayerAction(PropertyChangeSupport changeSupport, Game game) {
        super("Single Player");
        this.changeSupport = changeSupport;
        this.game = game;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        PanelEvents.firePanelSwitchEvent(changeSupport, PanelEvents.SINGLE_PLAYER, this);
        this.game.quit(); // Try to stop the game if it's currently running.
        this.game.initializeGameData(); // Resets the game's objects to their default state.
        this.game.start(); // Spools up the game's engine and starts the main game loop.
    }
}
