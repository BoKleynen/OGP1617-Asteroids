package asteroids.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A Class of space ships involving a position, a velocity, an orientation and a radius.
 *
 * @invar   The speed of the space ship will always be less then or equal to the maximum speed,
 *          wich in turn will always be less then or equal to the speed of light.
 *          | getVelocity() <= getMaxSpeed() <= getSpeedOfLight()
 *
 * @invar   The orientation of the ship is an angle between 0 and 2PI radians.
 *          0 <= getOrientation() <= 2PI
 *
 * @author Yrjo Koyen and Bo Kleynen
 *
 */
public class Ship {

    public Ship() {
        setPosition(new Vector(0, 0));
        setVelocity(new Vector(0, 0));
        setOrientation(0);
        this.radius = getMinRadius();
        maxSpeed = getSpeedOfLight();
    }
    
    public Ship(Vector position, Vector velocity, double orientation, double radius)
            throws  IllegalArgumentException, NullPointerException {

        if (! canHaveAsRadius(radius))
            throw new IllegalArgumentException();

        setPosition(position);
        setVelocity(velocity);
        setOrientation(orientation);
        this.radius = radius;
        this.maxSpeed = speedOfLight;
    }

    /**
     *
     * @param position
     * @param velocity
     * @param orientation
     * @param radius
     * @param maxSpeed
     * @throws  IllegalArgumentException
     *          ...
     *          | ! canHaveAsRadius(radius)
     * @throws  NullPointerException
     *          ...
     *          | position == null
     */
    public Ship(Vector position, Vector velocity, double orientation, double radius, double maxSpeed)
            throws  IllegalArgumentException, NullPointerException {

        if (! canHaveAsRadius(radius))
            throw new IllegalArgumentException();

        setPosition(position);
        setVelocity(velocity);
        setOrientation(orientation);
        this.radius = radius;
        this.maxSpeed = maxSpeed;
    }

    private Vector position;    // defensively

    /**
     *
     * @return  The current position of this ship.
     *          | this.position
     */
    @Basic
    public Vector getPosition() {
        return position;
    }

    /**
     *
     * @param newPosition
     */
    @Basic
    public void setPosition(Vector newPosition) throws NullPointerException {
        if (newPosition == null) {
            throw new NullPointerException();
        }

        position = newPosition;
    }

    /**
     *
     * @param time
     */
    public void move(double time) {
        setPosition(getPosition().add(getVelocity().multiply(time)));
    }

    private static final double speedOfLight = 300000;

    @Basic @Immutable
    private static double getSpeedOfLight() {
        return speedOfLight;
    }

    private final double maxSpeed;

    public double getMaxSpeed() {
        return maxSpeed;
    }

    private Vector velocity;    // total

    /**
     * Returns the current velocity of this ship.
     *
     * @return The velocity of this ship
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
     *
     * @param a
     *
     * @post    If a is less then or equal to 0, nothing will happen.
     *          | if a <= 0 then
     *          |   new.getVelocity == getVelocity
     * @post    ...
     *          | if a > 0 then
     *          |   new.getVelocity == setVelocity(getVelocity().add(new Vector(a * Math.cos(getOrientation()), a * Math.sin(getOrientation()))));
     */
    public void thrust(double a) {
        if (a > 0) {
            setVelocity(getVelocity().add(new Vector(a * Math.cos(getOrientation()), a * Math.sin(getOrientation()))));
        }
    }

    private double orientation;     // nominal

    /**
     * Returns a boolean to check whether the given value of orientation is a valid value for the orientation of a ship.
     *
     * @return  True if and only if the given orientation is nonnegative and if the given orientation is smaller the 2*PI.
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
     *
     * @param angle
     */
    public void turn(double angle) {
        setOrientation((getOrientation() + angle) % 2 * Math.PI);
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
        return getDistanceBetween(spaceship) < 0;
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

        double d = Math.pow(deltaR.dotProduct(deltaV), 2) - deltaV.dotProduct(deltaV) * (deltaR.dotProduct(deltaR) - (getRadius() + spaceship.getRadius()));

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

        else
            return getPosition().add(getVelocity().multiply(time));

    }
}
