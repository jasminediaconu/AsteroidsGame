package models.game.asteroids;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Random;
import models.game.Asteroid;
import org.junit.jupiter.api.Test;

class MediumTest {

    @Test
    void split() {
        Medium med = new Medium(new Random());

        List<Asteroid> res = med.split();

        assertEquals(2, res.size());
        assertTrue(res.get(0) instanceof Small);
        assertTrue(res.get(1) instanceof Small);
        assertEquals(med.getLocation(), res.get(0).getLocation());
        assertEquals(med.getLocation(), res.get(1).getLocation());
    }
}