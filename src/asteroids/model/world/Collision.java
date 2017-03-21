package asteroids.model.world;

import asteroids.model.entities.Entity;

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
    private final Entity entity2;       // enitit2 == null, entity 1 hits a wall
    private final double timeToCollision;

    Entity getEntity1() {
        return entity1;
    }

    Entity getEntity2() {
        return entity2;
    }

    double getTimeToCollision() {
        return timeToCollision;
    }

    public void resolve() {
        // entity1 hits a wall
        if (getEntity2() == null) {

        }
    }
}
