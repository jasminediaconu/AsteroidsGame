package models;

import controllers.GameScreenController;
import javafx.geometry.Point2D;
import models.game.Game;
import models.game.Player;
import models.game.hostiles.Sniper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class SniperTest {

    @Test
    void spawnTest() {
        Point2D spawnPoint = new Point2D(69, 69);
        Sniper sniper = new Sniper(spawnPoint);
        assertEquals(spawnPoint, sniper.getLocation());
    }

    @Test
    void moveTest() {
        Point2D spawnPoint = new Point2D(69, 69);
        Sniper sniper = new Sniper(spawnPoint);
        GameScreenController.setPlayer(new Player());
        sniper.action();
        sniper.updateLocation();
        assertNotEquals(spawnPoint, sniper.getLocation());
    }

    @Test
    void fleeingTest() {
        Point2D spawnPoint = new Point2D(420, 420);
        Sniper sniper = new Sniper(spawnPoint);
        GameScreenController.setPlayer(new Player());
        sniper.action();
        sniper.updateLocation();
        assertTrue(sniper.isFleeing());

        sniper.setLocation(new Point2D(0, 0));
        sniper.action();
        sniper.updateLocation();
        assertFalse(sniper.isFleeing());
    }

    @Test
    void scoreTest() {
        assertEquals(400, Sniper.getScore());
    }

}
