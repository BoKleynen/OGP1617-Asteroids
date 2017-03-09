package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A Class of space ships involving a position, a velocity, an orientation and a radius.
 *
 * TODO class invariants, @param
 * TODO add asserts to nominal methods
 *
 * @invar The speed shall never exceed the maximum speed, wich in turn shall never exceed the speed of light.
 *          | getVelocity.dotProduct(getVelocity()) <= getMaxSpeed() <= getSpeedOfLight
 * @invar   The orientation of the ship is an angle between 0 and 2PI radians.
 *          | 0 <= getOrientation() <= 2PI
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
    }

    /**
     * Creates a new ship and initializes its position to the given position vector,
     * its velocity to the given velocity vector, its orientation to the given orientation
     * and its radius to the given radius.
     * 
     * @Effect	Creates a new ship with all the specified values and initializes the ships
     * 			maximum speed to the speed of light.
     * 			| this(position, velocity, orientation, radius, getSpeedOfLight())
     * 
     * @throws 	IllegalArgumentException
     * 			If the specified radius is not valid for a ship.
     *          | ! canHaveAsRadius(radius)
     * @throws 	NullPointerException
     * 			If the specified position refers a null object.
     *          | position == null
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
     * @Pre     The orientation of this ship must be a valid orientation.
     *          | canHaveAsOrientation(orientation)
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
     *          If the specified radius is not valid for a ship, or the specified position contains NaN as one of its components.
     *          | ! canHaveAsRadius(radius) || ! canHaveAsPosition(position)
     * @throws  NullPointerException
     *          If the specified position refers a null object
     *          | position == null
     */
    public Ship(Vector position, Vector velocity, double orientation, double radius, double maxSpeed)
            throws  IllegalArgumentException, NullPointerException {

        if (! canHaveAsRadius(radius))
            throw new IllegalArgumentException();

        if (! canHaveAsPosition(position))
            throw new IllegalArgumentException();

        this.radius = radius;
        // this.maxSpeed will never be NaN
        this.maxSpeed = maxSpeed <= getSpeedOfLight() ? maxSpeed : getSpeedOfLight();
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
    private void setPosition(Vector newPosition) throws NullPointerException {   //private
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
    private boolean canHaveAsPosition(Vector position) throws NullPointerException {    // public
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
     * @post    If one of the components of newVelocity is NaN, the velocity is set to 0.
     *          | if Double.isNaN(newVelocity.getX()) || Double.isNaN(newVelocity.getY()) then
     *          |   new.getVelocity == Vector(0, 0)
     * @post    If the magnitude of newVelocity is smaller then the maximum value for the velocity, this ships velocity
     *          is equal to the given vector newVelocity.
     *          | if newVelocity.getMagnitude() =< getMaxSpeed() then new.getVelocity() == newVelocity
     * @post    If the magnitude of newVelocity exceeds the maximum velocity, the new velocity for this ship is set to a
     *          vector pointing in the direction of newVelocity with a magnitude equal to the maximum velocity.
     *          | if newVelocity.getMagnitude() > getMaxSpeed() then
     *          |   new.getVelocity() == newVelocity.normalize().multiply(getMaxSpeed())
     *
     */
    @Basic
    private void setVelocity(Vector newVelocity) {
        if (newVelocity == null || Double.isNaN(newVelocity.getX()) || Double.isNaN(newVelocity.getY())) {
            newVelocity = new Vector(0, 0);
        }

        else if (newVelocity.dotProduct(newVelocity) > Math.pow(getMaxSpeed(), 2)) {
            newVelocity = newVelocity.normalize().multiply(getMaxSpeed());
        }

        velocity = newVelocity;
    }

    /**
     * Thrusts the ship forward in the direction of the current orientation, and changes its
     * current velocity
     * 
     * @param 	a The magnitude of the thrust
     *
     * @post    If a is less then, equal to 0 or NaN, nothing will happen.
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

    /**
     * Returns an approximation for the speed of light.
     * 
     * @return  The speed of light (approximately 300000 km/s)
     *          | this.speedOfLight
     */
    @Immutable
    public static double getSpeedOfLight() {
        return 300000;
    }

    private final double maxSpeed;

    /**
     * Returns the maximum allowed speed for this ship.
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
     * @return  True if and only if the given orientation is non negative and
     * 			if the given orientation is smaller then 2*PI.
     *          | result == ((0.0 <= orientation) && (orientation < 2.0 * Math.PI))
     */
    @Basic 
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
    private void setOrientation(double newOrientation) {     // private
        assert canHaveAsOrientation(newOrientation);
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

        setOrientation(newOrientation >= 0 ? newOrientation : newOrientation + 2 * Math.PI);
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
     * Returns the smallest allowed radius for a ship.
     * 
     * @return	The smallest allowed radius.
     * 			| result == this.minRadius
     */
    @Basic @Immutable
    private static double getMinRadius() {
        return minRadius;
    }

    /**
     * Checks whether the supplied radius is a valid radius for a ship.
     *
     * @param 	radius 
     * 			The radius that needs to be validated
     * @return 	True if and only if radius is a valid radius for a ship
     * 			| result == (radius >= this.getMinRadius())
     */
    private boolean canHaveAsRadius(double radius) {    // public
        return (radius >= getMinRadius());
    }

    /**
     *	Returns the distance between the edges of this ship and the specified ship spaceship.
     *
     * @param 	spaceship
     * 			The distance will be calculated between this spaceship and the current ship.
     * @return  The distance between the edges of this ship and the ship spaceship.
     * 			| if spaceship == this then
     * 			|	result == 0
     * 			| else
     * 			|	result == getPosition().getDistance(spaceship.getPosition) - ( getRadius() + spaceship.getRadius() )
     */
    public double getDistanceBetween(Ship spaceship) {
        return this == spaceship ? 0 : getPosition().getDistance(spaceship.getPosition()) - getRadius() - spaceship.getRadius();
    }

    /**
     *	Returns true if the specified ship spaceship and this ship overlap.
     *
     * @param	spaceship
     * 			The ship that might overlap this ship
     * @return	True if and only if both ships overlap.
     * 			| result == ( getDistanceBetween(spaceship) <= 0 ) 
     */
    public boolean overlap(Ship spaceship){
        return getDistanceBetween(spaceship) <= 0;
    }

    /**
     * Returns the amount of time until this ship will collide with the specified spaceship. 
     * If both ships are on a collision course, the time returned by this method will be the
     * finite amount of time it will take for both ships to hit each other if they keep moving 
     * in the same direction and with the same velocity. If the ships are not currently on a
     * collision course, if they move away from each other or if they will just pass each other 
     * without the hulls touching, the time returned will be positive infinity.
     * 
     * @return	The time until the collision will happen if the ships keep moving in the exact
     * 			same way as they are currently moving.  If no collision will occur within a 
     * 			finite amount of time, this method will return positive infinity.
     * 
     * @Post	If both ships keep moving in the exact same way they are currently moving, then
     * 			they will collide after the returned amount of time. This means their hulls will 
     * 			touch after exactly the returned amount of time.
     * 			| this.getDistanceBetween(spaceship) == 0
     *          
     *
     * @throws IllegalArgumentException
     * 			If the supplied spaceship is this ship.
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

        double d = Math.pow(deltaR.dotProduct(deltaV), 2) - deltaV.dotProduct(deltaV) *
                (deltaR.dotProduct(deltaR) - Math.pow(getRadius() + spaceship.getRadius(), 2));

        return d <= 0 ? Double.POSITIVE_INFINITY : -(deltaV.dotProduct(deltaR) + Math.sqrt(d)) / deltaV.dotProduct(deltaV);

    }

    /**
     * Returns the position where this ship and the specified ship will collide, if they will collide. 
     * The collision position is the point where the hulls of both ships will touch.
     * 
     * @param 	spaceship
     * 			The spaceship that this ship will collide with.
     * @return  The position vector of the point of impact between this ship and the ship spaceship, if they will ever collide
     *          at their current heading, null otherwise.
     *          | result.getDistance(getPosition().move(getTimeToCollision(spaceship))) == getRadius && 
     *          	result.getDistance(spaceship.getPosition().move(getTimeToCollision(this))) == spaceship.getRadius()
     * @throws IllegalArgumentException
     *          If both ships overlap
     *          | overlap(spaceship)
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
