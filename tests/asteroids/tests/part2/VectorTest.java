package asteroids.tests.part2;

import vector.*;
import asteroids.model.entities.*;
import org.junit.*;
import static org.junit.Assert.*;
import asteroids.model.world.*;

public class VectorTest {

	private static Vector v1, v2, v3;
	
	private Ship ship;
	private World world;
	
	@BeforeClass
	public static void setupImmutableFixtures() {
		
		// For the equality tests
		v1 = new Vector(30.1, 2568.145);
		v2 = new Vector(30.1, 2568.145);
		v3 = new Vector(2.0, 2569);
	}
	
	@Before
	public void setupMutableFixtures() {
		world = new World(1000, 1000);
		ship = new Ship(new Vector(10, 10), new Vector(10, 0), 0, 10, 100);

	}
	
	@Test
	public void testVectorEquality() {
		assertTrue(v1.equals(v2));
		assertTrue(v1.equals(v1));
		assertFalse(v1.equals(v3));
		assertFalse(v1.equals(null));
		assertFalse(v1.equals(new Ship()));
	}
	
	@Test
	public void testHashCode() {
		int hash1 = v1.hashCode();
		int hash2 = v2.hashCode();
		assertTrue(v1.equals(v2));
		assertTrue(hash1 == hash2);
	}
	
	@Test
	public void testMovingShipInWorld() {
		
		Vector oldPosition = new Vector(10, 10);
		Vector newPosition = new Vector(20, 10);
		
		// Ship is at the correct position
		assertTrue(ship.getPosition().equals(oldPosition));
		
		// Ship is in the world.
		world.addEntity(ship);
		assertTrue(world.getAllEntities().contains(ship));
		assertTrue(ship.getWorld() == world);
		
		// The ship has moved
		ship.move(1);
		assertTrue(ship.getPosition().equals(newPosition));
		
		// Ship is still in world
		assertTrue(world.getAllEntities().contains(ship));
		
//		// Is the ship still at the old position?
//		assertFalse(world.getEntityAtPosition(oldPosition).equals(ship));

		// Get the entity at the new position of the ship? Fails!
		assertTrue(world.getEntityAtPosition(ship.getPosition()).equals(ship));
		
		
		
		
	}
}
