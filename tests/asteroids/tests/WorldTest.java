package asteroids.tests;
import asteroids.model.collisions.Collision;
import asteroids.model.collisions.EntityCollision;
import asteroids.model.world.World;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import asteroids.model.entities.*;
import vector.Vector;

/**
 * Created by Bo on 13/04/2017.
 */
public class WorldTest {

    private static final double EPSILON = 0.0001;

    @Test
    public void testCreateWorld() {
        World world = new World(666, 999);
        assertEquals(666,world.getWidth(), EPSILON);
        assertEquals(999, world.getHeight(), EPSILON);
        assertTrue(world.getAllEntities().isEmpty());
        assertTrue(world.getAllShips().isEmpty());
        assertTrue(world.getAllBullets().isEmpty());
    }

    // Ship(Vector position, Vector velocity, double orientation, double radius, double mass)

    @Test (expected = IllegalArgumentException.class)
    public void testAddEntity() {
        World world = new World(5000, 5000);

        Vector velocity = new Vector(0,0);
        Ship ship1 = new Ship(new Vector(1000,1000), velocity, 0, 15, 0);
        Ship ship2 = new Ship(new Vector(500,1000), velocity, 0, 15, 0);
        Ship ship3 = new Ship(new Vector(500,500), velocity, 0, 15, 0);
        Ship ship4 = new Ship(new Vector(1000,500), velocity, 0, 15, 0);
        Ship ship5 = new Ship(new Vector(510,500), velocity, 0, 15, 0);
        Ship[] ships = {ship1, ship2, ship3, ship4};

        for (Ship ship : ships) {
            world.addEntity(ship);
            assertTrue(world.getAllShips().contains(ship));
            assertTrue(world.getAllEntities().contains(ship));
        }
        assertEquals(ships.length, world.getAllShips().size(), EPSILON);
        assertEquals(ships.length, world.getAllEntities().size(), EPSILON);
        assertEquals(0, world.getAllBullets().size(), EPSILON);

        world.addEntity(ship5);
        assertFalse(world.getAllShips().contains(ship5));

        world.removeEntity(ship3);
        assertFalse(world.getAllEntities().contains(ship3));
    }

    @Test
    public void testUpdateEntityPosition() {
        World world = new World(1000, 1000);

        Vector shipPos = new Vector(100,100);
        Vector bulletPos = new Vector(300, 300);

        Ship ship = new Ship(shipPos, new Vector(10,0), 0, 15,0);
        Bullet bullet = new Bullet(bulletPos, new Vector(0,-5), 5);

        world.addEntity(ship);
        world.addEntity(bullet);

        assertTrue(world.getAllEntities().contains(ship));
        assertTrue(world.getAllEntities().contains(bullet));

        Vector newShipPos = new Vector(666,666);
        Vector newBulletPos = new Vector(22, 33);

        world.updateEntityPosition(ship, newShipPos);
        world.updateEntityPosition(bullet, newBulletPos);

        assertTrue(world.getAllEntities().contains(ship));
        assertTrue(world.getAllEntities().contains(bullet));
        assertEquals(newShipPos, ship.getPosition());
        assertEquals(newBulletPos, bullet.getPosition());
        assertEquals(ship, world.getEntityAtPosition(newShipPos));
        assertEquals(bullet, world.getEntityAtPosition(newBulletPos));

    }

    @Test
    public void getFirstCollisionTest_noCollision() {
        World world = new World(1000, 1000);

        Vector pos1 = new Vector(900, 100);
        Vector pos2 = new Vector(500,100);

        Vector velocity = new Vector(0,0);

        Ship ship1 = new Ship(pos1, velocity, Math.PI,20,0);
        Ship ship2 = new Ship(pos2, velocity,0,10,0);

        world.addEntity(ship1);
        world.addEntity(ship2);

        Collision firstCollision = world.getFirstCollision();
        assertEquals(Double.POSITIVE_INFINITY, firstCollision.getTimeToCollision(), EPSILON);

    }

    @Test
    public void getFirstCollisionTest_collidingShips() {
        World world = new World(1000, 1000);

        Vector pos1 = new Vector(900, 100);
        Vector pos2 = new Vector(500,100);

        Vector velocity1 = new Vector(-10,0);
        Vector velocity2 = new Vector(15,0);


        Ship ship1 = new Ship(pos1, velocity1, Math.PI,20,0);
        Ship ship2 = new Ship(pos2, velocity2,0,10,0);
        Ship ship3 = new Ship(new Vector(300,300), new Vector(0,0), 0,10,0);

        Ship[] ships = {ship1, ship2, ship3};
        for (Ship ship : ships) {
            world.addEntity(ship);
        }

        EntityCollision firstCollision = (EntityCollision) world.getFirstCollision();
        assertTrue((firstCollision.getEntity1() == ship1 && firstCollision.getEntity2() == ship2) ||
                (firstCollision.getEntity1() == ship2 && firstCollision.getEntity2() == ship1));
        assertEquals(ship1.getTimeToCollision(ship2), firstCollision.getTimeToCollision(), EPSILON);
        assertEquals(ship2.getCollisionPosition(ship1), firstCollision.getCollisionPosition());
    }

    @Test
    public void evolveTest() {
        // TODO
    }

    @Test (expected = IllegalStateException.class)
    public void destroyTest() {
        World world = new World(5000, 5000);

        Vector velocity = new Vector(0,0);
        Ship ship1 = new Ship(new Vector(1000,1000), velocity, 0, 15, 0);
        Ship ship2 = new Ship(new Vector(500,1000), velocity, 0, 15, 0);
        Ship ship3 = new Ship(new Vector(500,500), velocity, 0, 15, 0);
        Ship ship4 = new Ship(new Vector(1000,500), velocity, 0, 15, 0);
        Ship ship5 = new Ship(new Vector(510,500), velocity, 0, 15, 0);
        Ship[] ships = {ship1, ship2, ship3, ship4};

        for (Ship ship : ships) {
            world.addEntity(ship);
        }

        world.destroy();

        for (Ship ship : ships) {
            assertTrue(ship.getWorld() == null);
        }
        assertTrue(world.isTerminated());
        assertTrue(world.getAllEntities().isEmpty());

        world.addEntity(ship5);
        world.addEntity(ship1);
        assertTrue(world.getAllEntities().isEmpty());
    }
}
