package asteroids.tests;

import asteroids.model.Ship;
import asteroids.model.Vector;
import org.junit.*;


import static org.junit.Assert.*;


/**
 * A class containing tests for the class asteroids.model.Ship
 *
 * @author Bo Kleynen and Yrjo Koyen
 * 
 * TODO:	- Add tests for every method
 * TODO:	- Add documentation
 * 	
 */
public class ShipTests {

    private static Ship defaultShip;
    
    private Ship oppositeVelocityAndOrientation;
    private Ship sameVelocityAndOrientation;
    private Ship perpendicularOriantationAndVelocity;;
    private Ship mutableTestShip1;
    
	@BeforeClass
	public static void setupImmutableTestFixtures() {		
		defaultShip = new Ship();
	}

	@Before
	public void setupMutableTestFictures() {
		mutableTestShip1 = new Ship(new Vector(100, 100), new Vector(0, 0) ,0 , 10, 300000);
		oppositeVelocityAndOrientation = new Ship(new Vector(0, 0), new Vector(100, 0), Math.PI, 10, 300000);
		sameVelocityAndOrientation = new Ship(new Vector(0, 0), new Vector(100, 100), 0.25*Math.PI, 10, 300000);
		perpendicularOriantationAndVelocity = new Ship(new Vector(0, 0), new Vector(4, 0), 0.5*Math.PI, 10, 300000);
	}

//    @Test
//    public void testGetPosition()
//    {
//        assertTrue(defaultShip.getPosition().equals(new Vector(0, 0)));
//        assertTrue(mutableTestShip1.getPosition().equals(new Vector(100, 100)));
//    }

//    @Test
//    public void testSetPosition()
//    {
//    	Vector tempPosition = new Vector(250, -50);
//    	mutableTestShip1.setPosition(tempPosition);
//    	assertTrue(mutableTestShip1.getPosition().equals(tempPosition));
//    }

    @Test
    public void testCanHaveAsOrientation() {
    	double tooBig = 7.123;
    	double valid = 4.563;
    	double tooSmall = -2.3;
    	assertFalse(Ship.canHaveAsOrientation(tooBig));
    	assertTrue(Ship.canHaveAsOrientation(valid));
    	assertFalse(Ship.canHaveAsOrientation(tooSmall));
    }

//    @Test
//    public void testSetVelocity_LegalCase()
//    {
//    	Vector firstVelocity = new Vector(8462, 635);
//    	mutableTestShip1.setVelocity(firstVelocity);
//    	assertTrue(mutableTestShip1.getVelocity().equals(firstVelocity));
//
//    	Vector secondVelocity = new Vector(-320, -5000);
//    	mutableTestShip1.setVelocity(secondVelocity);
//    	assertTrue(mutableTestShip1.getVelocity().equals(secondVelocity));
//    }
//
//    @Test
//    public void testSetVelocity_ExceedsMaxSpeed() {
//    	Vector overSpeedOfLight = new Vector(500000, -750000);
//    	mutableTestShip1.setVelocity(overSpeedOfLight);
//    	assertFalse(mutableTestShip1.getVelocity().equals(overSpeedOfLight));
//    	assertEquals(mutableTestShip1.getVelocity().getMagnitude(), 300000, 0.0001);
//    }

    @Test
    public void testThrust_SameDirection()
    {
    	double acceleration = 5200;
    	Vector tempSpeed = sameVelocityAndOrientation.getVelocity();
    	sameVelocityAndOrientation.thrust(acceleration);
    	assertEquals(sameVelocityAndOrientation.getVelocity().getMagnitude(), tempSpeed.getMagnitude() + acceleration, 0.0001);
    }
    
    @Test
    public void testThrust_OppositeDirection()
    {
    	double acceleration = 50;
    	Vector tempSpeed = oppositeVelocityAndOrientation.getVelocity();
    	oppositeVelocityAndOrientation.thrust(acceleration);
    	assertEquals(oppositeVelocityAndOrientation.getVelocity().getMagnitude(),
    			tempSpeed.getMagnitude()-acceleration, 0.0001);
    }
    
    @Test
    public void testThrust_Perpendicular() {
    	double acceleration = 3;
    	Vector tempSpeed = perpendicularOriantationAndVelocity.getVelocity();
    	perpendicularOriantationAndVelocity.thrust(acceleration);
    	assertEquals(perpendicularOriantationAndVelocity.getVelocity().getMagnitude(),
    			Math.sqrt(Math.pow(acceleration, 2) + Math.pow(tempSpeed.getMagnitude(), 2)), 0.0001);    	
    }

    @Test
    public void testTurn_PositiveAngle()
    {
    	mutableTestShip1.turn(Math.PI/2);
    	assertEquals(mutableTestShip1.getOrientation(), Math.PI*0.5, 0.0001);
    }
    
    @Test
    public void testTurn_NegativeAngle()
    {	
    	mutableTestShip1.turn(-Math.PI);
    	assertEquals(mutableTestShip1.getOrientation(), Math.PI, 0.0001);
    }
    
    @Test
    public void testTurn_OverflowAngle() {   	
    	mutableTestShip1.turn(4*Math.PI);

    	assertEquals(mutableTestShip1.getOrientation(), 0, 0.0001);
    }

    @Test
    public void testMove() {
	    Vector u = new Vector(3,4);
	    Vector v = new Vector(2,5);
	    assertTrue(u.add(v.multiply(4)).getX() == 11);

//	    Ship ship1 = new Ship(new Vector(100, 100), new Vector(30, -15), 0, 20);
//	    ship1.setPosition(u);
//	    assertTrue(ship1.getPosition().equals(new Vector(3,4)));

        Ship ship2 = new Ship(new Vector(100, 100), new Vector(30, -15), 0, 20);
	    ship2.move(1);
        assertTrue(130 == ship2.getPosition().getX());
        assertTrue(85 == ship2.getPosition().getY());
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
}
