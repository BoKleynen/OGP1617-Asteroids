package asteroids.model.entities;


import asteroids.model.collisions.Collision;
import asteroids.model.collisions.EntityCollision;
import be.kuleuven.cs.som.annotate.*;
import vector.Vector;
import asteroids.model.world.*;

import java.util.Collection;

/**
 * @Invar   A bullet is associated with at most one world or one ship at once
 *          | getWorld() == null || getShip() == null
 * @Invar   A bullet associated with a ship, is associated with its parent ship
 *          | if getShip() instanceof Ship
 *          |   then getShip() == getParentShip()
 * @Invar   A bullet will never collide with a wall more then the maximum amount of wall hits
 *          | getWallHits() <= getMaxWallHits()
 *
 */
public class Bullet extends Entity {
	

	/**
	 * Creates a new bullet with all the given parameters and adds it to the given world if this is possible.
	 * 
	 * @param position	The position at which to create the bullet
	 * @param velocity	The current velocity of this bullet
	 * @param radius	The current radius for this bullet
	 * 
	 * @Effect this((null, position, velocity, getSpeedOfLight() ,radius)
	 */
    public Bullet(Vector position, Vector velocity, double radius) {
        this(null, position, velocity, getSpeedOfLight() ,radius);
    }

    /**
     * Creates a new bullet with all the given parameters and adds it to the given world if this is possible.
     * The initial position will be equal to the vector position. The initial velocity will be equal to the vector velocity.
     * The maximum speed will be equal to maxSpeed. The initial radius will be equal to radius. 
     * If this entity overlaps another entity in the given world, it will not be added to that world.
     * 
     * @param world		The world to add this bullet to.
     * @param position	The position at which to create the bullet
	 * @param maxSpeed	The highest allowed speed for this bullet
	 * @param velocity	The current velocity of this bullet
	 * @param radius	The current radius for this bullet
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
	 * @Post	The maximal amount of times this bullet bounces off a wall is equal to two.
	 * 			| this.maxWallHits == 2
	 * @Post	The initial amount of times this bullet has bounced off a wall will be equal to zero.
	 * 			| this.wallHits == 0
	 */
    public Bullet(World world, Vector position, Vector velocity, double maxSpeed,double radius) {

        super(world, position, maxSpeed, velocity, getMinRadius(), radius, getMassDensity(), getMassDensity()*4/3*Math.PI*Math.pow(radius, 3));

        maxWallHits = 2;
        wallHits = 0;

    }

    private static final double initialSpeed = 250;

    /**
     * Returns the initial speed of a bullet.
     * 
     * @return	...
     * 			| result == this.initialSpeed
     */
    public static double getInitialSpeed() {
        return initialSpeed;
    }

    private static final double minRadius = 1;

    /**
     * Returns the minimal value for the radius of a Bullet.
     *
     * @return 	...
     * 			| result == minRadius
     */
    public static double getMinRadius() {
        return minRadius;
    }

    private static final double massDensity = 7.8 * Math.pow(10, 12);

    /**
     * Returns the minimal mass density of a Bullet.
     * 
     * @return	...
     * 			| result == this.massDensity()
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
     * @Post	...
     * 			| (new this).getParentShip() == ship
     */
    @Basic
    void setParentShip(Ship ship) {
        this.parentShip = ship;
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
     * Returns true if this bullet is currently loaded to a ship.
     * 
     * @return	...
     * 			| result == (getShip() != null)
     */
    public boolean hasShip() {
    	return (getShip() != null);
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
    	if (hasShip())
    		ship = null;
    }

    /**
     * Creates the association with the ship that this bullet is currently loaded to.
     * 
     * @param ship	The ship that this bullet is currently loaded to.
     * 
     * @throws	IllegalArgumentException
     * 			...
     * 			| (ship.getAllBullets().contains(this) || hasShip()) ||
     * 			| 	(this.getParentShip() != ship)
     * @Post	...
     * 			| (new this).getShip() == ship
     */
    @Raw
    void setShip(Ship ship) {
    	if (ship.getAllBullets().contains(this) || hasShip())
    		throw new IllegalArgumentException();
    	if (this.getParentShip() != ship)
    	    throw new IllegalArgumentException();

    	this.ship = ship;
    }

    /**
     * Removes the association with the world this bullet is currently in.
     * Should only be used in methods to remove bullets from worlds or ships to guarantee
     * referential integrity.
     */
    @Raw
    public void removeWorld() {
    	if (getWorld() != null)
    		super.setWorld(null);
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
     * @Post	...
     * 			| (new this).getWallHits() == this.getWallHits() + 1
     */
   public void incrementWallHits() {
        wallHits += 1;
   }

   private char maxWallHits;

   /**
    * Returns the maximum amount of wall hits for this bullet.
    *
    * @return  result == this.maxWallHits
    */
   public char getMaxWallHits() {
        return maxWallHits;
   }

   /**
    * Terminates this bullet. A terminated bullet no longer belongs to a ship or a world.
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
	   if ( hasShip() )
		   getShip().removeBullet(this);
	   if ( hasWorld() )
		   getWorld().removeEntity(this);
	   isTerminated = true;
   }

    /**
     * Resolves a collision caused by the firing of this bullet from its parent ship. This method only
     * creates and resolves a collision if a fired bullet overlaps an entity in the current
     * world upon spawning at its initial location.
     */
    void resolveInitialCollisions() {
        Collection<Entity> allEntities = getWorld().getAllEntities();
        allEntities.remove(this);
        allEntities.remove(getParentShip());
        Collision initialBulletCollision = null;

        for (Entity entity : allEntities) {
            if ( entity.overlap(this) ) {
                initialBulletCollision = new EntityCollision(this, entity, 0);
                break;
            }
        }

        if ( initialBulletCollision != null ) {
            initialBulletCollision.resolve();
        }
    }
}
