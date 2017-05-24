package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import asteroids.model.util.vector.Vector;

/**
 * A class of planetoid entities.
 *
 * @author  Kleynen & Yrjo Koyen
 */
public class Planetoid extends MinorPlanet {

    /**
     * creates a new planetoid with the given parameters, the speed of light as max speed and the totalTraveledDistance
     * equal to 0.
     *
     * @param position
     * @param velocity
     * @param radius
     */
    public Planetoid(Vector position, Vector velocity, double radius) {
        this (position, velocity, radius, 0);
    }

    /**
     * Creates a new planetoid with the given parameters and the speed of light as max speed.
     *
     * @param position
     * @param velocity
     * @param radius
     * @param totalTraveledDistance
     * @Effect  | this(position, getSpeedOfLight(), velocity, radius, totalTraveledDistance)
     */
    public Planetoid(Vector position, Vector velocity, double radius, double totalTraveledDistance) {
        this(position, getSpeedOfLight(), velocity, radius, totalTraveledDistance);
    }

    /**
     * Creates a new planetoid with the given parameters.
     *
     * @param position
     *          The position for this planetoid.
     * @param maxSpeed
     *          The maximum speed this planetoid can achieve.
     * @param velocity
     *          The velocity for this plaentoid
     * @param radius
     *          The radius for this planetoid.
     * @param totalTraveledDistance
     *          The total distance this planetoid has travelled.
     * @Post    | result.getPosition == position
     * @Post    | result.getMaxSpeed() <= maxSpeed
     * @Post    | result.getVelocity() == velocity
     * @Post    | result.getRadius <= radius
     * @Post    | result.getTotalTraveledDistance == totalTravelledDistance
     * @Post    | if result.getRadius <= getMinRadius() then
     *          |   result.isTerminated() == true
     */
    public Planetoid(Vector position, double maxSpeed, Vector velocity, double radius, double totalTraveledDistance) {
        super(position, maxSpeed, velocity, radius, getMassDensity());
        addTraveledDistance(totalTraveledDistance);

    }

    private static final double massDensity = 0.917 * 1e12;

    /**
     * Returns the mass density of a Planetoid
     *
     * @return | Planetoid.massDensity
     */
    @Basic @Immutable
    public static double getMassDensity() {
        return massDensity;
    }

    private static final double minSplitRadius = 30;

    /**
     * Returns the minimal radius a Planetoid must have on death to split into two asteroids.
     *
     * @return | Planetoid.minSplitRadius
     */
    @Basic @Immutable
    public static double getMinSplitRadius() {
        return minSplitRadius;
    }

    /**
     * Moves this planetoid at its current velocity over a given time time.
     *
     * @param 	time
     * 			The time to move in the direction of the velocity vector.
     * @Effect  | super.move(time)
     *
     */
    @Override
    public void move(double time) {
        super.move(time);
        addTraveledDistance(getVelocity().getMagnitude() * time);
    }

    /**
     * Sets the radius of this planetoid to the specified value.
     *
     * @param newRadius
     *          The new radius for this planetoid
     * @Post    | new.getRadius == newRadius
     * @throws IllegalArgumentException
     *          | if (newRadius < MinorPlanet.getMinRadius())
     */
    @Override @Basic
    public void setRadius(double newRadius) throws IllegalArgumentException {
        if (newRadius < getMinRadius())
            throw new IllegalArgumentException();

        super.setRadius(newRadius);

    }

    private double totalTraveledDistance = 0;

    /**
     * Returns the total distance this planetoid has travelled.
     *
     * @return  The total distance this planetoid has travelled
     *          | this.totalTraveledDistance
     */
    @Basic
    public double getTotalTraveledDistance() {
        return totalTraveledDistance;
    }

    /**
     * Adds the specified distance to the total traveled distance of this planetoid.
     *
     * @param distance
     *          The distance to be added to this planetoids totalTraveledDistance
     * @Post    | new.getTotalTraveledDistance() == this.getTotalTraveledDistance + distance
     * @Post    | new.getRadius() == this.getRadius() - distance * 1e-6
     * @Post    If the radius of this new planetoid would be less then the minimal radius for a minor planet this new
     *          planetoid will die.
     *          | if new.getRadius < MinorPlanet.getMinRadius() then
     *          |   new.die()
     */
    @Basic
    public void addTraveledDistance(double distance) {
        totalTraveledDistance += distance;
        try {
            setRadius(getRadius() - distance * 1e-6);
        } catch (IllegalArgumentException e) {
            die();
        }
    }

    /**
     * Resolves the collision of this planetoid with the given ship, teleporting the ship to a random location in its
     * world, if that position is already occupied by another entity, the ship dies.
     * @param ship
     *          The ship that collides with this planetoid.
     * @Post    | new == this
     * @Post    | @see implementation for what happens to the ship
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

    /**
     * Kills this planetoid, removing it from its world and marking it as terminated. If this planetoids is located
     * within a world and its radius is greater then or equal to the minimal split radius, it will split into 2
     * asteroids each with a radius equal to half the radius of this asteroid. Both asteroids are then placed into the world
     * of this planetoid at a random position along a circle with radius half the radius of this planetoid and centered
     * on the center of this planetoid, in such a way that the centers of this planetoid and the 2 new asteroids are on
     * the same line. The magnitude of the velocity of these new asteroids is 1.5 times the magnitude of the velocity of
     * this asteroid and the orientation is set at random, but they both move in opposite directions.
     *
     * @Post    The new entity is terminated.
     *          | new.isTerminated == true
     * @Post    The new entity is no longer located in a world
     *          | new.hasWorld() == false
     * @Post    If this planetoid was part of a world and had a radius larger then or equal too a minimal threshold, the
     *          minimal split radius, it will split into 2 asteroids. Each of these 2 asteroids will be located in the
     *          world this planetoid was in at a random position on a circle with radius half the radius of this planetoid
     *          and centered on the center of this planetoid, in such a way that the centers of the asteroids and this planetoid
     *          are on the same line. The magnitude of the velocity of these new asteroids is 1.5 times the magnitude of
     *          the velocity of this asteroid and the orientation is set at random, but they both move in opposite directions.
     *          | if hasWorld() && getRadius() >= getMinSplitRadius() then
     *          |   this.getWorld.getAllEntities().contains(Asteroid(
     *          |                                                       this.getPosition().add(orientation.multiply(getRadius()/2)),
     *          |                                                       getSpeedOfLight(),
     *          |                                                       velocityOrientation.multiply(speed),
     *          |                                                       radius)
     *          | &&
     *          |   this.getWorld.getAllEntities().contains(Asteroid(
     *          |                                                       this.getPosition().add(orientation.multiply(-getRadius()/2)),
     *          |                                                       getSpeedOfLight(),
     *          |                                                       velocityOrientation.multiply(-speed),
     *          |                                                       radius)
     *          | where
     *          |       speed = 1.5 * this.getVelocity().getMagnitude()
     *          |       velocityOrientation = Vector(Math.cos(phi), Math.sin(phi)   with phi some random angle between 0..2PI
     *          |       orientation = Vector(Math.cos(theta), Math.sin(theta))      with theta some random angle between 0..2PI
     *          |
     */
    @Override
    public void die() {
        World world = getWorld();

        super.die();

        if (world != null && getRadius() >= getMinSplitRadius()) {

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

            world.addEntity(asteroid1, asteroid2);
        }
    }
}
