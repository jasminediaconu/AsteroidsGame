package models.game.asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Random;

import models.game.Asteroid;
import org.junit.jupiter.api.Test;

class SmallTest {

    @Test
    void split() {
        Small sm = new Small(new Random());

        List<Asteroid> res = sm.split();

        assertEquals(0, res.size());
    }
}