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
       return null;
    }

    public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius, double orientation)
            throws ModelException
    {
        return new Ship( new Vector(x, y), new Vector(xVelocity, yVelocity), orientation, radius);
    }

    public double[] getShipPosition(Ship ship) throws ModelException
    {
        double returnPosition[] = new double[2];
        Vector position = ship.getPosition();
        returnPosition[0] = position.getX();
        returnPosition[1] = position.getY();
        return returnPosition;
    }

    public double[] getShipVelocity(Ship ship) throws ModelException
    {
    	double returnVelocity[] = new double[2];
        Vector speed = ship.getVelocity();
        returnVelocity[0] = speed.getX();
        returnVelocity[1] = speed.getY();
        return returnVelocity;
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

    public double[] getCollisionPosition(Ship ship1, Ship ship2) throws ModelException
    {
        double returnPosition[] = new double[2];
        Vector collisionPosition = ship1.getCollisionPosition(ship2);
        returnPosition[0] = collisionPosition.getX();
        returnPosition[1] = collisionPosition.getY();
        return returnPosition;
    }

}
