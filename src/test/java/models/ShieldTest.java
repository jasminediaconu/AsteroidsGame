package models;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
