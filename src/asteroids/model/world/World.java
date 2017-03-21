package asteroids.model.world;

import vector.Vector;
import asteroids.model.entities.Bullet;
import asteroids.model.entities.Entity;
import asteroids.model.entities.Ship;
import be.kuleuven.cs.som.annotate.*;

import java.util.ArrayList;
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

    private static final double maxWidth = Double.MAX_VALUE;

    @Basic @Immutable
    public static double getMaxWidth() {
        return maxWidth;
    }

    private static final double maxHeight = Double.MAX_VALUE;

    @Basic @Immutable
    public static double getMaxHeight() {
        return maxHeight;
    }

    private final double width;

    @Basic @Immutable
    public double getWidth() {
        return width;
    }

    private final double height;

    @Basic @Immutable
    public double getHeight() {
        return height;
    }

    private HashMap<Vector, Entity> entities = new HashMap<>();

    public Entity getEntityAtPosition(Vector position){
        return entities.get(position);
    }

    public void addEntity(Entity entity) {

        entities.putIfAbsent(entity.getPosition(), entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity.getPosition());
        entity.setWorld(null);
    }

    public HashSet<Entity> getAllEntities() {
        return (HashSet<Entity>) entities.values();
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

    public Collision getFirstCollision() {
        HashSet<Entity> entities = getAllEntities();
        Collision earliestCollision = new Collision();

        for (Entity entity1: entities) {
            entities.remove(entity1);

            // TODO: 21/03/2017 check for collision with walls. 

            for (Entity entity2 : entities) {
                double collisionTime = entity1.getTimeToCollision(entity2);

                if (collisionTime < earliestCollision.getTimeToCollision()) {
                     earliestCollision = new Collision(entity1, entity2, collisionTime);

                }
            }
        }
        
        return earliestCollision;
    }

    public void evolve(double time) {
        Collision firstCollision = getFirstCollision();
        double timeToFirstCollision = firstCollision.getTimeToCollision();

        if (timeToFirstCollision < time) {
            for (Entity entity : getAllEntities()) {
                entity.move(time);
            }
        }

        else {

            for (Entity entity : getAllEntities()) {
                entity.move(timeToFirstCollision);
            }

            firstCollision.resolve();
            evolve(time - timeToFirstCollision);
        }
    }

}
