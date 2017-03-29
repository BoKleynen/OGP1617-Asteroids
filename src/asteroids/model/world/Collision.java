package asteroids.model.world;

import asteroids.model.entities.*;
import asteroids.part2.CollisionListener;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of collisions
 * Created by Bo on 21/03/2017.
 */
public class Collision {

    public Collision() {
        this(null, null, Double.POSITIVE_INFINITY);
    }

    public Collision(Entity entity, double timeToCollision) {
        this(entity, null, timeToCollision);
    }

    public Collision(Entity entity1, Entity entity2, double timeToCollision) {
        this.entity1 = entity1;
        this.entity2 = entity2;
        this.timeToCollision = timeToCollision;
    }

    private final Entity entity1;
    private final Entity entity2;       // entity2 == null, entity 1 hits a wall
    private final double timeToCollision;

    /**
     * Returns the first entity involved in this Collision.
     * @return  | @see implementation
     */
    @Basic @Immutable
    Entity getEntity1() {
        return entity1;
    }

    /**
     * Returns the second entity involved in this Collision.
     * @return  | @see implementation
     */
    @Basic @Immutable
    Entity getEntity2() {
        return entity2;
    }

    /**
     * Returns the time before this Collision will happen.
     * @return  | @see implementation
     */
    @Basic @Immutable
    double getTimeToCollision() {
        return timeToCollision;
    }

    public void resolve(CollisionListener collisionListener) {
        if (getEntity1() != null) {
            // entity1 hits a wall
            if (getEntity2() == null) {
                getEntity1().resolveCollisionWithBoundary(collisionListener);
            }

            else {
                if (getEntity2() instanceof Ship) {
                    getEntity1().resolveCollisionWithShip((Ship) getEntity2(), collisionListener);
                }

                else {
                    getEntity1().resolveCollisionWithBullet((Bullet) getEntity2(), collisionListener);
                }
            }
        }
    }
}
