package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import asteroids.model.util.vector.Vector;

/**
 * Created by Bo on 18/04/2017.
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
     * @return  | this.massDensity
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
     * @Post    | (new ship).isTerminated()
     */
    public void resolveCollisionWithShip(Ship ship) {
        ship.die();
    }
}
