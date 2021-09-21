package aoop.asteroids.view.panel;

import aoop.asteroids.control.action.QuitAction;
import aoop.asteroids.control.button.*;
import aoop.asteroids.model.Game;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MainMenuPanel extends JPanel {

    private JButton quitButton;
    private final PropertyChangeSupport changeSupport;
    private final Game game;

    public MainMenuPanel(Game game) {
        this.game = game;
        this.changeSupport = new PropertyChangeSupport(this);

        JPanel buttonPanel = makeButtonPanel();

        this.add(buttonPanel);
        this.add(quitButton);
    }

    private JPanel makeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        JButton singlePlayerButton = new SinglePlayerButton(changeSupport, game);
        JButton multiPlayerButton = new MultiPlayerMenuButton(changeSupport);

        JButton highScoreButton = new HighScoreButton(changeSupport);
        JButton userNameButton = new UserNameButton(changeSupport);
        this.quitButton = new JButton(new QuitAction());
        buttonPanel.add(userNameButton);
        buttonPanel.add(singlePlayerButton);
        buttonPanel.add(multiPlayerButton);
        buttonPanel.add(highScoreButton);
        
        buttonPanel.add(quitButton);
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
