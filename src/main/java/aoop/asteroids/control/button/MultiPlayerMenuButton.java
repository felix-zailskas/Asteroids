package aoop.asteroids.control.button;

import aoop.asteroids.control.action.MultiPlayerMenuAction;

import javax.swing.*;
import java.beans.PropertyChangeSupport;

public class MultiPlayerMenuButton extends JButton {

    /**
     * Initialise the properties of this button
     */
    private void setButtonProperties() {
        setVerticalTextPosition(AbstractButton.CENTER);
        setHorizontalTextPosition(AbstractButton.CENTER);
    }


    public MultiPlayerMenuButton(PropertyChangeSupport changeSupport) {
        super(new MultiPlayerMenuAction(changeSupport));
        setButtonProperties();
    }
}
