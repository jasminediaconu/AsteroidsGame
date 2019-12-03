package game;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends SpaceEntity {

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

    Player() {
        super(new ImageView(new Image("game/sprites/playerShip.png")));
    }

    /**
     * Thrust spaceship.
     */
    public void thrust() {
        setVelocity(new Point2D(0.7 * (velocity.getX() + Math.cos(Math.toRadians(getRotate()))),
                0.7 * (velocity.getY() + Math.sin(Math.toRadians(getRotate())))));

        view.setTranslateX(view.getTranslateX() + velocity.getX());
        view.setTranslateY(view.getTranslateY() + velocity.getY());
        System.out.println(velocity.getX() + " : " + velocity.getY());
    }

    /**
     * Adds a life to this player.
     * Increments the lives field by one.
     */
    public void addLife() {
        this.lives++;
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
}
