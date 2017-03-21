package asteroids.model.entities;


import vector.Vector;

public class Bullet extends Entity {

    public Bullet(Vector position, Vector velocity, double radius) {
        this(position, velocity, getSpeedOfLight() ,radius);
    }

    public Bullet(Vector position, Vector velocity, double maxSpeed,double radius) {
        super(position, velocity, maxSpeed, radius, getMinRadius(), 0, getMassDensity());

        maxWallHits = 2;
        wallHits = 0;

    }

    @Override
    public void move(double time) {
        if( time < 0 )
            throw new IllegalArgumentException();

        setPosition(getPosition().add(getVelocity().multiply(time)));
    }

    private static final double minRadius = 1;

    /**
     * Returns the minimal value for the radius of a Bullet.
     *
     * @return
     */
    public static double getMinRadius() {
        return minRadius;
    }

    private static final double massDensity = 7.8 * Math.pow(10, 12);

    /**
     * Returns the minimal mass density of a Bullet.
     * @return
     */
    public static double getMassDensity() {
        return massDensity;
    }

    private Ship parrentShip;

    /**
     * Returns the ship to which this bullet belongs or that fired it.
     *
     * @return
     */
    public Ship getParrentShip() {
        return parrentShip;
    }

    private char wallHits;

    public char getWallHits() {
        return wallHits;
    }

    private void incrementWallHits() {
        wallHits += 1;
    }

    private char maxWallHits;

    public char getMaxWallHits() {
        return maxWallHits;
    }

    @Override
    public void resolveCollisionWithBoundry() {
        if (getWallHits() == getMaxWallHits())
            die();

        incrementWallHits();
    }

}
