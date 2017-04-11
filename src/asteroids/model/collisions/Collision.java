package asteroids.model.collisions;

import asteroids.model.entities.*;
import asteroids.part2.CollisionListener;
import be.kuleuven.cs.som.annotate.*;
import vector.Vector;

/**
 * A class of collisions
 * Created by Bo Kleynen and Yrjo Koyen.
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
     * @return | @see implementation
     */
    @Basic @Immutable
    public Entity getEntity1() {
        return entity1;
    }

    private final double timeToCollision;

    /**
     * Returns the time before this Collision will happen.
     *
     * @return | @see implementation
     */
    @Basic @Immutable
    public double getTimeToCollision() {
        return timeToCollision;
    }

    private Vector collisionPosition;

    /**
     * Returns the position where this collision will happen.
     * @return | @see implementation
     */
    @Basic
    public Vector getCollisionPosition() {
        return collisionPosition;
    }

    /**
     * Sets the position where this collision will happen to the specified position.
     * 
     * @param collisionPosition
     * 
     * @Post | @see implementation
     */
    @Basic
    public void setCollisionPosition(Vector collisionPosition) {
        if (getCollisionPosition() == null) {
            this.collisionPosition = collisionPosition;
        }
    }

    public abstract Vector calculateCollisionPosition();

    public abstract void resolve();

    public abstract void collisionListener(CollisionListener collisionListener);
}
