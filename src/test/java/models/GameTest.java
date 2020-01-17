package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;

import models.game.Game;
import models.game.LeaderBoardGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
    private final int id = 1;
    private final String username = "username";
    private final String alias = "alias";
    private final transient Date date = new Date(1000);
    private final transient int score = 100;
    private transient Game game;

    @BeforeEach
    void setUp() {
        game = new Game(id, username, alias, date, score);
    }

    @Test
    void getId() {
        assertEquals(id, game.getId());
    }

    @Test
    void setId() {
        game.setId(2);

        assertEquals(2, game.getId());
    }

    @Test
    void getUsername() {
        assertEquals(username, game.getUsername());
    }

    @Test
    void setUsername() {
        game.setUsername("new");

        assertEquals("new", game.getUsername());
    }

    @Test
    void getAlias() {
        assertEquals(alias, game.getAlias());
    }

    @Test
    void setAlias() {
        game.setAlias("newalias");

        assertEquals("newalias", game.getAlias());
    }

    @Test
    void getTimestamp() {
        assertEquals(date, game.getTimestamp());
    }

    @Test
    void setTimestamp() {
        Date newDate = new Date(1302);
        game.setTimestamp(newDate);

        assertEquals(newDate, game.getTimestamp());
    }

    @Test
    void emptyConstructor() {
        Game emptyGame = new Game();

        assertNotNull(emptyGame);

        assertEquals(0, emptyGame.getId());
        assertNull(emptyGame.getUsername());
        assertNull(emptyGame.getAlias());
        assertNull(emptyGame.getTimestamp());
        assertEquals(0, emptyGame.getScore());
    }

    @Test
    void set_getScore() {
        assertEquals(score, game.getScore());

        game.setScore(201);
        assertEquals(201, game.getScore());
    }

    @Test
    void testHash() {
        int hash = game.hashCode();
        int hash2 = new Game(id, username, alias, date, score).hashCode();

        assertEquals(hash, hash2);

        game.setScore(120);
        assertNotEquals(hash2, game.hashCode());
    }

    @Test
    void toStringTest() {
        String gameString = "Game{id=" + id
                + ", username=" + "'username'" + ", alias=" + "'alias'"
                + ", timestamp=" + date + ", score=" + score + "}";
        assertEquals(gameString, game.toString());
    }

    @Test
    void equalsMutationTest() {
        LeaderBoardGame leaderBoardGame = new LeaderBoardGame(username, score, date);
        assertFalse(game.equals(leaderBoardGame));
    }

    @Test
    void equalsMutationTest2() {
        assertTrue(game.equals(game));
    }
}