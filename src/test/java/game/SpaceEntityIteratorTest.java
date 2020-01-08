package game;

import game.asteroids.Small;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SpaceEntityIteratorTest {

    @Test
    public void constructorTest() {
        SpaceEntityIterator iterator = new SpaceEntityIterator(new ArrayList<>());
        Assertions.assertEquals(0, iterator.getPosition());
    }

    @Test
    public void hasNextFalseTest() {
        SpaceEntityIterator iterator = new SpaceEntityIterator(new ArrayList<>());
        Assertions.assertFalse(iterator.hasNext());
    }

    @Test
    public void hasNextTrueTest() {
        ArrayList<SpaceEntity> list = new ArrayList<SpaceEntity>();
        list.add(new Small());
        SpaceEntityIterator iterator = new SpaceEntityIterator(list);
        Assertions.assertTrue(iterator.hasNext());
    }

    @Test
    public void nextPositiveTest() {
        ArrayList<SpaceEntity> list = new ArrayList<SpaceEntity>();
        list.add(new Small());
        SpaceEntityIterator iterator = new SpaceEntityIterator(list);
        iterator.next();
        Assertions.assertEquals(1, iterator.getPosition());
    }

    @Test
    public void nextNegativeTest() {
        SpaceEntityIterator iterator = new SpaceEntityIterator(new ArrayList<>());
        SpaceEntity res = iterator.next();
        Assertions.assertEquals(null, res);
    }
}
