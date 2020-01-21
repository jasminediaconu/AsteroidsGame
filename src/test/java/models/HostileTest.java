package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.geometry.Point2D;
import models.game.Hostile;
import models.game.hostiles.Juggernaut;
import models.game.hostiles.Sniper;
import org.junit.jupiter.api.Test;


public class HostileTest {
    private transient int small = 0;
    private transient int large = 0;

    @Test
    void spawnTest() {

        for (int i = 0; i < 4000; i++) {
            Hostile hostile = Hostile.spawnHostile();
            if (hostile instanceof Sniper) {
                small++;
            }
            if (hostile instanceof Juggernaut) {
                large++;
            }
        }

        System.out.println(small);
        System.out.println(large);
        assertTrue(small > 1500);
        assertTrue(large > 1500);
    }

    @Test
    void ufoLinkTest() {
        Juggernaut jg = new Juggernaut(new Point2D(0, 0));
        assertEquals("/views/sprites/Juggernaut.png",jg.getUrl());
        Sniper sp = new Sniper(new Point2D(0, 0));
        assertEquals("/views/sprites/Sniper.png",sp.getUrl());
    }

}
