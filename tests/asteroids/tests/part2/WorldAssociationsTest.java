package asteroids.tests.part2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.entities.Ship;
import asteroids.model.world.World;
import vector.Vector;

public class WorldAssociationsTest {
	
	World world;
	Ship s1, s2, s3;
	
	@Before
	public void setupMutableTestFixtures() {
		world = new World(1000, 1000);
		s1 = new Ship(new Vector(100, 100), new Vector(10, 0), 0, 10, 100);
		s2 = new Ship(new Vector(200, 200), new Vector(0, 10), 0, 20, 200);
		s3 = new Ship(new Vector(500, 900), new Vector(-50, -50), 0, 10, 200);
	}
	
	@Test
	public void testAddEntity_LegalCase() {
		world.addEntity(s1);
		world.addEntity(s2);
		world.addEntity(s3);

		assertTrue(world.getAllEntities().contains(s1));
		assertTrue(world.getAllEntities().contains(s2));
		assertTrue(world.getAllEntities().contains(s3));

	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddEntity_DuplicateShip() {
		world.addEntity(s1);
		world.addEntity(s1);
	}
	
	@Test
	public void testRemoveEntity_LegalCase() {
		world.addEntity(s1);
		world.addEntity(s2);
		world.addEntity(s3);
		
		assertTrue(world.getAllEntities().contains(s1));
		
		world.removeEntity(s1);
		
		assertFalse(world.getAllEntities().contains(s1));
		assertTrue(world.getAllEntities().contains(s2));
		assertTrue(world.getAllEntities().contains(s3));
		
		world.removeEntity(s2);
		world.removeEntity(s3);
		assertEquals(world.getAllEntities().size(), 0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testRemoveEntity_IllegalCase() {
		world.addEntity(s1);
		world.removeEntity(s2);
	}
}
