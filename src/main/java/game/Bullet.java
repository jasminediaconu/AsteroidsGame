package game;

import javafx.geometry.Point2D;

public class Bullet extends SpaceEntity {

    /**
     * Boolean that represents whether bullet was shot by player or enemy ship.
     */
    private boolean firedByPlayer;

    /**
     * Speed of bullets is relative to its origin.
     */
    private static final transient double defaultSpeed = 12.0;
    private transient SpaceEntity origin;

    /**
     * Constructor for a Bullet.
     * @param firedFrom SpaceEntity that fired the bullet
     */
    public Bullet(SpaceEntity firedFrom) {

        origin = firedFrom;

        setVelocity(new Point2D(
            Math.cos(Math.toRadians(firedFrom.getRotation())),
            Math.sin(Math.toRadians(firedFrom.getRotation()))
        ).normalize().multiply(defaultSpeed).add(firedFrom.getVelocity()));

        setRotation(firedFrom.getRotation() + 90);
    }

    public void checkMove() {

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
     * Gets the default speed of the bullet.
     * @return double defaultSpeed
     */
    public double getDefaultSpeed() {
        return defaultSpeed;
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
}
