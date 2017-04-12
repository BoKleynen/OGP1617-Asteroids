package asteroids.model.world;

import asteroids.model.collisions.Collision;
import asteroids.model.collisions.EntityCollision;
import asteroids.model.collisions.BoundaryCollision;
import asteroids.part2.CollisionListener;
import vector.Vector;
import asteroids.model.entities.*;
import be.kuleuven.cs.som.annotate.*;

import java.util.*;


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
     * 
     * @param position	The position 
     * @return
     */
    public Entity getEntityAtPosition(Vector position){
        return entities.get(position);
    }

    /**
     * 
     * @param entity
     * @throws 	IllegalArgumentException
     * 			...
     * 			if entity == null
     */
    public void addEntity(Entity entity) throws IllegalArgumentException {
        if (entity.isTerminated())
            throw new IllegalStateException("The entity is terminated");
        if ( entity == null )
    		throw new NullPointerException();
    	if ( entity.hasWorld() )
    		throw new IllegalArgumentException("Entity already has a world.");
    	if ( entities.containsValue(entity) )
    		throw new IllegalArgumentException("Entity is already in this world");
    	if (isTerminated())
    	    throw new IllegalStateException("This world is terminated");


        entities.put(entity.getPosition(), entity);
        entity.setWorld(this);
    }

    /**
     * @param entity
     * @throws	IllegalArgumentException
     * 			...
     * 			Triviaal
     */
    public void removeEntity(Entity entity) {
    	if ( entity == null )
    		throw new NullPointerException();
    	if ( ! entities.containsValue(entity))
    		throw new IllegalArgumentException("Entity is not in the world");
        entities.remove(entity.getPosition(), entity);
        entity.setWorld(null);
    }

    public HashSet<Entity> getAllEntities() {
        return new HashSet<Entity>(entities.values());
    }

    public HashSet<Ship> getAllShips() {
        HashSet<Ship> ships = new HashSet<>();

        for (Entity entity : getAllEntities()) {
            if (entity instanceof Ship) {
                ships.add((Ship) entity);
            }
        }

        return ships;
    }

    public HashSet<Bullet> getAllBullets() {
        HashSet<Bullet> bullets = new HashSet<>();

        for (Entity entity : getAllEntities()) {
            if (entity instanceof Bullet) {
                bullets.add((Bullet) entity);
            }
        }

        return bullets;
    }
    
    public void removeBullet(Bullet bullet) {
    	if (entities.containsKey(bullet.getPosition()) ) {
    		entities.remove(bullet.getPosition(), bullet);
    		bullet.removeWorld();
    	}
    }

    public void updateEntityPosition(Entity entity, Vector newPosition) {
        entities.remove(entity.getPosition());
        entity.setPosition(newPosition);
        entities.put(entity.getPosition(), entity);
    }

    public Collision getFirstCollision() {
        HashSet<Entity> entitiesSet = getAllEntities();
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

                firstCollision.collisionListener(collisionListener);
                firstCollision.resolve();
                if (time - collisionTime > 0)
                	evolve(time - collisionTime, collisionListener);
            }
        }
    }

    /**
     * Destroys this world, removing all entities from this world
     */
    public void destroy() {
        for (Entity entity : getAllEntities()) {
            removeEntity(entity);
        }
        terminate();
    }

}
