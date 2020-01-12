package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.hostiles.Ufo;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
class BulletTest {

    private transient Player player;
    private transient Bullet playerBullet;
    private transient Hostile ufo;
    private transient Bullet hostileBullet;

    @BeforeEach
    void setUp() {
        player = new Player();
        playerBullet = new Bullet(player);

        ufo = new Ufo();
        hostileBullet = new Bullet(ufo);
    }

    @Test
    void originTest() {
        assertEquals(player, playerBullet.getOrigin());
        assertEquals(ufo, hostileBullet.getOrigin());
    }

    @Test
    void constructorTest() {
        SpaceEntity origin = new Player();
        origin.setVelocity(new Point2D(10,0));
        Bullet b = new Bullet(origin);

        //Velocity should be 10 + 8
        Point2D velocity = new Point2D(22,0);
        assertEquals(velocity,b.getVelocity());
        assertEquals(origin.getRotation() + 90, b.getRotation());
    }

    @Test
    void getUrlTest() {
        assertEquals("/game/sprites/laserBlue16.png", playerBullet.getUrl());
        assertEquals("/game/sprites/laserGreen16.png", hostileBullet.getUrl());
    }

    @Test
    void testSpeed() {
        assertEquals(Bullet.getDefaultSpeed(), playerBullet.getSpeed());
        assertEquals(Bullet.getHostileSpeed(), hostileBullet.getSpeed());
    }

    @Test
    void checkDistanceTest() {
        playerBullet.checkDistance();
        assertTrue(player.isAlive());

        playerBullet.setDistanceTravelled(playerBullet.getMaxDistance() + 1);

        playerBullet.checkDistance();
        assertFalse(playerBullet.isAlive());
    }

    @Test
    void get_set_DistanceTravelledTest() {
        assertEquals(0, playerBullet.getDistanceTravelled());

        playerBullet.setDistanceTravelled(20);
        assertEquals(20, playerBullet.getDistanceTravelled());
    }

    @Test
    void get_set_MaxDistanceTest() {
        playerBullet.setMaxDistance(10.2);
        assertEquals(10.2, playerBullet.getMaxDistance());
    }

    @Test
    void updateLocationTest() {
        Point2D oldLoc = playerBullet.getLocation();
        double startDistance = playerBullet.getDistanceTravelled();

        playerBullet.updateLocation();

        Point2D newLoc = oldLoc.add(playerBullet.getVelocity());
        double endDistance = playerBullet.getDistanceTravelled();

        assertEquals(newLoc, playerBullet.getLocation());
        assertEquals(endDistance - startDistance, oldLoc.distance(newLoc));
    }

    @Test
    void getFiredByPlayerTest() {
        assertTrue(playerBullet.getFiredByPlayer());

        playerBullet.setFiredByPlayer(false);

        assertFalse(playerBullet.getFiredByPlayer());
    }
}
