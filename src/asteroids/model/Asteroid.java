package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import asteroids.model.util.vector.Vector;

/**
 * A class of asteroids.
 *
 * @Invar   Each asteroid has a mass density equal to Asteroid.getMassDensity().
 *          | getMass() / (4.0/3.0 * Math.PI * Math.pow(getRadius(), 3)) == getMassDensity()
 *
 * @author  Bo Kleynen and Yrjo Koyen.
 */
public class Asteroid extends MinorPlanet {

    public Asteroid(Vector position, Vector velocity, double radius) {
        this(position, getSpeedOfLight(), velocity, radius);
    }

    /**
     * Creates a new asteroid according to the given parameters.
     *
     * @param position
     *          The position for this asteroid.
     * @param maxSpeed
     *          The maximum speed this asteroid can achieve.
     * @param velocity
     *          The velocity for this asteroid.
     * @param radius
     *          The radius for this asteroid.
     *
     * @Effect  | super(position, maxSpeed, velocity, radius, getMassDensity())
     */
    public Asteroid(Vector position, double maxSpeed, Vector velocity, double radius) {
        super(position, maxSpeed, velocity, radius, getMassDensity());
    }

    private static final double massDensity = 2.65 * 1e12;

    /**
     * Returns the massDensity of an Asteroid.
     *
     * @return  | result = this.massDensity
     */
    @Basic
    public static double getMassDensity() {
        return massDensity;
    }

    /**
     * Resolves the collision of this asteroid with the specified ship, resulting in the death of the ship.
     *
     * @param ship
     *          The ship this Asteroids collides with.
     * @Post    The colliding ship is destroyed
     *          | (new ship).isTerminated()
     * @Post    This asteroid is unaffected
     *          | new == this
     */
    public void resolveCollisionWithShip(Ship ship) {
        ship.die();
    }
}
