package asteroids.model.collisions;

import asteroids.model.Bullet;
import asteroids.model.Entity;
import asteroids.model.Ship;
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
     * 
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
     * Resolves this collision of two entities. If both entities are ships, they bounce of each other.
     * If one of the involved entities is a bullet, the ship is destroyed if the bullet was not fired 
     * by that ship. If it was fired by that ship, it is instead reloaded to the ship. If both entities
     * are bullets, they both die.
     * 
     * @Post	If both involved entities are an instance of the Bullet class, both entities are destroyed.
     * 			| if ( getEntity1() instanceof Bullet && getEntity2() instanceof Bullet ) then
     * 			| 	(new this).getEntity1().isTerminated() && (new this).getEntity2().isTerminated()
     * @Post	If one of both involved entities is a bullet and the other one is a ship, the ship is destroyed
     * 			by the bullet unless the bullet was fired from the ship it collided with. If the bullet was fired
     * 			from that ship, it is reloaded to that ship on collision.
     * 			| if (getEntity1() instanceof Bullet && getEntity2() instanceof Ship ) then
     * 			|	if getEntity1().getParentShip().equals(getEntity2()) then
     * 			|		(new this).getEntity2().getAllBullets().contains(this.getEntity1())
     * 			|	else
     * 			|		(new this).getEntity1()isTerminated() && (new this).getEntity2()isTerminated()
     * 			| else if (getEntity1() instanceof Ship && getEntity2() instanceof Bullet ) 
     * 			|	if getEntity2().getParentShip().equals(getEntity1()) then
     * 			|		(new this).getEntity1().getAllBullets().contains(this.getEntity2())
     * 			|	else
     * 			|		(new this).getEntity1()isTerminated() && (new this).getEntity2()isTerminated()
     * @Post	If both involved entities are instances of the Ship class, both ships bounce of each other
     * 			based on their current mass and velocity.
     * 			| @see implementation
     */
    @Override
    public void resolve(CollisionListener collisionListener) {
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
                resolveShipBulletCollision(ship1, (Bullet) getEntity2(), collisionListener);
        }
        else {
            if (getEntity2() instanceof Ship)
                resolveShipBulletCollision((Ship) getEntity2(), (Bullet)getEntity1(), collisionListener);

            else {
                getEntity1().die();
                getEntity2().die();
            }
        }
    }

    /**
     * Resolves a collision between the Ship ship and the Bullet bullet. If The bullet was originally fired
     * by the given ship, it is reloaded to that ship, otherwise both entities die.
     * 
     * @param ship	The ship involved in this collision
     * @param bullet	The bullet involved in this collision
     * 
     * @Post	If The bullet was originally fired by the given ship, it is reloaded to that ship, otherwise both
     * 			entities die.
     * 			| if bullet.getParentShip().equals(ship) then
     * 			|		(new ship).getAllBullets().contains(bullet)
     * 			|	else
     * 			|		(new ship).isTerminated() && (new bullet).isTerminated()
     */
    private void resolveShipBulletCollision(Ship ship, Bullet bullet, CollisionListener collisionListener) {
        try {
            ship.loadBullet(bullet);
        } catch (IllegalArgumentException e) {
            ship.die();
            bullet.die();
            collisionListener(collisionListener);
        }
    }

    /**
     * @Effect  ...
     *          | @see implementation
     */
    @Override
    public void collisionListener(CollisionListener collisionListener) {
        collisionListener.objectCollision(getEntity1(), getEntity2(), getCollisionPosition().getX(), getCollisionPosition().getY());
    }

    /**
     * Returns a string representation of this entity collision
     * 
     * @return 	A string representing this collision.
     * 			| @see implementation
     */
    @Override
    public String toString() {
        return "Entity collision (" + getEntity1() + ", " + getEntity2() + ", Position: " + getCollisionPosition() + ", time: " + getTimeToCollision();
    }
}
