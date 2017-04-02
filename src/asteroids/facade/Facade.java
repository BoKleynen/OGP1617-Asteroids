package asteroids.facade;

import asteroids.model.entities.Bullet;
import asteroids.model.entities.Entity;
import asteroids.model.world.World;
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

    public Ship createShip() throws ModelException {
        try {
            return new Ship();

        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius, double orientation)
            throws ModelException {
        try {
            return new Ship( new Vector(x, y), new Vector(xVelocity, yVelocity), orientation, radius, 0);

        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    public Ship createShip(double x, double y, double xVelocity, double yVelocity, double radius, double direction, double mass) throws ModelException {
        try {
            return new Ship(new Vector(x, y), new Vector(xVelocity, yVelocity), direction, radius, mass);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public void terminateShip(Ship ship) throws ModelException {
        try {
            ship.terminate();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public boolean isTerminatedShip(Ship ship) throws ModelException {
        try {
            return ship.isTerminated();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public double getShipMass(Ship ship) throws ModelException {
        try {
            return ship.getTotalMass();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public World getShipWorld(Ship ship) throws ModelException {
        try {
            return ship.getWorld();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public boolean isShipThrusterActive(Ship ship) throws ModelException {
        try {
            return ship.thrusterOn();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public void setThrusterActive(Ship ship, boolean active) throws ModelException {
        try {
            if (active)
                ship.thrustOn();
            else
                ship.thrustOff();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public double getShipAcceleration(Ship ship) throws ModelException {
        try {
            return ship.getAcceleration();
        } catch (Exception e) {
            throw new ModelException(e);
        }
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
        try {
            bullet.terminate();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public boolean isTerminatedBullet(Bullet bullet) throws ModelException {
        try {
            return bullet.isTerminated();
        } catch (Exception e) {
            throw new ModelException(e);
        }
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
        try {
            return bullet.getRadius();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public double getBulletMass(Bullet bullet) throws ModelException {
        try {
            return bullet.getMass();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public World getBulletWorld(Bullet bullet) throws ModelException {
        try {
            return bullet.getWorld();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public Ship getBulletShip(Bullet bullet) throws ModelException {
        try {
            return bullet.getShip();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public Ship getBulletSource(Bullet bullet) throws ModelException {
        try {
            return bullet.getParentShip();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public World createWorld(double width, double height) throws ModelException {
        try {
            return new World(width, height);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public void terminateWorld(World world) throws ModelException {
        try {
            world.terminate();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public boolean isTerminatedWorld(World world) throws ModelException {
        try {
            return world.isTerminated();
        } catch (Exception e) {
            throw new ModelException(e);
        }
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
        try {
            return world.getAllShips();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public Set<? extends Bullet> getWorldBullets(World world) throws ModelException {
        try {
            return world.getAllBullets();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public void addShipToWorld(World world, Ship ship) throws ModelException {
        try {
            world.addEntity(ship);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public void removeShipFromWorld(World world, Ship ship) throws ModelException {
        try {
            world.removeEntity(ship);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public void addBulletToWorld(World world, Bullet bullet) throws ModelException {
        try {
            world.addEntity(bullet);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public void removeBulletFromWorld(World world, Bullet bullet) throws ModelException {
        try {
            world.removeEntity(bullet);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public Set<? extends Bullet> getBulletsOnShip(Ship ship) throws ModelException {
        try {
            return ship.getAllBullets();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public int getNbBulletsOnShip(Ship ship) throws ModelException {
        try {
            return ship.getNbBullets();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public void loadBulletOnShip(Ship ship, Bullet bullet) throws ModelException {
        try {
            ship.addBullet(bullet);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public void loadBulletsOnShip(Ship ship, Collection<Bullet> bullets) throws ModelException {
        try {
            ship.loadBullets(bullets);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public void removeBulletFromShip(Ship ship, Bullet bullet) throws ModelException {
        try {
            ship.removeBullet(bullet);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public void fireBullet(Ship ship) throws ModelException {
        try {
            ship.fireBullet();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public double getTimeCollisionBoundary(Object object) throws ModelException {
        try {
            return ((Entity) object).getTimeToWallCollision();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public double[] getPositionCollisionBoundary(Object object) throws ModelException {
        try {
            Vector collisionPosition = ((Entity) object).getWallCollisionPosition();
            return new double[]{collisionPosition.getX(), collisionPosition.getY()};
        } catch (Exception e) {
            throw new ModelException(e);
        }
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
        try {
            Vector collisionPosition = ((Entity) entity1).getCollisionPosition((Entity) entity2);
            return collisionPosition == null ? null : new double[]{collisionPosition.getX(), collisionPosition.getY()};
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public double getTimeNextCollision(World world) throws ModelException {
        try {
            return world.getFirstCollision().getTimeToCollision();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public double[] getPositionNextCollision(World world) throws ModelException {
        try {
            Vector collisionPosition = world.getFirstCollision().getCollisionPosition();
            return collisionPosition == null ? null : new double[]{collisionPosition.getX(), collisionPosition.getY()};
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public void evolve(World world, double dt, CollisionListener collisionListener) throws ModelException {
        try {
            world.evolve(dt, collisionListener);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public Object getEntityAt(World world, double x, double y) throws ModelException {
        try {
            return world.getEntityAtPosition(new Vector(x,y));
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    @Override
    public Set<? extends Object> getEntities(World world) throws ModelException {
        try {
            return world.getAllEntities();
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
