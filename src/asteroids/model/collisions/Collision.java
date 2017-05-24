package asteroids.model.collisions;

import asteroids.model.Entity;
import asteroids.part2.CollisionListener;
import be.kuleuven.cs.som.annotate.*;
import asteroids.model.util.vector.Vector;

/**
 * A class of collisions
 *
 *
 * @author  Bo Kleynen & Yrjo Koyen
 */
public abstract class Collision {

    public Collision(Entity entity1, double timeToCollision, Vector collisionPosition) {
        this.entity1 = entity1;
        this.timeToCollision = timeToCollision;
        this.collisionPosition = collisionPosition;
    }

    private final Entity entity1;

    /**
     * Returns the first entity involved in this Collision.
     *
     * @return	The first entity of this collision
     * 			| result == this.entity1
     */
    @Basic @Immutable
    public Entity getEntity1() {
        return entity1;
    }

    private final double timeToCollision;

    /**
     * Returns the time before this Collision will happen.
     *
     * @return 	The time until this collision.
     * 			| result == this.timeToCollision()
     */
    @Basic @Immutable
    public double getTimeToCollision() {
        return timeToCollision;
    }

    private Vector collisionPosition;

    /**
     * Returns the position where this collision will happen.
     * @return	The position of this collision.
     * 			| result == this.collisionPosition		
     */
    @Basic
    public Vector getCollisionPosition() {
        return collisionPosition;
    }

    /**
     * Sets the position where this collision will happen to the specified position if the current
     * position is still equal to null.
     * 
     * @param collisionPosition	The position where this collision will happen.
     * 
     * @Post	If the current collision position is equal to null, the new collision position
     * 			will be equal to the given collisionPosition
     * 			| if this.getCollisionPosition() == null then
     * 			|	(new this).getCollisionPosition() == collisionPosition
     * 			| else
     * 			|	(new this).getCollisionPosition() == this.getCollisionPosition()
     */
    @Basic
    public void setCollisionPosition(Vector collisionPosition) {
        if (getCollisionPosition() == null) {
            this.collisionPosition = collisionPosition;
        }
    }

    /**
     * Calculates and returns the position at which this collision will occur.
     */
    public abstract Vector calculateCollisionPosition();

    /**
     * Resolves this collision.
     */
    public abstract void resolve(CollisionListener collisionListener);

    public void resolve() {
        this.resolve(null);
    }

    /**
     * Invokes the collisionlistener against this collision.
     */
    public abstract void collisionListener(CollisionListener collisionListener);
}
