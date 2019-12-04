package game;

import static game.GameScreenController.screenSize;

import java.util.Random;
import javafx.geometry.Point2D;

public abstract class Asteroid extends SpaceEntity {

    private static final double minVelocity = 0.2;
    private static final int spawnMargin = 100;
    public static final int courseMargin = 5;

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
        // rand.nextInt(boundary + spawnMargin) - spawnMargin;
        int gaussianCourse = (int) (rand.nextGaussian()
                * ((screenSize / 2) - courseMargin) + (screenSize / 2));
        int gaussianSpawn = (int) (rand.nextGaussian()
                * ((screenSize / 2) + spawnMargin) + (screenSize / 2));
        int boundary = screenSize + spawnMargin;
        int x = 0;
        int y = 0;

        //TODO: set a random velocity with a direction towards the screen.
        switch (rand.nextInt(4)) {
            case 0:
                x = gaussianSpawn;
                y = -spawnMargin;
                setLocation(new Point2D(x, y));

                x = gaussianCourse;
                y = screenSize / 2;
                course = new Point2D(x, y);
                setVelocity(course.subtract(getLocation()));

                break;
            case 1:
                x = gaussianSpawn;
                y = boundary;
                setLocation(new Point2D(x, y));

                x = gaussianCourse;
                y = screenSize / 2;
                course = new Point2D(x, y);
                setVelocity(course.subtract(getLocation()));

                break;
            case 2:
                x = -spawnMargin;
                y = gaussianSpawn;
                setLocation(new Point2D(x, y));

                x = screenSize / 2;
                y = gaussianCourse;
                course = new Point2D(x, y);
                setVelocity(course.subtract(getLocation()));

                break;
            case 3:
                x = boundary;
                y = gaussianSpawn;
                setLocation(new Point2D(x, y));

                x = screenSize / 2;
                y = gaussianCourse;
                course = new Point2D(x, y);
                setVelocity(course.subtract(getLocation()));

                break;
            default:
                break;
        }

        setVelocity(getVelocity().normalize().multiply(rand.nextDouble() * maxVelocity + 0.1));
        setRotationSpeed(rand.nextInt(maxRotation * 2) - maxRotation);

    }

    public void checkMove() {
        //TODO: kill the asteroid if it flies of the screen. (further away than the margin)
    }

}