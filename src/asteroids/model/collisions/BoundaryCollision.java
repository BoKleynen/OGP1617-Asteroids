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

    public BoundaryCollision() {
        this(null, Double.POSITIVE_INFINITY, new Vector(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
    }

    public BoundaryCollision(Entity entity, double timeToCollision) {
        this(entity,  timeToCollision, null);
    }

    public BoundaryCollision(Entity entity, double timeToCollision, Vector collisionPosition) {
        super(entity, timeToCollision, collisionPosition);
    }

    @Override
    public Vector calculateCollisionPosition() {
        return getEntity1().getPosition().add(getEntity1().getVelocity().multiply(getTimeToCollision()));
    }

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
