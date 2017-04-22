package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import vector.Vector;

/**
 * Created by Bo on 18/04/2017.
 */
public class Asteroid extends MinorPlanet {

    public Asteroid(Vector position, Vector velocity, double radius) {
        this(position, getSpeedOfLight(), velocity, radius);
    }

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
     *
     * @param ship
     * @Post    | (new ship).isTerminated()
     */
    public void resolveCollisionWithShip(Ship ship) {
        ship.die();
    }
}
