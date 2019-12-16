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
}
