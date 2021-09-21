package aoop.asteroids.control.action;

import aoop.asteroids.util.PanelEvents;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;

public class HighScoreAction extends AbstractAction {

    private final PropertyChangeSupport changeSupport;

    public HighScoreAction(PropertyChangeSupport changeSupport) {
        super("View Highscores");
        this.changeSupport = changeSupport;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PanelEvents.firePanelSwitchEvent(changeSupport, PanelEvents.HIGHSCORE, this);
    }
}
