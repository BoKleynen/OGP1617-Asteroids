package asteroids.model.collisions;

import asteroids.model.entities.Bullet;
import asteroids.model.entities.Entity;
import asteroids.model.entities.Ship;
import asteroids.part2.CollisionListener;
import vector.Vector;

/**
 * Created by Bo on 29/03/2017.
 */
public class BoundaryCollision extends Collision {

	/**
	 * Creates a new collision with the edges of a world.
	 * 
	 * @Effect	this(null, Double.POSITIVE_INFINITY, new Vector(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY))
	 */
    public BoundaryCollision() {
        this(null, Double.POSITIVE_INFINITY, new Vector(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
    }

    /**
	 * Creates a new collision where the specified entity collides with the edges of a world after the
	 * given amount of time.
	 * 
	 * @Effect	this(entity, timeToCollision, new Vector(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY))
	 */
    public BoundaryCollision(Entity entity, double timeToCollision) {
        this(entity,  timeToCollision, null);
    }

    /**
	 * Creates a new collision where the specified entity collides with the edges of a world after the
	 * given amount of time and at the given collisionPosition.
	 * 
	 * @Post	The entity involved in this collision is the given entity.
	 * 			| this.getEntity1() == entity
	 * @Post	The time until this collision will happen is equal to the given timeToCollision.
	 * 			| this.getTimeToCollision() == timeToCollision
	 * @Post	The position where this collision will happen is equal to collisionPosition.
	 * 			| this.getCollisionPosition() == collisionPosition
	 */
    public BoundaryCollision(Entity entity, double timeToCollision, Vector collisionPosition) {
        super(entity, timeToCollision, collisionPosition);
    }
    
    /**
     * Calculates the position where this collision will happen based on the time until the collision and the
     * entity's speed.
     * 
     * @return	| @see implementation.
     */
    @Override
    public Vector calculateCollisionPosition() {
        return getEntity1().getPosition().add(getEntity1().getVelocity().multiply(getTimeToCollision()));
    }

    /**
     * Resolves this collision by changing the velocity of the involved entity.
     * Or if the entity is an instance of the class Bullet and has achieved its maximum number of wall collisions, it
     * will be destroyed
     *
     * @Post    | @see implementation
     */
    @Override
    public void resolve() {
        if (getEntity1() instanceof Bullet) {
        	Bullet bullet = (Bullet) getEntity1();

            if ( bullet.getWallHits() >= bullet.getMaxWallHits())
                bullet.die();
            else
                resolveCollisionWithBoundary();
                bullet.incrementWallHits();
        }

        else if (getEntity1() instanceof Ship) {
            resolveCollisionWithBoundary();
        }
    }

    /**
     * Resolves this collision with the edge of a world. The involved entity will bounce of the edge of the world.
     * 
     * @Post	The velocity of the involved entity will be changed so that the entity will be moving away from the edge
     * 			it collided with.
     * 			| @see implementation
     */
    private void resolveCollisionWithBoundary() {
        if (getCollisionPosition().getX() <= getEntity1().getRadius() 
        		|| getCollisionPosition().getX() >= getEntity1().getWorld().getWidth() - getEntity1().getRadius()) {
            getEntity1().setVelocity(new Vector(-getEntity1().getVelocity().getX(), getEntity1().getVelocity().getY()));
        }

        else if ( (getCollisionPosition().getY() <= getEntity1().getRadius())
        		|| (getCollisionPosition().getY() >= getEntity1().getWorld().getHeight() - getEntity1().getRadius()) ) {
            getEntity1().setVelocity(new Vector(getEntity1().getVelocity().getX(), -getEntity1().getVelocity().getY()));

        }
    }

    @Override
    public void collisionListener(CollisionListener collisionListener) {
        collisionListener.boundaryCollision(getEntity1(), getCollisionPosition().getX(), getCollisionPosition().getY());
    }

    @Override
    public String toString() {
        return "Entity collision (" + getEntity1() + ", Position: " + getCollisionPosition() + ", time: " + getTimeToCollision();
    }
}
