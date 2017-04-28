package asteroids.model.collisions;

import asteroids.model.*;
import asteroids.part2.CollisionListener;
import be.kuleuven.cs.som.annotate.*;
import asteroids.model.util.vector.Vector;


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
     * TODO: Update the documentation
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
        if (getEntity1() instanceof Bullet)
            ((Bullet) getEntity1()).resolveCollisionWithEntity(getEntity2());

        else if (getEntity2() instanceof Bullet)
            ((Bullet) getEntity2()).resolveCollisionWithEntity(getEntity1());

        else if (getEntity1() instanceof Ship) {
            if (getEntity2() instanceof Ship)
                ((Ship) getEntity1()).resolveCollisionWithShip((Ship) getEntity2());

            else if (getEntity2() instanceof Asteroid)
                ((Asteroid) getEntity2()).resolveCollisionWithShip((Ship) getEntity1());

            else //(getEntity2() instanceof Planetoid)
                ((Planetoid) getEntity2()).resolveCollisionWithShip((Ship) getEntity1());
        }

        else if (getEntity2() instanceof Ship) {
            if (getEntity1() instanceof Asteroid)
                ((Asteroid) getEntity1()).resolveCollisionWithShip((Ship) getEntity2());

            else //(getEntity1() instanceof Planetoid)
                ((Planetoid) getEntity1()).resolveCollisionWithShip((Ship) getEntity2());
        }

        else //(getEntity1() instanceof MinorPlanet && getEntity2() instanceof MinorPlanet)
            ((MinorPlanet) getEntity1()).resolveCollisionWithMinorPlanet((MinorPlanet) getEntity2());

        collisionListener(collisionListener);
    }

    /**
     * @Effect  ...
     *          | @see implementation
     */
    @Override
    public void collisionListener(CollisionListener collisionListener) {
        if (collisionListener != null)
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
        return "Entity collision ("
                + getEntity1()
                + ", "
                + getEntity2()
                + ", Position: "
                + getCollisionPosition()
                + ", time: "
                + getTimeToCollision();
    }
}
