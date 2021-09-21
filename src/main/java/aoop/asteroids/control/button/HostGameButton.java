package aoop.asteroids.control.button;

import aoop.asteroids.control.action.HostGameAction;
import aoop.asteroids.model.MultiPlayerGame;

import javax.swing.*;
import java.beans.PropertyChangeSupport;

public class HostGameButton extends JButton {

    /**
     * Initialise the properties of this button
     */
    private void setButtonProperties() {
        setVerticalTextPosition(AbstractButton.CENTER);
        setHorizontalTextPosition(AbstractButton.CENTER);
    }


    public HostGameButton(PropertyChangeSupport changeSupport, MultiPlayerGame game) {
        super(new HostGameAction(changeSupport, game));
        setButtonProperties();
    }
}
