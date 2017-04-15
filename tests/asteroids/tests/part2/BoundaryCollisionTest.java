package asteroids.tests;
import asteroids.model.collisions.*;
import asteroids.model.world.World;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import asteroids.model.entities.*;
import vector.Vector;

/**
 * Created by Bo on 15/04/2017.
 */
public class BoundaryCollisionTest {

    private static final double EPSILON = 0.0001;


    @Test
    public void testShipWallCollision() {
        World world = new World(1000,1000);
        Vector position = new Vector(990,100);
        Ship ship = new Ship(position, new Vector(20,0), 0,10,0);
        world.addEntity(ship);
        BoundaryCollision collision = new BoundaryCollision(ship,0);
        collision.setCollisionPosition(collision.calculateCollisionPosition());
        collision.resolve();
        assertTrue(world.getAllEntities().contains(ship));
        assertEquals(-20,ship.getVelocity().getX(), EPSILON);
        assertEquals(position,ship.getPosition());
    }

    @Test
    public void testBulletWallCollision() {
        World world = new World(1000,1000);
        Vector position = new Vector(995,300);
        Bullet bullet = new Bullet(position, new Vector(250,0),5);
        world.addEntity(bullet);
        BoundaryCollision collision = new BoundaryCollision(bullet, 0);
        collision.setCollisionPosition(collision.calculateCollisionPosition());
        collision.resolve();
        assertTrue(world.getAllEntities().contains(bullet));
        assertEquals(-250,bullet.getVelocity().getX(), EPSILON);
        assertEquals(position,bullet.getPosition());
    }

    @Test
    public void testBulletWallCollision_bulletDeath() {
        World world = new World(1000,1000);
        Vector position = new Vector(995,300);
        Bullet bullet = new Bullet(position, new Vector(250,0),5, (char) 0);
        world.addEntity(bullet);
        BoundaryCollision collision = new BoundaryCollision(bullet, 0);
        collision.setCollisionPosition(collision.calculateCollisionPosition());
        System.out.println(collision);
        collision.resolve();
        assertFalse(world.getAllEntities().contains(bullet));
        assertFalse(bullet.hasWorld());
        assertTrue(bullet.isTerminated());
    }
}
