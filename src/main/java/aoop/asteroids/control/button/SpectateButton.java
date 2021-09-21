package aoop.asteroids.control.button;

import aoop.asteroids.control.action.SpectateAction;
import aoop.asteroids.model.MultiPlayerGame;

import javax.swing.*;
import java.beans.PropertyChangeSupport;

public class SpectateButton extends JButton{

    /**
     * Initialise the properties of this button
     */
    private void setButtonProperties() {
        setVerticalTextPosition(AbstractButton.CENTER);
        setHorizontalTextPosition(AbstractButton.CENTER);
    }


    public SpectateButton(PropertyChangeSupport changeSupport, MultiPlayerGame game, JTextField port) {
        super(new SpectateAction(changeSupport, game, port));
        setButtonProperties();
    }
}
