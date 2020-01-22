package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import controllers.GameScreenController;
import javafx.geometry.Point2D;
import models.game.Bullet;
import models.game.Player;
import models.game.Shield;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PlayerTest {
    private transient Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test
    void respawnTest() {
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
    void thrust2() {
        Point2D velocity = new Point2D(10,0);
        player.setVelocity(velocity);
        player.setRotation(1);
        //Player acceleration is 0.069
        player.thrust();
        Point2D newVelocity = velocity.add((0.069 * Math.cos(Math.toRadians(player.getRotation()))),
                (0.069 * Math.sin(Math.toRadians(player.getRotation()))));
        assertEquals(newVelocity,player.getVelocity());
        assertEquals(new Point2D(10 + 0.06898949096579,
                0.0012042160441725624),player.getVelocity());
    }

    @Test
    void rotationTest() {
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
        assertEquals(player.getCurrentScore(), player.getTotalScore());

        player.incrementScore(15);
        assertEquals(15, player.getCurrentScore());

        player.incrementScore(10000);
        assertEquals(10015, player.getTotalScore());

        player.setCurrentScore(10);
        assertEquals(10, player.getCurrentScore());
    }

    @Test
    void wrapAroundTest() {
        player.setLocation(new Point2D(-1, -1));
        player.checkMove();
        assertEquals(player.getLocation().getX(), GameScreenController.screenSize);
        assertEquals(player.getLocation().getY(), GameScreenController.screenSize);

        player.setLocation(new Point2D(GameScreenController.screenSize + 1,
            GameScreenController.screenSize + 2));
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

    @Test
    void setGetShield() {
        Shield testShield = new Shield(player);
        player.setShield(testShield);

        assertEquals(testShield, player.getShield());
    }

    @Test
    void setGetInvulnerabilityTime() {
        player.setInvulnerabilityTime(1.0);

        assertEquals(1.0, player.getInvulnerabilityTime());
    }

    @Test
    void updateInvulnerabilityTime() {
        player.setInvulnerabilityTime(1.0);
        player.updateInvulnerabilityTime();
        double expected = 1.0 - 1d / 60d;
        assertEquals(expected, player.getInvulnerabilityTime());
    }

    @Test
    void mockedPlayerMutant() {
        Player mockedPlayer = mock(Player.class);
        mockedPlayer.removeLife();
        mockedPlayer.respawn();
        verify(mockedPlayer, times(1)).respawn();
    }
}
