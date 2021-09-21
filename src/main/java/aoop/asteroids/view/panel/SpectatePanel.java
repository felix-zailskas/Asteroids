package aoop.asteroids.view.panel;

import aoop.asteroids.control.button.MenuButton;
import aoop.asteroids.control.button.SpectateButton;
import aoop.asteroids.model.MultiPlayerGame;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SpectatePanel extends JPanel{

    private JTextField portField;
    private final PropertyChangeSupport changeSupport;
    private final MultiPlayerGame game;

    public SpectatePanel(MultiPlayerGame game) {
        this.changeSupport = new PropertyChangeSupport(this);
        this.game = game;
        makeTextField();
        this.add(makeButtonPanel());

    }

    private void makeTextField() {
        portField = new JTextField();
        portField.setColumns(15);
        this.add(portField);
    }

    private JPanel makeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        JButton menuButton = new MenuButton(changeSupport);
        JButton spectateButton = new SpectateButton(changeSupport, game, portField);
        buttonPanel.add(spectateButton);
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
