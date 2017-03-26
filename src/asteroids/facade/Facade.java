package asteroids.facade;

import asteroids.model.entities.Bullet;
import asteroids.model.entities.Entity;
import asteroids.model.world.World;
import asteroids.part1.facade.IFacade;
import asteroids.part2.CollisionListener;
import asteroids.util.ModelException;
import asteroids.model.entities.Ship;
import vector.Vector;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Bo Kleynen and Yrjo Koyen.
 */
public class Facade implements asteroids.part2.facade.IFacade {
    public Facade() {

    }

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

    @Override
    public void terminateShip(Ship ship) throws ModelException {
        ship.terminate();
    }

    @Override
    public boolean isTerminatedShip(Ship ship) throws ModelException {
        return ship.isTerminated();
    }

    @Override
    public double getShipMass(Ship ship) throws ModelException {
        return ship.getMass();
    }

    @Override
    public World getShipWorld(Ship ship) throws ModelException {
        return ship.getWorld();
    }

    @Override
    public boolean isShipThrusterActive(Ship ship) throws ModelException {
        return ship.thrusterOn();
    }

    @Override
    public void setThrusterActive(Ship ship, boolean active) throws ModelException {
        if (active)
            ship.thrustOn();
        else
            ship.thrustOff();
    }

    @Override
    public double getShipAcceleration(Ship ship) throws ModelException {
        return 0;
    }

    public double[] getShipPosition(Ship ship) throws ModelException {
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

    @Override
    public Bullet createBullet(double x, double y, double xVelocity, double yVelocity, double radius) throws ModelException {
        try {
            return new Bullet(new Vector(x,y), new Vector(xVelocity, yVelocity), radius);

        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public void terminateBullet(Bullet bullet) throws ModelException {
        bullet.terminate();
    }

    @Override
    public boolean isTerminatedBullet(Bullet bullet) throws ModelException {
        return bullet.isTerminated();
    }

    @Override
    public double[] getBulletPosition(Bullet bullet) throws ModelException {
        try {
            return new double[]{bullet.getPosition().getX(), bullet.getPosition().getY()};
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public double[] getBulletVelocity(Bullet bullet) throws ModelException {
        try {
            return new double[]{bullet.getVelocity().getX(), bullet.getVelocity().getY()};
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public double getBulletRadius(Bullet bullet) throws ModelException {
        return bullet.getRadius();
    }

    @Override
    public double getBulletMass(Bullet bullet) throws ModelException {
        return bullet.getMass();
    }

    @Override
    public World getBulletWorld(Bullet bullet) throws ModelException {
        return bullet.getWorld();
    }

    @Override
    public Ship getBulletShip(Bullet bullet) throws ModelException {
        return null;
    }

    @Override
    public Ship getBulletSource(Bullet bullet) throws ModelException {
        return bullet.getParentShip();
    }

    @Override
    public World createWorld(double width, double height) throws ModelException {
        return new World(width, height);
    }

    @Override
    public void terminateWorld(World world) throws ModelException {
        world.terminate();
    }

    @Override
    public boolean isTerminatedWorld(World world) throws ModelException {
        return world.isTerminated();
    }

    @Override
    public double[] getWorldSize(World world) throws ModelException {
        try {
            return new double[]{world.getWidth(), world.getHeight()};
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public Set<? extends Ship> getWorldShips(World world) throws ModelException {
        return world.getAllShips();
    }

    @Override
    public Set<? extends Bullet> getWorldBullets(World world) throws ModelException {
        return world.getAllBullets();
    }

    @Override
    public void addShipToWorld(World world, Ship ship) throws ModelException {
        world.addEntity(ship);
    }

    @Override
    public void removeShipFromWorld(World world, Ship ship) throws ModelException {
        world.removeEntity(ship);
    }

    @Override
    public void addBulletToWorld(World world, Bullet bullet) throws ModelException {
        world.addEntity(bullet);
    }

    @Override
    public void removeBulletFromWorld(World world, Bullet bullet) throws ModelException {
        world.removeEntity(bullet);
    }

    @Override
    public Set<? extends Bullet> getBulletsOnShip(Ship ship) throws ModelException {
        return null;
    }

    @Override
    public int getNbBulletsOnShip(Ship ship) throws ModelException {
        return 0;
    }

    @Override
    public void loadBulletOnShip(Ship ship, Bullet bullet) throws ModelException {
        ship.reloadBullet(bullet);
    }

    @Override
    public void loadBulletsOnShip(Ship ship, Collection<Bullet> bullets) throws ModelException {

    }

    @Override
    public void removeBulletFromShip(Ship ship, Bullet bullet) throws ModelException {

    }

    @Override
    public void fireBullet(Ship ship) throws ModelException {

    }

    @Override
    public double getTimeCollisionBoundary(Object object) throws ModelException {
        return ((Entity) object).getTimeToWallCollision();
    }

    @Override
    public double[] getPositionCollisionBoundary(Object object) throws ModelException {
        return new double[0];
    }

    @Override
    public double getTimeCollisionEntity(Object entity1, Object entity2) throws ModelException {
        try {
            return ((Entity) entity1).getTimeToCollision(((Entity) entity2));
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public double[] getPositionCollisionEntity(Object entity1, Object entity2) throws ModelException {
        return new double[0];
    }

    @Override
    public double getTimeNextCollision(World world) throws ModelException {
        return 0;
    }

    @Override
    public double[] getPositionNextCollision(World world) throws ModelException {
        return new double[0];
    }

    @Override
    public void evolve(World world, double dt, CollisionListener collisionListener) throws ModelException {

    }

    @Override
    public Object getEntityAt(World world, double x, double y) throws ModelException {
        return world.getEntityAtPosition(new Vector(x,y));
    }

    @Override
    public Set<? extends Object> getEntities(World world) throws ModelException {
        return null;
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
