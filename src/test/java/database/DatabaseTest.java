package database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;

import game.Game;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import user.User;

@SuppressWarnings({"PMD.CloseResource", "PMD.DataflowAnomalyAnalysis"})
class DatabaseTest {

    private static Database db;
    private transient Connection conn;
    private transient ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        conn = Mockito.mock(Connection.class);
        db = new Database(conn);
        // set System.out to verify the correct stuff is printed.
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void insertGameTest() {
        int id = 1;
        String username = "uname";
        String alias = "alias";
        Date timestamp = new Date(1234L);
        int score = 1;

        String statement = "insert into game values(?, ?, ?, ?, ?)";
        PreparedStatement stm = Mockito.mock(PreparedStatement.class);

        try {
            Mockito.when(conn.prepareStatement(statement)).thenReturn(stm);
            Game game = new Game(id, username, alias, timestamp, score);
            db.insertGame(game);

            Mockito.verify(conn, times(1)).prepareStatement(statement);
            Mockito.verify(stm, times(1)).execute();
            Mockito.verify(stm, times(1)).setInt(1, id);
            Mockito.verify(stm, times(1)).setString(2, username);
            Mockito.verify(stm, times(1)).setString(3, alias);
            Mockito.verify(stm, times(1)).setDate(4, timestamp);
            Mockito.verify(stm, times(1)).setInt(5, score);

        } catch (SQLException e) {
            Assertions.fail();
        }
    }

    @Test
    void insertUserTest() {
        byte[] pass = {'p', 'a', 's', 's'};
        byte[] salt = {'s', 'a', 'l', 't'};
        User user = new User("name", pass);
        user.setSalt(salt);

        String statement = "insert into user values(?,?,?)";


        try {
            PreparedStatement stm  = Mockito.mock(PreparedStatement.class);
            Mockito.when(conn.prepareStatement(statement)).thenReturn(stm);

            db.insertUser(user);

            Mockito.verify(conn, times(1)).prepareStatement(statement);
            Mockito.verify(stm, times(1)).execute();
            Mockito.verify(stm, times(1)).setString(1, user.getUsername());
            Mockito.verify(stm, times(1)).setBytes(2, user.getPassword());
            Mockito.verify(stm, times(1)).setBytes(3, user.getSalt());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getUserByUsernameNegativeTest() {
        String statement = "select * from user where username = ?";
        String username = "name";

        try {
            PreparedStatement stm  = Mockito.mock(PreparedStatement.class);
            Mockito.when(conn.prepareStatement(statement)).thenReturn(stm);

            ResultSet rs = Mockito.mock(ResultSet.class);

            Mockito.when(stm.executeQuery()).thenReturn(rs);

            Mockito.when(rs.next()).thenReturn(false);

            User res = db.getUserByUsername(username);

            Assertions.assertNull(res);
            Mockito.verify(conn, times(1)).prepareStatement(statement);
            Mockito.verify(stm, times(1)).executeQuery();
            Mockito.verify(stm, times(1)).setString(1, username);

        } catch (SQLException e) {
            Assertions.fail();
        }
    }

    @Test
    void getUserByUsernamePositiveTest() {
        String statement = "select * from user where username = ?";
        String username = "name";

        User user = new User(username);
        byte[] pass = {'p', 'a', 's', 's'};
        byte[] salt = {'s', 'a', 'l', 't'};
        user.setSalt(salt);
        user.setPassword(pass);

        try {
            PreparedStatement stm  = Mockito.mock(PreparedStatement.class);
            Mockito.when(conn.prepareStatement(statement)).thenReturn(stm);

            ResultSet rs = Mockito.mock(ResultSet.class);

            Mockito.when(stm.executeQuery()).thenReturn(rs);

            Mockito.when(rs.next()).thenReturn(true);
            Mockito.when(rs.getBytes(2)).thenReturn(pass);
            Mockito.when(rs.getBytes(3)).thenReturn(salt);

            User res = db.getUserByUsername(username);

            Assertions.assertEquals(user.getUsername(), res.getUsername());
            Assertions.assertEquals(user.getPassword(), res.getPassword());
            Assertions.assertEquals(user.getSalt(), res.getSalt());

            Mockito.verify(conn, times(1)).prepareStatement(statement);
            Mockito.verify(stm, times(1)).executeQuery();
            Mockito.verify(stm, times(1)).setString(1, username);
            Mockito.verify(rs, times(1)).getBytes(2);
            Mockito.verify(rs, times(1)).getBytes(3);

        } catch (SQLException e) {
            Assertions.fail();
        }
    }

    @Test
    void removeUserByUsernameNegative() {
        String username = "uname";

        String statement = "delete from user where username = ?";

        try {
            PreparedStatement stm  = Mockito.mock(PreparedStatement.class);
            Mockito.when(conn.prepareStatement(statement)).thenReturn(stm);
            Mockito.when(stm.executeUpdate()).thenReturn(0);

            boolean res = db.removeUserByUsername(username);

            Mockito.verify(stm, times(1)).setString(1, username);
            Mockito.verify(stm, times(1)).executeUpdate();
            Assertions.assertFalse(res);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void removeUserByUsernamePositive() {
        String username = "uname";

        String statement = "delete from user where username = ?";

        try {
            PreparedStatement stm  = Mockito.mock(PreparedStatement.class);
            Mockito.when(conn.prepareStatement(statement)).thenReturn(stm);
            Mockito.when(stm.executeUpdate()).thenReturn(1);

            boolean res = db.removeUserByUsername(username);

            Mockito.verify(stm, times(1)).setString(1, username);
            Mockito.verify(stm, times(1)).executeUpdate();
            Assertions.assertTrue(res);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getGameByIdTest() {
        String statement = "select * from game where id = ?";
        int id = 1;

        try {
            PreparedStatement stm  = Mockito.mock(PreparedStatement.class);
            Mockito.when(conn.prepareStatement(statement)).thenReturn(stm);

            ResultSet rs = Mockito.mock(ResultSet.class);
            Mockito.when(stm.executeQuery()).thenReturn(rs);
            Mockito.when(rs.next()).thenAnswer(new Answer<Object>() {
                boolean called = false;
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    if (!called) {
                        called = true;
                        return true;
                    } else {
                        return false;
                    }
                }
            });

            db.getGameById(id);

            rs.close();

            Mockito.verify(conn, times(1)).prepareStatement(statement);
            Mockito.verify(stm, times(1)).executeQuery();
            Mockito.verify(rs, times(2)).next();
            Mockito.verify(rs, times(1)).getInt(1);
            Mockito.verify(rs, times(1)).getString(2);
            Mockito.verify(rs, times(1)).getString(3);
            Mockito.verify(rs, times(1)).getDate(4);
            Mockito.verify(rs, times(1)).getInt(5);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getTop5ScoresTest() {
        String statement = "select "
                + "* from game order by score desc limit 12";

        try {
            PreparedStatement stm  = Mockito.mock(PreparedStatement.class);
            Mockito.when(conn.prepareStatement(statement)).thenReturn(stm);

            ResultSet rs = Mockito.mock(ResultSet.class);
            Mockito.when(stm.executeQuery()).thenReturn(rs);
            Mockito.when(rs.next()).thenAnswer(new Answer<Object>() {
                boolean called = false;
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    if (!called) {
                        called = true;
                        return true;
                    } else {
                        return false;
                    }
                }
            });

            db.getTop5Scores();

            rs.close();

            Mockito.verify(conn, times(1)).prepareStatement(statement);
            Mockito.verify(stm, times(1)).executeQuery();
            Mockito.verify(rs, times(2)).next();
            Mockito.verify(rs, times(1)).getInt("score");
            Mockito.verify(rs, times(1)).getString("username");
            Mockito.verify(rs, times(1)).getString("alias");
            Mockito.verify(rs, times(1)).getDate("timestamp");
            Mockito.verify(rs, times(1)).getInt("id");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void insertGameException() throws SQLException {
        Mockito.when(conn.prepareStatement(any())).thenThrow(SQLException.class);
        db.insertGame(0, "", "", null, 0);

        assertTrue(outContent.toString().contains("error: connection couldn't be established\n"));
    }

    @Test
    void getUserByUsernameException() throws SQLException {
        Mockito.when(conn.prepareStatement(any())).thenThrow(SQLException.class);

        assertNull(db.getUserByUsername("username"));
    }

    @Test
    void removeUserByUsernameException() throws SQLException {
        Mockito.when(conn.prepareStatement(any())).thenThrow(SQLException.class);

        assertFalse(db.removeUserByUsername("username"));
    }

    @Test
    void getGameByIdException() throws SQLException {
        Mockito.when(conn.prepareStatement(any())).thenThrow(SQLException.class);

        assertEquals(new Game(), db.getGameById(-1));
        assertNotNull("error: connection couldn't be established\n", outContent.toString());
    }

    @Test
    void getTop5ScoresException() throws SQLException {
        Mockito.when(conn.prepareStatement(any())).thenThrow(SQLException.class);

        assertEquals(new ArrayList<Game>(), db.getTop5Scores());
        assertEquals("error: connection couldn't be established\n", outContent.toString());
    }

    @Test
    void makeGamesFromFileTest() {
        ArrayList<Game> games = db.makeGamesFromFile(
                "src/main/resources/database/standard_data/games.txt");

        assertNotNull(games);
        assertEquals(0, db.makeGamesFromFile("not a valid path").size());
    }

    @Test
    void makeUsersFromFileTest() {
        ArrayList<User> users = db.makeUsersFromFile(
                "src/main/resources/database/standard_data/users.txt");

        assertNotNull(users);
        assertEquals(0, db.makeUsersFromFile("not a valid path").size());
    }

    @Test
    void populateDatabaseTest() {
        Database mockDatabase = Mockito.mock(Database.class);

        db.populateDatabase(mockDatabase);

        Mockito.verify(mockDatabase, atLeastOnce()).makeUsersFromFile(any());
        Mockito.verify(mockDatabase, atLeastOnce()).makeGamesFromFile(any());
    }
}

