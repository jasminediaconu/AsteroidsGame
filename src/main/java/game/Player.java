package game;

import javafx.geometry.Point2D;

public class Player extends SpaceEntity {

    private static final int center = GameScreenController.screenSize / 2;

    //amount of time (in seconds roughly) the player has to wait until it can fine again
    private transient double fireCooldown = 0.2;
    private transient double currentFireCooldown = 1;

    //acceleration modifier, very sensitive.
    private transient double acceleration = 0.069;

    Player() {
        setLocation(new Point2D(center, center));
    }

    /**
     * respawns the player in the middle of the screen,
     * with a velocity and rotation of 0.
     */
    public void respawn() {
        setVelocity(new Point2D(0, 0));
        setLocation(new Point2D(center, center));
        setRotation(0);
        setAlive(true);
        //TODO: make invulnerable
    }

    /**
     * Thrust spaceship.
     */
    public void thrust() {
        setVelocity(getVelocity().add(
                acceleration * Math.cos(Math.toRadians(getRotation())),
                acceleration * Math.sin(Math.toRadians(getRotation()))
                ));
    }

    /**
     * This method rotates the SpaceEntity object
     * 5 degrees to the right.
     */
    public void rotateRight() {
        setRotation(getRotation() + 4);

        //setRotationSpeed(getRotationSpeed() + 1.0 / 20);
    }

    /**
     * This method rotates the SpaceEntity object
     * 5 degrees to the left.
     */
    public void rotateLeft() {
        setRotation(getRotation() - 4);

        //setRotationSpeed(getRotationSpeed() - 1.0 / 20);
    }

    /**
     * Function to be called when the player shoots.
     */
    public Bullet shoot() {
        this.currentFireCooldown = this.fireCooldown;
        return new Bullet(this);
    }

    /**
     * Checks if the player can fire their weapon.
     * @return boolean the player can fire
     */
    public boolean canFire() {
        return currentFireCooldown <= 0.0;
    }

    /**
     * To be called every frame, decreases the cooldown timer.
     */
    public void cooldown() {
        if (currentFireCooldown > Double.MIN_VALUE) {
            currentFireCooldown -= 1.0 / 60.0;
        }
    }

    public void checkMove() {

    }

    /**
     * {@inheritDoc}
     */
    public String getUrl() {
        return "/game/sprites/playerShip.png";
    }
}