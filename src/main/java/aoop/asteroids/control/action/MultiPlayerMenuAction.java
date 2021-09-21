package aoop.asteroids.control.action;

import aoop.asteroids.util.PanelEvents;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;

public class MultiPlayerMenuAction extends AbstractAction {

    private final PropertyChangeSupport changeSupport;

    public MultiPlayerMenuAction(PropertyChangeSupport changeSupport) {
        super("Multiplayer");
        this.changeSupport = changeSupport;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PanelEvents.firePanelSwitchEvent(changeSupport, PanelEvents.MULTI_PLAYER_MENU, this);
    }
}
