package asteroids.tests.part2;

import asteroids.model.World;
import asteroids.model.Ship;
import asteroids.model.util.vector.Vector;
import org.junit.*;


import static org.junit.Assert.*;


/**
 * A class containing tests for the class asteroids.model.Ship
 * 
 * For tests for the loading and adding of bullets, see the BulletAssociations test file.
 * For tests for the firing of bullets, see the BulletFiring test file.
 *
 * @author Bo Kleynen and Yrjo Koyen
 *
 *
 */
public class ShipTests {

	
    private static Ship defaultShip;
    private static Ship massTestShip;

    private World world;
    private Ship mutableTestShip1;
    private Ship collisionShip1;
    private Ship collisionShip2;
    private Ship thrustShip;

	@BeforeClass
	public static void setupImmutableTestFixtures() {
		defaultShip = new Ship();
		massTestShip = new Ship(new Vector(100, 100), 1000, new Vector(0, 0), 0, 10, 5e20, 0);
	}

	@Before
	public void setupMutableTestFictures() {
		world = new World(1000, 1000);
		mutableTestShip1 = new Ship(new Vector(100, 100), new Vector(0, 0) ,0 , 10, 300000);
		collisionShip1 = new Ship(new Vector(-100, -100), new Vector(10, 10), 0, 20, 300000);
		collisionShip2 = new Ship(new Vector(100, 100), new Vector(-10, -10), 0, 20, 300000);
		thrustShip = new Ship(new Vector(100, 100), 10000, new Vector(0, 0), 0, 10, 5e20, 10);
	}

    @Test
    public void testGetPosition()
    {
        assertTrue(defaultShip.getPosition().equals(new Vector(50, 50)));
        assertTrue(mutableTestShip1.getPosition().equals(new Vector(100, 100)));
    }
    
    @Test
    public void testGetTotalMass() {
    	assertEquals(massTestShip.getTotalMass(), 5e20, 0.001);
    	massTestShip.loadBullet(5);
    	assertTrue(massTestShip.getTotalMass() > 5e20);  	
    }
    
    @Test
    public void testIsValidMass() {
    	assertTrue(massTestShip.isValidMass(massTestShip.getMinMass()));
    	assertTrue(massTestShip.isValidMass(massTestShip.getMass()));
    	assertTrue(massTestShip.isValidMass(massTestShip.getTotalMass()));
    	assertTrue(massTestShip.isValidMass((4.0/3.0) * Math.pow(massTestShip.getRadius(), 3) * Math.PI * Ship.getMinMassDensity()));
    }

    
    @Test
    public void testThruster() {
    	assertTrue(!thrustShip.thrusterOn());
    	assertEquals(thrustShip.getThrust(), 10, 0.001);
    	thrustShip.thrustOn();
    	assertTrue(thrustShip.thrusterOn());
    	thrustShip.thrustOff();
    	assertTrue(!thrustShip.thrusterOn());
    	
    	thrustShip.setThrust(250);
    	assertEquals(thrustShip.getThrust(), 250, 0.001);
    }
    
    
    @Test
    public void testCanHaveAsOrientation() {
    	double tooBig = 7.123;
    	double valid = 4.563;
    	double tooSmall = -2.3;
    	assertFalse(Ship.canHaveAsOrientation(tooBig));
    	assertTrue(Ship.canHaveAsOrientation(valid));
    	assertFalse(Ship.canHaveAsOrientation(tooSmall));
    }

    /**
     * Tests for the boundary values of the canHaveAsOrientation method.
     */
    @Test
    public void testCanHaveAsOrientation_BoundaryValues() {
    	assertTrue(Ship.canHaveAsOrientation((double) 0));
    	assertTrue(Ship.canHaveAsOrientation(2*Math.PI - 0.0001));
    	assertFalse(Ship.canHaveAsOrientation(2*Math.PI));
    	assertFalse(Ship.canHaveAsOrientation(2*Math.PI + 0.0001));
    }


    @Test
    public void testTurn_PositiveAngle()
    {
    	mutableTestShip1.turn(Math.PI/2);
    	assertEquals(mutableTestShip1.getOrientation(), Math.PI*0.5, 0.0001);
    }

    @Test(expected = AssertionError.class)
    public void testTurn_IllegalArgumentNaN() {
    	mutableTestShip1.turn(Double.NaN);
    }


    @Test(expected = AssertionError.class)
    public void testTurn_IllegalArgumentInfinity() {
    	mutableTestShip1.turn(Double.POSITIVE_INFINITY);
    }
    
    @Test
    public void testAccelerate() {
    	thrustShip.setThrust(thrustShip.getTotalMass()*10);
    	thrustShip.thrustOn();
    	assertEquals(thrustShip.getOrientation(), 0, 0.001);
    	assertEquals(thrustShip.getAcceleration(), 10, 0.001);
    	assertEquals(thrustShip.getVelocity().getMagnitude(), 0, 0.001);
    	thrustShip.accelerate(10);
    	assertEquals(thrustShip.getVelocity().getMagnitude(), 100, 0.001);
    }
    
    @Test
	public void testGetTimeToCollision_parallel() {
		Ship ship1 = new Ship(new Vector(0,50), new Vector(20,0),0,20);
		Ship ship2 = new Ship(new Vector(0,-50), new Vector(-20,0),0,20);

		assertTrue(ship1.getTimeToCollision(ship2) == Double.POSITIVE_INFINITY);
	}

	@Test
	public void testGetTimeToCollision_following() {
		Ship ship1 = new Ship(new Vector(50,0), new Vector(20,0),0,20);
		Ship ship2 = new Ship(new Vector(-50,0), new Vector(20,0),0,20);

		assertTrue(ship1.getTimeToCollision(ship2) == Double.POSITIVE_INFINITY);
	}

	@Test
	public void testGetTimeToCollision_colliding() {
		Ship ship1 = new Ship(new Vector(-40,0), new Vector(20,0),0,20);
		Ship ship2 = new Ship(new Vector(40,0), new Vector(0,0),0,20);

		assertTrue(ship1.getTimeToCollision(ship2) == 2);
	}

	@Test
	public void testGetTimeToCollision_perpendicularCollision() {
		Ship ship1 = new Ship(new Vector(-30,0), new Vector(10,0),0,20);
		Ship ship2 = new Ship(new Vector(0,-40), new Vector(0,10),0,20);

		assertEquals(ship1.getTimeToCollision(ship2), 7.0/2.0 - Math.sqrt(31)/2, 0.0001);
	}

	@Test
	public void testGetCollisionPosition_noCollision() {
		Ship ship1 = new Ship(new Vector(-40,0), new Vector(20,0),0,20);
		Ship ship2 = new Ship(new Vector(40,0), new Vector(20,0),0,20);

		assertEquals(ship1.getCollisionPosition(ship2), null);
	}

	@Test
	public void testGetCollisionPosition_Collision() {
		Ship ship1 = new Ship(new Vector(-40,0), new Vector(20,0),0,20);
		Ship ship2 = new Ship(new Vector(40,0), new Vector(0,0),0,20);

		assertTrue(ship1.getCollisionPosition(ship2).equals(new Vector(20,0)));

	}
	
	@Test
	public void testTerminate() {
		assertTrue(!mutableTestShip1.hasWorld());
		world.addEntity(mutableTestShip1);
		assertTrue(mutableTestShip1.hasWorld());
		assertTrue(mutableTestShip1.getWorld().equals(world));
		mutableTestShip1.loadBullet(15);
		assertEquals(mutableTestShip1.getNbBullets(), 15, 0.001);
		
		mutableTestShip1.terminate();
		assertTrue(mutableTestShip1.isTerminated());
		assertEquals(mutableTestShip1.getNbBullets(), 0, 0.001);
		assertTrue(!mutableTestShip1.hasWorld());

	}
}
