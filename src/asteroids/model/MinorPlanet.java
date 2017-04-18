package asteroids.model;


import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import vector.Vector;

/**
 * Created by Bo on 18/04/2017.
 */
public abstract class MinorPlanet extends Entity {

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
     * Resolves the collision of this minot planet with the specified minor planet.
     *
     * @param minorPlanet
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



    @Override
    public void terminate() {
        try {
            getWorld().removeEntity(this);
        } catch (NullPointerException e) {}

        isTerminated = true;
    }
}
