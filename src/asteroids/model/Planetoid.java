package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import vector.Vector;


/**
 * Created by Bo on 18/04/2017.
 */
public class Planetoid extends MinorPlanet {

    public Planetoid(Vector postition, Vector velocity, double radius) {
        this (postition, velocity, radius, 0);
    }

    public Planetoid(Vector position, Vector velocity, double radius, double totalTraveledDistance) {
        this(position, getSpeedOfLight(), velocity, radius, totalTraveledDistance);
    }

    public Planetoid(Vector position, double maxSpeed, Vector velocity, double radius, double totalTraveledDistance) {
        super(position, maxSpeed, velocity, radius, getMassDensity());
        addTraveledDistance(totalTraveledDistance);

    }

    private static final double massDensity = 0.917 * 1e12;

    /**
     * Returns the mass density of a Planetoid
     * @return | this.massDensity()
     */
    @Basic @Immutable
    public static double getMassDensity() {
        return massDensity;
    }

    private static final double minSplitRadius = 30;

    /**
     * Returns the minimal radius a Planetoid must have on death to split into two asteroids
     * @return | this.minSplitRadius
     */
    @Basic @Immutable
    public static double getMinSplitRadius() {
        return minSplitRadius;
    }

    @Override
    public void move(double time) {
        super.move(time);
        addTraveledDistance(getVelocity().getMagnitude() * time);
    }

    @Override
    public void setRadius(double newRadius) throws IllegalArgumentException {
        if (newRadius < getMinRadius())
            throw new IllegalArgumentException();

        super.setRadius(newRadius);

    }

    private double totalTraveledDistance = 0;

    public double getTotalTraveledDistance() {
        return totalTraveledDistance;
    }

    public void addTraveledDistance(double distance) {
        totalTraveledDistance += distance;
        try {
            setRadius(getRadius() - distance * 1e-6);
        } catch (IllegalArgumentException e) {
            die();
        }
    }

    /**
     * @param ship
     */
    public void resolveCollisionWithShip(Ship ship) {
        Vector randomPosition = new Vector(
                Math.random() * (getWorld().getWidth() - 2 * ship.getRadius()) + getRadius(),
                Math.random() * (getWorld().getHeight() - 2 * ship.getRadius()) + getRadius()
                );

        if (canHaveAsPositionInWorld(randomPosition, getWorld()))
            getWorld().setEntityPosition(ship, randomPosition);
        else
            ship.die();
    }

    @Override
    public void die() {
        if (hasWorld() && getRadius() >= getMinSplitRadius()) {

            double radius = getRadius() / 2;

            double theta = Math.random() * 2 * Math.PI;
            Vector orientation = new Vector(Math.cos(theta), Math.sin(theta));

            double phi = Math.random() * 2 * Math.PI;
            Vector velocityOrientation = new Vector(Math.cos(phi), Math.sin(phi));
            double speed = 1.5 * getVelocity().getMagnitude();

            Asteroid asteroid1 = new Asteroid(
                    getPosition().add(orientation.multiply(getRadius()/2)),
                    getSpeedOfLight(),
                    velocityOrientation.multiply(speed),
                    radius);

            Asteroid asteroid2 = new Asteroid(
                    getPosition().add(orientation.multiply(-getRadius()/2)),
                    getSpeedOfLight(),
                    velocityOrientation.multiply(-speed),
                    radius);

            getWorld().addEntity(asteroid1, asteroid2);
        }

        terminate();
    }
}
