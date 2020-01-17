package models.game.hostiles;

import controllers.GameScreenController;
import javafx.geometry.Point2D;

import models.game.Bullet;
import models.game.Hostile;

public class Sniper extends Hostile {

    private static final transient double speed = 3.5;
    private static final transient double rotationSpeed = 4;
    private static final transient double rotateChance = 0.02;
    // The distance between the Sniper and the player in which
    // the Sniper has the chance to rotate away.
    private static final transient int minDistance = 128;
    private static final transient double fullTurn = 180;
    private static final double fireCooldown = 0.6;
    private transient double currentFireCooldown = 2;
    private transient double course;
    private transient boolean fleeing;


    public Sniper(Point2D spawnPoint) {
        setLocation(spawnPoint);
    }

    @Override
    public void action() {

        double distanceToPlayer = GameScreenController.getPlayerLocation()
                .subtract(getLocation()).magnitude();

        if (distanceToPlayer > minDistance + minDistance && fleeing) {
            fleeing = false;
        }

        if (Math.random() < rotateChance && !fleeing) {
            course = findPlayer();
        }

        if (course > (rotationSpeed / 2)) {
            setRotation(getRotation() - rotationSpeed);
            course = course - rotationSpeed;

        } else if (course < -(rotationSpeed / 2)) {
            setRotation(getRotation() + rotationSpeed);
            course = course + rotationSpeed;

        } else if (currentFireCooldown < 0) {
            shoot();
            currentFireCooldown = fireCooldown;
        }

        if (distanceToPlayer < minDistance && !fleeing) {
            fleeing = true;
            if (course > fullTurn) {
                course -= fullTurn;
            } else {
                course += fullTurn;
            }
        }

        currentFireCooldown -= 1.0 / 60.0;

        setVelocity(new Point2D(
                Math.cos(Math.toRadians(getRotation())),
                Math.sin(Math.toRadians(getRotation()))
        ).normalize().multiply(speed));
    }

    public void shoot() {
        GameScreenController.addBullet(new Bullet(this), this);
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

        double dot = x1 * x2 + y1 * y2;
        double det = x1 * y2 - y1 * x2;

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

