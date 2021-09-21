package aoop.asteroids.view.panel;

import aoop.asteroids.control.button.MenuButton;
import aoop.asteroids.control.button.SpectateButton;
import aoop.asteroids.control.button.UserNameEnterButton;
import aoop.asteroids.model.Game;
import aoop.asteroids.model.MultiPlayerGame;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Username panel
 */
public class UserNamePanel extends JPanel {

    private  PropertyChangeSupport changeSupport;
    private Game game;
    private MultiPlayerGame multiPlayerGame;
    private JComboBox comboBox;
    private JButton enterButton;
    private JButton menuButton;
    private JTextField input;

    /**
     * Username panel constructor
     * @param game game
     * @param multiplayerGame multiplayer game
     */
    public UserNamePanel(Game game, MultiPlayerGame multiplayerGame) {
        this.game = game;
        this.multiPlayerGame = multiplayerGame;
        this.changeSupport = new PropertyChangeSupport(this);
        makeTextField();
        setUpComboBox();
        this.add(makeButtonPanel());
        this.add(enterButton);
        menuButton.setLocation(0, 0);
    }

    /**
     * Make text field for port
     */
    private void makeTextField() {
        input = new JTextField();
        input.setColumns(20);
        this.add(input);
    }

    /**
     * Make button panel
     * @return j button panel
     */
    private JPanel makeButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        menuButton = new MenuButton(changeSupport);
        enterButton = new UserNameEnterButton(changeSupport, input, comboBox, game, multiPlayerGame);
        buttonPanel.add(enterButton);
        buttonPanel.add(menuButton);
        return buttonPanel;
    }

    /**
     * Sets up the drop down menu to choose a color
     */
    private void setUpComboBox() {
        String colors[] = {"RED", "GREEN", "BLUE", "YELLOW", "BLACK", "PINK"};
//        Color colors[] = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.BLACK, Color.PINK};
        this.comboBox = new JComboBox(colors);
        this.add(comboBox);
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

