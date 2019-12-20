package game;

import javafx.geometry.Point2D;

import javax.annotation.processing.Generated;

public class Bullet extends SpaceEntity {

    /**
     * Boolean that represents whether bullet was shot by player or enemy ship.
     */
    private boolean firedByPlayer;

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
    @Generated(message = "")
    public void move() {
        Point2D oldLoc = this.getLocation();
        setLocation(oldLoc.add(getVelocity()));
        setRotation(getRotation() + getRotationSpeed());

        Point2D newLoc = this.getLocation();
        this.distanceTravelled += oldLoc.distance(newLoc);

        checkDistance();
        checkMove();

        //Can't test this method unless this line is commented out,
        //updateView needs the Node which doesnt work in a test suite.
        updateView();
    }

    /**
     * Checks if the Bullet move is valid.
     * Wraps bullet around if it goes out of screen
     */
    @Override
    public void checkMove() {
        checkWrapAround();
    }

    /**
     * Checks if the bullet has covered the max distance.
     * If the bullet has travelled more than this distance it is removed.
     */
    public boolean checkDistance() {
        if (this.distanceTravelled > maxDistance) {
            this.setAlive(false);
            return false;
        }

        return true;
    }

    /**
     * Getter for the distance the bullet has travelled.
     * @return distance the bullet has travelled
     */
    public double getDistanceTravelled() {
        return this.distanceTravelled;
    }

    /**
     * Getter for firedByPlayer.
     * @return true if bullet was fired by player, false otherwise.
     */
    public boolean getFiredByPlayer() {
        return this.firedByPlayer;
    }

    /**
     * Setter for firedByPlayer.
     * @param shot new value for firedByPlayer.
     */
    public void setFiredByPlayer(boolean shot) {
        this.firedByPlayer = shot;
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
