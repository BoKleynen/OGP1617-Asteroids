package asteroids.tests;

import vector.*;
import asteroids.model.entities.*;
import org.junit.*;
import static org.junit.Assert.*;

public class Part2VectorTests {

	@Test
	public void testVectorEquality() {
		Vector v1 = new Vector(30.1, 2568.145);
		Vector v2 = new Vector(30.1, 2568.145);
		Vector v3 = new Vector(2.0, 2569);
		assertTrue(v1.equals(v2));
		assertTrue(v1.equals(v1));
		assertFalse(v1.equals(v3));
		assertFalse(v1.equals(null));
		assertFalse(v1.equals(new Ship()));
	}
}
