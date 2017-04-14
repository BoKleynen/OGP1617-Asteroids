package asteroids.tests;
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
}
