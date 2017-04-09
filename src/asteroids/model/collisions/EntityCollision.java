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
        if (getEntity2() instanceof Ship) {
            Ship ship2 = (Ship)getEntity2();

            if (getEntity1() instanceof Ship) {
                Ship ship1 = (Ship)getEntity1();
                double sigma = ship1.getRadius() + ship2.getRadius();
                double J = (2.0 * ship1.getTotalMass() * ship2.getTotalMass() * ship2.getVelocity().getDifference(ship1.getVelocity()).dotProduct(ship2.getPosition().getDifference(ship1.getPosition()))) /
                        (sigma * (ship1.getTotalMass()+ ship2.getTotalMass()));

                double Jx = J * (ship2.getPosition().getX() - ship1.getPosition().getX()) / sigma;
                double Jy = J * (ship2.getPosition().getY() - ship1.getPosition().getY()) / sigma;

                ship1.setVelocity(new Vector(ship1.getVelocity().getX() + Jx/ship1.getTotalMass(),ship1.getVelocity().getY() + Jy/ship1.getTotalMass()));
                ship2.setVelocity(new Vector(ship2.getVelocity().getX() - Jx/ship2.getTotalMass(),ship2.getVelocity().getY() - Jy/ship2.getTotalMass()));
            }
            else {
                Bullet bullet = (Bullet) getEntity1();
                if (bullet.getParentShip() == ship2) {
                    ship2.addBullet(bullet);
                }
                else {
                    ship2.die();
                    bullet.die();
                }
            }
        }
//            getEntity1().resolveCollisionWithShip((Ship) getEntity2());
        else {
            Bullet bullet = (Bullet) getEntity2();
            Ship ship = (Ship) getEntity1();
            if (bullet.getParentShip() == ship) {
                ship.addBullet(bullet);
            }
            else {
                ship.die();
                bullet.die();
            }

//            getEntity1().resolveCollisionWithBullet((Bullet) getEntity2());
        }
    }

    @Override
    public void collisionListener(CollisionListener collisionListener) {
        collisionListener.objectCollision(getEntity1(), getEntity2(), getCollisionPosition().getX(), getCollisionPosition().getY());
    }

    @Override
    public String toString() {
        return "Entity collision (" + getEntity1() + ", " + getEntity2() + ", Position: " + getCollisionPosition() + ", time: " + getTimeToCollision();
    }
}
