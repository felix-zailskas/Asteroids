package aoop.asteroids.control.button;

import aoop.asteroids.control.action.JoinPanelAction;

import javax.swing.*;
import java.beans.PropertyChangeSupport;

public class JoinPanelButton extends JButton{

    /**
     * Initialise the properties of this button
     */
    private void setButtonProperties() {
        setVerticalTextPosition(AbstractButton.CENTER);
        setHorizontalTextPosition(AbstractButton.CENTER);
    }


    public JoinPanelButton(PropertyChangeSupport changeSupport) {
        super(new JoinPanelAction(changeSupport));
        setButtonProperties();
    }
}
