package aoop.asteroids.model;

import aoop.asteroids.util.SerializeID;
import aoop.asteroids.util.UserEvents;
import aoop.asteroids.view.AsteroidsFrame;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.awt.*;
import java.io.Serializable;

/**
 * This class represents a player's ship. Like all other game objects, it has a location and velocity, but additionally,
 * the spaceship has a weapon that can be used to shoot bullets to destroy asteroids. The spaceship also slows down over
 * time. You may think this is unrealistic, but imagine for a moment that this spaceship has reaction control thrusters
 * allowing it all 6 degrees of freedom, and that it has an inertial dampening system, like any modern starfighter
 * would.
 *
 * Furthermore, the spaceship has a limited energy supply which is regenerated slowly over time by onboard solar panels.
 * Accelerating, turning, and shooting the weapon all drain some of this energy. If there's not enough energy remaining
 * to perform some action, the spaceship will simply remain idle until it has recharged its batteries.
 */
public class Spaceship extends GameObject implements Serializable, UserEvents {

	private static final long serialVersionUID = SerializeID.SHIP_ID;
	
	/**
	 * The maximum speed that the spaceship is allowed to reach before extra acceleration will not do anything.
	 */
	public static final double MAXIMUM_SPEED = 20.0;

	/**
	 * The coefficient to multiply the ship's velocity by every tick, so that it slows down.
	 */
	public static final double VELOCITY_DAMPENING_COEFFICIENT = 0.99;

	/**
	 * The rate at which the spaceship will speed up, per axis, per tick.
	 */
	public static final double ACCELERATION_PER_TICK = 0.4;

	/**
	 * The amount in radians that the spaceship rotates per tick, if the player is rotating it.
	 */
	public static final double ROTATION_PER_TICK = 0.04 * Math.PI;

	/**
	 * The number of game ticks that must pass after firing the ship's weapon before it is able to fire again.
	 */
	public static final int WEAPON_COOLDOWN_TICKS = 5;

	/**
	 * The amount of energy used by firing the weapon.
	 */
	public static final double WEAPON_ENERGY_COST = 10.0;

	/**
	 * The amount of energy used by using the thruster to accelerate forward.
	 */
	public static final double ACCELERATION_ENERGY_COST = 5.0;

	/**
	 * The amount of energy used by the reaction control thrusters to change the orientation of the ship.
	 */
	public static final double TURNING_ENERGY_COST = 3.0;

	/**
	 * The total amount of energy that can be stored on the ship.
	 */
	public static final double ENERGY_CAPACITY = 256.0;

	/**
	 * How much energy the ship generates each tick.
	 */
	public static final double ENERGY_GENERATION = 3.0;

	/** Direction the spaceship is pointed in. */
	private double direction;

	/**
	 * Amount of game ticks left, until the spaceship can fire again.
	 */
	private int weaponCooldownRemaining;

	/**
	 * The amount of energy stored in the ship's batteries.
	 */
	private double energy;

	/** Score of the player. I.e. amount of destroyed asteroids. */
	private int score;

	/** Indicates whether the fire button is pressed. */
	private boolean isFiring;

	/** Indicates whether the accelerate button is pressed. */
	private boolean accelerateKeyPressed;

	/** Indicates whether the turn right button is pressed. */
	private boolean turnRightKeyPressed;

	/** Indicates whether the turn left button is pressed. */
	private boolean turnLeftKeyPressed;

	private String name;

	private int id;

	private Color color;

	/**
	 * Constructs a new spaceship with default values. It starts in the middle of the window, facing directly upwards,
	 * with no velocity.
	 */
	public Spaceship() {
		super(AsteroidsFrame.WINDOW_SIZE.width / 2,AsteroidsFrame.WINDOW_SIZE.height / 2, 0, 0, 15);
		this.reset();
		this.id = -1;
	}

	/**
	 * Resets all parameters to default values, so a new game can be started.
	 */
	public void reset() {
		this.getLocation().x = AsteroidsFrame.WINDOW_SIZE.width / 2;
		this.getLocation().y = AsteroidsFrame.WINDOW_SIZE.height / 2;
		this.getVelocity().x = 0;
		this.getVelocity().y = 0;
		this.direction = 0;
		this.isFiring = false;
		this.accelerateKeyPressed = false;
		this.turnLeftKeyPressed = false;
		this.turnRightKeyPressed = false;
		this.destroyed = false;
		this.weaponCooldownRemaining = 0;
		this.score = 0;
		this.energy = ENERGY_CAPACITY;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getID() { return this.id; }

	public void setID(int id) { this.id = id; }

	public boolean validUser() {
		return this.id != -1 && !this.name.equals("");
	}

	/**
	 *	Sets the isFiring field to the specified value.
	 *
	 *	@param b new value of the field.
	 */
	public void setIsFiring(boolean b) {
		this.isFiring = b;
	}

	/**
	 *	Sets the left field to the specified value.
	 *
	 *	@param b new value of the field.
	 */
	public void setTurnLeftKeyPressed(boolean b) {
		this.turnLeftKeyPressed = b;
	}

	/**
	 *	Sets the right field to the specified value.
	 *
	 *	@param b new value of the field.
	 */
	public void setTurnRightKeyPressed(boolean b) {
		this.turnRightKeyPressed = b;
	}

	/**
	 *	Sets the up field to the specified value.
	 *
	 *	@param b new value of the field.
	 */
	public void setAccelerateKeyPressed(boolean b) {
		this.accelerateKeyPressed = b;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	/**
	 * Defines how the spaceship moves. This includes rotating the ship if the user is pressing the key to turn the
	 * ship, or accelerating the ship, or firing the weapon.
	 */
	@Override 
	public void nextStep() {
		super.nextStep();

		this.attemptToTurn();
		this.attemptToAccelerate();
		this.dampenVelocity();
		this.restWeapon();
		this.rechargeEnergy();
	}

	/**
	 * Recharges the ship's energy during a game tick. The energy is renewable, in case you were wondering.
	 */
	private void rechargeEnergy() {
		this.energy += ENERGY_GENERATION;
		this.energy = Math.min(this.energy, ENERGY_CAPACITY);
	}

	/**
	 * 'Rests' the ship's weapon, if necessary. This essentially just cools down the weapon each game tick until it can
	 * be fired again.
	 */
	private void restWeapon() {
		if (this.weaponCooldownRemaining != 0) {
			this.weaponCooldownRemaining--;
		}
	}

	/**
	 * Dampens the ship's velocity, i.e. slows it down slightly, so that you don't drift endlessly across the screen.
	 */
	private void dampenVelocity() {
		this.getVelocity().x *= VELOCITY_DAMPENING_COEFFICIENT;
		this.getVelocity().y *= VELOCITY_DAMPENING_COEFFICIENT;
	}

	/**
	 * Attempts to accelerate the spaceship. If all of the criteria for accelerating the ship are met, then it will
	 * accelerate. For a ship to be able to accelerate, the user must be pressing the key to do so, and the ship must
	 * have enough energy, and finally, the ship must not exceed its maximum set speed.
	 */
	private void attemptToAccelerate() {
		if (this.accelerateKeyPressed && this.energy >= ACCELERATION_ENERGY_COST && this.getSpeed() < MAXIMUM_SPEED) {
			this.getVelocity().x += Math.sin(direction) * ACCELERATION_PER_TICK;
			this.getVelocity().y -= Math.cos(direction) * ACCELERATION_PER_TICK; // Note that we subtract here, because the y-axis on the screen is flipped, compared to normal math.
			this.energy -= ACCELERATION_ENERGY_COST;
		}
	}

	/**
	 * Attempts to turn the spaceship. If all of the criteria for turning the ship are met, then it will rotate.
	 * For a ship to be able to rotate, the user must be pressing the key to turn it either left or right, and the ship
	 * must have enough energy to rotate.
	 */
	private void attemptToTurn() {
		if (this.energy >= TURNING_ENERGY_COST) {
			boolean didTurn = false;
			if (this.turnLeftKeyPressed) {
				this.direction -= ROTATION_PER_TICK;
				didTurn = true;
			}
			if (this.turnRightKeyPressed) {
				this.direction += ROTATION_PER_TICK;
				didTurn = true;
			}
			if (didTurn) {
				this.energy -= TURNING_ENERGY_COST;
			}
		}
	}

	/**
	 * @return The number of steps, or game ticks, for which this object is immune from collisions.
	 */
	@Override
	protected int getDefaultStepsUntilCollisionPossible() {
		return 10;
	}

	/**
	 * @return the direction.
	 */
	public double getDirection() {
		return this.direction;
	}

	/**
	 * @return The percentage of energy that is available on the ship, out of the total capacity.
	 */
	public double getEnergyPercentage() {
		return 100 * this.energy / ENERGY_CAPACITY;
	}

	/**
	 * @return true if acceleration button is pressed, false otherwise.
	 */
	public boolean isAccelerating()	{
		return this.accelerateKeyPressed;
	}

	public boolean isTurningRight() {
		return turnRightKeyPressed;
	}

	public boolean isFiring() {
		return isFiring;
	}

	public boolean isTurningLeft() {
		return turnLeftKeyPressed;
	}

	/**
	 * @return True if the spaceship may fire a bullet. A spaceship is allowed to fire if its weapon is done cooling
	 * down, and it has enough energy, and the user is pressing the button to fire the weapon.
	 */
	public boolean canFireWeapon() {
		return this.isFiring
				&& this.weaponCooldownRemaining == 0
				&& this.energy >= WEAPON_ENERGY_COST;
	}

	/**
	 * Sets the fire tick counter to its starting value, to begin a new countdown until the weapon can be used again.
	 */
	public void setFired() {
		this.weaponCooldownRemaining = WEAPON_COOLDOWN_TICKS;
		this.energy -= WEAPON_ENERGY_COST;
	}

	public void updateValues(SpaceshipData data) {
		this.setLocation(data.getPos());
		this.setDirection(data.getDir());
		boolean[] keys = data.getKeys();
		this.setTurnRightKeyPressed(keys[0]);
		this.setTurnLeftKeyPressed(keys[1]);
		this.setAccelerateKeyPressed(keys[2]);
		this.setIsFiring(keys[3]);
		this.energy = data.getEnergy();
		this.name = data.getName();
		this.color = data.getColor();
	}

	/**
	 * Increments score field.
	 */
	public void increaseScore() {
		this.score++;
	}

	/**
	 * @return the score.
	 */
	public int getScore() {
		return this.score;
	}

	public String toString() {
        return "User:{'id': " + this.id + ", 'userName': " + this.name + ", 'score': " + this.score + "}";
    }

	public void setUpUser(String name) {
		this.id = -1;
		JsonNode userNode = getRequestName(name);
		if(userNode.getObject().isNull("id")) id = postRequestName(name);
		else id = userNode.getObject().getInt("id");
		setUserInfo(name, id);
		System.out.println(toString());
	}

	public void setUserInfo(String name, int id) {
		setID(id);
		setName(name);
	}

	public JsonNode getRequestName(String name) {
		HttpResponse<JsonNode> response = Unirest.get("https://asteroids-database.herokuapp.com/players/" + name)
				.asJson();
		System.out.println(response.getBody());
		return response.getBody();
	}

	public int postRequestName(String name) {
		HttpResponse<JsonNode> response = Unirest.post("https://asteroids-database.herokuapp.com/players")
				.header("content-type", "application/json")
				.body("{\"name\": \"" + name + "\"}")
				.asJson();
		return response.getBody().getObject().getInt("id");
	}

	public void postRequestScore() {
		HttpResponse<String> request = Unirest.post("https://asteroids-database.herokuapp.com/scores")
				.header("content-type", "application/json")
				.body("{\n\t\"player\":" + this.id + ",\n\t\"score\":" + this.score + ",\n\t\"name\": \""
						+ this.name + "\"\n}").asString();
	}

}
