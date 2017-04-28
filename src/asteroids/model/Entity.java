package asteroids.model;

import asteroids.model.util.vector.Vector;
import be.kuleuven.cs.som.annotate.*;

/**
 * @Invar 	An entity is associated with at most one world at once.
 * 			| ( (getWorld() instanceof World) && (getWorld().getAllEntities().contains(this)) || getWorld() == null
 * @Invar 	An entity always has a valid position as its position in its current world.
 * 			| hasValidPositionInWorld(getWorld())
 * @Invar 	An entity always has a valid radius as its radius.
 * 			| canHaveAsRadius(getRadius())
 * @Invar   An entities mass is greater then or equal to its minimal mass.
 *          | getMass() >= getMinMass(getRadius(), getMinMassDensity())
 * @Invar   A terminated entity does not belong to a world
 *          | if isTerminated() then hasWorld() == false
 * @Invar   An entities speed is less then or equal to the speed of light
 *          This is the magnitude of an entities velocity is less then or equal to the speed of light
 *          | getVelocity().getMagnitude() <= getSpeedOfLight()
 */
public abstract class Entity {

    protected Entity(Vector position, double maxSpeed, Vector velocity, double minRadius,double radius, double massDensity) {
        this(position, maxSpeed, velocity, minRadius, radius, massDensity, 0);
    }

    /**
     * Creates a new Entity with all the given parameters.
     * The initial position will be equal to the vector position. The initial velocity will be equal to the vector velocity.
     * The maximum speed will be equal to maxSpeed. The initial radius will be equal to radius and the smallest allowed radius
     * will be equal to minRadius. The initial mass will be equal to mass and the smallest allowed mass density will be
     * equal to minMassDensity. If this entity overlaps another entity in the given world, it will not be added to that world.
     *
     * @param position	The position at which to create the entity
	 * @param maxSpeed	The highest allowed speed for this entity
	 * @param velocity	The current velocity of this Entity
	 * @param minRadius The smallest allowed radius for this Entity
	 * @param radius	The current radius for this entity
	 * @param minMassDensity	The smallest allowed mass density for this Entity
	 * @param mass		The current mass for this Entity
	 * 
	 * @throws 	IllegalArgumentException
	 * 			When the given position is not valid.
	 * 			! canHaveAsPosition(position)
	 * @throws IllegalArgumentException
	 * 			When the radius is not a valid radius for this entity.
	 * 			! canHaveAsRadius(radius, minRadius)
	 * 
	 * @Post	If the given position is a valid position for this entity, its position will
	 * 			be equal to the given position.
	 * 			| if canHaveAsPosition(position) then
	 * 			| this.getPosition() == position
	 * @Post	If the given maximum speed does not exceed the speed of light, the maximum speed of this entity will be equal to the
	 * 			given maximum speed, otherwise it will be equal to the speed of light.
	 * 			| this.getMaxSpeed == ( maxSpeed <= getSpeedOfLight() ? maxSpeed : getSpeedOfLight() )
	 * @Post	The velocity of this entity is equal to the given velocity if it is a valid velocity for this entity. If the given
	 * 			velocity would make this entity exceed its maximal speed, its velocity will be equal to a vector pointing in the
	 * 			direction of velocity with a magnitude of getMaxSpeed().
	 * 			| if velocity.getMagnitude() <= getMaxSpeed() then
	 * 			|	this.getVelocity() == velocity
	 * 			| else
	 * 			|	( (getVelocity().getMagnitude() == getMaxSpeed()) && (getVelocity.normailze().equals(velocity.normalize()))
	 * @Post	The radius of this entity is equal to the given radius if it is a valid radius for this entity.
	 * 			| if canHaveAsRadius(radius, minRadius) then
	 * 			| 	this.getRadius() == radius
	 * @Post	The mass of this entity is equal to the given mass if it is a valid mass for this entity.
	 * 			| if mass < getMinMass(getRadius(), minMassDensity) then
	 * 			|	this.mass = getMinMass(getRadius(), minMassDensity)
	 * 			| else
	 * 			|	this.mass = mass
	 * 
	 * 
	 * 
     */
    protected Entity(Vector position, double maxSpeed, Vector velocity, double minRadius, double radius, double minMassDensity, double mass)
            throws IllegalArgumentException {

        if (! canHaveAsRadius(radius, minRadius))
            throw new IllegalArgumentException();

        setPosition(position);
        this.maxSpeed = maxSpeed <= getSpeedOfLight() ? maxSpeed : getSpeedOfLight();
        setVelocity(velocity);
        this.radius = radius;
        setMass(mass, minMassDensity);
    }

    protected boolean isTerminated = false;

    /**
     * Returns true if and only if this entity is in a terminated state.
     *
     *	@return	True if this entity is terminated.
     *			| result == this.isTerminated
     */
    public boolean isTerminated() {
        return isTerminated;
    }

    /**
     * Terminates this entity. When an entity is terminated it no longer interacts with any other entity
     * and no longer belongs to a world.
     */
    public abstract void terminate();

    /**
     * Checks whether the supplied radius is a valid radius for an entity.
     *
     * @param 	radius The radius that needs to be validated.
     * @return 	True if and only if radius is a valid radius for an entity.
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
     *          | result == this.speedOfLight
     */
    @Immutable
    public static double getSpeedOfLight() {
        return speedOfLight;
    }

    private double radius;        // defensively

    /**
     * Returns the radius of this entity.
     *
     * @return 	The radius of this entity
     * 			| result == this.radius
     */
    @Basic
    public double getRadius() {
        return radius;
    }

    protected void setRadius(double newRadius) {
        radius = newRadius;
    }

    private double mass;    // total

    /**
     * Returns the mass of this entity.
     * 
     * @return 	The mass of this entity
     * 			| result == this.mass
     */
    @Basic
    public double getMass() {
        return mass;
    }


    /**
     * Sets the mass of this entity to the given newMass, unless this would result in this entity having a mass that is
     * lower then the minimal allowed mass, then it is set to this minimal value.
     *
     * @param newMass	The new mass for this entity.
     * @param minMassDensity	The smallest allowed mass density for this entity.
     * 
     * @Post	The new mass for this entity is equal to newMass if newMass is greater then the smallest allowed mass.
     * 			| @see implementation
     */
    @Basic
    private void setMass(double newMass, double minMassDensity) {
        double minMass = getMinMass(getRadius(), minMassDensity);
        this.mass = newMass >= minMass ? newMass : minMass;
    }

    /**
     * Returns the minimal mass of a spherical object given a radius and a minimal mass density.
     * 
     * @param radius	The radius of the entity.
     * @param minMassDensity	The smallest allowed mass density for the entity.
     * 
     * @return	The smallest allowed mass for the entity.
     * 			| @see implementation
     */
    @Basic
    public static double getMinMass(double radius, double minMassDensity) {
        return 4.0/3.0 * Math.PI * Math.pow(radius, 3) * minMassDensity;
    }
    
    /**
     * Returns true if this entity currently is in a world.
     * 
     * @return 	True if and only if the entity is currently associated with a world.
     * 			| result == ( getWorld() != null )
     */
    @Basic
    public boolean hasWorld() {
    	return getWorld() != null;
    }

    private World world;

    /**
     * Returns the world this Entity belongs to, if this Entity belongs to no world this method will return null.
     *
     * @return 	The world that this entity currently is in.
     * 			| result == this.world
     */
    @Basic
    public World getWorld() {
        return world;
    }

    /**
     * Associates this entity with the given World. If the given world is null, the entity is
     * no longer associated with a world.
     * This method should only be used inside the method addEntity or removeEntity of the World Class to guarantee
     * the integrity of the associations.
     *
     * @param world	The world to add this entity to.
     */
    @Basic @Raw
    public void setWorld(World world) throws IllegalStateException {
    	if (isTerminated())
    	    throw new IllegalStateException("This entity is Terminated");

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
     * @throws  IllegalStateException
     *          If this entity is terminated
     *          | isTerminated()
     */
    @Basic
    public void setPosition(Vector newPosition) throws NullPointerException, IllegalArgumentException, IllegalStateException {
        if (isTerminated())
            throw new IllegalStateException("This entity is terminated");
        if (!isValidPosition(newPosition))
            throw new IllegalArgumentException("The new position is invalid");

        position = newPosition;
    }

    public void setPosition(double xpos, double ypos) {
        setPosition(new Vector(xpos, ypos));
    }

    /**
     * Checks whether or not a given position is a valid position for an entity.
     * valid position are non-null vector objects that do not have NaN as their x or y component.
     *
     * @param   position
     *          The position to be tested.
     * @return  True if and only if the vector is not a null object and the components of the vector are real numbers
     *          | @see implementation
     */
    public static boolean isValidPosition(Vector position) {
        return !(position == null || Double.isNaN(position.getX()) ||  Double.isNaN(position.getY()));
    }

    /**
     * Checks whether or not the specified position is within the boundaries of the specified world.
     * An entity lies within a world if it lies fully within the boundries of the world.
     *
     * @param world
     *          The world in which the position has to be validated.
     * @param position
     *          A non-null vector object which has to be validated as a position for this entity in the specified world.
     * @return  True if and only if the entity lies fully within the boundaries of the specified world.
     *          | @see implementation
     * @throws IllegalArgumentException
     *          If the specified world or position are a null object
     *          | world == null || position == null
     */
    public boolean isWithinBoundariesOfWorld(World world, Vector position) throws IllegalArgumentException {
        if (position == null)
            throw new IllegalArgumentException();

        return world == null ||
                ((position.getX() >= getRadius()) &&
                (position.getX() <= world.getWidth() - getRadius()) &&
                (position.getY() >= getRadius()) &&
                (position.getY() <= world.getHeight() - getRadius()));
    }

    /**
     * Checks whether or not this entities current position is within the boundaries of the specified world.
     *
     * @param world
     *          The world in which the position has to be validated.
     * @return  True if and only if this entities current position lays fully within the boundaries of the specified world.
     *          | isWithinBoundariesOfWorld(world, getPosition)
     */
    public boolean isWithinBoundariesOfWorld(World world) {
        return isWithinBoundariesOfWorld(world, getPosition());
    }

    /**
     * Checks whether or not this entity can have the specified position in the specified world
     * @param position
     *          The position that has to be validated.
     * @param world
     *          The world in which the position has to be validated.
     * @return  True if and only if the specified world is a null object
     *          | world == null
     * @return  True if and only if the specified position results in the entity laying fully within the boundaries of
     *          the world and this entity not overlapping with any entities already present in the world.
     *          | isWithinBoundariesOfWorld(world, position) && !overlapWithEntityInWorld(world)
     */
    public boolean canHaveAsPositionInWorld(Vector position, World world) {
        return world == null || (isWithinBoundariesOfWorld(world, position) && !overlapWithEntityInWorld(world, position));

    }

    /**
     * Checks whether or not this entity has a valid position in the specified world
     *
     * @param world
     *          The world in which the position has to be validated.
     * @return  True if and only if this entity can have its current position in the specified world.
     *          | canHaveAsPositionInWorld(getPosition(), world)
     *
     */
    public boolean hasValidPositionInWorld(World world) {
        return canHaveAsPositionInWorld(getPosition(), world);
    }

    /**
     * Checks whether or not this entity overlaps with another entity in the specified world.
     * @param world
     *          The world from which the entities have to be checked for overlap with this entity.
     * @return  True if and only if this entity overlaps with an entity in the world.
     *          | overlap(entity) for any entity in world.getAllEntities()
     */
    public boolean overlapWithEntityInWorld(World world) {
        return overlapWithEntityInWorld(world, getPosition());
    }

    public boolean overlapWithEntityInWorld(World world, Vector virtualPosition) {
        for (Entity otherEntity : world.getAllEntities()) {
            if (overlap(otherEntity, virtualPosition))
                return true;
        }

        return false;
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
            throw new IllegalArgumentException(Double.toString(time));

        try {
            getWorld().setEntityPosition(this, getPosition().add(getVelocity().multiply(time)));
        } catch (NullPointerException e) {
            setPosition(getPosition().add(getVelocity().multiply(time)));
        }
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
     * @Post    If this entity is terminated nothing happens.
     *          | if isTerminated() then
     *          |   this == new
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
    public void setVelocity(Vector newVelocity) {
        if (!isTerminated()) {
            if (newVelocity == null || Double.isNaN(newVelocity.getX()) || Double.isNaN(newVelocity.getY())) {
                newVelocity = new Vector(0, 0);
            } else if (newVelocity.dotProduct(newVelocity) > Math.pow(getMaxSpeed(), 2)) {
                newVelocity = newVelocity.normalize().multiply(getMaxSpeed());
            }

            velocity = newVelocity;
        }
    }

    public void setVelocity(double xVel, double yVel) {
        setVelocity(new Vector(xVel, yVel));
    }

    private final double maxSpeed;

    /**
     * Returns the maximum speed of this Entity.
     *
     * @return  The maximum speed of this Entity.
     *          | result == this.maxSpeed
     */
    @Basic @Immutable
    public double getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Returns the distance between the edges of this entity and the specified entity. The distance between a entity and itself is equal to 0.
     *
     * @param entity The entity between which and this the distance needs to be calculated.
     * 
     * @return  The distance between the edges of this entity and the entity entity.
     * 			| @see implementation
     */
    public double getDistanceBetween(Entity entity) {
        return this == entity ? 0 : getPosition().getDistance(entity.getPosition()) - getRadius() - entity.getRadius();
    }

    /**
     * Returns the distance between the center of this entity and the specified entity.
     *
     * @param entity	The entity to measure the distance to from this entity.
     * 
     * @return	The distance between this entity and the given entity.
     * 			| @see implementation
     */
    public double getDistanceBetweenCenters(Entity entity) {
        return getPosition().getDistance(entity.getPosition());
    }

    /**
     * Returns a boolean to check if this entity overlaps with the specified other entity.
     *
     * Two entities overlap if and only if they are the same other or the distance between their centers is negative.
     * | spaceentity == this || this.getDistanceBetween(other) < 0
     *
     * @param	other
     * 			The other entity that might overlap this entity
     * @return	True if and only if both entities overlap.
     * 			| result == ( getDistanceBetween(other) <= 0 )
     */
    public boolean overlap(Entity other) {
        return overlap(other, getPosition(), other.getPosition());
    }

    public boolean overlap (Entity other, Vector virtualPosThis) {
        return overlap(other,virtualPosThis, other.getPosition());
    }

    public boolean overlap (Entity other, Vector virtualPosThis, Vector virtualPosOther) {
        return virtualPosThis.getDistance(virtualPosOther) <= (getRadius() + other.getRadius()) * 0.99;
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
    	if ( entity.equals(this) )
    		throw new IllegalArgumentException("Can't get time to collision with same entity");
        if (overlap(entity)) {
            throw new IllegalArgumentException("Overlapping Entities");
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
     *          collisionTime == getTimeToCollision(entity) has passed since the invocation of this method.
     *          | new == this.move(collisionTime) && (new entity) == entity.move(collisionTime)
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
     * Returns the time until this entity collides with an edge of the world it is in.
     * If this entity is not currently in a finite world, returns positive infinity.
     *
     * @return	If the entity is not in a world, returns positive infinity.
     * 			| if getWorld() == null then
     * 			|	result == Double.PPOSITIVE_INFINITY
     * @return The time until the first collision with a wall of the world this entity is in.
     * 			| @see implementation
     */
    public double getTimeToWallCollision() {
        if ( getWorld() == null)
            return Double.POSITIVE_INFINITY;

        double xCollisionTime, yCollisionTime;

        if ( getVelocity().getX() == 0 )
        	xCollisionTime = Double.POSITIVE_INFINITY;
        else if ( getVelocity().getX() > 0 )
        	xCollisionTime = (getWorld().getWidth() - getPosition().getX() - getRadius()) / getVelocity().getX();
        else
        	xCollisionTime = -(getPosition().getX() - getRadius()) / getVelocity().getX();

        if ( getVelocity().getY()  == 0 )
        	yCollisionTime = Double.POSITIVE_INFINITY;
        else if ( getVelocity().getY()  > 0 )
        	yCollisionTime = (getWorld().getHeight() - getPosition().getY() - getRadius()) / getVelocity().getY();
        else
        	yCollisionTime = -(getPosition().getY() - getRadius()) / getVelocity().getY();

        return Math.min(xCollisionTime, yCollisionTime);
    }

    
    /**
     * Returns the position at which this entity will collide with an edge of the world it is in if it keeps
     * moving with the same velocity vector. If the entity is not in a world, this method returns a vector 
     * with positive infinity as its x and y value.
     * 
     * @return	The position vector where this entity will collide with the edge of the world it is in.
     * 			| @see implementation
     */
    public Vector getWallCollisionPosition() {

        double timeToWallCollision = getTimeToWallCollision();

        if (timeToWallCollision == Double.POSITIVE_INFINITY)
            return new Vector(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

        return getPosition().add(getVelocity().multiply(timeToWallCollision));
    }


    /**
     * kills off an entity. When an entity dies it is removed from the world it belongs to and
     * it is terminated.
     * 
     * @Post	If the entity dies, it no longer belongs to a world.
     * 			| (new this).getWorld() == null
     * @Post	If the entity dies, it is in a terminated state.
     * 			| (new this).isTerminated()
     */
    public void die() {
        try {
            this.getWorld().removeEntity(this);
        } catch (NullPointerException e){}
        this.terminate();
    }
}
