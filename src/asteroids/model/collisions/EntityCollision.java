package asteroids.model.collisions;

import asteroids.model.entities.*;
import asteroids.part2.CollisionListener;
import be.kuleuven.cs.som.annotate.*;
import vector.Vector;


/**
 * Created by Bo on 29/03/2017.
 */
public class EntityCollision extends Collision {

    public EntityCollision(Entity entity1, Entity entity2, double  timeToCollision) {
        this(entity1, entity2, timeToCollision, null);
    }

    public EntityCollision(Entity entity1, Entity entity2, double  timeToCollision, Vector collisionPosition) {
        super(entity1, timeToCollision, collisionPosition);
        this.entity2 = entity2;
    }

    private final Entity entity2;

    /**
     * Returns the second entity involved in this Collision.
     * @return  | @see implementation
     */
    @Basic
    @Immutable
    public Entity getEntity2() {
        return entity2;
    }

    @Override
    public Vector calculateCollisionPosition() {
        return getEntity1().getCollisionPosition(getEntity2());
    }

    @Override
    public void resolve() {
        if (getEntity2() instanceof Ship)
            getEntity1().resolveCollisionWithShip((Ship) getEntity2());

        else
            getEntity1().resolveCollisionWithBullet((Bullet) getEntity2());
    }

    @Override
    public void collisionListener(CollisionListener collisionListener) {
        collisionListener.objectCollision(getEntity1(), getEntity2(), getCollisionPosition().getX(), getCollisionPosition().getY());
    }
}
