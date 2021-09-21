package aoop.asteroids.control.action;

import aoop.asteroids.util.PanelEvents;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;

public class JoinPanelAction extends AbstractAction {

    private final PropertyChangeSupport changeSupport;

    public JoinPanelAction(PropertyChangeSupport changeSupport) {
        super("Join Game");
        this.changeSupport = changeSupport;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PanelEvents.firePanelSwitchEvent(changeSupport, PanelEvents.JOIN_GAME_MENU, this);
    }
}
