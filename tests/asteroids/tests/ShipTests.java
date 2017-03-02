package asteroids.tests;

import asteroids.model.Ship;
import asteroids.model.Vector;
import org.junit.*;
import static org.junit.Assert.*;


/**
 * A class containing tests for the class asteroids.model.Ship
 *
 * Created by Yrjo Koyen on 28/02/2017.
 * 
 * TODO:	- Add tests for every method
 * 			- Add documentation
 * 	
 */
public class ShipTests {

    private static Ship defaultShip;
    
    private Ship mutableTestShip1;
	
	@BeforeClass
	public static void setupImmutableTestFixtures() {
		defaultShip = new Ship();
	}
	
	@Before
	public void setupMutableTestFictures() {
		mutableTestShip1 = new Ship(new Vector(100, 100), new Vector(0, 0) ,0 , 10, 10);
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
    public void testCanHaveAsOrientation_IllegalCase() {
    	double tooBig = 7.123;
    	double valid = 4.563;
    	double tooSmall = -2.3;
    	assertFalse(Ship.canHaveAsOrientation(tooBig));
    	assertTrue(Ship.canHaveAsOrientation(valid));
    	assertFalse(Ship.canHaveAsOrientation(tooSmall));
    }
    

    @Test
    public void testGetVelocity()
    {

    }

    @Test
    public void testSetVelocity()
    {

    }

    @Test
    public void testThrust()
    {

    }

    @Test
    public void testTurn()
    {

    }
}
