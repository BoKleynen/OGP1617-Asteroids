package asteroids.model.world;

import asteroids.part2.CollisionListener;
import vector.Vector;
import asteroids.model.entities.*;
import be.kuleuven.cs.som.annotate.*;
import java.util.HashMap;
import java.util.HashSet;


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
    	if ( entity == null )
    		throw new NullPointerException();
    	if ( (entity.hasWorld()) || (entities.containsValue(entity)) )
    		throw new IllegalArgumentException();
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
    	if ( ! entities.containsKey(entity.getPosition())) 
    		throw new IllegalArgumentException();
        entities.remove(entity.getPosition());
        entity.setWorld(null);
        entity.terminate();
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
    	if ( entities.containsKey(bullet.getPosition()) ) {
    		entities.remove(bullet.getPosition(), bullet);
    		bullet.removeWorld();
    	}
    }

    public Collision getFirstCollision() {
        HashSet<Entity> entities = getAllEntities();
        Collision earliestCollision = new Collision();

        for (Entity entity1: entities) {
            entities.remove(entity1);

            double wallCollisionTime = entity1.getTimeToWallCollision();

            if ( wallCollisionTime < earliestCollision.getTimeToCollision() )
            	earliestCollision = new Collision(entity1, wallCollisionTime);

            for (Entity entity2 : entities) {
                double collisionTime = entity1.getTimeToCollision(entity2);

                if (collisionTime < earliestCollision.getTimeToCollision()) {
                     earliestCollision = new Collision(entity1, entity2, collisionTime);

                }
            }
        }
        
        return earliestCollision;
    }


    public void evolve(double time, CollisionListener collisionListener) {
        if (time < 0)
            throw new IllegalArgumentException();

        if (time > 0) {
            Collision firstCollision = getFirstCollision();
            double timeToFirstCollision = firstCollision.getTimeToCollision();

        
            if (timeToFirstCollision > time) {
                for (Entity entity : getAllEntities()) {
                    entity.move(time);
                }
            }

            else {
                for (Entity entity : getAllEntities()) {
                    entity.move(timeToFirstCollision);
                }

                firstCollision.resolve(collisionListener);
                evolve(time - timeToFirstCollision, collisionListener);
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
