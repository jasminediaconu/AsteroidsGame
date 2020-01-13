package models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.geometry.Point2D;
import models.game.Player;
import models.game.Shield;
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
        assertEquals("/views/sprites/shield1.png", shield.getUrl());
    }

    @Test
    void checkMoveTest() {
        Shield shield2 = shield;
        shield.checkMove();

        assertEquals(shield2, shield);
    }

    @Test
    void updateLocationTest() {
        assertEquals(player.getLocation(), shield.getLocation());
        assertEquals(player.getRotation(), shield.getRotation());

        player.setLocation(new Point2D(20, 25));
        player.setRotation(50);

        shield.updateLocation();

        assertEquals(player.getLocation(), shield.getLocation());
        assertEquals(player.getRotation(), shield.getRotation());
    }
}
