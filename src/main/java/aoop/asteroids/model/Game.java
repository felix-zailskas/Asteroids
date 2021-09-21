package aoop.asteroids.model;

import aoop.asteroids.control.GameUpdater;
import aoop.asteroids.game_observer.ObservableGame;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class is the main model for the Asteroids game. It contains all game objects, and has methods to start and stop
 * the game.
 *
 * This is strictly a model class, containing only the state of the game. Updates to the game are done in
 * {@link GameUpdater}, which runs in its own thread, and manages the main game loop and physics updates.
 */
public class Game extends ObservableGame {
	/**
	 * The spaceship object that the player is in control of.
	 */
	protected final Spaceship ship;

	/**
	 * The list of all bullets currently active in the game.
	 */
	protected Collection<Bullet> bullets;

	/**
	 * The list of all asteroids in the game.
	 */
	protected Collection<Asteroid> asteroids;

	/**
	 * Indicates whether or not the game is running. Setting this to false causes the game to exit its loop and quit.
	 */
	protected volatile boolean running = false;

	/**
	 * The game updater thread, which is responsible for updating the game's state as time goes on.
	 */
	protected Thread gameUpdaterThread;

	/**
	 * Constructs a new game, with a new spaceship and all other model data in its default starting state.
	 */
	public Game() {
		this.ship = new Spaceship();
		this.initializeGameData();
	}

	/**
	 * Initializes all of the model objects used by the game. Can also be used to reset the game's state back to a
	 * default starting state before beginning a new game.
	 */
	public void initializeGameData() {
		this.bullets = new ArrayList<>();
		this.asteroids = new ArrayList<>();
		this.ship.reset();
	}

	/**
	 * @return The game's spaceship.
	 */
	public Spaceship getSpaceship() {
		return this.ship;
	}

	/**
	 * @return The collection of asteroids in the game.
	 */
	public Collection<Asteroid> getAsteroids() {
		return this.asteroids;
	}

	/**
	 * @return The collection of bullets in the game.
	 */
	public Collection<Bullet> getBullets ()
	{
		return this.bullets;
	}

	/**
	 * @return Whether or not the game is running.
	 */
	public synchronized boolean isRunning() {
		return this.running;
	}

	public Thread getGameUpdaterThread() {
		return gameUpdaterThread;
	}

	/**
	 * @return True if the player's ship has been destroyed, or false otherwise.
	 */
	public boolean isGameOver() {
		if(this.ship.isDestroyed()) {
			this.ship.postRequestScore();
		}
		return this.ship.isDestroyed();
	}

	public void setBullets(Collection<Bullet> bullets) {
		this.bullets = bullets;
	}

	public void setAsteroids(Collection<Asteroid> asteroids) {
		this.asteroids = asteroids;
	}

	/**
	 * Using this game's current model, spools up a new game updater thread to begin a game loop and start processing
	 * user input and physics updates. Only if the game isn't currently running, that is.
	 */
	public void start() {
		if (!this.running) {
			this.running = true;
			this.gameUpdaterThread = new Thread(new GameUpdater(this));
			this.gameUpdaterThread.start();
		}
	}

	/**
	 * Tries to quit the game, if it is running.
	 */
	public void quit() {
		if (this.running) {
			try { // Attempt to wait for the game updater to exit its game loop.
				this.gameUpdaterThread.join(100);
			} catch (InterruptedException exception) {
				System.err.println("Interrupted while waiting for the game updater thread to finish execution.");
			} finally {
				this.running = false;
				this.gameUpdaterThread = null; // Throw away the game updater thread and let the GC remove it.
			}
		}
	}
}
