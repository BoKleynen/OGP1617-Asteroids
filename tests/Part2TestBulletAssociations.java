
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import asteroids.model.entities.*;
import asteroids.model.world.*;
import asteroids.util.ModelException;
import vector.*;

public class Part2TestBulletAssociations {
	
	private Ship s1, s2;
	private World w1, w2;
	
	@Before
	public void setupMutableTestFixtures() {
		s1 = new Ship();
		s2 = new Ship();
		w1 = new World(100, 200);
		w2 = new World(1000, 1000);
	}
	
	@Test
	public void testAddBulletToShip_LegalCase() {
		Bullet b = new Bullet(new Vector(0, 0), new Vector(0, 0), 2);
		
		assertTrue(s1.getNbBullets() == 15);
		s1.addBullet(b);
		
		assertTrue(s1.getNbBullets() == 16);
		assertTrue(s1.getAllBullets().contains(b));
		assertTrue(b.getShip() == s1);
		
		s1.removeBullet(b);
		assertTrue(s1.getNbBullets() == 15);
		assertFalse(s1.getAllBullets().contains(b));	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddBulletToShip_AddOneBulletTwice() {
		Bullet b = new Bullet(new Vector(0, 0), new Vector(0, 0), 2);
		
		// Adding a bullet multiple times to one ship.
		s1.addBullet(b);
		s1.addBullet(b);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddBulletToShip_AddBulletToTwoShips() {
		Bullet b = new Bullet(new Vector(0, 0), new Vector(0, 0), 2);
		
		// Adding one bullet to multiple ships.
		s1.addBullet(b);
		s2.addBullet(b);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddBulletToShip_addBulletToWorlAndShip() {
		Bullet b = new Bullet(new Vector(0, 0), new Vector(0, 0), 2);
		
		// Adding one bullet to a world and a ship.
		w1.addEntity(b);
		s1.addBullet(b);
	}
	
	@Test
	public void testBulletPositionInShip() {
		Bullet b = new Bullet(new Vector(0, 0), new Vector(0, 0), 2);
		
		s1.addBullet(b);
		assertTrue(b.getPosition().getDistance(s1.getPosition()) == s1.getRadius()/2);
	}
	
}
