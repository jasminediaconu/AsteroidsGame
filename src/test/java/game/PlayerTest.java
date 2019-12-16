package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    @Test
    void respawnTest() {
        Player player = new Player();
        player.setLocation(new Point2D(100,100));
        assertTrue(player.isAlive());
        assertEquals(new Point2D(100,100), player.getLocation());

        player.setAlive(false);
        assertTrue(player.isDead());

        player.respawn();

        assertEquals(new Point2D(400,400), player.getLocation());
        assertEquals(new Point2D(0,0), player.getVelocity());
        assertEquals(0,player.getRotation());
        assertTrue(player.isAlive());
    }

    @Test
    void thrust() {
        Player player = new Player();
        Point2D velocity = new Point2D(10,0);
        player.setVelocity(velocity);

        //Player acceleration is 0.069
        player.thrust();
        Point2D newVelocity = velocity.add((0.069 * Math.cos(Math.toRadians(player.getRotation()))),
                (0.069 * Math.sin(Math.toRadians(player.getRotation()))));
        assertEquals(newVelocity,player.getVelocity());
        assertEquals(new Point2D(10 + 0.069, 0),player.getVelocity());
    }

    @Test
    void rotationTest() {
        Player player = new Player();
        assertEquals(0,player.getRotation());

        player.rotateRight();
        assertEquals(4,player.getRotation());
        player.rotateLeft();
        player.rotateLeft();
        assertEquals(-4,player.getRotation());
        assertEquals(new Point2D(0,0),player.getVelocity());
    }

    @Test
    void fireTest() {
        Player player = new Player();
        Bullet bullet = player.shoot();

        assertTrue(bullet.getOrigin() instanceof Player);
        assertEquals(player, bullet.getOrigin());
        assertFalse(player.canFire());

        double cooldown = player.getFireCooldownTime();
        double cooldownFrames = 60 * cooldown + 1;

        System.out.println(player.getCurrentFireCooldown());
        System.out.println(cooldownFrames);
        for (int i = 0; i < cooldownFrames; i++) {
            System.out.println(player.getCurrentFireCooldown());
            player.cooldown();
        }

        assertTrue(player.canFire());
    }

    @Test
    void lifeTest() {
        Player player = new Player();

        assertEquals(3, player.getLives());
        assertTrue(player.hasLives());

        player.removeLife();
        assertEquals(2, player.getLives());

        player.setInvulnerabilityTime(0);

        player.removeLife();

        player.setInvulnerabilityTime(0);

        player.removeLife();
        assertEquals(0, player.getLives());
        assertFalse(player.hasLives());

        player.addLife();
        assertEquals(1, player.getLives());
        assertTrue(player.hasLives());
    }

    @Test
    void scoreTest() {
        Player player = new Player();

        assertEquals(player.getCurrentScore(), player.getTotalScore());

        player.incrementScore(15);
        assertEquals(15, player.getCurrentScore());

        player.incrementScore(10000);
        assertEquals(10015, player.getTotalScore());
    }

    @Test
    void wrapAroundTest() {
        Player player = new Player();

        player.setLocation(new Point2D(-1, -1));
        player.checkMove();
        assertEquals(player.getLocation().getX(), GameScreenController.screenSize);
        assertEquals(player.getLocation().getY(), GameScreenController.screenSize);

        player.setLocation(new Point2D(GameScreenController.screenSize + 1, GameScreenController.screenSize + 2));
        player.checkMove();
        assertEquals(player.getLocation().getX(), 0);
        assertEquals(player.getLocation().getY(), 0);

        player.setLocation(new Point2D(GameScreenController.screenSize + 1, 1));
        player.checkMove();
        assertEquals(player.getLocation().getX(), 0);
        assertEquals(player.getLocation().getY(), 1);

        player.setLocation(new Point2D(1, GameScreenController.screenSize + 1));
        player.checkMove();
        assertEquals(player.getLocation().getX(), 1);
        assertEquals(player.getLocation().getY(), 0);

        player.setLocation(new Point2D(-1, 1));
        player.checkMove();
        assertEquals(player.getLocation().getX(), GameScreenController.screenSize);
        assertEquals(player.getLocation().getY(), 1);

        player.setLocation(new Point2D(1, -1));
        player.checkMove();
        assertEquals(player.getLocation().getX(), 1);
        assertEquals(player.getLocation().getY(), GameScreenController.screenSize);
    }
}
