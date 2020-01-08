package game;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.asteroids.Large;
import game.asteroids.Medium;
import game.asteroids.Small;
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
}
