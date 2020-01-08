package game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShieldTest {
    private transient Shield shield;
    private transient Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
        shield = new Shield(player);
    }

    @Test
    void originTest() {
        assertEquals(player, shield.getOrigin());
    }

    @Test
    void getUrlTest() {
        assertEquals("/game/sprites/shield1.png", shield.getUrl());
    }

    @Test
    void updateLocationTest() {
        // location should be the same as the origin (player in this case)
        Point2D startLoc = player.getLocation();
        double startRotation = player.getRotation();

        shield.updateLocation();

        assertEquals(startLoc, shield.getLocation());
        assertEquals(startRotation, shield.getRotation());

        // move origin
        Point2D newLoc = new Point2D(1, 2);
        player.setLocation(newLoc);

        shield.updateLocation();

        assertEquals(newLoc, shield.getLocation());
    }

    @Test
    void testCheckMove() {
        // checkmove shouldn't change anything
        final Point2D startLoc = shield.getLocation();
        final double startRot = shield.getRotation();
        final Point2D startVel = shield.getVelocity();

        shield.checkMove();
        assertEquals(startLoc, shield.getLocation());
        assertEquals(startRot, shield.getRotation());
        assertEquals(startVel, shield.getVelocity());
    }
}
