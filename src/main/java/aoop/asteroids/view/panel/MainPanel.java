package aoop.asteroids.view.panel;

import aoop.asteroids.control.action.MenuAction;
import aoop.asteroids.control.action.QuitAction;
import aoop.asteroids.control.action.SinglePlayerAction;
import aoop.asteroids.control.action.UserNameAction;
import aoop.asteroids.model.Game;
import aoop.asteroids.model.MultiPlayerGame;
import aoop.asteroids.util.PanelEvents;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainPanel extends JPanel implements PropertyChangeListener {

    private final CardLayout cardLayout;
    private final Game game;
    private final MultiPlayerGame multiPlayerGame;
    private final JMenuBar menuBar;
    private MainMenuPanel mainMenuPanel;
    private HighScorePanel highScorePanel;
    private JMenu userNameMenu;

    public MainPanel(Game game, MultiPlayerGame multiPlayerGame, JMenuBar menuBar) {
        this.game = game;
        this.multiPlayerGame = multiPlayerGame;
        this.menuBar = menuBar;
        this.cardLayout = new CardLayout();
        this.setLayout(cardLayout);

        setUpPanel();
        setUpMenuBar();

        cardLayout.show(this, PanelEvents.MENU);
    }

    private void setUpPanel() {
        AsteroidsPanel asteroidsPanel = new AsteroidsPanel(game);
        asteroidsPanel.addListener(this);
        this.add(asteroidsPanel, PanelEvents.SINGLE_PLAYER);

        this.mainMenuPanel = new MainMenuPanel(game);
        this.mainMenuPanel.addListener(this);
        this.add(mainMenuPanel, PanelEvents.MENU);

        MultiPlayerPanel multiPlayerPanel = new MultiPlayerPanel(multiPlayerGame);
        multiPlayerPanel.addListener(this);
        this.add(multiPlayerPanel, PanelEvents.MULTI_PLAYER);

        JoinGamePanel joinGamePanel = new JoinGamePanel(multiPlayerGame);
        joinGamePanel.addListener(this);
        this.add(joinGamePanel, PanelEvents.JOIN_GAME_MENU);

        MultiPlayerMenuPanel multiPlayerMenuPanel = new MultiPlayerMenuPanel(multiPlayerGame);
        multiPlayerMenuPanel.addListener(this);
        this.add(multiPlayerMenuPanel, PanelEvents.MULTI_PLAYER_MENU);

        this.highScorePanel = new HighScorePanel();
        this.highScorePanel.addListener(this);
        this.add(highScorePanel, PanelEvents.HIGHSCORE);

        UserNamePanel userNamePanel = new UserNamePanel(game, multiPlayerGame);
        userNamePanel.addListener(this);
        this.add(userNamePanel, PanelEvents.USERNAME);

        SpectatePanel spectatePanel = new SpectatePanel(this.multiPlayerGame);
        spectatePanel.addListener(this);
        this.add(spectatePanel, PanelEvents.SPECTATE);

        InvalidPortPanel invalidPortPanel = new InvalidPortPanel();
        invalidPortPanel.addListener(this);
        this.add(invalidPortPanel, PanelEvents.INVALID_PORT);
    }

    private void setUpMenuBar() {
        JMenu menu = new JMenu("Game");
        userNameMenu = new JMenu("Set a USERNAME!");
        menuBar.add(menu);
        menuBar.add(userNameMenu);
        menu.add(new SinglePlayerAction(mainMenuPanel.getChangeSupport(), this.game));
        menu.add(new MenuAction(mainMenuPanel.getChangeSupport()));
        menu.add(new QuitAction());
        userNameMenu.add(new UserNameAction(mainMenuPanel.getChangeSupport()));
    }

    /**
     * Is called when a Panel switch is required.
     * The property changed event will contain the string to which panel the card layout needs to switch.
     *
     * @param evt Property Changed Event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String newPanel = (String) evt.getNewValue();
        System.out.println("new panel:" + newPanel);
        switch (newPanel) {
            case PanelEvents.MENU:
                userNameMenu.setText(game.getSpaceship().getName());
                break;
            case PanelEvents.HIGHSCORE:
                highScorePanel.newHighScores();
                break;
            case PanelEvents.SINGLE_PLAYER:
            case PanelEvents.MULTI_PLAYER_MENU:
                if(!game.getSpaceship().validUser()) newPanel = PanelEvents.USERNAME;
        }
        cardLayout.show(this, newPanel);
    }
}
