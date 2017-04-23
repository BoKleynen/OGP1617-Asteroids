package asteroids.model;

import asteroids.model.collisions.Collision;
import asteroids.model.collisions.EntityCollision;
import asteroids.model.collisions.BoundaryCollision;
import asteroids.part2.CollisionListener;
import vector.Vector;
import be.kuleuven.cs.som.annotate.*;
import java.util.*;

/**
 * Created by Bo Kleynen and Yrjo Koyen
 */
public class World {

    public World(double width, double height) {
        if (width < 0)
            this.width = 0;

        else if (width > getMaxWidth())
            this.width = getMaxWidth();

        else
            this.width = width;

        if (height < 0)
            this.height = 0;

        else if (height > getMaxHeight())
            this.height = getMaxHeight();

        else
            this.height = height;
    }

    private boolean isTerminated = false;

    public boolean isTerminated() {
        return isTerminated;
    }

    public void terminate() {
        if (! entities.isEmpty())
            throw new IllegalStateException("This world still contains entities");

        isTerminated = true;
    }

    private static final double maxWidth = Double.MAX_VALUE;

    /**
     * Returns the maximum width of this World.
     *
     * @return  | @see implementation
     */
    @Basic @Immutable
    public static double getMaxWidth() {
        return maxWidth;
    }

    private static final double maxHeight = Double.MAX_VALUE;

    /**
     * Returns the maximum height of this World.
     *
     * @return  | @see implementation
     */
    @Basic @Immutable
    public static double getMaxHeight() {
        return maxHeight;
    }

    private final double width;

    /**
     * Returns the width of this World.
     *
     * @return  | @see implementation
     */
    @Basic @Immutable
    public double getWidth() {
        return width;
    }

    private final double height;

    /**
     * Returns the height of this World.
     *
     * @return  | @see implementation
     */
    @Basic @Immutable
    public double getHeight() {
        return height;
    }

    private HashMap<Vector, Entity> entities = new HashMap<>();

    /**
     * Returns the Entity at the given position in this world in near constant time.
     * 
     * @param position	The position where the entity should be
     * 
     * @return	If the given position is the position of the center of an entity in this world,
     *  returns that entity, otherwise, returns null.
     * 			| result == this.entities.get(position)
     */
    public Entity getEntityAtPosition(Vector position){
        return entities.get(position);
    }

    /**
     * 
     * @param entity	The entity to be added to this world
     * 
     * @throws 	IllegalArgumentException
     * 			...
     * 			| this.getAllEntities().contains(entity)
     * @throws	IllegalArgumentException
     * 			...
     * 			| entity.hasWorld()
     * @throws 	NullPointerException
     * 			...
     * 			| entity == null
     * @throws IllegalStateException
     *          If this world is terminated
     *          | isTerminated()
     *          
     * @Post	...
     * 			| (new this).getAllEntities().contains(entity) &&
     * 			| 	entity.getWorld() == (new this)
     */
    public void addEntity(Entity entity) throws IllegalArgumentException, IllegalStateException, NullPointerException {
        if (isTerminated())
            throw new IllegalStateException("This world is terminated");
        if ( entity == null )
    		throw new NullPointerException();
    	if ( entity.hasWorld() )
    		throw new IllegalArgumentException("Entity already has a world.");
    	if (! entity.hasValidPositionInWorld(this))
    	    throw new IllegalArgumentException("Entity at invalid position for this world");

    	entities.put(entity.getPosition(), entity);
        entity.setWorld(this);
    }

    public void addEntity(Entity... entities) {
        for (Entity entity : entities) {
            addEntity(entity);
        }
    }

    /**
     * @param entity	The entity to be removed from this world
     * @throws	IllegalArgumentException
     * 			...
     * 			| ( ! this.getAllEntities().contains(entity) )
     * @throws NullPointerException
     *          ...
     *          | entity == null
     *          
     * @Post	...
     * 			| ( ! (new this).getAllEntities().contains(entity) ) &&
     * 			|	entity.getWorld() != (new this)
     */
    public void removeEntity(Entity entity) throws NullPointerException, IllegalArgumentException {
    	if (entity == null)
    		throw new NullPointerException();
    	if (!entities.containsValue(entity))
    		throw new IllegalArgumentException("Entity is not in the world");

        entities.remove(entity.getPosition(), entity);
        entity.setWorld(null);
    }

    /**
     * Returns a Set containing all entities in this world.
     * 
     * @return 	A Set containing all entities in this world.
     * 			| result == entities.values()
     */
    public Set<Entity> getAllEntities() {
        return new HashSet<Entity>(entities.values());
    }

    /**
     * Returns a Set containing all ships in this world.
     * 
     * @return 	A Set containing all ships in this world.
     * 			| @see implementation
     */
    public Set<Ship> getAllShips() {
        HashSet<Ship> ships = new HashSet<>();

        for (Entity entity : getAllEntities()) {
            if (entity instanceof Ship)
                ships.add((Ship) entity);
        }

        return ships;
    }


    /**
     * Returns a Set containing all bullets in this world.
     * 
     * @return 	A Set containing all bullets in this world.
     * 			| @see implementation
     */
    public Set<Bullet> getAllBullets() {
        HashSet<Bullet> bullets = new HashSet<>();

        for (Entity entity : getAllEntities())
            if (entity instanceof Bullet) {
                bullets.add((Bullet) entity);
        }

        return bullets;
    }

    /**
     * Returns a Set containing all minor planets in this world.
     *
     * @return | @see implementation
     */
    public Set<MinorPlanet> getAllMinorPlanets() {
        Set<MinorPlanet> minorPlanets = new HashSet<>();

        for (Entity entity : getAllEntities()) {
            if (entity instanceof MinorPlanet)
                minorPlanets.add((MinorPlanet) entity);
        }

        return minorPlanets;
    }

    /**
     * Updates the position of the given entity in this world to the given position.
     * @param entity
     *          The entity that is to be moved.
     * @param newPosition
     *          The position the entity is to be moved to.
     * @Post    The specified entity will be at the specified position
     *          | (new entity).getPosition() == newPosition
     * @Post    The specified entity will be a part of this world.
     *          | (new entity).getWorld() == new
     * @throws IllegalArgumentException
     *          ...
     *          | entity.getWorld() != this
     */
    public void setEntityPosition(Entity entity, Vector newPosition) throws IllegalArgumentException {
        if (entity.getWorld() != this)
            throw new IllegalArgumentException("The specified entity does not belong to this world");

        entities.remove(entity.getPosition());
        entity.setPosition(newPosition);
        entities.put(entity.getPosition(), entity);
    }

    /**
     * Returns the collision that would happen first in this world if its state does not change. That is
     * if all entities in it keep moving with the same velocities. If this world evolves for a time equal
     * to the time until the returned collision, no other collisions will occur this period. The only 
     * collision will be the returned collision at exactly the end of that period.
     */
    public Collision getFirstCollision() {
        Set<Entity> entitiesSet = getAllEntities();
        Entity[] entities = entitiesSet.toArray(new Entity[entitiesSet.size()]);
        Collision earliestCollision = new BoundaryCollision();

        for (int i = 0; i < entities.length; i++) {
            Entity entity1 = entities[i];
            
            double wallCollisionTime = entity1.getTimeToWallCollision();
            
            if ( wallCollisionTime < earliestCollision.getTimeToCollision() )
            	earliestCollision = new BoundaryCollision(entity1, wallCollisionTime);

            for (int j = i + 1; j < entities.length; j++) {
                Entity entity2 = entities[j];
                double collisionTime = entity1.getTimeToCollision(entity2);

                if (collisionTime < earliestCollision.getTimeToCollision()) {
                     earliestCollision = new EntityCollision(entity1, entity2, collisionTime);

                }
            }

        }

        earliestCollision.setCollisionPosition(earliestCollision.calculateCollisionPosition());
        return earliestCollision;
    }


    public void evolve(double time, CollisionListener collisionListener) {
        if (time < 0)
            throw new IllegalArgumentException(Double.toString(time));

        if (time > 0) {
            Collision firstCollision = getFirstCollision();
            double collisionTime = firstCollision.getTimeToCollision();

            if (collisionTime > time) {
                for (Entity entity : getAllEntities()) {
                    entity.move(time);
                }
            }

            else {
                for (Entity entity : getAllEntities()) {
                    entity.move(collisionTime);
                }

                firstCollision.resolve(collisionListener);
                evolve(time - collisionTime, collisionListener);
            }
        }
    }

    /**
     * Destroys this world, removing all entities from this world.
     * 
     * @Post	This world no longer contains any entities
     * 			| (new this).getAllEntities().size() == 0
     * @Post	This world is terminated
     * 			| (new this).isTerminated()
     */
    public void destroy() {
        for (Entity entity : getAllEntities()) {
            removeEntity(entity);
        }
        terminate();
    }
}
