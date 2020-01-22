package models.game.hostiles;

import controllers.GameScreenController;
import javafx.geometry.Point2D;

import models.game.Bullet;
import models.game.Hostile;
import models.game.Player;

public class Sniper extends Hostile {

    private static final transient double speed = 3.0;
    private static final transient double rotationSpeed = 4;
    private static final transient double rotateChance = 0.02;
    // The distance between the Sniper and the player in which
    // the Sniper has the chance to rotate away.
    private static final transient int minDistance = 128;
    private static final transient double fullTurn = 180;
    private static final double fireCooldown = 1.5;
    private transient double currentFireCooldown = 4;
    private transient double course;
    private transient boolean fleeing;
    private static final transient int score = 400;
    private transient Player player;



    public Sniper(Player p) {
        this.player = p;
        setLocation(spawnPoint);
    }

    @Override
    public Bullet action() {
        Bullet b = null; // NOPMD
        double distanceToPlayer = player.getLocation()
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

        } else if (currentFireCooldown <= 0) {
            b = shoot();
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
        return b;
    }

    @Override
    public Bullet shoot() {
        this.currentFireCooldown = fireCooldown;
        return new Bullet(this);
    }

    /**
     * Finds the difference in degrees between the current direction
     * the Sniper is flying and the direction the player ship is.
     * @return The amount of degrees.
     */
    private double findPlayer() {
        Point2D target = player.getLocation();

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
    }

    @Override
    public String getUrl() {
        return "/views/sprites/Sniper.png";
    }

    public int getScore() {
        return score;
    }

    public boolean isFleeing() {
        return fleeing;
    }

    public void setCurrentFireCooldown(double currentFireCooldown) {
        this.currentFireCooldown = currentFireCooldown;
    }
}

