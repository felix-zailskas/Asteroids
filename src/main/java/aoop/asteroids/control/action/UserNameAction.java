package aoop.asteroids.control.action;

import aoop.asteroids.util.PanelEvents;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;

public class UserNameAction extends AbstractAction {

    private final PropertyChangeSupport changeSupport;

    public UserNameAction(PropertyChangeSupport changeSupport) {
        super("Set Username");
        this.changeSupport = changeSupport;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PanelEvents.firePanelSwitchEvent(changeSupport, PanelEvents.USERNAME, this);
    }
}
