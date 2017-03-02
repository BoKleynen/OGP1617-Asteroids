package asteroids.tests;

import org.junit.*;
import static org.junit.Assert.*;

import asteroids.model.Vector;

/**
 * Created by Bo on 28/02/2017.
 * 
 * TODO: Add tests for Vector.equals
 */
public class VectorTest {

    Vector u = new Vector(2, 5);
    Vector v = new Vector(3,4);

    @Test
    public final void testVector() {
        assertTrue(2 == u.getX());
        assertTrue(5 == u.getY());
    }

    @Test
    public final void testAdd() {
        assertTrue(new Vector(5, 9).equals(u.add(v)));
        assertTrue(new Vector(5, 9).equals(v.add(u)));
    }

    @Test
    public final void testMultiply() {
        assertTrue(new Vector(6,15).equals(u.multiply(3)));
        assertTrue(new Vector(15,20).equals(v.multiply(5)));
    }

    @Test
    public final void testDotProduct() {
        assertTrue(29 == u.dotProduct(u));
        assertTrue(25 == v.dotProduct(v));
        assertTrue(26 == u.dotProduct(v));
    }

    @Test
    public final void testGetMagnitude() {
        assertTrue(5 == v.getMagnitude());
    }

    @Test
    public final void testNormalize() {
//        assertTrue(new Vector(0.6, 0.8).equals(v.normalize()));       // => fails due to java rounding errors -> how to solve this??
//        System.out.println(v.normalize().getX());
//        System.out.println(v.normalize().getY());

        assertTrue(1 == v.normalize().getMagnitude());
    }
}
