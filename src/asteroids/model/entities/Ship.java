package asteroids.model.entities;

import vector.Vector;
import be.kuleuven.cs.som.annotate.*;

import java.util.HashSet;

/**
 * A Class of space ships involving a position, a velocity, an orientation and a radius.
 *
 * @Invar 	The speed shall never exceed the maximum speed, which in turn shall never exceed the speed of light.
 *          | getVelocity().getMagnitude() <= getMaxSpeed() && getMaxSpeed() <= getSpeedOfLight
 * @Invar   The orientation of a ship is always a valid orientation.
 *          | Ship.anHaveAsOrientation(this.getOrientation)
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
    	this(new Vector(0, 0), new Vector(0, 0), 0, getMinRadius(), getSpeedOfLight(), 0, 1.1 * Math.pow(10, 21));
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

    	this(position, velocity, orientation, radius, getSpeedOfLight(), mass, 1.1 * Math.pow(10, 21));
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
    public Ship(Vector position, Vector velocity, double orientation, double radius, double maxSpeed, double mass, double thrust)
            throws  IllegalArgumentException, NullPointerException {
        super(position, velocity, maxSpeed, radius, getMinRadius(), mass, getMinMassDensity());

        setOrientation(orientation);
        setThrust(thrust);
        thrustOff();

    }

    private static final double minMassDensity = 1.42 * Math.pow(10, 12);

    /**
     * Returns the minimal mass density for a Ship.
     *
     * @return
     */
    @Basic @Immutable
    public static double getMinMassDensity() {
        return minMassDensity;
    }

    private static final double minRadius = 10;

    /**
     *
     * @return
     */
    @Basic @Immutable
    private static double getMinRadius() {
        return minRadius;
    }

    /**
     * Returns the total mass of a ship.
     * The total mass is the mass of the ship itself plus the mass of all the bullets.
     *
     * @return
     */
    public double getTotalMass() {
        double totalMass = getMass();

        for (Bullet bullet: bullets) {
            totalMass += bullet.getMass();
        }

        return totalMass;
    }
    
    public boolean isValidMass(double mass) {
    	return mass >= ( (4.0/3.0) * Math.pow(getRadius(), 3) * Math.PI * getMinMassDensity() );
    }
    
    public double getMassDensity() {
    	return ( getTotalMass() / (4.0/3.0) * Math.pow(getRadius(), 3) * Math.PI );
    }  
    
    private boolean thrusterOn;

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
     * @return
     */
    @Basic
    public double getThrust() {
        return thrust;
    }

    public void setThrust(double newThrust) {
        thrust = newThrust;
    }

    /**
     *
     * @param time
     * @Post    | new.getVelocity().getX() == getThrust() / getMass() * Math.cos(getOrientation()) * time
     *          | new.getVelocity().getY() == getThrust() / getMass() * Math.sin(getOrientation()) * time
     */
    public void accelerate(double time) {
        if (thrusterOn()) {
            double acceleration = getThrust() / getMass();

            setVelocity(getVelocity().add(new Vector(acceleration * Math.cos(getOrientation()) * time, acceleration * Math.sin(getOrientation()) * time)));
        }
    }

    /**
     *
     * @param 	time
     * 			The time to move in the direction of the velocity vector.
     */
    @Override
    public void move(double time) {
        if( time < 0 )
            throw new IllegalArgumentException();

        setPosition(getPosition().add(getVelocity().multiply(time)));

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
            setVelocity(getVelocity().add(new Vector(acceleration * Math.cos(getOrientation()), acceleration * Math.sin(getOrientation()))));
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
     * @Pre     newOrientation is a valid orientation for a ship
     *          | isValidOrientation(newOrientation)
     * @post    The new orientation of this ship is equal to newOrientation
     *          | new.getOrientation() == newOrientation
     */
    @Basic
    private void setOrientation(double newOrientation) {
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
    	assert( ( ! Double.isNaN(angle) ) && ( ! Double.isInfinite(angle) ) );
    	double newOrientation = (getOrientation() + angle) % (2 * Math.PI);

        setOrientation(newOrientation >= 0 ? newOrientation : newOrientation + 2 * Math.PI);
    }

    private HashSet<Bullet> bullets = new HashSet<>();

    /**
     * Reloads the given bullet onto this ship
     *
     * @param bullet
     */
    public void reloadBullet(Bullet bullet) {
        bullet.setWorld(null);
        bullets.add(bullet);
    }

    @Override
    public void resolveCollisionWithShip(Ship ship) {
        double sigma = getRadius() + ship.getRadius();
        double J = (2.0 * getMass() * ship.getMass() * getVelocity().getDifference(ship.getVelocity()).dotProduct(getPosition().getDifference(ship.getPosition()))) /
                (sigma * (getMass() + ship.getMass()));

        double Jx = J * (getPosition().getX() - ship.getPosition().getX()) / sigma;
        double Jy = J * (getPosition().getY() - ship.getPosition().getY()) / sigma;

        setVelocity(new Vector(getVelocity().getX() + Jx/getMass(),getVelocity().getY() + Jy/getMass()));
        ship.setVelocity(new Vector(ship.getVelocity().getX() - Jx/ship.getMass(),ship.getVelocity().getY() - Jy/ship.getMass()));


    }

    @Override
    public void resolveCollisionWithBullet(Bullet bullet) {
        if (bullet.getParentShip() == this) {
            reloadBullet(bullet);
        }

        else {
            die();
            bullet.die();
        }

    }
}
