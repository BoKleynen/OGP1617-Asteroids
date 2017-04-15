
import asteroids.model.world.*;
import vector.Vector;
import asteroids.model.entities.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asteroids.model.entities.Bullet;
import asteroids.model.entities.Ship;

public class BulletTests {
	
	private Bullet b1, b2;
	static Bullet fiveWallHitBullet;
	private World world;
	private Ship ship;
	
	@BeforeClass
	public static void setupImmutableTestfixtures() {
		fiveWallHitBullet = new Bullet(new Vector(100, 100), new Vector(0, 0), 2, (char) 5);
	}
	
	@Before
	public void setupMutableTestFixtures() {
		world = new World(5000, 5000);
		ship = new Ship();
		world.addEntity(ship);
		b1 = new Bullet(new Vector(100, 100), new Vector(0, 0), 5);
		b2 = new Bullet(new Vector(200, 50), new Vector(10, 0), 10);
	}
	
	@Test
	public void testGetParentShip() {
		ship.addBullet(b1);
		assertTrue(b1.getParentShip().equals(ship));
		
		ship.removeBullet(b1);
		assertTrue(b1.getParentShip().equals(ship));
		ship.loadBullet(b1);
	}
	
	@Test
	public void testGetShip() {
		assertTrue(b1.getShip() == null);
		ship.addBullet(b2);
		assertTrue(b2.getShip().equals(ship));
	}
	
	@Test
	public void testGetWallHits() {
		assertTrue(b1.getWallHits() == 0);
		b1.incrementWallHits();
		assertTrue(b1.getWallHits() == 1);
	}
	
	@Test
	public void testGetMaxWallHits() {
		assertTrue(b1.getMaxWallHits() == 2);
		assertTrue(fiveWallHitBullet.getMaxWallHits() == 5);
	}
	
	@Test
	public void testTerminate() {
		world.addEntity(b1);
		ship.addBullet(b2);
		assertTrue(b2.getShip().equals(ship));
		assertTrue(b1.getWorld().equals(world));

		b1.terminate();
		b2.terminate();
		assertTrue(b1.isTerminated() && b2.isTerminated());
		
		assertTrue(b1.getShip() == null && b2.getShip() == null);
		assertTrue(b1.getWorld() == null && b2.getWorld() == null);
		assertTrue(b1.getParentShip() == null && b2.getParentShip() == null);
	}
	
	

}
