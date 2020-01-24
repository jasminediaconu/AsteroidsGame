package models.game;

import static controllers.GameScreenController.screenSize;

import java.util.List;
import java.util.Random;

import javafx.geometry.Point2D;
import models.game.asteroids.Large;
import models.game.asteroids.Medium;
import models.game.asteroids.Small;

public abstract class Asteroid extends SpaceEntity {

    //see spawnAsteroid()
    private static final double smallSpawnThreshold = 0.1;
    private static final double medSpawnThreshold = 0.3;

    private static final double minVelocity = 1;
    private static final int spawnMargin = 100;
    public static int score = 0;
    private static final int courseMargin = 5;

    private transient int caseNr = -1;

    /**
     * Method to retrieve the point value of an asteroid.
     *
     * @return int representing points awarded to player if they hit this asteroid.
     */
    public abstract int getScore();

    /**
     * Asteroid constructor, further instantiates an asteroid subclass.
     * gives it a random location outside the screen,
     * a random rotation between -maxRotation and maxRotation,
     * and a random velocity between -maxVelocity and maxVelocity
     * with a direction towards the screen.
     *
     * @param maxVelocity the maximum possible velocity of this asteroid.
     * @param maxRotation the maximum possible rotation of this asteroid.
     */
    public Asteroid(int maxVelocity, int maxRotation, Random rand) {


        setOrigin(rand);

        setVelocity(getVelocity().normalize().multiply(
                rand.nextDouble() * maxVelocity + minVelocity
        ));
        setRotationSpeed(rand.nextInt(maxRotation * 2) - maxRotation);

    }
    /**
     * Method called when asteroid is hit by a bullet.
     *
     * @return a list of 2 asteroids of the next type.
     */
    public abstract List<Asteroid> split();

    /**
     * Method that spawns in a new random asteroid.
     *
     * @return A new Asteroid.
     */
    public static Asteroid spawnAsteroid(double number) {
        Random random = new Random();
        if (number < smallSpawnThreshold) {
            return new Small(random);
        } else if (number < medSpawnThreshold) {
            return new Medium(random);
        } else {
            return new Large(random);
        }
    }


    /**
     * Checks if asteroid is off screen.
     *
     * @return true iff asteroid is off screen, false otherwise
     */
    public boolean isOffscreen() {
        double x = this.getLocation().getX();
        double y = this.getLocation().getY();

        if (x < 0 || y < 0 || x > screenSize || y > screenSize) {
            return true;
        }

        return false;
    }

    @Override
    public void checkMove() {

    }

    /**
     * Used in tests.
     *
     * @return type of asteroid that is created.
     */
    public int caseTest() {
        return caseNr;
    }

    /**
     * Creates Asteroids when called, dependent on this Asteroid's type.
     * @return ArrayList of Asteroids to be spawned by GameScreenController
     */
    public ArrayList<Asteroid> split() {
        ArrayList<Asteroid> chunks = new ArrayList<>();

        if (this instanceof Large) {
            Medium md1 = new Medium(new Random());
            Medium md2 = new Medium(new Random());
            md1.setLocation(getLocation());
            md2.setLocation(getLocation());
            chunks.add(md1);
            chunks.add(md2);
        } else if (this instanceof Medium) {
            Small sm1 = new Small(new Random());
            Small sm2 = new Small(new Random());
            sm1.setLocation(getLocation());
            sm2.setLocation(getLocation());
            chunks.add(sm1);
            chunks.add(sm2);
        }

        return chunks;
    }

    public enum Origin {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT;

        /**
         * Returns the enum result based on an integer index input.
         * @param choice The integer to select output.
         * @return The enum corresponding to the chosen index
         */
        public static Origin getOrigin(int choice) {
            if (choice < values().length && choice >= 0) {
                return values()[choice];
            }
            else { return values()[0]; }
        }
    }

    /**
     * Sets the spawn location for the Asteroid and gives it a speed and direction.
     * @param rand The Random class instance to use for this class and testing purposes.
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public final void setOrigin(Random rand) {
        Origin o = Origin.getOrigin(rand.nextInt(Origin.values().length));

        int gaussianCourse = (int) (rand.nextGaussian()
                * ((screenSize / 2) - courseMargin) + (screenSize / 2));
        int gaussianSpawn = (int) (rand.nextGaussian()
                * ((screenSize / 2) + spawnMargin) + (screenSize / 2));

        switch (o) {
            case TOP:
                spawnTop(gaussianSpawn,gaussianCourse);
                caseNr = 0;
                break;
            case BOTTOM:
                spawnBottom(gaussianSpawn,gaussianCourse);
                caseNr = 1;
                break;
            case LEFT:
                spawnLeft(gaussianSpawn,gaussianCourse);
                caseNr = 2;
                break;
            case RIGHT:
                spawnRight(gaussianSpawn,gaussianCourse);
                caseNr = 3;
                break;
            default:
                break;
        }
    }

    /**
     * Sets a spawn on the top of the screen and sets an appropriate course vector.
     * @param gaussianSpawn The spawn point
     * @param gaussianCourse The course direction
     */
    public void spawnTop(int gaussianSpawn, int gaussianCourse) {
        setLocation(new Point2D(gaussianSpawn, -spawnMargin));
        Point2D course = new Point2D(gaussianCourse, (int)(screenSize / 2));
        setVelocity(course.subtract(getLocation()));
    }

    /**
     * Sets a spawn on the bottom of the screen and sets an appropriate course vector.
     * @param gaussianSpawn The spawn point
     * @param gaussianCourse The course direction
     */
    public void spawnBottom(int gaussianSpawn, int gaussianCourse) {
        setLocation(new Point2D(gaussianSpawn, screenSize + spawnMargin));
        Point2D course = new Point2D(gaussianCourse, (int)(screenSize / 2));
        setVelocity(course.subtract(getLocation()));
    }

    /**
     * Sets a spawn on the left of the screen and sets an appropriate course vector.
     * @param gaussianSpawn The spawn point
     * @param gaussianCourse The course direction
     */
    public void spawnLeft(int gaussianSpawn, int gaussianCourse) {
        setLocation(new Point2D(-spawnMargin, gaussianSpawn));
        Point2D course = new Point2D((int)(screenSize / 2), gaussianCourse);
        setVelocity(course.subtract(getLocation()));
    }

    /**
     * Sets a spawn on the right of the screen and sets an appropriate course vector.
     * @param gaussianSpawn The spawn point
     * @param gaussianCourse The course direction
     */
    public void spawnRight(int gaussianSpawn, int gaussianCourse) {
        setLocation(new Point2D(screenSize + spawnMargin, gaussianSpawn));
        Point2D course = new Point2D((int)(screenSize / 2), gaussianCourse);
        setVelocity(course.subtract(getLocation()));
    }

}
