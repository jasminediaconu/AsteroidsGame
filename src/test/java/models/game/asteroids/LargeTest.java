package models.game.asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Random;
import models.game.Asteroid;
import org.junit.jupiter.api.Test;

class LargeTest {

    @Test
    void split() {
        Large large = new Large(new Random());

        List<Asteroid> res = large.split();

        assertEquals(2, res.size());
        assertTrue(res.get(0) instanceof Medium);
        assertTrue(res.get(1) instanceof Medium);
        assertEquals(large.getLocation(), res.get(0).getLocation());
        assertEquals(large.getLocation(), res.get(1).getLocation());
    }
}