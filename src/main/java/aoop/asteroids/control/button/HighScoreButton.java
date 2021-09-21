package aoop.asteroids.control.button;

import aoop.asteroids.control.action.HighScoreAction;

import javax.swing.*;
import java.beans.PropertyChangeSupport;

public class HighScoreButton extends JButton {

    /**
     * Initialise the properties of this button
     */
    private void setButtonProperties() {
        setVerticalTextPosition(AbstractButton.CENTER);
        setHorizontalTextPosition(AbstractButton.CENTER);
    }


    public HighScoreButton(PropertyChangeSupport changeSupport) {
        super(new HighScoreAction(changeSupport));
        setButtonProperties();
    }
}
