package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.geometry.Point2D;
import models.game.Bullet;
import models.game.Hostile;
import models.game.Player;
import models.game.SpaceEntity;
import models.game.asteroids.Small;
import models.game.hostiles.SmallUfo;
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

        ufo = new SmallUfo();
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
        assertEquals("/views/sprites/laserBlue16.png", playerBullet.getUrl());
        assertEquals("/views/sprites/laserGreen16.png", hostileBullet.getUrl());
    }

    @Test
    void testSpeed() {
        assertEquals(Bullet.getDefaultSpeed(), playerBullet.getSpeed());
        assertEquals(Bullet.getHostileSpeed(), hostileBullet.getSpeed());
    }

    @Test
    void checkDistanceTest() {
        assertTrue(playerBullet.checkDistance());
        assertTrue(player.isAlive());

        playerBullet.setDistanceTravelled(playerBullet.getMaxDistance());

        assertTrue(playerBullet.checkDistance());

        playerBullet.setDistanceTravelled(playerBullet.getMaxDistance() + 0.0001);

        assertFalse(playerBullet.checkDistance());
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
        Point2D start = new Point2D(0, 0);
        Point2D end = new Point2D(1, 1);
        playerBullet.setLocation(start);
        playerBullet.setVelocity(end);
        playerBullet.setRotation(0.0);
        playerBullet.setRotationSpeed(1.0);
        playerBullet.setDistanceTravelled(0.0);

        playerBullet.updateLocation();

        assertEquals(new Point2D(1,1), playerBullet.getLocation());
        assertEquals(1.0, playerBullet.getRotation());
        assertEquals(start.distance(end), playerBullet.getDistanceTravelled());
    }

    @Test
    void get_set_firedByPlayerTest() {
        playerBullet.setFiredByPlayer(true);

        assertTrue(playerBullet.getFiredByPlayer());
    }
}
