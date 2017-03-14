package asteroids.model;

import be.kuleuven.cs.som.annotate.*;
import java.util.HashMap;


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

    public double getHeight() {
        return height;
    }

    private HashMap<Vector, Entity> entities = new HashMap<>();

    public Entity getEntityAtPosition(Vector position){
        return entities.get(position);
    }

    public void addEntity(Entity entity) {
        entities.put(entity.getPosition(), entity);
    }
}
