package game;

import game.asteroids.Small;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class AsteroidIteratorTest {

    @Test
    public void constructorTest() {
        AsteroidIterator iterator = new AsteroidIterator(new ArrayList<>());
        Assertions.assertFalse(iterator.hasNext());
    }

    @Test
    public void hasNextFalseTest() {
        AsteroidIterator iterator = new AsteroidIterator(new ArrayList<>());
        Assertions.assertFalse(iterator.hasNext());
    }

    @Test
    public void hasNextTrueTest() {
        ArrayList<Asteroid> list = new ArrayList<Asteroid>();
        list.add(new Small());
        AsteroidIterator iterator = new AsteroidIterator(list);
        Assertions.assertTrue(iterator.hasNext());
    }

    @Test
    public void nextPositiveTest() {
        ArrayList<Asteroid> list = new ArrayList<Asteroid>();
        list.add(new Small());
        AsteroidIterator iterator = new AsteroidIterator(list);
        iterator.next();
        Assertions.assertFalse(iterator.hasNext());
    }

    @Test
    public void nextNegativeTest() {
        AsteroidIterator iterator = new AsteroidIterator(new ArrayList<>());
        SpaceEntity res = iterator.next();
        Assertions.assertEquals(null, res);
    }
}
