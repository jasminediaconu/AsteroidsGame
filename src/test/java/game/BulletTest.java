package game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import game.asteroids.Small;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
class BulletTest {
    @Test
    void originTest() {
        Asteroid origin = new Small();
        Bullet b = new Bullet(origin);

        assertEquals(origin,b.getOrigin());
    }

    @Test
    void defaultSpeedTest() {
        Asteroid origin = new Small();
        Bullet b = new Bullet(origin);

        assertEquals(12.0,b.getDefaultSpeed());
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
        SpaceEntity player = new Player();
        player.setVelocity(new Point2D(10,0));
        Bullet b = new Bullet(player);

        assertEquals("/game/sprites/laserBlue16.png",b.getUrl());

        SpaceEntity hostile = new Hostile() {
            @Override
            public void checkMove() {

            }

            @Override
            public String getUrl() {
                return null;
            }
        };

        hostile.setVelocity(new Point2D(10,0));
        Bullet b2 = new Bullet(hostile);

        assertEquals("/game/sprites/laserGreen16.png",b2.getUrl());
    }
}
