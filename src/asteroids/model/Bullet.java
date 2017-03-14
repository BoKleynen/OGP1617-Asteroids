package asteroids.model;

/**
 * Created by Bo on 13/03/2017.
 */
public class Bullet extends Entity {

    public Bullet() {
        super(null, null, 0, 0, 0);
    }

    private static final double minRadius = 1;

    public static double getMinRadius() {
        return minRadius;
    }
}
