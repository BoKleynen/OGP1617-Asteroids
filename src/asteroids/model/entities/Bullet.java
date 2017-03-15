package asteroids.model.entities;


public class Bullet extends Entity {

    public Bullet() {
        super(null, null, 0, 0, 0);

        mass = 4/3 * Math.PI * Math.pow(getRadius(), 3) * getMinMassDensity();
    }

    private static final double minRadius = 1;

    public static double getMinRadius() {
        return minRadius;
    }

    private Ship parrentShip;

    public Ship getParrentShip() {
        return parrentShip;
    }

    private static final double minMassDensity = 7.8 * Math.pow(10, 12);

    public static double getMinMassDensity() {
        return minMassDensity;
    }

    private final double mass;

    public double getMass() {
        return mass;
    }
}
