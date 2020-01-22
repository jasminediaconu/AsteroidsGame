package models.game.hostiles;

import controllers.GameScreenController;
import javafx.geometry.Point2D;

import models.game.Bullet;
import models.game.Hostile;

public class Juggernaut extends Hostile {

    private transient double course;
    private static final transient double speed = 1.8;
    private static final transient double fullTurn = 180;
    private static final transient double rotationSpeed = 1;
    private static final transient double rotateChance = 0.005;
    private static final double fireCooldown = 0.4;
    private transient double currentFireCooldown = 2;
    private static final transient int score = 200;

    /**
     * Spawns a new Juggernaut at the given coordinates,
     * and gives it a new random course.
     */
    public Juggernaut() {
        setRotation(-90);
        course = randomCourse();
        setVelocity(new Point2D(0, -1));
        setLocation(spawnPoint);
    }

    @Override
    public Bullet action() {
        Bullet b = null; // NOPMD
        currentFireCooldown -= 1.0 / 60.0;

        if (getRotation() > course + rotationSpeed) {
            setRotation(getRotation() - rotationSpeed);
            return null;

        } else if (getRotation() < course - rotationSpeed) {
            setRotation(getRotation() + rotationSpeed);
            return null;
        }

        if (Math.random() < rotateChance) {
            course = randomCourse();
        }

        setVelocity(new Point2D(
                Math.cos(Math.toRadians(getRotation())),
                Math.sin(Math.toRadians(getRotation()))
        ).normalize().multiply(speed));

        if (currentFireCooldown < 0) {
            b = shoot();
            currentFireCooldown = fireCooldown;
        }

        return b;
    }

    @Override
    public Bullet shoot() {
        this.currentFireCooldown = fireCooldown;
        return new Bullet(this);
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

    public void setCurrentFireCooldown(double currentFireCooldown) {
        this.currentFireCooldown = currentFireCooldown;
    }

    public void setCourse(double course) {
        this.course = course;
    }

    public int getScore() {
        return score;
    }

    public double getCourse() {
        return course;
    }
}

