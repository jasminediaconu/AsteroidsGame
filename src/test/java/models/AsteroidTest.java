package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import models.game.Asteroid;
import models.game.asteroids.Large;
import models.game.asteroids.Medium;
import models.game.asteroids.Small;
import org.junit.jupiter.api.Test;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
class AsteroidTest {

    @Test
    void randomGeneration() {
        int[] caseCounter = {0,0,0,0};

        for (int i = 0; i < 400; i++) {
            Asteroid sm = new Small();
            caseCounter[sm.caseTest()]++;
        }

        for (int i = 0; i < 4; i++) {
            assertNotEquals(0,caseCounter[i]);
            System.out.println("Instances of case " + i + ": " + caseCounter[i]);
        }
    }

    @Test
    void getUrlTest() {
        Asteroid sm;
        Asteroid md;
        Asteroid lg;

        for (int i = 0; i < 300; i++) {
            sm = new Small();
            md = new Medium();
            lg = new Large();

            assertTrue(sm.getUrl().contains("small"));
            assertTrue(md.getUrl().contains("med"));
            assertTrue(lg.getUrl().contains("big"));
        }
    }

    @Test
    void spawnTest() {
        int smCount = 0;
        int mdCount = 0;
        int lgCount = 0;
        for (int i = 0; i < 300; i++) {
            Asteroid s = Asteroid.spawnAsteroid();
            if (s instanceof Small) {
                smCount++;
            }
            if (s instanceof Medium) {
                mdCount++;
            }
            if (s instanceof Large) {
                lgCount++;
            }
            s.checkMove();
        }

        System.out.println("Instances of Small: " + smCount);
        System.out.println("Instances of Med: " + mdCount);
        System.out.println("Instances of Large: " + lgCount);
        assertNotEquals(0,smCount);
        assertNotEquals(0,mdCount);
        assertNotEquals(0,lgCount);
    }

    @Test
    void getScoreTest() {
        Large large = new Large();
        assertEquals(100, large.getScore());

        Medium medium = new Medium();
        assertEquals(150, medium.getScore());

        Small small = new Small();
        assertEquals(200, small.getScore());
    }

    @Test
    void largeSpriteTest() {
        Large large = new Large();
        assertEquals("/views/sprites/asteroids/asteroidBrown_big0.png",
                large.getSprite(0));
        assertEquals("/views/sprites/asteroids/asteroidBrown_big1.png",
                large.getSprite(1));
        assertEquals("/views/sprites/asteroids/asteroidBrown_big2.png",
                large.getSprite(2));
        assertEquals("/views/sprites/asteroids/asteroidBrown_big3.png",
                large.getSprite(3));
    }

    @Test
    void mediumSpriteTest() {
        Medium medium = new Medium();
        assertEquals("/views/sprites/asteroids/asteroidBrown_med0.png",
                medium.getSprite(0));
        assertEquals("/views/sprites/asteroids/asteroidBrown_med1.png",
                medium.getSprite(1));
    }

    @Test
    void smallSpriteTest() {
        Small small = new Small();
        assertEquals("/views/sprites/asteroids/asteroidBrown_small0.png",
                small.getSprite(0));
        assertEquals("/views/sprites/asteroids/asteroidBrown_small1.png",
                small.getSprite(1));
    }
}
