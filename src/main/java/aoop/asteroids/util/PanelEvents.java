package aoop.asteroids.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

public class PanelEvents {

    public static final String SINGLE_PLAYER = "singlePlayer";
    public static final String MENU = "menu";
    public static final String MULTI_PLAYER = "multiPlayer";
    public static final String JOIN_GAME_MENU = "joinGameMenu";
    public static final String MULTI_PLAYER_MENU = "multiPlayerMenu";
    public static final String HIGHSCORE = "highscore";
    public static final String USERNAME = "username";
    public static final String SPECTATE = "spectate";
    public static final String INVALID_PORT = "invalidPort";

    public static void firePanelSwitchEvent(PropertyChangeSupport support, String s, Object source) {
        PropertyChangeEvent panelEvent = new PropertyChangeEvent(source, "PanelSwitch",
                null, s);
        support.firePropertyChange(panelEvent);
    }
}
