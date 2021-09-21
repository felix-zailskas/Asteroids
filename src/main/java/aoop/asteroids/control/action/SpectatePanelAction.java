package aoop.asteroids.control.action;

import aoop.asteroids.util.PanelEvents;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;

public class SpectatePanelAction extends AbstractAction {

    private final PropertyChangeSupport changeSupport;

    public SpectatePanelAction(PropertyChangeSupport changeSupport) {
        super("Spectate Game");
        this.changeSupport = changeSupport;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PanelEvents.firePanelSwitchEvent(changeSupport, PanelEvents.SPECTATE, this);
    }
}
