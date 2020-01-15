package models.game;

import java.sql.Date;
import java.util.Objects;

/**
 * The type User.
 */
public class LeaderBoardGame {

    /**
     * The username or alias of the player.
     */
    protected String username;

    /**
     * The score.
     */
    protected int score;

    /**
     * The date.
     */
    protected transient Date date;

    /**
     * The abstract User constructor, used for all other users that are not the client.
     */
    public LeaderBoardGame() {
    }

    /**
     * Instantiates a new LeaderBoardGame.
     *
     * @param username the username or alias
     * @param score the score
     * @param date the date
     */
    public LeaderBoardGame(String username, int score, Date date) {
        this.username = username;
        this.score = score;
        this.date = date;
    }

    /**
     * This function will get the players username or alias.
     *
     * @return the username or alias of the player
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This function will get the users score.
     *
     * @return the score of the user
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets score.
     *
     * @param score the score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * This function will get the game date.
     *
     * @return returns the game date
     */
    public Date getTimestamp() {
        return date;
    }

    /**
     * This function will set the date of the game.
     *
     * @param date the date
     */
    public void setTimestamp(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "LeaderBoardGame{"
                + "username=" + username
                + ", score=" + score
                + ", date=" + date
                + '}';
    }

    /**
     * This function compares this Game with another Game to check if they are equal.
     *
     * @param obj Object type
     * @return a boolean, whether they are equal or not
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LeaderBoardGame game = (LeaderBoardGame) obj;
        return Double.compare(game.getScore(), score) == 0
                && Objects.equals(game.getUsername(), username)
                && Objects.equals(game.getTimestamp(), date);
    }

    /**
     * Standard IntelliJ generated hashCode.
     * @return int hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getScore(), getTimestamp());
    }
}

