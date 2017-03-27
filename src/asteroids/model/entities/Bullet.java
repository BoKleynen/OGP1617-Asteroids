package asteroids.model.entities;


import asteroids.part2.CollisionListener;
import vector.Vector;
import asteroids.model.world.*;
import be.kuleuven.cs.som.annotate.Basic;

public class Bullet extends Entity {
	

    public Bullet(Vector position, Vector velocity, double radius) {
        this(null, position, velocity, getSpeedOfLight() ,radius);
    }

    public Bullet(World world, Vector position, Vector velocity, double maxSpeed,double radius) {
        super(world, position, maxSpeed, velocity, radius, getMinRadius(), 0, getMassDensity());

        maxWallHits = 2;
        wallHits = 0;

    }

    @Override
    public void move(double time) {
        if( time < 0 )
            throw new IllegalArgumentException();

        setPosition(getPosition().add(getVelocity().multiply(time)));
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


    /**
     * Returns the ship to which this bullet belongs or that fired it.
     *
     * @return
     */
    @Basic
    public Ship getParentShip() {
        return parentShip;
    }
    
    @Basic
    public World getParentWorld() {
    	return parentWorld;
    }
    
    public void setParentShip(Ship ship) {
    	if ( (ship == null) || (ship.getAllBullets().contains(this)) )
    		throw new IllegalArgumentException();
    	if ( hasParentShip() || hasParentWorld() )
    		throw new IllegalArgumentException();
    	
    	parentShip = ship;
    }
    
    public boolean hasParentShip() {
    	return !(parentShip == null);
    }
    
    public void removeParentShip() {
    	if (parentShip instanceof Ship)
    		parentShip = null;
    }
    
    public void setParentWorld(World world) {
    	if ( (world == null) || (world.getAllBullets().contains(this)))
    		throw new IllegalArgumentException();
    	if ( hasParentShip() || hasParentWorld() )
    		throw new IllegalArgumentException();
    	
    	parentWorld = world;
    }
    
    public boolean hasParentWorld() {
    	return !(parentWorld == null);
    }
    
    public void removeParentWorld() {
    	if (parentWorld instanceof World)
    		parentWorld = null;
    }
    
    private Ship parentShip = null; 
    private World parentWorld = null;

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
    private void incrementWallHits() {
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
     * Resolves the collision of this bullet with a boundary of the world this bullet belongs to.
     * If this bullet has reached its maximum amount of wall hits, it will die and be removed from this bullets world.
     * Otherwise the velocity in the x or y direction is negated for a collision with a vertical or horizontal boundary
     * respectively. The amount of sustained wall hits for this bullet is incremented.
     */
    @Override
    public void resolveCollisionWithBoundary(CollisionListener collisionListener) {
        if (getWallHits() == getMaxWallHits()) {
            collisionListener.boundaryCollision(this, getPosition().getX(), getPosition().getY());
            die();
        }

        else {
            super.resolveCollisionWithBoundary(collisionListener);
            incrementWallHits();
        }
    }

    /**
     * Resolves the collision of this bullet with a Ship that belongs to the same world as this bullet.
     * If the ship is this bullets parent ship, the bullet is reloaded onto the ship.
     * Else both this bullet and the ship are destroyed.
     *
     * @param ship
     */
    @Override
    public void resolveCollisionWithShip(Ship ship, CollisionListener collisionListener) {
        ship.resolveCollisionWithBullet(this, collisionListener);
    }

    /**
     * Resolves the collision of this bullet with another bullet that belongs to the same world as this bullet.
     * Both this bullet and the bullet are destroyed.
     * @param bullet
     */
    @Override
    public void resolveCollisionWithBullet(Bullet bullet, CollisionListener collisionListener) {
        collisionListener.objectCollision(this, bullet, getPosition().getX(), getPosition().getY());
        die();
        bullet.die();
    }

}
