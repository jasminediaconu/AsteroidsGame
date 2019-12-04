package game;

import java.util.Random;
import javafx.geometry.Point2D;

public abstract class Asteroid extends SpaceEntity {

    private static final int boundaryMargin = 200;

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

        Random rand = new Random();

        int boundary = GameScreenController.screenSize + boundaryMargin;
        Point2D topLeftCorner = new Point2D(0, 0);
        Point2D topRightUpCorner = new Point2D(GameScreenController.screenSize, 0);
        Point2D bottomLeftCorner = new Point2D(0, GameScreenController.screenSize);
        Point2D bottomRightCorner =
                new Point2D(GameScreenController.screenSize, GameScreenController.screenSize);

        int x = 0;
        int y = 0;

        //TODO: set a random velocity with a direction towards the screen.
        switch (rand.nextInt(4)) {
            case 0:
                x = rand.nextInt(boundary + boundaryMargin) - boundaryMargin;
                y = -boundaryMargin;

                setLocation(new Point2D(x, y));

                break;
            case 1:
                x = rand.nextInt(boundary + boundaryMargin) - boundaryMargin;
                y = boundary;

                break;
            case 2:
                x = -boundaryMargin;
                y = rand.nextInt(boundary + boundaryMargin) - boundaryMargin;

                break;
            case 3:
                x = boundary;
                y = rand.nextInt(boundary + boundaryMargin) - boundaryMargin;

                break;
            default:
                break;
        }

        setLocation(new Point2D(x, y));
    }

    public void checkMove() {
        //TODO: kill the asteroid if it flies of the screen. (further away than the margin)
    }

}