package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.geometry.Point2D;
import models.game.hostiles.Juggernaut;
import org.junit.jupiter.api.Test;

public class JuggernautTest {

    @Test
    void spawnTest() {
        Point2D spawnPoint = new Point2D(400, 800);
        Juggernaut juggernaut = new Juggernaut();
        assertEquals(spawnPoint, juggernaut.getLocation());
    }

    @Test
    void moveTest() {
        Point2D spawnPoint = new Point2D(69, 69);
        Juggernaut juggernaut = new Juggernaut();
        juggernaut.action();
        juggernaut.updateLocation();
        assertNotEquals(spawnPoint, juggernaut.getLocation());
    }

    @Test
    void rotateTest() {
        Juggernaut juggernaut = new Juggernaut();

        juggernaut.setRotation(90);
        juggernaut.setCourse(135);
        juggernaut.action();
        juggernaut.updateLocation();
        assertTrue(juggernaut.getRotation() > 90);

        juggernaut.setRotation(90);
        juggernaut.setCourse(45);
        juggernaut.action();
        juggernaut.updateLocation();
        assertTrue(juggernaut.getRotation() < 90);
    }

    @Test
    void shootTest() {
        Juggernaut juggernaut = new Juggernaut();
        juggernaut.setRotation(juggernaut.getCourse());
        juggernaut.setCurrentFireCooldown(0);
        assertNotNull(juggernaut.action());
    }

    @Test
    void outOfBoundsTest() {
        Juggernaut juggernaut = new Juggernaut();
        double c;

        juggernaut.setLocation(new Point2D(400, 850));
        c = 90;
        juggernaut.setCourse(c);
        juggernaut.checkMove();
        assertTrue(juggernaut.getCourse() == c + 90);

        juggernaut.setLocation(new Point2D(400, -50));
        c = -90;
        juggernaut.setCourse(c);
        juggernaut.checkMove();
        assertTrue(juggernaut.getCourse() == c + 90);

        juggernaut.setLocation(new Point2D(850, 400));
        c = 0;
        juggernaut.setCourse(c);
        juggernaut.checkMove();
        assertTrue(juggernaut.getCourse() == c + 90);

        juggernaut.setLocation(new Point2D(-50, 400));
        c = 180;
        juggernaut.setCourse(c);
        juggernaut.checkMove();
        assertTrue(juggernaut.getCourse() == c + 90);
    }

    @Test
    void scoreTest() {
        Juggernaut juggernaut = new Juggernaut();
        assertEquals(200, juggernaut.getScore());
    }

}
