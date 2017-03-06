package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A Class of space ships involving a position, a velocity, an orientation and a radius.
 *
 * @invar   The orientation of the ship is an angle between 0 and 2PI radians.
 *          0 <= getOrientation() <= 2PI
 *
 * @author Yrjo Koyen and Bo Kleynen
 *
 */
public class Ship {

    /**
     * Creates a new ship with default values.
     * 
     * @Effect	Creates a new ship with default values. The velocity and position are equal
     * 			to the zero vector, the orientation is equal to zero, the radius is equal 
     * 			to the smallest possible radius and the maximum speed is equal to the speed
     * 			of light.
     * 			| this(new Vector(0, 0), new Vector(0, 0), 0, getMinRadius(), getSpeedOfLight())
     * 
     */
    public Ship() {
    	this(new Vector(0, 0), new Vector(0, 0), 0, getMinRadius(), getSpeedOfLight());
//        maxSpeed = getSpeedOfLight();
//        radius = getMinRadius();
//
//        setPosition(new Vector(0, 0));
//        setVelocity(new Vector(0, 0));
//        setOrientation(0);
    }

    /**
     * Creates a new ship and initializes its position to the given position vector,
     * its velocity to the given velocity vector, its orientation to the given orientation
     * and its radius to the given radius.
     * 
     * @param position
     * @param velocity
     * @param orientation
     * @param radius
     * 
     * @Effect	Creates a new ship with all the specified values and initializes the ships
     * 			maximum speed to the speed of light.
     * 			| this(position, velocity, orientation, radius, getSpeedOfLight())
     * 
     * @throws IllegalArgumentException
     * @throws NullPointerException
     */
    public Ship(Vector position, Vector velocity, double orientation, double radius)
            throws  IllegalArgumentException, NullPointerException {

    	this(position, velocity, orientation, radius, getSpeedOfLight());
    }

    /**
     * Creates a new ship and initializes its position to the given position vector,
     * its velocity to the given velocity vector, its orientation to the given orientation, 
     * its radius to the given radius and its maxSpeed to the given maxSpeed.
     * 
     * @param position
     * @param velocity
     * @param orientation
     * @param radius
     * @param maxSpeed
     * 
     * @Post	The new position of this ship is equal to the specified vector position.
     * 			| new.getPosition().equals(position)
     * @Post	The new velocity of this ship is equal to the specified vector velocity.
     * 			| new.getVelocity().equals(velocity)
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
     * 
     * @throws  IllegalArgumentException
     *          If the specified radius is not valid for a ship.
     *          | ! canHaveAsRadius(radius)
     * @throws  NullPointerException
     *          ...
     *          | position == null
     */
    public Ship(Vector position, Vector velocity, double orientation, double radius, double maxSpeed)
            throws  IllegalArgumentException, NullPointerException {

        if (! canHaveAsRadius(radius))
            throw new IllegalArgumentException();

        if (! canHaveAsPosition(position))
            throw new IllegalArgumentException();

        this.radius = radius;

        if (maxSpeed > getSpeedOfLight())
            this.maxSpeed = getSpeedOfLight();
        else
            this.maxSpeed = maxSpeed;

        setPosition(position);
        setVelocity(velocity);
        setOrientation(orientation);
    }

    private Vector position;    // defensively

    /**
     * Returns the current position vector of this ship.
     * 
     * @return  The current position of this ship.
     *          | result == this.position
     */
    @Basic
    public Vector getPosition() {
        return position;
    }

    /**
     * Sets the position of the ship to the specified position newPosition if it is a 
     * valid position for a ship.
     * 
     * @param newPosition The new position for the ship.
     * 
     * @Post	The new position of this ship is equal to the given vector newPosition if
     * 			newPosition is a valid position for a ship
     * 			| if canHaveAsPosition(newPosition) then
     * 			|	new.getPosition() == newPosition
     * 
     * @throws	NullPointerException if newPosition is null.
     */
    @Basic
    public void setPosition(Vector newPosition) throws NullPointerException {
        if ( canHaveAsPosition(newPosition) )
        	position = newPosition;
    }

    /**
     * Checks whether the vector position is a valid position for a ship.
     * 
     * @param position
     * 
     * @return	True if and only if the components of the vector position are smaller then
     * 			the maximum value for the type double.
     * 			| result == ( (Math.abs(position.getX()) <= Double.MAX_VALUE)
     * 			|	&& (Math.abs(position.getY()) <= Double.MAX_VALUE) )
     * @throws NullPointerException if position is null
     */
    private boolean canHaveAsPosition(Vector position) throws NullPointerException {
    	if (position == null)
    		throw new NullPointerException();
    	
        return -Double.MAX_VALUE <= position.getX() && position.getX() <= Double.MAX_VALUE &&
                -Double.MAX_VALUE <= position.getY() && position.getY() <= Double.MAX_VALUE;
    }

    /**
     * Moves the ship in the direction of its current velocity. The distance traveled is
     * equal to the current velocity multiplied with the time it should travel in that direction.
     * 
     * @post	The new position is equal the the sum of the old position and the product of
     * 			the current velocity and time.
     * 			| new.getPosition() == getPosition().add(getVelocity().multiply(time)))
     * @param time
     */
    public void move(double time) {
        setPosition(getPosition().add(getVelocity().multiply(time)));
    }

    private Vector velocity;    // total

    /**
     * Returns the current velocity of this ship.
     *
     * @return 	The velocity of this ship
     * 			| result == this.velocity
     */
    @Basic
    public Vector getVelocity() {
        return velocity;
    }

    /**
     * Sets the velocity of the given ship to the specified velocity vector newVelocity. If the magnitude of newVelocity
     * exceeds the maximum value for a ship's velocity, the velocity of the ship is set to a new vector pointing in the
     * same direction as the vector newVelocity but with a magnitude of the maximum velocity.
     *
     * @param   newVelocity
     *          The new velocity vector for this ship.
     * @post    If newVelocity references a null object, the velocity is set to 0 in both the x and y direction.
     *          | if newVelocity == null then
     *          |   new.getVelocity() == Vector(0, 0)
     * @post    If the magnitude of newVelocity is smaller then the maximum value for the velocity, this ships velocity
     *          is equal to the given vector newVelocity.
     *          | if newVelocity.getMagnitude() =< getMaxSpeed() then new.getVelocity() == newVelocity
     * @post    If the magnitude of newVelocity exceeds the maximum velocity, the new velocity for this ship is set to a
     *          vector pointing in the direction of newVelocity with a magnitude equal to the maximum velocity.
     *          | if newVelocity.getMagnitude() > getMaxSpeed() then
     *          |   new.getVelocity() == newVelocity.normalize().multiply(getMaxSpeed())
     */
    @Basic
    public void setVelocity(Vector newVelocity) {
        if (newVelocity == null) {
            velocity = new Vector(0, 0);
        }

        if (newVelocity.getMagnitude() > getMaxSpeed()) {
            newVelocity = newVelocity.normalize().multiply(getMaxSpeed());
        }

        velocity = newVelocity;
    }

    /**
     * Thrusts the ship forward in the direction of the current orientation, and changes its
     * current velocity
     * 
     * @param a The magnitude of the thrust
     *
     * @post    If a is less then or equal to 0, nothing will happen.
     *          | if a <= 0 then
     *          |   new.getVelocity == getVelocity
     * @post    If a is strictly positive, the velocity of the ship will change. A vector
     * 			with a magnitude of 'a' and in the direction of the current orientation
     * 			will be added to the current velocity.
     *          | if a > 0 then
     *          |   new.getVelocity == getVelocity().add(
     *          |		new Vector(a * Math.cos(getOrientation()), a * Math.sin(getOrientation())));
     */
    public void thrust(double a) {
        if (a > 0) {
            setVelocity(getVelocity().add(new Vector(a * Math.cos(getOrientation()), a * Math.sin(getOrientation()))));
        }
    }

    private static final double speedOfLight = 300000;

    /**
     * Returns an approximation for the speed of light.
     * @return  The speed of light (approximated to 300000km/s)
     *          | this.speedOfLight
     */
    @Basic @Immutable
    private static double getSpeedOfLight() {
        return speedOfLight;
    }

    private final double maxSpeed;

    /**
     *
     * @return  The maximum speed of this ship.
     *          | this.maxSpeed
     */
    @Basic @Immutable
    public double getMaxSpeed() {
        return maxSpeed;
    }

    private double orientation;     // nominal

    /**
     * Returns a boolean to check whether the given value of orientation is a valid value
     * for the orientation of a ship.
     *
     * @return  True if and only if the given orientation is nonnegative and
     * 			if the given orientation is smaller the 2*PI.
     *          | result == ((0.0 <= orientation) && (orientation <= 2.0 * Math.PI))
     */
    public static boolean canHaveAsOrientation(double orientation) {
        return ((0.0 <= orientation) && (orientation <= 2.0 * Math.PI));
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
     * Sets the orientation for this asteroids.model.Ship to newOrientation
     *
     * @param   newOrientation
     *          The new orientation for this ship.
     * @Pre     newOrientation is a valid orientation for a ship
     *          | isValidOrientation(newOrientation)
     * @post    The new orientation of this ship is equal to newOrientation
     *          | new.getOrientation() == newOrientation
     */
    @Basic
    public void setOrientation(double newOrientation) {
        orientation = newOrientation;
    }

    /**
     * Turns the ship clockwise over the specified angle
     * 
     * @param angle
     * 
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
    	double newOrientation = (getOrientation() + angle) % (2 * Math.PI);
        setOrientation( (newOrientation>=0 ? newOrientation : newOrientation + 2*Math.PI));
    }

    private final double radius;        // defensively

    /**
     * Returns the radius of this ship.
     * 
     * @return The radius of this ship
     */
    @Basic @Immutable
    public double getRadius() {
        return radius;
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
     * Checks whether the supplied radius is a valid radius for a ship.
     *
     * @param 	radius The radius that needs to be validated
     * @return 	True if and only if radius is a valid radius for a ship
     * 			| result == (radius >= this.getMinRadius())
     */
    private boolean canHaveAsRadius(double radius) {
        return (radius >= getMinRadius());
    }

    /**
     *
     * @param spaceship
     * @return  The distance between the edges of this ship and the ship spaceship.
     */
    public double getDistanceBetween(Ship spaceship) {
        if (this == spaceship)
            return 0;

        return getPosition().getDistance(spaceship.getPosition()) - getRadius() - spaceship.getRadius();
    }

    /**
     *
     * @param spaceship
     * @return
     */
    public boolean overlap(Ship spaceship){
        return getDistanceBetween(spaceship) <= 0;
    }

    /**
     *
     * @param spaceship
     * @return
     * @throws IllegalArgumentException
     */
    public double getTimeToCollision(Ship spaceship) throws IllegalArgumentException {
        if (overlap(spaceship)) {
            throw new IllegalArgumentException();
        }

        Vector deltaR = spaceship.getPosition().getDifference(this.getPosition());
        Vector deltaV = spaceship.getVelocity().getDifference(this.getVelocity());

        if (deltaR.dotProduct(deltaV) >= 0) {
            return Double.POSITIVE_INFINITY;
        }

        double d = Math.pow(deltaR.dotProduct(deltaV), 2) - deltaV.dotProduct(deltaV) * (deltaR.dotProduct(deltaR) - Math.pow(getRadius() + spaceship.getRadius(), 2));

        if (d <= 0) {
            return Double.POSITIVE_INFINITY;
        }

        else
            return -((deltaV.dotProduct(deltaR) + Math.sqrt(d)) / deltaV.dotProduct(deltaV));

    }

    /**
     *
     * @param spaceship
     * @return
     * @throws IllegalArgumentException
     */
    public Vector getCollisionPosition(Ship spaceship) throws IllegalArgumentException {
        if (overlap(spaceship)) {
            throw new IllegalArgumentException();
        }

        double time = getTimeToCollision(spaceship);

        if (time == Double.POSITIVE_INFINITY)
            return null;

        else {
            Vector position1 = getPosition().add(getVelocity().multiply(time));
            Vector position2 = spaceship.getPosition().add(spaceship.getVelocity().multiply(time));

            return position2.getDifference(position1).normalize().multiply(getRadius()).add(position1);

        }
    }
}
