package aoop.asteroids.view.panel;

import aoop.asteroids.control.button.MenuButton;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class InvalidPortPanel extends JPanel {

    private final PropertyChangeSupport changeSupport;

    public InvalidPortPanel() {
        this.changeSupport = new PropertyChangeSupport(this);
        makeTextArea();
        this.add(makeButtonPanel());

    }

    private void makeTextArea() {
        JTextArea textArea = new JTextArea("YOU HAVE GIVEN AN INVALID PORT!");
        this.add(textArea);
    }

    private JPanel makeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        JButton menuButton = new MenuButton(changeSupport);
        buttonPanel.add(menuButton);
        return buttonPanel;
    }

    /**
     * Method used to add PropertyChangeListeners to changeSupport
     *
     * @param listener listener to be added to changeSupport
     */
    public void addListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }
}
