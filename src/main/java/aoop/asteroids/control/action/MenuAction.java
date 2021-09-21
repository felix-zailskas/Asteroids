package aoop.asteroids.control.action;

import aoop.asteroids.util.PanelEvents;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;

public class MenuAction extends AbstractAction {

    private final PropertyChangeSupport changeSupport;

    public MenuAction(PropertyChangeSupport changeSupport) {
        super("Back To Menu");
        this.changeSupport = changeSupport;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PanelEvents.firePanelSwitchEvent(changeSupport, PanelEvents.MENU, this);
    }
}
