package models.game;

import java.sql.Date;
import java.util.Objects;

/**
 * Class that represents a play.
 */
public class Game {
    /**
     * Unique id of the play.
     */
    private int id;
    /**
     * Username of the user playing.
     */
    private String username;

    /**
     * Alias of the user playing.
     */
    private String alias;
    /**
     * Timestamp of when the score was recorded.
     */
    private Date timestamp;
    /**
     * Score of this play.
     */
    private int score;

    /**
     * Empty constructor.
     */
    public Game() {

    }

    /**
     * Constructor.
     * @param id id.
     * @param username username.
     * @param alias alias.
     * @param timestamp timestamp.
     * @param score score.
     */
    public Game(int id, String username, String alias, Date timestamp, int score) {
        this.id = id;
        this.username = username;
        this.alias = alias;
        this.timestamp = timestamp;
        this.score = score;
    }

    /**
     * Getter for id.
     * @return id of this game.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for id.
     * @param id Id that is going to be set for the game.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for username.
     * @return username associated with this game.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for username.
     * @param username new value of username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for alias.
     * @return alias associated with this game.
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Setter for alias.
     * @param alias new value of alias.
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Getter for timestamp.
     * @return timestamp of this game.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Setter for timestamp.
     * @param timestamp new value of timestamp.
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Getter for score.
     * @return score of this game.
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter for score.
     * @param score new value of score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Game{"
                + "id=" + id
                + ", username='" + username + '\''
                + ", alias='" + alias + '\''
                + ", timestamp=" + timestamp
                + ", score=" + score
                + '}';
    }

    /**
     * Equals method for Game object.
     * @param o Object other
     * @return true iff all fields are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Game game = (Game) o;
        return getId() == game.getId()
                && getScore() == game.getScore()
                && Objects.equals(getUsername(), game.getUsername())
                && Objects.equals(getAlias(), game.getAlias())
                && Objects.equals(getTimestamp(), game.getTimestamp());
    }

    /**
     * Standard IntelliJ generated hashCode.
     * @return int hash
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getAlias(), getTimestamp(), getScore());
    }
}
