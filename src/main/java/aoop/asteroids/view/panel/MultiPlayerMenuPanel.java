package aoop.asteroids.view.panel;

import aoop.asteroids.control.button.*;
import aoop.asteroids.model.MultiPlayerGame;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MultiPlayerMenuPanel extends JPanel{

    private final PropertyChangeSupport changeSupport;
    private final MultiPlayerGame game;

    public MultiPlayerMenuPanel(MultiPlayerGame game) {
        this.changeSupport = new PropertyChangeSupport(this);
        this.game = game;
        JPanel buttonPanel = makeButtonPanel();
        this.add(buttonPanel);
    }

    private JPanel makeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        JButton menuButton = new MenuButton(changeSupport);
        JButton hostGameButton = new HostGameButton(changeSupport, game);
        JButton joinGameButton = new JoinPanelButton(changeSupport);
        JButton spectateButton = new SpectatePanelButton(changeSupport);
        buttonPanel.add(menuButton);
        buttonPanel.add(hostGameButton);
        buttonPanel.add(joinGameButton);
        buttonPanel.add(spectateButton);
        return buttonPanel;
    }

    public PropertyChangeSupport getChangeSupport() {
        return changeSupport;
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
