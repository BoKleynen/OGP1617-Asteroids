package asteroids.model;


import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import vector.Vector;

/**
 * Created by Bo on 18/04/2017.
 */
public abstract class MinorPlanet extends Entity {

    /**
     * Creates a new minor planet in accordance with the give parameters.
     *
     * @param position
     *          The position for this minor planet.
     * @param maxSpeed
     *          The maximum speed this minor planet can achieve.
     * @param velocity
     *          The velocity for this minor planet.
     * @param radius
     *          The radius for this minor planet.
     * @param massDensity
     *          The mass density of this minor planet.
     * @Effect  | super(position, maxSpeed, velocity, radius, getMinRadius(), massDensity)
     */
    public  MinorPlanet(Vector position, double maxSpeed, Vector velocity, double radius, double massDensity) {
        super(position, maxSpeed, velocity, radius, getMinRadius(), massDensity);
    }

    private static final double minRadius = 5;

    /**
     * Returns the minamal radius for a MinorPlanet.
     *
     * @return  this.minRadius
     */
    @Basic @Immutable
    public static double getMinRadius() {
        return minRadius;
    }

    /**
     * Resolves the collision of this minor planet with the specified minor planet.
     * When 2 minor planets collide they bounce of off each other.
     *
     * @param minorPlanet
     *          The minor planet that collides with this minor planet.
     */
    public void resolveCollisionWithMinorPlanet(MinorPlanet minorPlanet) {
        double sigma = getRadius() + minorPlanet.getRadius();
        double J =
                (2.0 * getMass() * minorPlanet.getMass() *
                        minorPlanet
                                .getVelocity()
                                .getDifference(getVelocity())
                                .dotProduct(
                                        minorPlanet
                                                .getPosition()
                                                .getDifference(getPosition())
                                )
                ) / (sigma * (getMass() + minorPlanet.getMass()));

        double Jx = J * (minorPlanet.getPosition().getX() - getPosition().getX()) / sigma;
        double Jy = J * (minorPlanet.getPosition().getY() - getPosition().getY()) / sigma;

        setVelocity(
                getVelocity().getX() + Jx/getMass(),
                getVelocity().getY() + Jy/getMass());
        minorPlanet.setVelocity(
                minorPlanet.getVelocity().getX() - Jx/minorPlanet.getMass(),
                minorPlanet.getVelocity().getY() - Jy/minorPlanet.getMass());
    }

    /**
     * Terminates this minor Planet removing it from its world and marking it as terminated.
     *
     * @Post    | new.isTerminated() == true
     * @Post    | new.hasWorld() == false
     */
    @Override
    public void terminate() {
        try {
            getWorld().removeEntity(this);
        } catch (NullPointerException e) {}

        isTerminated = true;
    }
}
