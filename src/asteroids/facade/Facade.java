package asteroids.facade;

import asteroids.part1.facade.IFacade;
import asteroids.util.ModelException;
import asteroids.model.entities.Ship;
import vector.Vector;

/**
 * Created by Bo Kleynen and Yrjo Koyen.
 */
public class Facade implements IFacade {

    public Facade()
    {

    }

    /**************
     * SHIP: Basic methods
     *************/

    public Ship createShip() throws ModelException
    {
        try {
            return new Ship();

        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius, double orientation)
            throws ModelException
    {
        try {
            return new Ship( new Vector(x, y), new Vector(xVelocity, yVelocity), orientation, radius, 0);

        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius, double direction,
                           double mass) throws ModelException {
        try {
            return new Ship(new Vector(x, y), new Vector(xVelocity, yVelocity), direction, radius, mass);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    public double[] getShipPosition(Ship ship) throws ModelException
    {
        try {
            return new double[]{ship.getPosition().getX(), ship.getPosition().getY()};
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    public double[] getShipVelocity(Ship ship) throws ModelException
    {
        try {
            return new double[]{ship.getVelocity().getX(), ship.getVelocity().getY()};
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    public double getShipRadius(Ship ship) throws ModelException
    {
        try {
            return ship.getRadius();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    public double getShipOrientation(Ship ship) throws ModelException
    {
        try {
            return ship.getOrientation();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    public void move(Ship ship, double dt) throws ModelException
    {
        try {
            ship.move(dt);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    public void thrust(Ship ship, double amount) throws ModelException
    {
        try {
            ship.thrust(amount);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    public void turn(Ship ship, double angle) throws ModelException
    {
        try {
            ship.turn(angle);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    public double getDistanceBetween(Ship ship1, Ship ship2) throws ModelException
    {
        try {
            return ship1.getDistanceBetween(ship2);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    public boolean overlap(Ship ship1, Ship ship2) throws ModelException
    {
        try {
            return ship1.overlap(ship2);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    public double getTimeToCollision(Ship ship1, Ship ship2) throws ModelException
    {
        try {
            return ship1.getTimeToCollision(ship2);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    public double[] getCollisionPosition(Ship ship1, Ship ship2) throws ModelException {
        try {
            Vector collisionPosition = ship1.getCollisionPosition(ship2);
            return collisionPosition == null ? null : new double[]{collisionPosition.getX(), collisionPosition.getY()};
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }
}
