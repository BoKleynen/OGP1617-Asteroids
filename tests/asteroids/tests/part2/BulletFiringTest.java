package asteroids.tests.part2;

import static org.junit.Assert.*;

import asteroids.model.Bullet;
import asteroids.model.Ship;
import asteroids.model.World;
import org.junit.*;

import vector.*;

public class BulletFiringTest {
	
	Ship ship;
	World world;
	
	Double eps = 0.0001;
	
	@Before
	public void setupMutableTestFixtures() {
		ship = new Ship();
		world = new World(100, 100);
	}

	
	@Test
	public void testFireBullet_LegalCase() {
		assertEquals(ship.getPosition(), new Vector(50, 50));
		assertEquals(ship.getOrientation(), 0.0, eps);
		
		world.addEntity(ship);
		assertTrue(world.getAllEntities().contains(ship));
		assertEquals(world.getEntityAtPosition(ship.getPosition()), ship);
		
		ship.fireBullet();
		ship.turn(Math.PI/3);
		ship.fireBullet();
		ship.turn(Math.PI/5);
		ship.fireBullet();
		
		for (Bullet bullet : world.getAllBullets()) {
			// The velocity of a bullet is always equal to 250
			assertEquals(bullet.getVelocity().getMagnitude(), 250, eps);
			// The distance between the ship and the bullet is smaller then (radius1 + radius2)/2
			assertTrue(bullet.getPosition().getDistance(ship.getPosition())-ship.getRadius()-bullet.getRadius()
					<= (ship.getRadius() + bullet.getRadius())/2);
			// The ship that fired the bullet is the parent ship, even after firing
			assertTrue(bullet.getParentShip().equals(ship));
		}
		
		
	}

}
