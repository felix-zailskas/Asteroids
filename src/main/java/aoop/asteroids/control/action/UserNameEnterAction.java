package aoop.asteroids.control.action;

import aoop.asteroids.model.Game;
import aoop.asteroids.model.MultiPlayerGame;
import aoop.asteroids.util.PanelEvents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeSupport;

/**
 * Username enter action
 */
public class UserNameEnterAction extends AbstractAction {

    private PropertyChangeSupport changeSupport;
    private JTextField input;
    private JComboBox comboBox;
    private Game game;
    private MultiPlayerGame multiPlayerGame;

    /**
     * Create new username enter action
     * @param changeSupport property change support
     * @param input input
     * @param game game
     * @param multiPlayerGame multi player game
     */
    public UserNameEnterAction(PropertyChangeSupport changeSupport, JTextField input, JComboBox comboBox, Game game, MultiPlayerGame multiPlayerGame) {
        super("Confirm Username");
        this.changeSupport = changeSupport;
        this.input = input;
        this.comboBox = comboBox;
        this.game = game;
        this.multiPlayerGame = multiPlayerGame;
    }

    /**
     * Set username based off input JTextField box
     * @param e event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String shortName = input.getText();
        if (shortName.equals("")) shortName = "User";
        if (shortName.length() > 20) shortName = shortName.substring(0, 20);
        game.getSpaceship().setName(shortName);
        multiPlayerGame.getSpaceship().setName(shortName);
        game.getSpaceship().setUpUser(shortName);
        this.input.setText("");
        Color shipColor = getColor((String) comboBox.getSelectedItem());
        game.getSpaceship().setColor(shipColor);
        multiPlayerGame.getSpaceship().setColor(shipColor);
        PanelEvents.firePanelSwitchEvent(changeSupport, PanelEvents.MENU, this);
    }

    /**
     * Returns the color based on the provided string
     * @param selectedItem String indicating color
     * @return Color
     */
    private Color getColor(String selectedItem) {
        Color c = Color.BLACK;
        switch (selectedItem) {
            case "GREEN":
                c = Color.GREEN;
                break;
            case "RED":
                c = Color.RED;
                break;
            case "BLACK":
                c = Color.BLACK;
                break;
            case "BLUE":
                c = Color.BLUE;
                break;
            case "YELLOW":
                c = Color.YELLOW;
                break;
            case "PINK":
                c = Color.PINK;
                break;
        }
        return c;
    }
}

