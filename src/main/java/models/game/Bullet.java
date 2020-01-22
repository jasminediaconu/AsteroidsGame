package models.game;

import javafx.geometry.Point2D;
import models.game.hostiles.Juggernaut;
import models.game.hostiles.Sniper;

public class Bullet extends SpaceEntity {

    /**
     * Boolean that represents whether bullet was shot by player or enemy ship.
     */
    private boolean firedByPlayer;

    /**
     * Speed of bullets is relative to its origin.
     */
    private static final transient double defaultSpeed = 12.0;
    private static final transient double SniperSpeed = 4.0;
    private static final transient double JuggernautSpeed = 3.0;

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

        if (firedFrom instanceof Sniper) {
            currentSpeed = SniperSpeed;
            this.firedByPlayer = false;
        } else if (firedFrom instanceof Juggernaut) {
            currentSpeed = JuggernautSpeed;
            this.firedByPlayer = false;
        } else {
            this.firedByPlayer = true;
        }

        setRotation(firedFrom.getRotation() + 90);

        setVelocity(new Point2D(
                Math.cos(Math.toRadians(firedFrom.getRotation())),
                Math.sin(Math.toRadians(firedFrom.getRotation()))
        ).normalize().multiply(currentSpeed).add(firedFrom.getVelocity()));

        setRotation(firedFrom.getRotation() + 90);
    }


    /**
     * Updates location of the Bullet.
     */
    @Override
    public void updateLocation() {
        Point2D oldLoc = this.getLocation();
        setLocation(oldLoc.add(getVelocity()));
        setRotation(getRotation() + getRotationSpeed());

        Point2D newLoc = this.getLocation();
        this.distanceTravelled += oldLoc.distance(newLoc);
    }

    /**
     * Checks if the Bullet move is valid.
     * Wraps bullet around if it goes out of screen
     */
    @Override
    public void checkMove() {
        if (firedByPlayer) {
            checkWrapAround();
        }
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
            return "/views/sprites/laserGreen16.png";
        }
        return "/views/sprites/laserBlue16.png";
    }

    /**
     * Getter for the default bullet speed.
     * @return double defaultspeed
     */
    public static double getDefaultSpeed() {
        return defaultSpeed;
    }

    /**
     * Getter for the default sniper bullet speed.
     * @return double SniperSpeed
     */
    public static double getSniperSpeed() {
        return SniperSpeed;
    }

    /**
     * Getter for the default juggernaut bullet speed.
     * @return double JuggernautSpeed
     */
    public static double getJuggernautSpeed() {
        return JuggernautSpeed;
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
