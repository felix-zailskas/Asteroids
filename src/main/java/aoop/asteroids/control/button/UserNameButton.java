package aoop.asteroids.control.button;

import aoop.asteroids.control.action.UserNameAction;

import javax.swing.*;
import java.beans.PropertyChangeSupport;

public class UserNameButton extends JButton {

    /**
     * Initialise the properties of this button
     */
    private void setButtonProperties() {
        setVerticalTextPosition(AbstractButton.CENTER);
        setHorizontalTextPosition(AbstractButton.CENTER);
    }


    public UserNameButton(PropertyChangeSupport changeSupport) {
        super(new UserNameAction(changeSupport));
        setButtonProperties();
    }
}
