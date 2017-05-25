package asteroids.model;

import asteroids.model.collisions.EntityCollision;
import be.kuleuven.cs.som.annotate.*;
import asteroids.model.util.vector.Vector;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Invar   A bullet is associated with at most one world or one ship at once
 *          | getWorld() == null || getShip() == null
 * @Invar   A bullet associated with a ship, has that ship as its parent ship.
 *          | if getShip() instanceof Ship then
 *          |   getShip() == getParentShip()
 * @Invar   A bullet will never collide with a wall more then the maximum amount of wall hits
 *          | getWallHits() <= getMaxWallHits()
 * @Invar   A terminated bullet is not associated with a world or a ship
 *          | if isTerminated() then 
 *          |	!(hasShip() || hasWorld())
 *
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class Bullet extends Entity {
	

	/**
	 * Creates a new bullet with all the given parameters that can sustain a maximum of 2 wall hits.
	 * The velocity, position and radius are equal to respectively the given velocity, the given velocity
	 * and the given radius.
	 * 
	 * @param position	The position at which to create the bullet
	 * @param velocity	The current velocity of this bullet
	 * @param radius	The current radius for this bullet
	 * 
	 * @Effect this(position, velocity, getSpeedOfLight() ,radius)
	 */
    public Bullet(Vector position, Vector velocity, double radius) {
        this(position, velocity, getSpeedOfLight() ,radius,(char) 2);
    }

    /**
	 * Creates a new bullet with all the given parameters. The bullet can sustain a maximum amount of wall hits
	 * equal to maxWallHits. The velocity, position and radius are equal to respectively the given velocity, 
	 * the given velocity and the given radius.
	 * 
	 * @param position	The position at which to create the bullet.
	 * @param velocity	The current velocity of this bullet.
	 * @param radius	The current radius for this bullet.
	 * @param maxWallHits	The maximum amount of times a bullet can bounce off a wall.
	 * 
	 * @Effect this(position, velocity, getSpeedOfLight() ,radius)
	 */
    public Bullet(Vector position, Vector velocity, double radius, char maxWallHits) {
        this(position,velocity,getSpeedOfLight(),radius,maxWallHits);
    }
    
    /**
     * Creates a new bullet with all the given parameters.
     * The initial position will be equal to the vector position. The initial velocity will be equal to the vector velocity.
     * The maximum speed will be equal to maxSpeed. The initial radius will be equal to radius. The smallest allowed radius
     * will be equal to getMinRadius. The smallest allowed mass density will be equal to getMassDensity(). The mass will be
     * equal to the smallest allowed mass.
     * If this entity overlaps another entity in the given world, it will not be added to that world.
     *  
     * @param position	The position at which to create the bullet
	 * @param maxSpeed	The highest allowed speed for this bullet
	 * @param velocity	The current velocity of this bullet
	 * @param radius	The current radius for this bullet
	 * @param maxWallHits	The amount of times a bullet can bounce off the edges of a world before it dies.
	 * 
	 * @throws 	IllegalArgumentException
	 * 			When the given position is not valid.
	 * 			! canHaveAsPosition(position)
	 * @throws IllegalArgumentException
	 * 			When the radius is not a valid radius for this bullet.
	 * 			! Entity.canHaveAsRadius(radius, getMinRadius())
	 * 
	 * @Post	If the given position is a valid position for this bullet in the given world, its position will
	 * 			be equal to the given position.
	 * 			| if canHaveAsPosition(position) then
	 * 			| this.getPosition() == position
	 * @Post	If the bullet has a valid position within the given world, and it does not overlap any other entities
	 * 			in that world, this entity will be added to the given world.
	 * 			| if checkValidPositionInWorld() then
	 * 			|	this.getWorld() == world
	 * 			|else this.getWorld() == null
	 * @Post	If the given maximum speed does not exceed the speed of light, the maximum speed of this bullet will be equal to the
	 * 			given maximum speed, otherwise it will be equal to the speed of light.
	 * 			| this.getMaxSpeed == ( maxSpeed <= getSpeedOfLight() ? maxSpeed : getSpeedOfLight() )
	 * @Post	The velocity of this bullet is equal to the given velocity if it is a valid velocity for this bullet. If the given
	 * 			velocity would make this bullet exceed its maximal speed, its velocity will be equal to a vector pointing in the
	 * 			direction of velocity with a magnitude of getMaxSpeed().
	 * 			| if velocity.getMagnitude() <= getMaxSpeed() then
	 * 			|	this.getVelocity() == velocity
	 * 			| else
	 * 			|	( (getVelocity().getMagnitude() == getMaxSpeed()) && (getVelocity.normailze().equals(velocity.normalize()))
	 * @Post	The radius of this bullet is equal to the given radius if it is a valid radius for this bullet.
	 * 			| if canHaveAsRadius(radius, getMinRadius()) then
	 * 			| 	this.getRadius() == radius
	 * @Post	The maximal amount of times this bullet bounces off a wall is equal to the given amount maxWallHits.
	 * 			| this.maxWallHits == 2
	 * @Post	The initial amount of times this bullet has bounced off a wall will be equal to zero.
	 * 			| this.wallHits == 0
	 * @Post	The mass of this bullet is equal to the smallest allowed mass of a bullet with the given radius
	 * 			| this.mass = getMinMass(radius, getMassDensity())
	 * @Post	The smallest allowed mass density of this bullet is equal to getMassDensity().
	 * 			| this.getSmallestMassDensity() = getMassDensity()
	 */
    public Bullet(Vector position, Vector velocity, double maxSpeed, double radius, char maxWallHits) {

        super(
                position,
                maxSpeed,
                velocity,
                getMinRadius(),
                radius,
                getMassDensity(),
                getMinMass(radius, getMassDensity())
        );

        this.maxWallHits = maxWallHits;
        wallHits = 0;

    }

    private static final double initialSpeed = 250;

    /**
     * Returns the initial speed of a bullet.
     * 
     * @return	The initial speed of a bullet.
     * 			| result == Bullet.initialSpeed
     */
    public static double getInitialSpeed() {
        return initialSpeed;
    }

    private static final double minRadius = 1;

    /**
     * Returns the minimal value for the radius of a Bullet.
     *
     * @return 	The minimal radius of a bullet.
     * 			| result == Bullet.minRadius
     */
    public static double getMinRadius() {
        return minRadius;
    }

    private static final double massDensity = 7.8 * Math.pow(10, 12);

    /**
     * Returns the minimal mass density of a Bullet.
     * 
     * @return	The minimal mass density of a bullet.
     * 			| result == Bullet.massDensity()
     */
    public static double getMassDensity() {
        return massDensity;
    }

    private Ship parentShip = null;

    /** Sets the parent ship of this bullet to the given ship. The parent ship of a bullet is the ship
     * that the bullet originally belonged to. The parent ship attribute is used to see whether a bullet should
     * be reloaded to a ship on collision or whether it should destroy that ship.
     * 
     * @param ship	The ship to set as parent ship
     * 
     * @Post	This bullets parent ship is set to the specified ship
     * 			| (new this).getParentShip() == ship
     */
    @Basic
    public void setParentShip(Ship ship) {
        this.parentShip = ship;
    }


    /**
     * Removes the parent ship from this bullet. This method is useful for terminating bullets to tear down the
     * parent ship association when bullets die or are terminated.
     * 
     * @Post	The parent ship of this bullet is null
     * 			| (new this).getParentShip() == null
     */
    @Basic
    void removeParentShip() {
        this.parentShip = null;
    }

    /**
     * Returns the ship to which this bullet belongs or that fired it.
     *
     * @return	...
     * 			| this.parentShip
     */
    @Basic
    public Ship getParentShip() {
    	return parentShip;
    }

    /**
     * Returns true if this bullet has a parent ship.
     * 
     * @return 	...
     * 			| result = ( getParentShip() != null)
     */
    public boolean hasParentShip() {
        return getParentShip() != null;
    }

    private Ship ship = null;

    /**
     * Returns the ship that has this bullet loaded.
     * 
     * @return	...
     * 			| result == this.ship
     */
    @Basic
    public Ship getShip() {
        return ship;
    }
    
    /**
     * Returns true if this bullet is currently loaded onto its parent ship.
     * 
     * @return	True if and only if the bullet is currently loaded onto its parent ship.
     * 			| getShip() != null
     */
    public boolean isLoadedOntoShip() {
    	return getShip() != null;
    }

    /**
     * Removes the association with the ship that this bullet is loaded to.
     * Should only be used in methods to remove bullets from ships to guarantee
     * referential integrity.
     * @Post	...
     * 			| (new this).getShip() == null
     */
    @Raw
    void removeShip() {
        ship = null;
    }

    /**
     * Creates the association with the ship that this bullet is currently loaded to.
     * 
     * @param ship	The ship that this bullet is currently loaded to.
     * 
     * @throws	IllegalArgumentException
     * 			...
     * 			| (ship.getAllBullets().contains(this) || isLoadedOntoShip()) ||
     * 			| 	(this.getParentShip() != ship)
     * @Post	...
     * 			| (new this).getShip() == ship
     */
    @Raw
    void setShip(Ship ship) throws IllegalStateException, IllegalArgumentException{
    	if (isLoadedOntoShip())
    		throw new IllegalStateException("Bullet already loaded onto its parent ship");

    	this.ship = ship;
    	this.parentShip = ship;
    }
    
    private char wallHits;

    /**
     * Returns the amount of times this bullet has hit a wall.
     * 
     * @return	...
     * 			| result == this.wallHits
     */
    public char getWallHits() {
        return wallHits;
    }

    /**
     * Increments the amount of wall hits for this bullet by 1.
     * 
     * @Post	The wallHits counter will be increased by one.
     * 			| (new this).getWallHits() == this.getWallHits() + 1
     */
   public void incrementWallHits() {
        wallHits += 1;
   }

   private char maxWallHits;

   /**
    * Returns the maximum amount of wall hits for this bullet.
    *
    * @return  | result == this.maxWallHits
    */
   public char getMaxWallHits() {
        return maxWallHits;
   }

   /**
    * Terminates this bullet. A terminated bullet no longer belongs to a ship or a world and it doesn't interact
    * with other entities anymore.
    * 
    * @Post	This bullet does not belong to a world.
    * 		| this.getWorld() == null
    * @Post This bullet does not belong to a ship.
    * 		| this.getShip() == null
    * @Post This bullet is in a terminated state.
    * 		| this.isTerminated() == true
    */
   @Override
   public void terminate() {
       try {
           getWorld().removeEntity(this);
       } catch (NullPointerException e) {
           try {
               getShip().removeBullet(this);
           } catch (NullPointerException ex) {}
       }

	   isTerminated = true;
   }

    /**
     * Resolves a collision caused by the firing of this bullet from its parent ship. This method only
     * creates and resolves a collision if a fired bullet overlaps an entity in the current
     * world upon spawning at its initial location.
     * 
     * @Post	If this bullet does not overlap an entity in its world, it is not changed.
     * 			| if ! this.overlapWithEntityInWorld(this.getWorld()) then
     * 			|	new this == this
     * @Post	If this bullet overlaps another entity upon spawning in a world, a collision is created
     *			with this bullet and the entity it overlaps with and this collision is resolved.
     *			| if this.overlapWithEntityInWorld(this.getWorld()) then
     * 			|	(new this).isTerminated();
     */
    void resolveInitialCollisions() {
        Collection<Entity> allEntities = getParentShip().getWorld().getAllEntities();
        allEntities.remove(getParentShip());
        ArrayList<EntityCollision> initialBulletCollisions = new ArrayList<>();

        for (Entity entity : allEntities) {
            if (entity.overlap(this)) {
                initialBulletCollisions.add(new EntityCollision(this, entity, 0, getPosition()));
            }
        }

        for (EntityCollision collision : initialBulletCollisions) {
            collision.resolve();
        }
    }

    /**
     * Resolves a collision between this bullet and the given entity. If the entity is the parentShip of
     * this bullet, the bullet is loaded onto that sip once again. Otherwise, both the entity and this
     * bullet die.
     * 
     * @param entity	The entity to collide with.
     * 
     * @Post	...
     * 			| if getParentShip() == entity) then
     * 			|	(new this).isLoadedOntoShip() && (new this).getShip() = entity && (new this).getWorld() = null
     * 			| else
     * 			|	this.die() && entity.die()
     */
    public void resolveCollisionWithEntity(Entity entity) {
        if (getParentShip() == entity) {
            getWorld().removeEntity(this);
            setPosition(entity.getPosition());
            ((Ship) entity).loadBullet(this);
        }
        else {
            entity.die();
            die();
        }
    }

    /**
     * Returns true if this bullet lies within the given ship.
     * 
     * @param ship
     * 
     * @return	...
     * 			| @see implementation
     */
    public boolean liesWithinShip(Ship ship) {
        return getDistanceBetweenCenters(ship) <= ship.getRadius() - getRadius();
    }

}
