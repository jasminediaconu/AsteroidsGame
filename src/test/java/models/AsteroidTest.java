package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;

import javafx.geometry.Point2D;
import controllers.GameScreenController;
import javafx.geometry.Point2D;
import models.game.Asteroid;
import models.game.SpaceEntity;
import models.game.asteroids.Large;
import models.game.asteroids.Medium;
import models.game.asteroids.Small;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
class AsteroidTest {

    private transient Random random;
    private transient Asteroid small;
    private transient Asteroid medium;
    private transient Asteroid large;

    @BeforeEach
    void setUp() {
        random = new Random();
        small = new Small(random);
        medium = new Medium(random);
        large = new Large(random);
    }

    @Test
    void randomGeneration() {
        int[] caseCounter = {0,0,0,0};

        for (int i = 0; i < 400; i++) {
            Asteroid sm = new Small(random);
            caseCounter[sm.caseTest()]++;
        }

        for (int i = 0; i < 4; i++) {
            assertNotEquals(0,caseCounter[i]);
            System.out.println("Instances of case " + i + ": " + caseCounter[i]);
        }
    }

    @Test
    void getUrlTest() {
        for (int i = 0; i < 300; i++) {
            assertTrue(small.getUrl().contains("small"));
            assertTrue(medium.getUrl().contains("med"));
            assertTrue(large.getUrl().contains("big"));
        }
    }

    @Test
    void randomSpawnTest() {
        int smCount = 0;
        int mdCount = 0;
        int lgCount = 0;
        for (int i = 0; i < 300; i++) {
            Asteroid s = Asteroid.spawnAsteroid(Math.random());
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
    void smallMutantSpawnTest() {
        Asteroid m = Asteroid.spawnAsteroid(0.1);
        int mdCount = 0;
        if (m instanceof Medium) {
            mdCount++;
        }
        assertEquals(1, mdCount);
    }

    @Test
    void mediumMutantSpawnTest() {
        Asteroid l = Asteroid.spawnAsteroid(0.3);
        int lgCount = 0;
        if (l instanceof Large) {
            lgCount++;
        }
        assertEquals(1, lgCount);
    }

    @Test
    void mutationConstructorTest0() {
        Random random = mock(Random.class);
        when(random.nextGaussian()).thenReturn(0.0);
        when(random.nextInt(4)).thenReturn(0)
        .thenReturn(1)
        .thenReturn(2)
        .thenReturn(3);
        when(random.nextDouble()).thenReturn(5.0);
        Point2D start = new Point2D(400, -100);
        SpaceEntity asteroid = new Small(random);

        assertEquals(asteroid.getLocation(), start);

        Point2D result = new Point2D(0,  41);
        assertEquals(asteroid.getVelocity(), result);
    }

    @Test
    void mutationConstructorTest1() {
        Random random = mock(Random.class);
        when(random.nextGaussian()).thenReturn(50.0);
        when(random.nextInt(4)).thenReturn(1);
        when(random.nextDouble()).thenReturn(3.0);

        Point2D start = new Point2D(800, -150);
        SpaceEntity asteroid = new Small(random);
        asteroid.setLocation(start);
        assertEquals(asteroid.getLocation(), start);
        Point2D result = new Point2D(-24.887386814848803, -2.370227315699886);
        assertEquals(asteroid.getVelocity(), result);
    }

    @Test
    void mutationConstructorTest2() {
        Random random = mock(Random.class);
        when(random.nextInt()).thenReturn(10)
                .thenReturn(10);
        when(random.nextInt(4)).thenReturn(2);
        when(random.nextDouble()).thenReturn(0.0);

        Point2D start = new Point2D(800, -150);
        Medium asteroid = new Medium(random);
        asteroid.setLocation(start);
        assertEquals(asteroid.getLocation(), start);
        asteroid.setRotationSpeed(0);
        Point2D result = new Point2D(1, 0);
        assertEquals(asteroid.getVelocity(), result);
        assertEquals(asteroid.getRotationSpeed(), 0);
    }

    @Test
    void mutationConstructorTest3() {
        Random random = mock(Random.class);
        when(random.nextInt()).thenReturn(10)
                .thenReturn(10);
        when(random.nextInt(4)).thenReturn(3);
        when(random.nextDouble()).thenReturn(0.0);

        Point2D start = new Point2D(800, -150);
        Large asteroid = new Large(random);
        asteroid.setLocation(start);
        assertEquals(asteroid.getLocation(), start);
        asteroid.setRotationSpeed(0);
        Point2D result = new Point2D(-1, 0);
        assertEquals(asteroid.getVelocity(), result);
        assertEquals(asteroid.getRotationSpeed(), 0);
    }

    @Test
    void mutationConstructorTest4() {
        Random random = mock(Random.class);
        when(random.nextInt()).thenReturn(10)
                .thenReturn(10);
        when(random.nextInt(8)).thenReturn(1);
        when(random.nextDouble()).thenReturn(3.0);

        Medium asteroid = new Medium(random);
        assertEquals(asteroid.getRotationSpeed(), -3);
    }

    @Test
    void updateLocationTest() {
        Point2D start = new Point2D(0, 0);
        Point2D end = new Point2D(1, 1);
        small.setLocation(start);
        small.setVelocity(end);
        small.setRotation(0.0);
        small.setRotationSpeed(1.0);

        small.updateLocation();

        assertEquals(end, small.getLocation());
        assertEquals(1.0, small.getRotation());
    }

    @Test
    public void testIsOffScreen() {
        Asteroid asteroid = new Large();
        asteroid.setLocation(new Point2D(-1, 5));
        assertEquals(true, asteroid.isOffscreen());
    }

    @Test
    public void testIsOffScreen1() {
        Asteroid asteroid = new Large();
        asteroid.setLocation(new Point2D(56, GameScreenController.screenSize + 1));
        assertEquals(true, asteroid.isOffscreen());
    }

    @Test
    public void testIsOffScreen2() {
        Asteroid asteroid = new Large();
        asteroid.setLocation(new Point2D(45, 5));
        assertEquals(false, asteroid.isOffscreen());
    }
}
