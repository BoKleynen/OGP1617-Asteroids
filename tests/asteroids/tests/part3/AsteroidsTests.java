package asteroids.tests.part3;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Asteroid;
import asteroids.model.MinorPlanet;
import asteroids.model.Planetoid;
import asteroids.model.Ship;
import asteroids.model.World;
import asteroids.model.util.vector.Vector;

public class AsteroidsTests {
	
	private Ship ship;
	private World world;
	private Asteroid asteroid;
	private MinorPlanet mp;
	
	@Before
	public void setupFixtures() {
		ship = new Ship();
		world = new World(1000, 1000);
		asteroid = new Asteroid(new Vector(100, 100), new Vector(-5, -5), 50);
		mp = new Planetoid(new Vector(300, 300), new Vector(25, 0), 35, 0);
	}
	
	@Test
	public void testCollisionWithShip() {
		world.addEntity(asteroid);
		world.addEntity(ship);
		
		assertTrue(world.getAllEntities().size() == 2);
		asteroid.resolveCollisionWithShip(ship);
		assertTrue(world.getAllAsteroids().size() == 1);
		assertTrue(world.getAllShips().size() == 0);
		assertTrue(ship.isTerminated());
	}
	
	@Test
	public void testMinorPlanetCollision() {
		world.addEntity(asteroid);
		world.addEntity(mp);
		Vector firstOldVel = asteroid.getVelocity();
		Vector secondOldVel = mp.getVelocity();
		
		assertTrue(world.getAllMinorPlanets().size() == 2);
		asteroid.resolveCollisionWithMinorPlanet(mp);
		// After two minor planets collide, they both change velocities.
		assertFalse(asteroid.getVelocity().equals(firstOldVel));
		assertFalse(mp.getVelocity().equals(secondOldVel));
		// Both minor planets stay in the world and are still alive and not terminated.
		assertTrue(world.getAllMinorPlanets().size() == 2);
		assertFalse(asteroid.isTerminated());
		assertFalse(mp.isTerminated());
	}
	
	@Test
	public void testTerminate() {
		world.addEntity(asteroid);
		asteroid.terminate();
		assertFalse(world.getAllMinorPlanets().contains(asteroid));
		assertFalse(asteroid.hasWorld());
		assertTrue(asteroid.isTerminated());
	}

}
