package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Juggernaut jg = new Juggernaut();
        assertEquals("/views/sprites/enemyBlue1.png",jg.getUrl());
        Sniper sp = new Sniper();
        assertEquals("/views/sprites/enemyBlue1.png",sp.getUrl());
    }

}
