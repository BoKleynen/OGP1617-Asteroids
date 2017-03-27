package asteroids.model.entities;

import asteroids.part2.CollisionListener;
import vector.Vector;
import asteroids.model.world.World;
import be.kuleuven.cs.som.annotate.*;


public abstract class Entity {

    /**
     * Initializes a new entity with given position, velocity, maximum speed, radius mass. This entity is not associated with a World.
     *
     * @param position
     * @param velocity
     * @param maxSpeed
     * @param radius
     * @param minRadius
     * @param mass
     * @param minMassDensity
     */
    Entity(Vector position, Vector velocity, double maxSpeed, double radius, double minRadius, double mass, double minMassDensity){
        this(position, velocity, maxSpeed, radius, minRadius, mass, minMassDensity, null);
    }


    /**
     * Initializes a new entity with given position, velocity, maximum speed, radius mass and associates this entity with the given world.
     *
     * @param position
     * @param velocity
     * @param maxSpeed
     * @param radius
     * @param minRadius
     * @param mass
     * @param minMassDensity
     * @param world
     */
    Entity(Vector position, Vector velocity, double maxSpeed, double radius, double minRadius, double mass, double minMassDensity, World world) {
        if (! canHaveAsPosition(position))
            throw new IllegalArgumentException();

        if (! canHaveAsRadius(radius, minRadius))
            throw new IllegalArgumentException();

        this.radius = radius;

        setPosition(position);

        // this.maxSpeed will never be NaN
        this.maxSpeed = maxSpeed <= getSpeedOfLight() ? maxSpeed : getSpeedOfLight();
        setVelocity(velocity);

        setMass(mass, minMassDensity);

        setWorld(world);
    }

    private boolean isTerminated = false;

    public boolean isTerminated() {
        return isTerminated;
    }

    public void terminate() {
        isTerminated = true;
    }

    /**
     * Checks whether the supplied radius is a valid radius for a entity.
     *
     * @param 	radius The radius that needs to be validated.
     * @return 	True if and only if radius is a valid radius for a entity.
     * 			| result == (radius >= this.getMinRadius())
     */
    public boolean canHaveAsRadius(double radius, double minRadius) {
        return (radius >= minRadius);
    }

    private static final double speedOfLight = 300000;

    /**
     * Returns the speed of light.
     *
     * @return  The speed of light (approximately 300000 km/s)
     *          | this.speedOfLight
     */
    @Immutable
    public static double getSpeedOfLight() {
        return speedOfLight;
    }

    private final double radius;        // defensively

    /**
     * Returns the radius of this entity.
     *
     * @return 	The radius of this entity
     * 			| result == this.radius
     */
    @Basic @Immutable
    public double getRadius() {
        return radius;
    }

    private double mass;    // total

    @Basic
    public double getMass() {
        return mass;
    }

    /**
     * Sets the mass of this entity to the given newMass, unless this would result in this entity having a mass that is
     * lower then the minimal allowed mass, then it is set to this minimal value.
     *
     * @param newMass
     * @param minMassDensity
     */
    private void setMass(double newMass, double minMassDensity) {
        double minMass = getMinMass(getRadius(), minMassDensity);
        this.mass = newMass >= minMass ? newMass : minMass;
    }

    /**
     * Returns the minimal mass of a spherical object given a radius and a minimal mass density
     * @param radius
     * @param minMassDensity
     * @return
     */
    public double getMinMass(double radius, double minMassDensity) {
        return 4/3 * Math.PI * Math.pow(radius, 3) * minMassDensity;
    }

    private World world;

    /**
     * Returns the world this Entity belongs to, if this Entity belongs to no world this method will return null.
     *
     * @return
     */
    public World getWorld() {
        return world;
    }

    /**
     * Associates this entity with the given World.
     *
     * @param world
     */
    public void setParentWorld(World world) {
        this.world = world;
    }

    private Vector position;    // defensively

    /**
     * Returns the current position vector of this entity.
     *
     * @return  The current position of this entity.
     *          | result == this.position
     */
    @Basic
    public Vector getPosition() {
        return position;
    }

    /**
     * Sets the position of the entity to the specified position newPosition if it is a
     * valid position for a entity.
     *
     * @param 	newPosition
     * 			The new position for the entity.
     * @Post	The new position of this entity is equal to the given vector newPosition if
     * 			newPosition is a valid position for a entity
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
    void setPosition(Vector newPosition) throws NullPointerException, IllegalArgumentException {
        if ( canHaveAsPosition(newPosition) )
            position = newPosition;
        else
            throw new IllegalArgumentException();
    }

    /**
     * Checks whether the vector position is a valid position for a entity.
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
     * Moves the entity in the direction of its current velocity. The distance traveled is
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
    public void move(double time) {
        if( time < 0 )
            throw new IllegalArgumentException();

        if (getWorld() != null) {
            World world = getWorld();

            world.removeEntity(this);
            setPosition(getPosition().add(getVelocity().multiply(time)));
            world.addEntity(this);


        }

        else
            setPosition(getPosition().add(getVelocity().multiply(time)));
    }

    private Vector velocity;    // total

    /**
     * Returns the current velocity of this entity.
     *
     * @return 	The velocity of this entity
     * 			| result == this.velocity
     */
    @Basic
    public Vector getVelocity() {
        return velocity;
    }

    /**
     * Sets the velocity of the given entity to the specified velocity vector newVelocity. If the magnitude of newVelocity
     * exceeds the maximum value for a entity's velocity, the velocity of the entity is set to a new vector pointing in the
     * same direction as the vector newVelocity but with a magnitude of the maximum velocity.
     *
     * @param   newVelocity
     *          The new velocity vector for this entity.
     * @post    If newVelocity references a null object, the velocity is set to 0 in both the x and y direction.
     *          | if newVelocity == null then
     *          |   new.getVelocity() == Vector(0, 0)
     * @post    If one of the components of newVelocity is NaN, the velocity is set to 0.
     *          | if Double.isNaN(newVelocity.getX()) || Double.isNaN(newVelocity.getY()) then
     *          |   new.getVelocity == Vector(0, 0)
     * @post    If the magnitude of newVelocity is smaller then the maximum value for the velocity, this entities velocity
     *          is equal to the given vector newVelocity.
     *          | if newVelocity.getMagnitude() =< getMaxSpeed() then new.getVelocity() == newVelocity
     * @post    If the magnitude of newVelocity exceeds the maximum velocity, the new velocity for this entity is set to a
     *          vector pointing in the direction of newVelocity with a magnitude equal to the maximum velocity.
     *          | if newVelocity.getMagnitude() > getMaxSpeed() then
     *          |   new.getVelocity() == newVelocity.normalize().multiply(getMaxSpeed())
     *
     */
    @Basic
    void setVelocity(Vector newVelocity) {
        if (newVelocity == null || Double.isNaN(newVelocity.getX()) || Double.isNaN(newVelocity.getY())) {
            newVelocity = new Vector(0, 0);
        }

        else if (newVelocity.dotProduct(newVelocity) > Math.pow(getMaxSpeed(), 2)) {
            newVelocity = newVelocity.normalize().multiply(getMaxSpeed());
        }

        velocity = newVelocity;
    }

    private final double maxSpeed;

    /**
     * Returns the maximum speed of this Entity.
     *
     * @return  The maximum speed of this Entity.
     *          | this.maxSpeed
     */
    @Basic @Immutable
    public double getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Returns the distance between the edges of this entity and the specified entity. The distance between a entity and itself is equal to 0.
     *
     * @param entity The entity between which and this the distance needs to be calculated.
     * @return  The distance between the edges of this entity and the entity entity.
     */
    public double getDistanceBetween(Entity entity) {
        return this == entity ? 0 : getPosition().getDistance(entity.getPosition()) - getRadius() - entity.getRadius();
    }

    /**
     * Returns the distance between the center of this entity and the specified entity.
     *
     * @param entity
     * @return
     */
    public double getDistanceBetweenCenters(Entity entity) {
        return getPosition().getDistance(entity.getPosition());
    }

    /**
     * Returns a boolean to check if this entity overlaps with the specified entity.
     *
     * Two entities overlap if and only if they are the same entity or the distance between their centers is negative.
     * | spaceentity == this || this.getDistanceBetween(spaceentity) < 0
     *
     * @param	entity
     * 			The entity that might overlap this entity
     * @return	True if and only if both entities overlap.
     * 			| result == ( getDistanceBetween(spaceentity) <= 0 )
     */
    public boolean overlap(Entity entity){
        double distanceBetweenCentres = getDistanceBetweenCenters(entity);
        double radiusSum = getRadius() + entity.getRadius();

        return radiusSum * 0.99 <= distanceBetweenCentres && distanceBetweenCentres <= radiusSum * 1.01 ;
    }

    /**
     * Returns the amount of time until this entity will collide with the specified entity.
     * If both entities are on a collision course, the time returned by this method will be the
     * finite amount of time it will take for both entities to hit each other if they keep moving
     * in the same direction and with the same velocity. If the entities are not currently on a
     * collision course, if they move away from each other or if they will just pass each other
     * without the hulls touching, the time returned will be positive infinity.
     *
     * @return	The time until the collision will happen if the entities keep moving in the exact
     * 			same way as they are currently moving.  If no collision will occur within a
     * 			finite amount of time, this method will return positive infinity.
     * @throws 	IllegalArgumentException
     * 			If the supplied entity is this entity or if the supplied entity already overlaps
     * 			this entity.
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
     * Returns the position at which this entity will collide with the specified entity, if ever, assuming both
     * entities maintain their velocity and direction from the time this method is called, otherwise null is returned.
     *
     * @return  The position of the point of impact between this entity and the specified entity if they maintain their
     *          velocity and direction from the time this method is called.
     *          If no collision will occur, in case the time to collision is positive infinity, this method returns null.
     *          | if getTimeToCollision(entity) == Double.POSITIVE_INFINITY then
     *          |   null
     *          If the time to collision is a finite value this method returns the point of impact between both entities.
     *          | if getTimeToCollision(entity) != Double.POSITIVE_INFINITY
     *          This point is located at a distance equal to the radius of this entity from the center of this entity and at
     *          a distance equal to the radius of the specified entity from the center of the specified entity after a time
     *          ∆t == getTimeToCollision(entity) has passed since the invocation of this method.
     *          | new == this.move(∆t) && (new entity) == entity.move(∆t)
     *          | new.getPosition().getDistance(result) == new.getRadius() && (new entity).getPosition().getDistance(result)
     * @throws IllegalArgumentException
     *          If this entity overlaps with the specified entity.
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
    
    /**
     * Returns the time until this entity collides with a 'wall' of the world it is in.
     * If this entity is not currently in a finite world, returns positive infinity.
     *
     * @return The time until the first collision with a wall of the world this entity is in.
     */
    public double getTimeToWallCollision() {
        if ( getWorld() == null )
            return Double.POSITIVE_INFINITY;

        double xCollisionTime, yCollisionTime;

        xCollisionTime = getVelocity().getX() > 0 ? (getWorld().getWidth() - getPosition().getX()) / getVelocity().getX()
                : -getPosition().getX() / getVelocity().getX();

        yCollisionTime = getVelocity().getY() > 0 ? (getWorld().getHeight() - getPosition().getY()) / getVelocity().getY()
                : -getPosition().getY() / getVelocity().getY();
        	
        	return Math.min(xCollisionTime, yCollisionTime);
        }


    /**
     * kills off an entity.
     * When an entity dies it is removed from the world it belongs to.
     */
    public void die() {
        this.getWorld().removeEntity(this);
    }

    /**
     * Resolves the collision of this entity with a boundary of the world it belongs to.
     * The velocity of this entity in the x or y direction is negated for a collision with a vertical or horizontal
     * boundary respectively.
     */
    public void resolveCollisionWithBoundary(CollisionListener collisionListener) {
        if (getPosition().getX() == 0 || getPosition().getX() == getWorld().getWidth()){
            collisionListener.boundaryCollision(this, this.getPosition().getX(), this.getPosition().getY());
            setVelocity(new Vector(-getVelocity().getX(), getVelocity().getY()));
        }

        else {
            setVelocity(new Vector(getVelocity().getX(), -getVelocity().getY()));

        }
    }

    /**
     * Resolves the collision of this Entity with the given Ship.
     * @param ship
     */
    public abstract void resolveCollisionWithShip(Ship ship, CollisionListener collisionListener);

    /**
     * Resolves the collision of this Entity with the given Bullet
     * @param bullet
     */
    public abstract void resolveCollisionWithBullet(Bullet bullet, CollisionListener collisionListener);
}
