package aoop.asteroids.control.button;

import aoop.asteroids.control.action.MenuAction;

import javax.swing.*;
import java.beans.PropertyChangeSupport;

public class MenuButton extends JButton {

    /**
     * Initialise the properties of this button
     */
    private void setButtonProperties() {
        setVerticalTextPosition(AbstractButton.CENTER);
        setHorizontalTextPosition(AbstractButton.CENTER);
    }


    public MenuButton(PropertyChangeSupport changeSupport) {
        super(new MenuAction(changeSupport));
        setButtonProperties();
    }
}
