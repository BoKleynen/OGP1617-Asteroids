package asteroids.model;

import asteroids.model.util.vector.Vector;
import be.kuleuven.cs.som.annotate.*;
import java.util.HashSet;
import java.util.Collection;
import java.util.List;


/**
 * A Class of space ships involving a position, a velocity, an orientation and a radius.
 *
 * @Invar 	The speed shall never exceed the maximum speed, which in turn shall never exceed the speed of light.
 *          | getVelocity().getMagnitude() <= getMaxSpeed() && getMaxSpeed() <= getSpeedOfLight
 * @Invar   The orientation of a ship is always a valid orientation.
 *          | Ship.canHaveAsOrientation(this.getOrientation)
 * @Invar	The radius of a ship is always greater the the smallest allowed radius.
 * 			| getRadius() >= getMinRadius()
 * 
 * Created by Bo Kleynen and Yrjo Koyen.
 *
 */
public class Ship extends Entity {

    /**
     * Creates a new ship with default values.
     * 
      * @Effect	Creates a new ship with default values. The velocity and position are equal
     * 			to the zero vector, the orientation is equal to zero, the radius is equal 
     * 			to the smallest possible radius and the maximum speed is equal to the speed
     * 			of light.
     * 			| this(new Vector(0, 0), new Vector(0, 0), 0, getMinRadius(), getSpeedOfLight())
     */
    public Ship() {
    	this(new Vector(50, 50), getSpeedOfLight(), new Vector(0, 0), 0, getMinRadius(), 0, 1.1 * Math.pow(10, 21));
    }

    public Ship(Vector position, Vector velocity, double orientation, double radius) {
        this(position, getSpeedOfLight(), velocity, orientation,radius,0,1.1 * 1e18);
    }

    /**
     * Creates a new ship and initializes its position to the given position vector,
     * its velocity to the given velocity vector, its orientation to the given orientation
     * and its radius to the given radius.
     * 
     * @Pre     The orientation of this ship must be a valid orientation.
     *          | canHaveAsOrientation(orientation)
     * @Effect	Creates a new ship with all the specified values and initializes the ships
     * 			maximum speed to the speed of light.
     * 			| this(position, velocity, orientation, radius, getSpeedOfLight())
     * @throws 	IllegalArgumentException
     * 			If the specified radius is not valid for a ship.
     *          | ! canHaveAsRadius(radius)
     * @throws 	NullPointerException
     * 			If the specified position refers a null object.
     *          | position == null
     */
    public Ship(Vector position, Vector velocity, double orientation, double radius, double mass)
            throws  IllegalArgumentException, NullPointerException {

    	this(position, getSpeedOfLight(), velocity, orientation, radius, mass, 1.1 * 1e18);
    }

    /**
     * Creates a new ship and initializes its position to the given position vector,
     * its velocity to the given velocity vector, its orientation to the given orientation, 
     * its radius to the given radius and its maxSpeed to the given maxSpeed.
     *
     * @Pre     The orientation of this ship must be a valid orientation.
     *          | canHaveAsOrientation(orientation)
     * @Post	The new position of this ship is equal to the specified vector position.
     * 			| new.getPosition().equals(position)
     * @Post	The new velocity of this ship is equal to the specified vector velocity if it does not reference a null object,
     *          if it does, the velocity is set to 0 in x and y direction.
     *          | if velocity != null then
     * 			|   new.getVelocity().equals(velocity)
     * 		    | else
     * 		    |    new.getVelocity().equals(new Vector(0, 0))
     * @Post	The new orientation of this ship is equal to the specified orientation.
     * 			| new.getOrientation() == orientation
     * @Post	The new radius for this ship is equal to the specified radius.
     * 			| new.getRadius() == radius
     * @Post	If the specified maxSpeed is smaller the the speed of light, the maximum speed
     * 			is equal to maxSpeed, otherwise the maximum speed is equal to the speed of light.
     * 			| if maxSpeed > getSpeedOfLight() then
     * 			|	new.getMaxSpeed() == getSpeedOfLight()
     * 			| else
     * 			|	new.getMaxSpeed() == maxSpeed
     * @throws  IllegalArgumentException
     *          If the specified radius is not valid for a ship, or the specified position contains NaN as one of its components.
     *          | ! canHaveAsRadius(radius) || ! canHaveAsPosition(position)
     * @throws  NullPointerException
     *          If the specified position refers a null object
     *          | position == null
     */
    public Ship(Vector position, double maxSpeed, Vector velocity, double orientation, double radius, double mass, double thrust)
            throws  IllegalArgumentException, NullPointerException {

        super(position, maxSpeed,velocity,minRadius,radius,minMassDensity,mass);
        
        loadBullet(getInitialBulletAmount());
        setOrientation(orientation);
        setThrust(thrust);
        thrustOff();

    }

    private final static char initialBulletAmount = 0;

    /**
     * Returns the amount of bullets loaded on a ship when it is created.
     * 
     * @return  The amount of bullets loaded on a ship when it is created.
     * 			| result == this.initialBulletAmount
     */
    @Basic @Immutable
    public static char getInitialBulletAmount() {
        return initialBulletAmount;
    }

    private static final double minMassDensity = 1.42 * 1e12;

    /**
     * Returns the minimal mass density for a Ship.
     *
     * @return 	The minimal mass density for a Ship.
     * 			| result = Ship.minMassDensity
     */
    @Basic @Immutable
    public static double getMinMassDensity() {
        return minMassDensity;
    }

    private static final double minRadius = 10;

    /**
     * Returns the smallest allowed radius for a ship.
     *
     * @return	The smallest allowed radius for a ship.
     * 			| result == Ship.minRadius
     */
    @Basic @Immutable
    private static double getMinRadius() {
        return minRadius;
    }

    /**
     * Returns the total mass of a ship.
     * The total mass is the mass of the ship itself plus the mass of all the bullets.
     *
     * @return	The total mass of this ship, that is the mass of the ship itself plus the mass of all bullets loaded
     *          onto this ship.
     * 			| @see implementation
     */
    public double getTotalMass() {
        double totalMass = getMass();

        for (Bullet bullet: bullets) {
            totalMass += bullet.getMass();
        }

        return totalMass;
    }

    /**
     * Checks whether or not the specified mass is a valid mass for this ship.
     *
     * @param mass
     *          The mass to be validated.
     * @return  True if and only if the specified mass is greater then or equal to the minimal mass for this ship.
     *          | isValidMass(mass, getRadius())
     */
    public boolean isValidMass(double mass) {
    	return isValidMass(mass, getRadius());
    }

    /**
     * Checks whether or not the specified mass is a valid mass for a ship with the specified radius.
     *
     * @param mass
     *          The mass to be validated.
     * @param radius
     *          The radius for which the specified mass has to be validated
     * @return  True if and only if the specified mass is greater then or equal to the minimal mass for a ship with
     *          the specified radius.
     *          | mass >= getMinMass(radius)
     */
    public static boolean isValidMass(double mass, double radius) {
    	return mass >= getMinMass(radius);
    }

    /**
     * Returns the minimal mass for this ship.
     *
     * @return  ...
     *          | getMinMass(getRadius())
     */
    public double getMinMass() {
        return getMinMass(getRadius());
    }

    /**
     * Returns the minimal mass for a ship with the specified radius.
     *
     * @param radius
     *          The radius for which the minimal mass has to be calculated.
     * @return  ...
     *          | (4.0/ 3.0) * radius^3 * PI * Ship.getMinMassDensity()
     */
    public static double getMinMass(double radius) {
    	return ( (4.0/3.0) * Math.pow(radius, 3) * Math.PI * Ship.getMinMassDensity() );
    }
    
    private boolean thrusterOn;

    /**
     * Checks whether or not this ships thrusters are enabled.
     *
     * @return  True if and only if the thruster(s) of this ship are active.
     *          | this.thrusterOn
     */
    @Basic
    public boolean thrusterOn() {
        return thrusterOn;
    }

    /**
     * Turns the thrusters of this ship on.
     * @Post    | new.thrusterOn == true
     */
    public void thrustOn() {
        thrusterOn = true;
    }

    /**
     * Turns the thrusters of this ship off
     * @Post    | new.thrusterOn == false
     */
    public void thrustOff() {
        thrusterOn = false;
    }

    private double thrust;

    /**
     * Returns the amount of thrust this ships thrusters can generate.
     *
     * @return  ...
     *          | @see implementation
     */
    @Basic
    public double getThrust() {
        return thrust;
    }

    /**
     * Sets the thrust of this ship to the specified value.
     *
     * @param newThrust
     *          The value of the new thrust
     * @Post    ...
     *          | @see implementation
     */
    @Basic
    public void setThrust(double newThrust) {
        thrust = newThrust;
    }

    /**
     * Returns the acceleration of this ship
     *
     * @return  ...
     *          | @see implementation
     */
    public double getAcceleration() {
        return thrusterOn ? getThrust() / getTotalMass() : 0;
    }

    /**
     * Accelerates the ship for the specified amount of time.
     *
     * @param time
     *          The time during which the ship has to accelerate.
     * @Post    If this ship is terminated nothing happens
     *          | if isTerminated then
     *          |   new == this
     * @Post    If the ships thruster is turned on the ships velocity changes as specified in the formal specification
     *          | if thrusterOn() then
     *          |   new.getVelocity().getX() == getThrust() / getMass() * Math.cos(getOrientation()) * time
     *          |   new.getVelocity().getY() == getThrust() / getMass() * Math.sin(getOrientation()) * time
     */
    public void accelerate(double time) {
        double acceleration = getAcceleration();
        setVelocity(getVelocity().add(new Vector(
                acceleration * Math.cos(getOrientation()) * time,
                acceleration * Math.sin(getOrientation()) * time)));
    }

    /**
     * Moves the ship for the specified amount of time, if this ships thruster(s) is (are) active, this ship will be
     * accelerated for the specified amount of time.
     *
     * @param 	time
     * 			The time to move in the direction of the velocity vector.
     * @Post    This ship has moved for the specified amount of time.
     *          | super.move(time)
     * @Post    If this ships thruster(s) is (are) active this ship has accelerated for the specified amount of time.
     *          | if thrusterOn() then
     *          |   new.getVelocity() == this.accelerate(time)
     * @Post    The bullets loaded onto this ship have moved with this ship.
     *          | bullet.getPosition() == new.getPosition() for any bullet in new.getAllBullets()
     * @throws IllegalArgumentException
     *          If the specified time is smaller then zero.
     *          | time < 0
     */
    @Override	
    public void move(double time) throws IllegalArgumentException {
        if( time < 0 ) {
            throw new IllegalArgumentException(Double.toString(time));
        }

        super.move(time);
        
        for (Bullet bullet : bullets) {
        	bullet.setPosition(getPosition());
        }

        if (thrusterOn())
            accelerate(time);
    }

    /**
     * Thrusts the ship forward in the direction of the current orientation, and changes its
     * current velocity according to the given acceleration a.
     * 
     * @param 	acceleration
     * 			The magnitude of the thrust
     * @post    If acceleration is less then, equal to 0 or NaN, nothing will happen.
     *          | if acceleration <= 0 then
     *          |   new.getVelocity == getVelocity
     * @post    If acceleration is strictly positive, the velocity of the ship will change. A vector
     * 			with a magnitude of acceleration and in the direction of the current orientation
     * 			will be added to the current velocity.
     *          | if acceleration > 0 then
     *          |   new.getVelocity == getVelocity().add(
     *          |		new Vector(acceleration * Math.cos(getOrientation()), acceleration * Math.sin(getOrientation())));
     */
    @Deprecated
    public void thrust(double acceleration) {
        if (acceleration > 0) {
            setVelocity(getVelocity().add(new Vector(
                    acceleration * Math.cos(getOrientation()),
                    acceleration * Math.sin(getOrientation()))));
        }
    }

    private double orientation;     // nominal

    /**
     * Returns a boolean to check whether the given value of orientation is a valid value
     * for the orientation of a ship.
     *
     * @return  True if and only if the given orientation is non negative and
     * 			if the given orientation is smaller then 2*PI.
     *          | result == ((0.0 <= orientation) && (orientation < 2.0 * Math.PI))
     */
    public static boolean canHaveAsOrientation(double orientation) {
        return (0.0 <= orientation) && (orientation < 2.0 * Math.PI);
    }
    
    /**
     * Return the direction this ship is currently facing. The returned vector is a unit vector.
     * 
     * @return  ...
     *          | @see implementation
     */
    private Vector getDirection() {
    	return new Vector(Math.cos(getOrientation()), Math.sin(getOrientation()));
    }

    /**
     * Returns the current orientation of this ship.
     *
     * @return  The orientation of this ship
     *          | result == this.orientation
     */
    @Basic
    public double getOrientation() {
        return orientation;
    }

    /**
     * Sets the orientation for this ship to newOrientation. The specified orientation must be a valid orientation
     * for a ship.
     *
     * @param   newOrientation
     *          The new orientation for this ship.
     * @Pre     This ship isn't terminated
     *          | ! isTerminated()
     * @Pre     newOrientation is a valid orientation for a ship
     *          | isValidOrientation(newOrientation)
     * @post    The new orientation of this ship is equal to newOrientation
     *          | new.getOrientation() == newOrientation
     */
    @Basic
    private void setOrientation(double newOrientation) {
        assert ! isTerminated();
        assert canHaveAsOrientation(newOrientation);
        orientation = newOrientation;
    }

    /**
     * Turns the ship counterclockwise over the specified angle.
     * 
     * @param 	angle
     * 			The angle to turn the ship around its center.
     * @Pre 	Angle is a valid real number.
     * 			| ( angle.isNaN(angle) ) && ( ! Double.isInfinite(angle) )
     * @post	If the sum of the current orientation and the specified angle is positive
     * 			or equal to zero, the new orientation will be the an equivalent orientation
     * 			to the old orientation incremented with the specified angle.
     * 			| if this.getOrientation() + angle >= 0 then
     * 			|	new.getOrientation == (this.getOrientation + angle) % (2*PI)
     * @Post	If the sum of the current orientation and the specified angle is negative,
     * 			the new orientation will be equal to the smallest positive orientation that
     * 			is equivalent to the sum of current orientation and the specified angle.
     * 			| if this.getOrientation() + angle < 0 then
     * 			|	new.getOrientation == (this.getOrientation() + angle) % (2*PI) + 2*PI
     *  
     */
    public void turn(double angle) {
    	assert(!Double.isNaN(angle) && !Double.isInfinite(angle));
    	double newOrientation = (getOrientation() + angle) % (2 * Math.PI);

        setOrientation(newOrientation >= 0 ? newOrientation : newOrientation + 2 * Math.PI);
    }

    private HashSet<Bullet> bullets = new HashSet<>();

    /**
     * Adds the given bullet to this ship.
     * Setting this ship as its parent ship and loading it onto this ship.
     *
     * @param bullet The bullet to be added to this ship.
     * @Post	This ship is the bullets parent ship.
     *          | (new bullet).getParentShip() == new
     */
    public void addBullet(Bullet bullet) {
        bullet.setParentShip(this);
        loadBullet(bullet);
    }

    /**
     * Loads the given bullet onto this ship.
     *
     * @param bullet
     * @Post    The bullet is loaded onto this ship
     *          | (new bullet).getShip() == new
     *          | new.getAllBullets().contain((new bullet))
     * @Post    The bullet is not part of a world
     *          | !(new bullet).hasWorld()
     * @throws IllegalStateException
     *          If this ship is terminated
     *          | isTerminated()
     * @throws IllegalArgumentException
     *          If this ship is not the bullets parent ship.
     *          | bullet.getParentShip() != this
     */
    public void loadBullet(Bullet bullet) throws IllegalStateException, IllegalArgumentException {
        if (isTerminated())
            throw new IllegalStateException("This ship is terminated");
        if (bullet.getParentShip() != this)
            throw new IllegalArgumentException("A bullet can only be loaded onto its parent ship");

        try {
            getWorld().removeEntity(bullet);
        } catch (IllegalArgumentException | NullPointerException e) {}

        bullet.setShip(this);
        bullets.add(bullet);
        bullet.setPosition(getPosition().add(new Vector(getRadius()/2, 0)));


    }

    public void loadBullet(Bullet... bullets) {
        for (Bullet bullet : bullets) {
            loadBullet(bullet);
        }
    }
    
    /**
     * Loads the given amount of new bullets onto this ship.
     * @param amount
     * @throws IllegalArgumentException
     *          If the amount of bullets is negative
     *          | amount < 0
     * @throws IllegalStateException
     *          If this ship is terminated
     *          | isTerminated()
     */
    public void loadBullet(int amount) throws IllegalStateException, IllegalArgumentException {
    	if (isTerminated())
    	    throw new IllegalStateException("This ship is terminated");
        if ( amount < 0 )
    		throw new IllegalArgumentException();
    	
    	for (int i = 0; i < amount; i++) {
    		Bullet b = new Bullet(getPosition(), getVelocity(), getRadius()/5.0);
    		addBullet(b);
    	}
    }
    
    /**
     * Loads all the bullets in the given collection of bullets onto this ship.
     * @param bulletList
     */
    public void loadBullet(Collection<Bullet> bulletList) {
    	for (Bullet bullet : bulletList)
    		addBullet(bullet);
    }
    
    /**
     * Removes the given bullet from the set of bullets currently loaded on this ship.
     * If the bullet is not loaded on this ship, nothing happens.
     * @param bullet
     */
    public void removeBullet(Bullet bullet) {
        if (bullet.getShip() == this)
            bullets.remove(bullet);
            bullet.removeShip();
    }
    
    /**
     * Returns the amount of bullets currently loaded on this ship.
     * 
     * @return
     */
    public int getNbBullets() {
    	return bullets.size();
    }
    
    /**
     * Returns a HashSet containing all the bullets loaded on this ship.
     * 
     * @return  ...
     *          | @see implementation
     */
    public HashSet<Bullet> getAllBullets() {
    	return new HashSet<Bullet>(bullets);
    }
    
    /**
     * Returns the first bullet that is loaded on this ship. If there are no bullets loaded,
     * returns null.
     */
    private Bullet getFirstBullet() {
    	if ( bullets.size() == 0 )
    		return null;

    	for (Bullet bullet : bullets) {
    		return bullet;
		}
    	return null;
    }
    
    /**
     * Fires a bullet from the current ship in the direction that the ship is facing with
     * a velocity of 250km/s. If the ship is not in a world, it can't fire. If the position
     * the bullet should spawn at is not valid, the bullet is destroyed immediately. If the
     * bullet overlaps another entity in this world upon spawning, a collision is created
	 * and resolved.
     * 
     * @Post	The bullet is no longer loaded on this ship.
     *          | new.getAllBullets().contains(this.getFirstBullet()) == false
     * TODO : something with bullets spawning outside of the world
     */
    public void fireBullet() {				//Totally
        Bullet bullet = getFirstBullet();
        if ((bullet != null) && hasWorld()) {
            Vector nextBulletPosition = getPosition().add(getDirection().multiply((getRadius() + bullet.getRadius())));
            removeBullet(bullet);

            try {
                bullet.setPosition(nextBulletPosition);
                getWorld().addEntity(bullet);
                bullet.setVelocity(getDirection().multiply(Bullet.getInitialSpeed()));

            } catch (IllegalArgumentException e) {
                bullet.resolveInitialCollisions();
            }
        }
	}

	public void resolveCollisionWithShip(Ship ship) {
        double sigma = getRadius() + ship.getRadius();
        double J =
                (2.0 * getTotalMass() * ship.getTotalMass() *
                        ship
                        .getVelocity()
                        .getDifference(getVelocity())
                        .dotProduct(
                                ship
                                .getPosition()
                                .getDifference(getPosition())
                        )
                ) / (sigma * (getTotalMass() + ship.getTotalMass()));

        double Jx = J * (ship.getPosition().getX() - getPosition().getX()) / sigma;
        double Jy = J * (ship.getPosition().getY() - getPosition().getY()) / sigma;

        setVelocity(
                getVelocity().getX() + Jx/getTotalMass(),
                getVelocity().getY() + Jy/getTotalMass());
        ship.setVelocity(
                ship.getVelocity().getX() - Jx/ship.getTotalMass(),
                ship.getVelocity().getY() - Jy/ship.getTotalMass());
    }
    
    /** Terminates this ship. A terminated ship no longer belongs to a world and no longer has any bullets.
     * 	All bullets currently in this ship will be terminated as well.
     * 
     * @Post 	All bullets currently in this ship will be terminated.
     * 			| for bullet in this.getAllBullets()
     * 			|	bullet.isTerminated == true
     * @Post	This ship no longer has any bullets.
     * 			| (new this).getAllBullets().size() == 0
     * @Post	This ship does not belong to a world.
     * 			| (new this).getWorld() == null
     */
	@Override
	public void terminate() {
		try {
		    getWorld().removeEntity(this);
        } catch (NullPointerException e){}

		for (Bullet bullet : getAllBullets() ) {
			bullet.terminate();
		}

		bullets = new HashSet<Bullet>();
		isTerminated = true;
	}

    private Program program;

	@Basic
	public Program getProgram() {
	    return program;
    }

    @Basic
    public void loadProgram(Program program) {
	    program.setShip(this);
	    this.program = program;
    }

    public List<Object> executeProgram(double time) {
	    return program.execute(time);
    }
    
}
