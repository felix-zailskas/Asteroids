package aoop.asteroids.control.button;

import aoop.asteroids.control.action.JoinGameAction;
import aoop.asteroids.model.MultiPlayerGame;

import javax.swing.*;
import java.beans.PropertyChangeSupport;

public class JoinGameButton extends JButton {

    /**
     * Initialise the properties of this button
     */
    private void setButtonProperties() {
        setVerticalTextPosition(AbstractButton.CENTER);
        setHorizontalTextPosition(AbstractButton.CENTER);
    }

    public JoinGameButton(PropertyChangeSupport changeSupport, MultiPlayerGame game, JTextField port) {
        super(new JoinGameAction(changeSupport, game, port));
        setButtonProperties();
    }
}
