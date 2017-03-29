
import static org.junit.Assert.*;

import org.junit.*;

import vector.*;
import asteroids.model.entities.*;
import asteroids.model.world.*;

public class Part2TestBulletFiring {
	
	Ship ship;
	World world; 
	
	Double eps = 0.0001;
	
	@Before
	public void setupMutableTestFixtures() {
		ship = new Ship();
		world = new World(100, 100);
	}

	
	@Test
	public void testFireBullet() {
		assertEquals(ship.getPosition(), new Vector(0,0));
		assertEquals(ship.getOrientation(), 0.0, eps);
		
		world.addEntity(ship);
		assertTrue(world.getAllEntities().contains(ship));
		assertEquals(world.getEntityAtPosition(ship.getPosition()), ship);
		
		ship.fireBullet();
		for (Bullet bullet : world.getAllBullets()) {
			assertEquals(bullet.getVelocity().getMagnitude(), 250, eps);
			assertTrue(bullet.getPosition().getDistance(ship.getPosition())-ship.getRadius()-bullet.getRadius()
					<= (ship.getRadius() + bullet.getRadius())/2);
		}
		
		
	}

}
