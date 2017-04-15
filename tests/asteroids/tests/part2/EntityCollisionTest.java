package asteroids.tests.part2;
import asteroids.model.Bullet;
import asteroids.model.Ship;
import asteroids.model.collisions.*;
import asteroids.model.World;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import vector.Vector;

/**
 * Created by Bo on 15/04/2017.
 */
public class EntityCollisionTest {

    private static final double EPSILON = 0.0001;

    private World world;
    private Ship ship1, ship2, ship3, ship4;
    private Bullet bullet1, bullet2;

    @Before
    public void setUp() {
        ship1 = new Ship(new Vector(350,300), new Vector(20,0), 0,10);
        ship2 = new Ship(new Vector(380,300), new Vector(-10, 0),0,20);

        ship3 = new Ship(new Vector(760,530), new Vector(23,38),0,17);
        bullet1 = new Bullet(new Vector(760,552), new Vector(0,-250),5);
        bullet1.setParentShip(ship3);

        ship4 = new Ship(new Vector(260,720), new Vector(230,120),0,15);
        bullet2 = new Bullet(new Vector(280,720), new Vector(-250,0),5);

        world = new World(1000,1000);
        world.addEntity(ship1, ship2, ship3, ship4, bullet1, bullet2);

        ship1.loadBullet(5);
        ship2.loadBullet(7);
        ship3.loadBullet(3);
        ship4.loadBullet(8);
    }

    @Test
    public void shipShipCollisionTest() {
        Collision collision = new EntityCollision(ship1, ship2, 0);
        collision.setCollisionPosition(collision.calculateCollisionPosition());
        collision.resolve();

        assertTrue(world.getAllEntities().contains(ship1));
        assertTrue(world.getAllEntities().contains(ship2));
        assertEquals(-33.7346, ship1.getVelocity().getX(), EPSILON);
        assertEquals(-3.7346, ship2.getVelocity().getX(), EPSILON);

    }

    @Test
    public void shipBulletCollisionTest_reload() {
        Collision collision = new EntityCollision(ship3,bullet1,0);
        collision.setCollisionPosition(collision.calculateCollisionPosition());
        collision.resolve();

        assertTrue(world.getAllEntities().contains(ship3));
        assertFalse(world.getAllEntities().contains(bullet1));
        assertTrue(ship3.getAllBullets().contains(bullet1));
    }

    @Test
    public void shipBulletCollisionTest_destruction() {
        Collision collision = new EntityCollision(ship4, bullet2,0);
        collision.setCollisionPosition(collision.calculateCollisionPosition());
        collision.resolve();

        assertFalse(world.getAllEntities().contains(ship4));
        assertFalse(world.getAllEntities().contains(bullet2));
        assertTrue(ship4.isTerminated());
        assertTrue(bullet2.isTerminated());
    }

}
