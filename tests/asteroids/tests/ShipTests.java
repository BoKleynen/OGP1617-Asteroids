package asteroids.tests;

import asteroids.model.Ship;
import asteroids.model.Vector;
import org.junit.*;

import java.util.Random;

import static org.junit.Assert.*;


/**
 * A class containing tests for the class asteroids.model.Ship
 *
 * Created by Yrjo Koyen on 28/02/2017.
 * 
 * TODO:	- Add tests for every method
 * TODO:	- Add documentation
 * 	
 */
public class ShipTests {

    private static Ship defaultShip;

    private Ship mutableTestShip1;
    
    private static Random RNG;

	@BeforeClass
	public static void setupImmutableTestFixtures() {
		RNG = new Random(System.currentTimeMillis());
		
		defaultShip = new Ship();
	}

	@Before
	public void setupMutableTestFictures() {
		mutableTestShip1 = new Ship(new Vector(100, 100), new Vector(0, 0) ,0 , 10, 300000);
	}

    @Test
    public void testGetPosition()
    {
        assertTrue(defaultShip.getPosition().equals(new Vector(0, 0)));
        assertTrue(mutableTestShip1.getPosition().equals(new Vector(100, 100)));
    }

    @Test
    public void testSetPosition()
    {
    	Vector tempPosition = new Vector(250, -50);
    	mutableTestShip1.setPosition(tempPosition);
    	assertTrue(mutableTestShip1.getPosition().equals(tempPosition));
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

    @Test
    public void testSetVelocity()
    {
    	Vector firstVelocity = new Vector(8462, 635);
    	mutableTestShip1.setVelocity(firstVelocity);
    	assertTrue(mutableTestShip1.getVelocity().equals(firstVelocity));
    	
    	Vector secondVelocity = new Vector(-320, -5000);
    	mutableTestShip1.setVelocity(secondVelocity);
    	assertTrue(mutableTestShip1.getVelocity().equals(secondVelocity));
    
    	Vector overSpeedOfLight = new Vector(500000, -750000);
    	mutableTestShip1.setVelocity(overSpeedOfLight);
    	assertFalse(mutableTestShip1.getVelocity().equals(overSpeedOfLight));
    	assertEquals(mutableTestShip1.getVelocity().getMagnitude(), 300000, 0.0001);
    }

    @Test
    public void testThrust()
    {
    	// Thrust in X direction only
    	double acceleration = 10;
    	Vector tempSpeed = mutableTestShip1.getVelocity();
    	mutableTestShip1.thrust(acceleration);
    	assertEquals(mutableTestShip1.getVelocity().getX(), tempSpeed.getX()+acceleration, 0.0001);
    	
//    	// Thrust in a random direction
//    	acceleration = 20*RNG.nextFloat();
//    	double newOrientation = 2*Math.PI*RNG.nextFloat();
//    	mutableTestShip1.setOrientation(newOrientation);
//    	tempSpeed = mutableTestShip1.getVelocity();
//    	mutableTestShip1.thrust(acceleration);
//    	System.out.println(mutableTestShip1.getVelocity().getMagnitude());
//    	System.out.println(tempSpeed.getMagnitude() + acceleration);
//
//    	assertEquals(mutableTestShip1.getVelocity().getMagnitude(), tempSpeed.getMagnitude() + acceleration, 0.0001);
    }

    @Test
    public void testTurn()
    {
    	double newOrientation = Math.PI;
    	mutableTestShip1.setOrientation(newOrientation);
    	mutableTestShip1.turn(Math.PI/2);
    	assertEquals(mutableTestShip1.getOrientation(), Math.PI*1.5, 0.0001);
    	
    	mutableTestShip1.turn(-Math.PI);
    	assertEquals(mutableTestShip1.getOrientation(), Math.PI*0.5, 0.0001);
    	
    	mutableTestShip1.turn(4*Math.PI);
    	assertEquals(mutableTestShip1.getOrientation(), Math.PI*0.5, 0.0001);
    }

    @Test
    public void testMove() {
	    Vector u = new Vector(3,4);
	    Vector v = new Vector(2,5);
	    assertTrue(u.add(v.multiply(4)).getX() == 11);

	    Ship ship1 = new Ship(new Vector(100, 100), new Vector(30, -15), 0, 20);
	    ship1.setPosition(u);
	    assertTrue(ship1.getPosition().equals(new Vector(3,4)));

        Ship ship2 = new Ship(new Vector(100, 100), new Vector(30, -15), 0, 20);
	    ship2.move(1);
        assertTrue(130 == ship2.getPosition().getX());
        assertTrue(85 == ship2.getPosition().getY());
    }
}
