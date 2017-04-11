package asteroids.model.entities;


import asteroids.model.collisions.Collision;
import asteroids.model.collisions.EntityCollision;
import be.kuleuven.cs.som.annotate.*;
import vector.Vector;
import asteroids.model.world.*;

import java.util.Collection;

/**
 * @Invar A bullet is associated with at most one world or one ship at once
 *
 */
public class Bullet extends Entity {
	

    public Bullet(Vector position, Vector velocity, double radius) {
        this(null, position, velocity, getSpeedOfLight() ,radius);
    }

    public Bullet(World world, Vector position, Vector velocity, double maxSpeed,double radius) {

        super(world, position, maxSpeed, velocity, getMinRadius(), radius, 0, getMassDensity());

        maxWallHits = 2;
        wallHits = 0;

    }

    private static final double initialSpeed = 250;

    public static double getInitialSpeed() {
        return initialSpeed;
    }

    private static final double minRadius = 1;

    /**
     * Returns the minimal value for the radius of a Bullet.
     *
     * @return
     */
    public static double getMinRadius() {
        return minRadius;
    }

    private static final double massDensity = 7.8 * Math.pow(10, 12);

    /**
     * Returns the minimal mass density of a Bullet.
     * @return
     */
    public static double getMassDensity() {
        return massDensity;
    }

    private Ship parentShip = null;

    @Basic
    void setParentShip(Ship ship) {
        this.parentShip = ship;
    }

    /**
     * Returns the ship to which this bullet belongs or that fired it.
     *
     * @return
     */
    @Basic
    public Ship getParentShip() {
    	return parentShip;
    }

    private Ship ship = null;

    @Basic
    public Ship getShip() {
        return ship;
    }
    
    public boolean hasShip() {
    	return (getShip() != null);
    }

    @Raw
    void removeShip() {
    	if (hasShip())
    		ship = null;
    }

    @Raw
    void setShip(Ship ship) {
    	if (ship.getAllBullets().contains(this) || hasShip())
    		throw new IllegalArgumentException();
    	if (this.getParentShip() != ship)
    	    throw new IllegalArgumentException();

    	this.ship = ship;
    }

    @Raw
    public void removeWorld() {
    	if (getWorld() != null)
    		super.setWorld(null);
    }
    
    private char wallHits;

    /**
     * Returns the amount of times this bullet has hit a wall.
     * @return
     */
    public char getWallHits() {
        return wallHits;
    }

    /**
     * Increments the amount of wall hits for this bullet by 1.
     */
   public void incrementWallHits() {
        wallHits += 1;
   }

   private char maxWallHits;

   /**
    * Returns the maximum amount of wall hits for this bullet.
    *
    * @return  this.maxWallHits
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
