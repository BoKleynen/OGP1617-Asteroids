package asteroids.model;

import be.kuleuven.cs.som.annotate.Basic;
import asteroids.model.util.vector.Vector;

/**
 * A class of asteroids.
 *
 * @Invar 	A asteroid is associated with at most one world at once.
 * 			| ((getWorld() instanceof World) && (getWorld().getAllEntities().contains(this)) || getWorld() == null
 * @Invar 	A asteroid always has a valid position as its position in its current world.
 * 			| hasValidPositionInWorld(getWorld())
 * @Invar 	A asteroid always has a valid radius as its radius.
 * 			| canHaveAsRadius(getRadius())
 * @Invar   A asteroids mass is greater then or equal to its minimal mass.
 *          | getMass() >= getMinMass(getRadius(), getMinMassDensity())
 * @Invar   A terminated asteroid does not belong to a world
 *          | if isTerminated() then hasWorld() == false
 * @Invar   The maximum speed of each asteroid is less then or equal to the speed of light.
 *          This is the magnitude of an entities velocity is less then or equal to the speed of light.
 *          | getMaxSpeed() <= getSpeedOfLight()
 * @Invar   No asteroid will move faster than its maximum speed.
 *          | getVelocity().getMagnitude() <= getMaxSpeed();
 * @Invar   Each asteroid has a mass density equal to Asteroid.getMassDensity().
 *          | getMass() / (4.0/3.0 * Math.PI * Math.pow(getRadius(), 3)) == getMassDensity()
 * @Invar   The radius of each asteroid is greater then or equal to the minimal radius for a asteroid.
 *          | getRadius() >= getMinRadius
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
