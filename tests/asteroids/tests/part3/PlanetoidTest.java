package asteroids.tests.part3;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Asteroid;
import asteroids.model.Planetoid;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.util.vector.Vector;

public class PlanetoidTest {
	
	private Planetoid movingPlanetoid;
	private Planetoid collisionPlanetoid;
	private Ship collisionShip;
	private World world;
	
	@Before
	public void setupFixtures() {
		world = new World(1000, 1000);
		collisionPlanetoid = new Planetoid(new Vector(100, 100), new Vector(25, 0), 20, 0);
		movingPlanetoid = new Planetoid(new Vector(500, 500), new Vector(10, 0), 60, 0);
		collisionShip = new Ship();
	}
	
	@Test
	public void testCollisionWithShip() {
		world.addEntity(collisionPlanetoid);
		world.addEntity(collisionShip);
		assertTrue(world.getAllEntities().size() == 2);
		
		Vector oldPosition = collisionShip.getPosition();
		collisionPlanetoid.resolveCollisionWithShip(collisionShip);
		// After collision the ship is either dead or has moved to a random position.
		assertTrue(collisionShip.isTerminated() || (! collisionShip.getPosition().equals(oldPosition)));
	}
	
	@Test
	public void testDie_notInWorld() {
		collisionPlanetoid.die();
		assertTrue(collisionPlanetoid.isTerminated());
	}
	
	@Test
	public void testDie_splittingCase() {
		world.addEntity(collisionPlanetoid);
		double initialRadius = 50.0;
		collisionPlanetoid.setPlanetoidRadius(initialRadius);
		assertTrue(collisionPlanetoid.getRadius() >= Planetoid.getMinSplitRadius());	
		assertTrue(world.getAllEntities().size() == 1);
		
		// If the planetoid dies with a large radius, it is split into two asteroids.
		collisionPlanetoid.die();
		assertTrue(world.getAllAsteroids().size() == 2);
		
		for (Asteroid A : world.getAllAsteroids()) {
			assertEquals(A.getRadius(), initialRadius/2, 0.0001);
			A.die();
		}
		assertTrue(world.getAllAsteroids().size() == 0);
	}
	
	@Test
	public void testDie_terminateCase() {
		world.addEntity(collisionPlanetoid);
		collisionPlanetoid.setPlanetoidRadius(20);
		assertTrue(collisionPlanetoid.getRadius() < Planetoid.getMinSplitRadius());	
		assertTrue(world.getAllEntities().size() == 1);
		
		// If the planetoid dies with a small radius, it is terminated.
		collisionPlanetoid.die();
		assertTrue(world.getAllEntities().size() == 0);
	}
	
	@Test
	public void testAsteroidSizeDecay() {
		world.addEntity(movingPlanetoid);
		double initialRadius = movingPlanetoid.getRadius();
		movingPlanetoid.move(10);
		assertTrue(initialRadius > movingPlanetoid.getRadius());
		assertEquals(movingPlanetoid.getTotalTraveledDistance(), 100, 0.001);
		assertEquals(movingPlanetoid.getRadius(), 
				initialRadius - movingPlanetoid.getTotalTraveledDistance()*1e-6, 0.001);
	}
	
	@Test
	public void testAsteroidDyingByShrinking() {
		movingPlanetoid.setPlanetoidRadius(6);
		// Radius should shrink to 5km after 1e5 seconds.
		movingPlanetoid.move(1e5+1);
		assertTrue(movingPlanetoid.isTerminated());
	}

}
