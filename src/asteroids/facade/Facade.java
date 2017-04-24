package asteroids.facade;

import asteroids.model.*;
import asteroids.part2.CollisionListener;
import asteroids.part3.programs.IProgramFactory;
import asteroids.util.ModelException;
import vector.Vector;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Bo Kleynen and Yrjo Koyen.
 */
public class Facade implements asteroids.part3.facade.IFacade {
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
            ship.loadBullet(bullets);
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

    /**
     * Return the number of students in your team (used to adapt the tests for
     * single-student groups).
     *
     * @return 1 or 2
     */
    @Override
    public int getNbStudentsInTeam() {
        return 2;
    }

    /**
     * Return all asteroids located in <code>world</code>.
     *
     * @param world
     */
    @Override
    public Set<? extends Asteroid> getWorldAsteroids(World world) throws ModelException {
        return null;
    }

    /**
     * Add <code>asteroid</code> to <code>world</code>.
     *
     * @param world
     * @param asteroid
     */
    @Override
    public void addAsteroidToWorld(World world, Asteroid asteroid) throws ModelException {
        try {
           world.addEntity(asteroid);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Remove <code>asteroid</code> from <code>world</code>.
     *
     * @param world
     * @param asteroid
     */
    @Override
    public void removeAsteroidFromWorld(World world, Asteroid asteroid) throws ModelException {
        try {
            world.removeEntity(asteroid);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Return all planetoids located in <code>world</code>.
     *
     * @param world
     */
    @Override
    public Set<? extends Planetoid> getWorldPlanetoids(World world) throws ModelException {
        return null;
    }

    /**
     * Add <code>planetoid</code> to <code>world</code>.
     *
     * @param world
     * @param planetoid
     */
    @Override
    public void addPlanetoidToWorld(World world, Planetoid planetoid) throws ModelException {
        try {
            world.addEntity(planetoid);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Remove <code>planetoid</code> from <code>world</code>.
     *
     * @param world
     * @param planetoid
     */
    @Override
    public void removePlanetoidFromWorld(World world, Planetoid planetoid) throws ModelException {
        try {
            world.addEntity(planetoid);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Create a new non-null asteroid with the given position, velocity and
     * radius.
     * <p>
     * The asteroid is not located in a world.
     *
     * @param x
     * @param y
     * @param xVelocity
     * @param yVelocity
     * @param radius
     */
    @Override
    public Asteroid createAsteroid(double x, double y, double xVelocity, double yVelocity, double radius) throws ModelException {
        try {
            return new Asteroid(
                    new Vector(x,y),
                    new Vector(xVelocity, yVelocity),
                    radius);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Terminate <code>asteroid</code>.
     *
     * @param asteroid
     */
    @Override
    public void terminateAsteroid(Asteroid asteroid) throws ModelException {
        try {
           asteroid.terminate();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Check whether <code>asteroid</code> is terminated.
     *
     * @param asteroid
     */
    @Override
    public boolean isTerminatedAsteroid(Asteroid asteroid) throws ModelException {
        try {
            return asteroid.isTerminated();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Return the position of <code>asteroid</code> as an array containing the
     * x-coordinate, followed by the y-coordinate.
     *
     * @param asteroid
     */
    @Override
    public double[] getAsteroidPosition(Asteroid asteroid) throws ModelException {
        try {
            return new double[] {asteroid.getPosition().getX(), asteroid.getPosition().getY()};
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Return the velocity of <code>asteroid</code> as an array containing the
     * velocity along the X-axis, followed by the velocity along the Y-axis.
     *
     * @param asteroid
     */
    @Override
    public double[] getAsteroidVelocity(Asteroid asteroid) throws ModelException {
        try {
            return new double[] {asteroid.getVelocity().getX(), asteroid.getVelocity().getY()};
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Return the radius of <code>asteroid</code>.
     *
     * @param asteroid
     */
    @Override
    public double getAsteroidRadius(Asteroid asteroid) throws ModelException {
        try {
            return asteroid.getRadius();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Return the mass of <code>asteroid</code>.
     *
     * @param asteroid
     */
    @Override
    public double getAsteroidMass(Asteroid asteroid) throws ModelException {
        try {
            return asteroid.getMass();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Return the world in which <code>asteroid</code> is positioned.
     *
     * @param asteroid
     */
    @Override
    public World getAsteroidWorld(Asteroid asteroid) throws ModelException {
        try {
            return asteroid.getWorld();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Create a new non-null planetoid with the given position, velocity,
     * radius, and total traveled distance.
     * <p>
     * The planetoid is not located in a world.
     *
     * @param x
     * @param y
     * @param xVelocity
     * @param yVelocity
     * @param radius
     * @param totalTraveledDistance
     */
    @Override
    public Planetoid createPlanetoid(double x, double y, double xVelocity, double yVelocity, double radius, double totalTraveledDistance) throws ModelException {
        try {
            return new Planetoid(
                    new Vector(x,y),
                    new Vector(xVelocity, yVelocity),
                    radius,
                    totalTraveledDistance);
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Terminate <code>planetoid</code>.
     *
     * @param planetoid
     */
    @Override
    public void terminatePlanetoid(Planetoid planetoid) throws ModelException {
        try {
            planetoid.terminate();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Check whether <code>planetoid</code> is terminated.
     *
     * @param planetoid
     */
    @Override
    public boolean isTerminatedPlanetoid(Planetoid planetoid) throws ModelException {
        try {
            return planetoid.isTerminated();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Return the position of <code>planetoid</code> as an array containing the
     * x-coordinate, followed by the y-coordinate.
     *
     * @param planetoid
     */
    @Override
    public double[] getPlanetoidPosition(Planetoid planetoid) throws ModelException {
        try {
            return new double[] {planetoid.getPosition().getX(), planetoid.getPosition().getY()};
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Return the velocity of <code>planetoid</code> as an array containing the
     * velocity along the X-axis, followed by the velocity along the Y-axis.
     *
     * @param planetoid
     */
    @Override
    public double[] getPlanetoidVelocity(Planetoid planetoid) throws ModelException {
        try {
            return new double[] {planetoid.getVelocity().getX(), planetoid.getVelocity().getY()};
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Return the radius of <code>planetoid</code>.
     *
     * @param planetoid
     */
    @Override
    public double getPlanetoidRadius(Planetoid planetoid) throws ModelException {
        try {
            return planetoid.getRadius();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Return the mass of <code>planetoid</code>.
     *
     * @param planetoid
     */
    @Override
    public double getPlanetoidMass(Planetoid planetoid) throws ModelException {
        try {
            return planetoid.getMass();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Return the total traveled distance of <code>planetoid</code>.
     *
     * @param planetoid
     */
    @Override
    public double getPlanetoidTotalTraveledDistance(Planetoid planetoid) throws ModelException {
        return planetoid.getTotalTraveledDistance();
    }

    /**
     * Return the world in which <code>planetoid</code> is positioned.
     *
     * @param planetoid
     */
    @Override
    public World getPlanetoidWorld(Planetoid planetoid) throws ModelException {
        try {
            return planetoid.getWorld();
        } catch (Exception e) {
            throw new ModelException(e);
        }
    }

    /**
     * Return the program loaded on the given ship.
     *
     * @param ship
     */
    @Override
    public Program getShipProgram(Ship ship) throws ModelException {
        return ship.getProgram();
    }

    /**
     * Load the given program on the given ship.
     *
     * @param ship
     * @param program
     */
    @Override
    public void loadProgramOnShip(Ship ship, Program program) throws ModelException {
        ship.loadProgram(program);
    }

    /**
     * Execute the program loaded on the given ship during the given period of
     * time. The ship is positioned in some world. Returns null if the program
     * is not completely executed. Otherwise, returns the objects that have been
     * printed.
     *
     * @param ship
     * @param dt
     */
    @Override
    public List<Object> executeProgram(Ship ship, double dt) throws ModelException {
        return null;
    }

    /**
     * Creates a new program factory.
     */
    @Override
    public IProgramFactory<?, ?, ?, ? extends Program> createProgramFactory() throws ModelException {
        return null;
    }
}
