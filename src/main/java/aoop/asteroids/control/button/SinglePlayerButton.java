package aoop.asteroids.control.button;

import aoop.asteroids.control.action.SinglePlayerAction;
import aoop.asteroids.model.Game;

import javax.swing.*;
import java.beans.PropertyChangeSupport;

public class SinglePlayerButton extends JButton {

    /**
     * Initialise the properties of this button
     */
    private void setButtonProperties() {
        setVerticalTextPosition(AbstractButton.CENTER);
        setHorizontalTextPosition(AbstractButton.CENTER);
    }


    public SinglePlayerButton(PropertyChangeSupport changeSupport, Game game) {
        super(new SinglePlayerAction(changeSupport, game));
        setButtonProperties();
    }
}
