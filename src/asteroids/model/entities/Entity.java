package asteroids.model.entities;

import vector.Vector;
import asteroids.model.world.World;
import be.kuleuven.cs.som.annotate.*;


public abstract class Entity {


    Entity(Vector position, Vector velocity, double maxSpeed, double radius, double minRadius) {

        if (! canHaveAsPosition(position))
            throw new IllegalArgumentException();

        if (! canHaveAsRadius(radius, minRadius))
            throw new IllegalArgumentException();

        this.radius = radius;
        setPosition(position);
        // this.maxSpeed will never be NaN
        this.maxSpeed = maxSpeed <= getSpeedOfLight() ? maxSpeed : getSpeedOfLight();
        setVelocity(velocity);
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
     * @param 	newPosition
     * 			The new position for the ship.
     * @Post	The new position of this ship is equal to the given vector newPosition if
     * 			newPosition is a valid position for a ship
     * 			| if canHaveAsPosition(newPosition) then
     * 			|	new.getPosition() == newPosition
     * @throws	NullPointerException
     * 			If the vector newPosition refers a null object.
     * 			| newPosition == null;
     * @throws	IllegalArgumentException
     * 			If the the vector newPosition is not a valid position
     * 			| ! canHaveAsPosition(newPosition)
     */
    @Basic
    protected void setPosition(Vector newPosition) throws NullPointerException, IllegalArgumentException {
        if ( canHaveAsPosition(newPosition) )
            position = newPosition;
        else
            throw new IllegalArgumentException();
    }

    /**
     * Checks whether the vector position is a valid position for a ship.
     *
     * @param 	position
     * 			The position to be tested.
     * @return	True if and only if the components of the vector position are valid real numbers.
     * 			| result == ((! Double.isNaN(position.getX())) && (! Double.isNaN(position.getY())))
     * @throws 	NullPointerException
     * 			If the specified position refers a null object.
     * 		    | position == null
     */
    @Basic
    public boolean canHaveAsPosition(Vector position) throws NullPointerException {
        if (position == null)
            throw new NullPointerException();

        return ( ( ! Double.isNaN(position.getX()) ) && ( ! Double.isNaN(position.getY()) ) );
    }

    /**
     * Moves the ship in the direction of its current velocity. The distance traveled is
     * equal to the current velocity multiplied with the specified time it should travel in that direction.
     *
     * @param 	time
     * 			The time to move in the direction of the velocity vector.
     * @post	The new position is equal the the sum of the old position and the product of
     * 			the current velocity and time.
     * 			| new.getPosition() == getPosition().add(getVelocity().multiply(time)))
     *
     * @throws 	IllegalArgumentException
     * 			If the specified time is negative.
     * 			| time < 0
     */
    public void move(double time) throws IllegalArgumentException {
        if( time < 0 )
            throw new IllegalArgumentException();

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
    protected void setVelocity(Vector newVelocity) {
        if (newVelocity == null || Double.isNaN(newVelocity.getX()) || Double.isNaN(newVelocity.getY())) {
            newVelocity = new Vector(0, 0);
        }

        else if (newVelocity.dotProduct(newVelocity) > Math.pow(getMaxSpeed(), 2)) {
            newVelocity = newVelocity.normalize().multiply(getMaxSpeed());
        }

        velocity = newVelocity;
    }

    /**
     * Returns the speed of light.
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
     *
     * @return  The maximum speed of this ship.
     *          | this.maxSpeed
     */
    @Basic @Immutable
    public double getMaxSpeed() {
        return maxSpeed;
    }


    private World world;

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    private final double radius;        // defensively

    /**
     * Returns the radius of this ship.
     *
     * @return 	The radius of this ship
     * 			| result == this.radius
     */
    @Basic @Immutable
    public double getRadius() {
        return radius;
    }

    /**
     * Checks whether the supplied radius is a valid radius for a ship.
     *
     * @param 	radius The radius that needs to be validated.
     * @return 	True if and only if radius is a valid radius for a ship.
     * 			| result == (radius >= this.getMinRadius())
     */
    public boolean canHaveAsRadius(double radius, double minRadius) {
        return (radius >= minRadius);
    }

    /**
     * Returns the distance between this ship and the specified ship. The distance between a ship and itself is equal to 0.
     *
     * @param entity The ship between which and this the distance needs to be calculated.
     * @return  The distance between the edges of this ship and the ship entity.
     */
    public double getDistanceBetween(Entity entity) {
        return this == entity ? 0 : getPosition().getDistance(entity.getPosition()) - getRadius() - entity.getRadius();
    }

    /**
     * Returns a boolean to check if this ship overlaps with the specified ship.
     * Two ships overlap if and only if they are the same ship or the distance between their centers is negative.
     * | spaceship == this || this.getDistanceBetween(spaceship) < 0
     *
     * @param	entity
     * 			The ship that might overlap this ship
     * @return	True if and only if both ships overlap.
     * 			| result == ( getDistanceBetween(spaceship) <= 0 )
     */
    public boolean overlap(Entity entity){
        return getDistanceBetween(entity) <= 0;
    }

    /**
     * Returns the amount of time until this ship will collide with the specified entity.
     * If both ships are on a collision course, the time returned by this method will be the
     * finite amount of time it will take for both ships to hit each other if they keep moving
     * in the same direction and with the same velocity. If the ships are not currently on a
     * collision course, if they move away from each other or if they will just pass each other
     * without the hulls touching, the time returned will be positive infinity.
     *
     * @return	The time until the collision will happen if the ships keep moving in the exact
     * 			same way as they are currently moving.  If no collision will occur within a
     * 			finite amount of time, this method will return positive infinity.
     * @Post	If both ships keep moving in the exact same way they are currently moving, then
     * 			they will collide after the returned amount of time. This means their hulls will
     * 			touch after exactly the returned amount of time.
     * 			| this.getDistanceBetween(entity) == 0
     * @throws 	IllegalArgumentException
     * 			If the supplied entity is this ship or if the supplied ship already overlaps
     * 			this ship.
     * 			| this.overlap(entity)
     */
    public double getTimeToCollision(Entity entity) throws IllegalArgumentException {
        if (overlap(entity)) {
            throw new IllegalArgumentException();
        }

        Vector deltaR = entity.getPosition().getDifference(this.getPosition());
        Vector deltaV = entity.getVelocity().getDifference(this.getVelocity());

        if (deltaR.dotProduct(deltaV) >= 0) {
            return Double.POSITIVE_INFINITY;
        }

        double d = Math.pow(deltaR.dotProduct(deltaV), 2) - deltaV.dotProduct(deltaV) *
                (deltaR.dotProduct(deltaR) - Math.pow(getRadius() + entity.getRadius(), 2));

        return d <= 0 ? Double.POSITIVE_INFINITY : -(deltaV.dotProduct(deltaR) + Math.sqrt(d)) / deltaV.dotProduct(deltaV);

    }

    /**
     * Returns the position at which this ship will collide with the specified ship, if ever, assuming both
     * ships maintain their velocity and direction from the time this method is called, otherwise null is returned.
     *
     * @return  The position of the point of impact between this ship and the specified ship if they maintain their
     *          velocity and direction from the time this method is called.
     *          If no collision will occur, in case the time to collision is positive infinity, this method returns null.
     *          | if getTimeToCollision(entity) == Double.POSITIVE_INFINITY then
     *          |   null
     *          If the time to collision is a finite value this method returns the point of impact between both ships.
     *          | if getTimeToCollision(entity) != Double.POSITIVE_INFINITY
     *          This point is located at a distance equal to the radius of this ship from the center of this ship and at
     *          a distance equal to the radius of the specified ship from the center of the specified ship after a time
     *          ∆t == getTimeToCollision(entity) has passed since the invocation of this method.
     *          | new == this.move(∆t) && (new entity) == entity.move(∆t)
     *          | new.getPosition().getDistance(result) == new.getRadius() && (new entity).getPosition().getDistance(result)
     * @throws IllegalArgumentException
     *          If this ship overlaps with the specified ship.
     *          | this.overlap(entity)
     */
    public Vector getCollisionPosition(Entity entity) throws IllegalArgumentException {
        if (overlap(entity)) {
            throw new IllegalArgumentException();
        }

        double time = getTimeToCollision(entity);

        if (time == Double.POSITIVE_INFINITY)
            return null;

        else {
            Vector position1 = getPosition().add(getVelocity().multiply(time));
            Vector position2 = entity.getPosition().add(entity.getVelocity().multiply(time));

            return position2.getDifference(position1).normalize().multiply(getRadius()).add(position1);

        }
    }
}
