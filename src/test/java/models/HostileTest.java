package models.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import models.game.hostiles.LargeUfo;
import models.game.hostiles.SmallUfo;
import org.junit.jupiter.api.Test;


public class HostileTest {
    private transient int small = 0;
    private transient int large = 0;

    @Test
    void spawnTest() {

        for (int i = 0; i < 4000; i++) {
            Hostile hostile = Hostile.spawnHostile();
            if (hostile instanceof SmallUfo) {
                small++;
            }
            if (hostile instanceof LargeUfo) {
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
        LargeUfo lg = new LargeUfo();
        assertEquals("/game/sprites/enemyBlue1.png",lg.getUrl());
        SmallUfo sm = new SmallUfo();
        assertEquals("/game/sprites/enemyBlue1.png",sm.getUrl());
    }

}
