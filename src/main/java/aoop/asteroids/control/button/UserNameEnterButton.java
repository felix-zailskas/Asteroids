package aoop.asteroids.control.button;

import aoop.asteroids.control.action.UserNameEnterAction;
import aoop.asteroids.model.Game;
import aoop.asteroids.model.MultiPlayerGame;

import javax.swing.*;
import java.beans.PropertyChangeSupport;

/**
 * Username enter button
 */
public class UserNameEnterButton extends JButton {

    /**
     * Initialise the properties of this button
     */
    private void setButtonProperties() {
        setVerticalTextPosition(AbstractButton.CENTER);
        setHorizontalTextPosition(AbstractButton.CENTER);
    }

    /**
     * Create new username enter button
     * @param changeSupport property change support
     * @param input input
     * @param game game
     * @param multiplayerGame multiplayer game
     */
    public UserNameEnterButton(PropertyChangeSupport changeSupport, JTextField input, JComboBox comboBox, Game game, MultiPlayerGame multiplayerGame) {
        super(new UserNameEnterAction(changeSupport, input, comboBox, game, multiplayerGame));
        setButtonProperties();
    }
}

