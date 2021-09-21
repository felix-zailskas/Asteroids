package aoop.asteroids.view;

import aoop.asteroids.control.PlayerKeyListener;
import aoop.asteroids.model.Game;
import aoop.asteroids.model.MultiPlayerGame;
import aoop.asteroids.view.panel.MainPanel;

import javax.swing.*;
import java.awt.*;

/**
 * The main window that's used for displaying the game.
 */
public class AsteroidsFrame extends JFrame {
	/**
	 * The title which appears in the upper border of the window.
	 */
	private static final String WINDOW_TITLE = "Asteroids";

	/**
	 * The size that the window should be.
	 */
	public static final Dimension WINDOW_SIZE = new Dimension(800, 800);

	/** The game model. */
	private final Game game;

	private final MultiPlayerGame multiPlayerGame;

	/**
	 * Constructs the game's main window.
	 *
	 * @param game The game model that this window will show.
	 */
	public AsteroidsFrame (Game game, MultiPlayerGame multiPlayerGame) {
		this.game = game;
		this.multiPlayerGame = multiPlayerGame;
		System.out.println("SIZE IN FRAME CONSTRUCTOR:" + multiPlayerGame.getPlayers().size());
		this.initSwingUI();
	}

	/**
	 * A helper method to do the tedious task of initializing the Swing UI components.
	 */
	private void initSwingUI() {
		// Basic frame properties.
		this.setTitle(WINDOW_TITLE);
		this.setSize(WINDOW_SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setFocusable(true);

		// Add a key listener that can control the game's spaceship.
		this.addKeyListener(new PlayerKeyListener(this.multiPlayerGame.getSpaceship()));
		this.addKeyListener(new PlayerKeyListener(this.game.getSpaceship()));

		// Add a menu bar with some simple actions.
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		// Add the custom panel that the game will be drawn to.
//		this.add(new AsteroidsPanel(this.game));
		this.add(new MainPanel(this.game, this.multiPlayerGame, menuBar));
		System.out.println("SIZE BEFOR ADDING THE PANEL: " + multiPlayerGame.getPlayers().size());
//		this.add(new MultiPlayerPanel(multiPlayerGame));
//		multiPlayerGame.start();
		this.setVisible(true);
	}
}
