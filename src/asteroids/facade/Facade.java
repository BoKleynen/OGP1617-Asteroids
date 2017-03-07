package asteroids.facade;

import asteroids.part1.facade.IFacade;
import asteroids.util.ModelException;
import asteroids.model.Ship;
import asteroids.model.Vector;

/**
 * Created by Yrjo Koyen on 28/02/2017.
 */
public class Facade implements IFacade {

    public Facade()
    {

    }

    public Ship createShip() throws ModelException
    {
        try {
            return new Ship(new Vector(0,0), new Vector(0,0), 0, 1);

        } catch (IllegalArgumentException e) {
            throw new ModelException(e);
        }
    }

    public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius, double orientation)
            throws ModelException
    {
        try {
            return new Ship( new Vector(x, y), new Vector(xVelocity, yVelocity), orientation, radius);

        } catch (IllegalArgumentException e) {
            throw new ModelException(e);
        }
    }

    public double[] getShipPosition(Ship ship) throws ModelException
    {
        return new double[] {ship.getPosition().getX(), ship.getPosition().getY()};
    }

    public double[] getShipVelocity(Ship ship) throws ModelException
    {
        return new double[] {ship.getVelocity().getX(), ship.getVelocity().getY()};
    }

    public double getShipRadius(Ship ship) throws ModelException
    {
        return ship.getRadius();
    }

    public double getShipOrientation(Ship ship) throws ModelException
    {
        return ship.getOrientation();
    }

    public void move(Ship ship, double dt) throws ModelException
    {
    	ship.move(dt);
    }

    public void thrust(Ship ship, double amount) throws ModelException
    {
    	ship.thrust(amount);
    }

    public void turn(Ship ship, double angle) throws ModelException
    {
    	ship.turn(angle);
    }

    public double getDistanceBetween(Ship ship1, Ship ship2) throws ModelException
    {
        return ship1.getDistanceBetween(ship2);
    }

    public boolean overlap(Ship ship1, Ship ship2) throws ModelException
    {
        return ship1.overlap(ship2);
    }

    public double getTimeToCollision(Ship ship1, Ship ship2) throws ModelException
    {
        return ship1.getTimeToCollision(ship2);
    }

    public double[] getCollisionPosition(Ship ship1, Ship ship2) throws ModelException {
        Vector collisionPosition = ship1.getCollisionPosition(ship2);

        return collisionPosition == null ? null : new double[] {collisionPosition.getX(), collisionPosition.getY()};
    }
}
