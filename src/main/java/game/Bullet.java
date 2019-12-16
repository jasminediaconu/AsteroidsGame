package game;

import game.hostiles.Ufo;
import javafx.geometry.Point2D;

public class Bullet extends SpaceEntity {

    /**
     * Speed of bullets is relative to its origin.
     */
    private static final transient double defaultSpeed = 12.0;
    private static final transient double hostileSpeed = 8.0;

    private transient double maxDistance = 1000;
    private transient SpaceEntity origin;
    private transient double currentSpeed;
    private transient double distanceTravelled = 0;

    /**
     * Constructor for a Bullet.
     * @param firedFrom SpaceEntity that fired the bullet
     */
    public Bullet(SpaceEntity firedFrom) {
        origin = firedFrom;

        currentSpeed = defaultSpeed;

        if (firedFrom instanceof Hostile) {
            currentSpeed = hostileSpeed;
        }

        setVelocity(new Point2D(
                Math.cos(Math.toRadians(firedFrom.getRotation())),
                Math.sin(Math.toRadians(firedFrom.getRotation()))
        ).normalize().multiply(currentSpeed).add(firedFrom.getVelocity()));

        setRotation(firedFrom.getRotation() + 90);
    }

    /**
     * Overrides SpaceEntity move method to also keep track of distance travelled.
     */
    @Override
    public void move() {
        Point2D oldLoc = this.getLocation();
        setLocation(oldLoc.add(getVelocity()));
        setRotation(getRotation() + getRotationSpeed());

        Point2D newLoc = this.getLocation();
        this.distanceTravelled += oldLoc.distance(newLoc);

        //Can't test this method unless this line is commented out,
        //updateView needs the Node which doesnt work in a test suite.
        updateView();
        checkDistance();
    }

    /**
     * Checks if the bullet has covered the max distance.
     * If the bullet has travelled more than this distance it is removed.
     */
    public void checkDistance() {
        if (this.distanceTravelled > maxDistance) {
            this.setAlive(false);
        }
    }

    /**
     * Getter for the distance the bullet has travelled.
     * @return distance the bullet has travelled
     */
    public double getDistanceTravelled() {
        return this.distanceTravelled;
    }

    /**
     * Gets the origin of the bullet (the SpaceEntity that fired it).
     * @return SpaceEntity origin
     */
    public SpaceEntity getOrigin() {
        return origin;
    }

    /**
     * Gets the speed of the bullet.
     * @return double defaultSpeed
     */
    public double getSpeed() {
        return currentSpeed;
    }

    /**
     * {@inheritDoc}
     */
    public String getUrl() {
        if (origin instanceof Hostile) {
            return "/game/sprites/laserGreen16.png";
        }
        return "/game/sprites/laserBlue16.png";
    }

    /**
     * Getter for the default bullet speed.
     * @return double defaultspeed
     */
    public static double getDefaultSpeed() {
        return defaultSpeed;
    }

    /**
     * Getter for the bullet speed if bullet is fired from a Hostile.
     * @return double hostileSpeed
     */
    public static double getHostileSpeed() {
        return hostileSpeed;
    }

    /**
     * Setter for the distance the bullet has travelled.
     * @param distance distance to set distanceTravlled to.
     */
    public void setDistanceTravelled(double distance) {
        this.distanceTravelled = distance;
    }

    /**
     * Getter for the max distance a bullet can travel.
     * @return max Distance
     */
    public double getMaxDistance() {
        return maxDistance;
    }

    /**
     * Setter for max distance a bullet can travel.
     */
    public void setMaxDistance(double distance) {
        maxDistance = distance;
    }
}
