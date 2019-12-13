package game;

import javafx.geometry.Point2D;

public class Player extends SpaceEntity {

    private static final int center = GameScreenController.screenSize / 2;

    //amount of time (in seconds roughly) the player has to wait until it can fire again
    private transient double fireCooldown = 0.2;
    private transient double currentFireCooldown = 1;

    //acceleration modifier, very sensitive.
    private transient double acceleration = 0.069;

    private Shield shield;

    /**
     * Constructor for Player.
     * Initial values: lives = 3, totalScore = 0, currentScore = 0.
     */
    Player() {
        setLocation(new Point2D(center, center));
        this.lives = 3;
        this.totalScore = 0;
        this.currentScore = 0;
    }

    /**
     * Number of lives the player has.
     */
    private int lives;
    /**
     * Total score of player.
     */
    private int totalScore;
    /**
     * Current score of player.
     * Needed to keep count of the 10000 points, so the extra life can be added.
     * Gets reset to 0 when the extra life has been added.
     */
    private int currentScore;


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
     * Adds a life to this player.
     * Increments the lives field by one.
     */
    public void addLife() {
        this.lives++;
    }

    /**
     * Removes a life.
     */
    public void removeLife() {
        this.lives--;
    }

    /**
     * Checks if the player has lives left.
     * @return false is the player has 0 lives, true if they have more.
     */
    public boolean hasLives() {
        if (this.lives <= 0) {
            return false;
        }

        return true;
    }

    /**
     * Getter for the lives.
     * @return how many lives the player has.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Setter for the life.
     * @param lives new value of life.
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Increments the player's score by the value passed.
     * @param value value.
     */
    public void incrementScore(int value) {
        this.currentScore += value;
        this.totalScore += value;
    }

    /**
     * Getter for totalScore.
     * @return total score of player.
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * Setter for totalScore.
     * @param totalScore new value for totalScore.
     */
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * Getter for currentScore.
     * @return current score of the player.
     */
    public int getCurrentScore() {
        return currentScore;
    }

    /**
     * Setter of currentScore.
     * @param currentScore new value for currentScore.
     */
    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
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

    public double getCurrentFireCooldown() {
        return currentFireCooldown;
    }

    public double getFireCooldownTime() {
        return fireCooldown;
    }

    /**
     * {@inheritDoc}
     */
    public String getUrl() {
        return "/game/sprites/playerShip.png";
    }

    /**
     * Returns the shield of the player.
     * @return Shield
     */
    public Shield getShield() {
        return this.shield;
    }

    /**
     * Setter for the Shield.
     * @param shield new value of Shield
     */
    public void setShield(Shield shield) {
        this.shield = shield;
    }
}
