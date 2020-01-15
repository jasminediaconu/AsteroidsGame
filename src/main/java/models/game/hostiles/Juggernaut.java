package models.game.hostiles;

import controllers.GameScreenController;
import models.game.Hostile;
import javafx.geometry.Point2D;

public class Juggernaut extends Hostile {

    private transient double course;
    private static final transient double speed = 1;
    private static final transient double fullTurn = 180;
    private static final transient double rotationSpeed = 1;
    private static final transient double rotateChance = 0.01;

    public Juggernaut(Point2D spawnPoint) {
        course = randomCourse();
        setVelocity(new Point2D(0, 1));
        setLocation(spawnPoint);
    }

    @Override
    public void action() {

        if (getRotation() > course - rotationSpeed
                && getRotation() < course + rotationSpeed) {

            if(Math.random() < rotateChance) {
                course = randomCourse();
            }

            setVelocity(new Point2D(
                    Math.cos(Math.toRadians(getRotation())),
                    Math.sin(Math.toRadians(getRotation()))
            ).normalize().multiply(speed));

        } else {
            if (getRotation() > course) {
                setRotation(getRotation() - rotationSpeed);
            }
            if (getRotation() < course) {
                setRotation(getRotation() + rotationSpeed);
            }
        }
    }

    private double randomCourse() {
        double min = getRotation() - fullTurn;
        double max = getRotation() + fullTurn;
        return (Math.random() * ((max - min) + 1)) + min;
    }

    @Override
    public void checkMove() {
        if (getLocation().getX() > GameScreenController.screenSize) {
            setVelocity(getVelocity().subtract(2 * getVelocity().getX(), 0));
            course = course + 90;
        }
        if (getLocation().getY() > GameScreenController.screenSize) {
            setVelocity(getVelocity().subtract(0, 2 * getVelocity().getY()));
            course = course + 90;
        }
        if (getLocation().getX() < 0) {
            setVelocity(getVelocity().subtract(2 * getVelocity().getX(), 0));
            course = course + 90;
        }
        if (getLocation().getY() < 0) {
            setVelocity(getVelocity().subtract(0, 2 * getVelocity().getY()));
            course = course + 90;
        }
    }

    @Override
    public String getUrl() {
        return "/views/sprites/Juggernaut.png";
    }
}

