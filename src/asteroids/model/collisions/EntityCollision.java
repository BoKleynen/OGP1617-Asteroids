package asteroids.model.collisions;

import asteroids.model.entities.*;
import asteroids.part2.CollisionListener;
import be.kuleuven.cs.som.annotate.*;
import vector.Vector;


/**
 * Created by Bo Kleynen and Yrjo Koyen.
 */
public class EntityCollision extends Collision {

	/**
	 * Creates a new collision between the two given entities. The collision will happen after the given timeToCollision.
	 * 
	 * @param entity1	The first entity involved in this collision
	 * @param entity2	The second entity involved in this collision
	 * @param timeToCollision	The time until this collision will happen.
	 * 
	 * @Effect	this(entity1, entity2, timeToCollision, null)
	 */
    public EntityCollision(Entity entity1, Entity entity2, double  timeToCollision) {
        this(entity1, entity2, timeToCollision, null);
    }

    /**
	 * Creates a new collision between the two given entities. The collision will happen after the given timeToCollision.
	 * 
	 * @param entity1	The first entity involved in this collision
	 * @param entity2	The second entity involved in this collision
	 * @param timeToCollision	The time until this collision will happen.
	 * @param collisionPosition	The position where this collision will happen.
	 * 
	 * @Post	The entity first entity involved in this collision is the given entity1.
	 * 			| this.getEntity1() == entity1
	 * @Post	The entity second entity involved in this collision is the given entity2.
	 * 			| this.getEntity1() == entity2
	 * @Post	The time until this collision will happen is equal to the given timeToCollision.
	 * 			| this.getTimeToCollision() == timeToCollision
	 * @Post	The position where this collision will happen is equal to collisionPosition.
	 * 			| this.getCollisionPosition() == collisionPosition
	 */
    public EntityCollision(Entity entity1, Entity entity2, double  timeToCollision, Vector collisionPosition) {
        super(entity1, timeToCollision, collisionPosition);
        this.entity2 = entity2;
    }

    private final Entity entity2;

    /**
     * Returns the second entity involved in this Collision.
     * @return  | @see implementation
     */
    @Basic @Immutable
    public Entity getEntity2() {
        return entity2;
    }

    /**
     * Calculates where the two entities involved in this collision will collide.
     * 
     * @return | @see implementation
     */
    @Override
    public Vector calculateCollisionPosition() {
        return getEntity1().getCollisionPosition(getEntity2());
    }

    /**
     * Resolves this collision of two entities. If both entities are ships, the bounce of each other.
     * If one of the involved entities is a bullet, the ship is destroyed if the bullet was not fired 
     * by that ship. If it was fired by that ship, it is instead reloaded to the ship. If both entities
     * are bullets, they both die.
     */
    @Override
    public void resolve() {
        if (getEntity1() instanceof Ship) {
            Ship ship1 = (Ship)getEntity1();

            if (getEntity2() instanceof Ship) {
                Ship ship2 = (Ship)getEntity2();
                double sigma = ship1.getRadius() + ship2.getRadius();
                double J = (2.0 * ship1.getTotalMass() * ship2.getTotalMass() * ship2.getVelocity().getDifference(ship1.getVelocity()).dotProduct(ship2.getPosition().getDifference(ship1.getPosition()))) /
                        (sigma * (ship1.getTotalMass()+ ship2.getTotalMass()));

                double Jx = J * (ship2.getPosition().getX() - ship1.getPosition().getX()) / sigma;
                double Jy = J * (ship2.getPosition().getY() - ship1.getPosition().getY()) / sigma;

                ship1.setVelocity(new Vector(ship1.getVelocity().getX() + Jx/ship1.getTotalMass(),ship1.getVelocity().getY() + Jy/ship1.getTotalMass()));
                ship2.setVelocity(new Vector(ship2.getVelocity().getX() - Jx/ship2.getTotalMass(),ship2.getVelocity().getY() - Jy/ship2.getTotalMass()));
            }
            else
                resolveShipBulletCollision(ship1, (Bullet) getEntity2());
        }
        else {
            if (getEntity2() instanceof Ship)
                resolveShipBulletCollision((Ship) getEntity2(), (Bullet)getEntity1());

            else {
                getEntity1().die();
                getEntity2().die();
            }
        }
    }

    private void resolveShipBulletCollision(Ship ship, Bullet bullet) {
        if (bullet.getParentShip() == ship) {
            ship.loadBullet(bullet);
        }
        else {
            ship.die();
            bullet.die();
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
