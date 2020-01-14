package models.game;

import static controllers.GameScreenController.screenSize;

import java.util.Random;
import javafx.geometry.Point2D;
import models.game.asteroids.Large;
import models.game.asteroids.Medium;
import models.game.asteroids.Small;

public abstract class Asteroid extends SpaceEntity {

    //see spawnAsteroid()
    private static final double smallSpawnThreshold = 0.1;
    private static final double medSpawnThreshold = 0.3;
    private static final double largeSpawnThreshold = 1.0;

    private static final double minVelocity = 1;
    private static final int spawnMargin = 100;
    public static int score = 0;
    private static final int courseMargin = 5;

    private transient int caseNr = -1;

    /**
     * Method to retrieve the point value of an asteroid.
     * @return int representing points awarded to player if they hit this asteroid.
     */
    public abstract int getScore();

    /**
     * Asteroid constructor, further instantiates an asteroid subclass.
     * gives it a random location outside the screen,
     * a random rotation between -maxRotation and maxRotation,
     * and a random velocity between -maxVelocity and maxVelocity
     * with a direction towards the screen.
     * @param maxVelocity the maximum possible velocity of this asteroid.
     * @param maxRotation the maximum possible rotation of this asteroid.
     */
    public Asteroid(int maxVelocity, int maxRotation) {

        Point2D course;
        Random rand = new Random();
        int gaussianCourse = (int) (rand.nextGaussian()
                * ((screenSize / 2) - courseMargin) + (screenSize / 2));
        int gaussianSpawn = (int) (rand.nextGaussian()
                * ((screenSize / 2) + spawnMargin) + (screenSize / 2));
        int boundary = screenSize + spawnMargin;
        int x;
        int y;

        switch (rand.nextInt(4)) {
            case 0:
                x = gaussianSpawn;
                y = -spawnMargin;
                setLocation(new Point2D(x, y));

                x = gaussianCourse;
                y = screenSize / 2;
                course = new Point2D(x, y);
                setVelocity(course.subtract(getLocation()));

                caseNr = 0;
                break;
            case 1:
                x = gaussianSpawn;
                y = boundary;
                setLocation(new Point2D(x, y));

                x = gaussianCourse;
                y = screenSize / 2;
                course = new Point2D(x, y);
                setVelocity(course.subtract(getLocation()));

                caseNr = 1;
                break;
            case 2:
                x = -spawnMargin;
                y = gaussianSpawn;
                setLocation(new Point2D(x, y));

                x = screenSize / 2;
                y = gaussianCourse;
                course = new Point2D(x, y);
                setVelocity(course.subtract(getLocation()));

                caseNr = 2;
                break;
            case 3:
                x = boundary;
                y = gaussianSpawn;
                setLocation(new Point2D(x, y));

                x = screenSize / 2;
                y = gaussianCourse;
                course = new Point2D(x, y);
                setVelocity(course.subtract(getLocation()));

                caseNr = 3;
                break;
            default:
                break;
        }

        setVelocity(getVelocity().normalize().multiply(
                rand.nextDouble() * maxVelocity + minVelocity
        ));
        setRotationSpeed(rand.nextInt(maxRotation * 2) - maxRotation);

    }

    /**
     * Method that spawns in a new random asteroid.
     * @return A new Asteroid.
     */
    public static Asteroid spawnAsteroid() {

        double number = Math.random();

        if (number < smallSpawnThreshold) {
            return new Small();
        } else if (number < medSpawnThreshold) {
            return new Medium();
        } else {
            return new Large();
        }
    }


    @Override
    public void checkMove() {
        //TODO: kill the asteroid if it flies of the screen. (further away than the margin)
    }

    public int caseTest() {
        return caseNr;
    }

}