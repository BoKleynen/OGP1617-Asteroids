package asteroids.model.entities;


import be.kuleuven.cs.som.annotate.*;
import vector.Vector;
import asteroids.model.world.*;

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

    	this.ship = ship;
    }

    @Raw
    public void removeWorld() {
    	if (getWorld() != null)
    		super.setWorld(null);
    }
    
    @Override @Basic
    public void setWorld(World world) {
    	super.setWorld(world);
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
    * Resolves the collision of this bullet with a Ship that belongs to the same world as this bullet.
    * If the ship is this bullets parent ship, the bullet is reloaded onto the ship.
    * Else both this bullet and the ship are destroyed.
    *
    * @param ship
    */
   @Override
   public void resolveCollisionWithShip(Ship ship) {
        ship.resolveCollisionWithBullet(this);
   }
   /**
    * Resolves the collision of this bullet with another bullet that belongs to the same world as this bullet.
    * Both this bullet and the bullet are destroyed.
    * @param bullet
    */
   @Override
   public void resolveCollisionWithBullet(Bullet bullet) {
        die();
        bullet.die();
   }
}
