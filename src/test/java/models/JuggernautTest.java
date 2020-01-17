package models;

import javafx.geometry.Point2D;
import models.game.hostiles.Juggernaut;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JuggernautTest {

    @Test
    void spawnTest() {
        Point2D spawnPoint = new Point2D(69, 69);
        Juggernaut juggernaut = new Juggernaut(spawnPoint);
        assertEquals(spawnPoint, juggernaut.getLocation());
    }

    @Test
    void moveTest() {
        Point2D spawnPoint = new Point2D(69, 69);
        Juggernaut juggernaut = new Juggernaut(spawnPoint);
        juggernaut.action();
        juggernaut.updateLocation();
        assertNotEquals(spawnPoint, juggernaut.getLocation());
    }

    @Test
    void outOfBoundsTest() {
        Point2D spawnPoint = new Point2D(400, 820);
        Juggernaut juggernaut = new Juggernaut(spawnPoint);
        double c = juggernaut.getCourse();
        juggernaut.action();
        juggernaut.updateLocation();
        juggernaut.checkMove();
        assertTrue(juggernaut.getCourse() != c);
    }

    @Test
    void scoreTest() {
        assertEquals(200, Juggernaut.getScore());
    }

}
