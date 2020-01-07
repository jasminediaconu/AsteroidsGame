package game;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LeaderBoardGameTest {
    private final transient int id = 3;
    private final String username = "jasmine";
    private final transient Date date = new Date(1000);
    private final transient int score = 2000;
    private transient Game game;
    private transient LeaderBoardGame leaderBoardGame;

    @BeforeEach
    void setUp() {
        game = new Game(id, username, null, date, score);
        leaderBoardGame = new LeaderBoardGame(game.getUsername(),
                game.getScore(), game.getTimestamp());
    }

    @Test
    void getUsername() {
        assertEquals(username, leaderBoardGame.getUsername());
    }

    @Test
    void setUsername() {
        leaderBoardGame.setUsername("jas");
        assertEquals("jas", leaderBoardGame.getUsername());
    }

    @Test
    void getScore() {
        assertEquals(score, leaderBoardGame.getScore());
    }

    @Test
    void setScore() {
        leaderBoardGame.setScore(200);
        assertEquals(200, leaderBoardGame.getScore());
    }

    @Test
    void getTimestamp() {
        assertEquals(date, leaderBoardGame.getTimestamp());
    }

    @Test
    void setTimestamp() {
        Date newDate = new Date(1302);
        leaderBoardGame.setTimestamp(newDate);

        assertEquals(newDate, leaderBoardGame.getTimestamp());
    }

    @Test
    void emptyConstructor() {
        LeaderBoardGame emptyLeaderBoardGame = new LeaderBoardGame();

        assertNotNull(emptyLeaderBoardGame);

        assertNull(emptyLeaderBoardGame.getUsername());
        assertEquals(0, emptyLeaderBoardGame.getScore());
        assertNull(emptyLeaderBoardGame.getTimestamp());
    }

    @Test
    void toStringTest() {
        String leaderboardString = "LeaderBoardGame{username=" + username
                + ", score=" + score + ", date=" + date + "}";
        assertEquals(leaderboardString, leaderBoardGame.toString());
    }

    @Test
    void equalsTestFalse() {
        LeaderBoardGame leaderBoardGame2 = new LeaderBoardGame();
        assertFalse(leaderBoardGame2.equals(leaderBoardGame));
    }

    @Test
    void equalsTestTrue() {
        LeaderBoardGame leaderBoardGame2 = new LeaderBoardGame(username, score, date);
        assertTrue(leaderBoardGame2.equals(leaderBoardGame));
    }

    @Test
    void testHash() {
        int hash = leaderBoardGame.hashCode();
        int hash2 = new LeaderBoardGame(username, score, date).hashCode();

        assertEquals(hash, hash2);

        leaderBoardGame.setScore(120);
        assertNotEquals(hash2, leaderBoardGame.hashCode());
    }
}
