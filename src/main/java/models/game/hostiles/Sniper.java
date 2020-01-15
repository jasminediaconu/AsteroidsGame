package models.game.hostiles;

import controllers.GameScreenController;
import models.game.Hostile;
import javafx.geometry.Point2D;

public class Sniper extends Hostile {

    private transient double course;
    private static final transient double speed = 2;
    private static final transient double rotationSpeed = 3;
    private static final transient double rotateChance = 0.01;
    private static final transient double magicNumber = 1;

    public Sniper(Point2D spawnPoint) {
        setLocation(spawnPoint);
    }

    @Override
    public void action() {

        if(Math.random() < rotateChance) {
            course = findPlayer();
            System.out.println(course);
        }

        if (course > magicNumber) {
            setRotation(getRotation() - rotationSpeed);
            course = course - rotationSpeed;
        }
        if (course < -magicNumber) {
            setRotation(getRotation() + rotationSpeed);
            course = course + rotationSpeed;
        }

        setVelocity(new Point2D(
                Math.cos(Math.toRadians(getRotation())),
                Math.sin(Math.toRadians(getRotation()))
        ).normalize().multiply(speed));
    }

    /**
     * Finds the difference in degrees between the current direction
     * the Sniper is flying and the direction the player ship is.
     * @return The amount of degrees.
     */
    private double findPlayer() {

        Point2D target = GameScreenController.getPlayerLocation();

        double x1 = target.subtract(getLocation()).getX();
        double y1 = target.subtract(getLocation()).getY();
        double x2 = getVelocity().getX();
        double y2 = getVelocity().getY();

        double dot = x1*x2 + y1*y2;
        double det = x1*y2 - y1*x2;

        return Math.toDegrees(Math.atan2(det, dot));
    }

    @Override
    public void checkMove() {
        if (getLocation().getX() > GameScreenController.screenSize) {
            setVelocity(getVelocity().multiply(0.1));
            course = findPlayer();
        }
        if (getLocation().getY() > GameScreenController.screenSize) {
            setVelocity(getVelocity().multiply(0.1));
        }
        if (getLocation().getX() < 0) {
            setVelocity(getVelocity().multiply(0.1));
        }
        if (getLocation().getY() < 0) {
            setVelocity(getVelocity().multiply(0.1));
        }
    }

    @Override
    public String getUrl() {
        return "/views/sprites/Sniper.png";
    }
}

