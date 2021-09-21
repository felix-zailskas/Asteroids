package aoop.asteroids.control.button;

import aoop.asteroids.control.action.SpectatePanelAction;

import javax.swing.*;
import java.beans.PropertyChangeSupport;

public class SpectatePanelButton extends JButton {
    /**
     * Initialise the properties of this button
     */
    private void setButtonProperties() {
        setVerticalTextPosition(AbstractButton.CENTER);
        setHorizontalTextPosition(AbstractButton.CENTER);
    }


    public SpectatePanelButton(PropertyChangeSupport changeSupport) {
        super(new SpectatePanelAction(changeSupport));
        setButtonProperties();
    }
}
