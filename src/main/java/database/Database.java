package database;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import user.User;

@SuppressWarnings("PMD")
public class Database {

    /**
     * Url containing the path to where the database is stored.
     */
    private String url;

    private static final String defaultURL = "jdbc:sqlite:C:/sqlite/db/semdatabase.db";


    /**
     * Constructor.
     * @param url String to the path where the db is stored
     */
    public Database(String url) {
        this.url = url;
        this.connect();
    }

    /**
     * Constructor used when no specific database url is supplied.
     */
    public Database() {
        this.url = defaultURL;
        this.connect();
    }

    /**
     * Getter for the url.
     * @return String url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Setter for the url.
     * @param url String url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Connect to the database.
     */
    public void connect() {
        try {
            // create a connection to the database
            Connection conn = DriverManager.getConnection(this.url);

            System.out.println("Connection to SQLite has been established.");

            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create a new table in the databased specified in the url.
     * @param sql statement for creating a new table
     */
    public void createNewTable(String sql) {

        try (Connection conn = DriverManager.getConnection(this.getUrl());
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            System.out.println("table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Inserts a record into the game table.
     * @param id  id of game
     * @param username username of player
     * @param alias alias of player
     * @param timestamp timestamp of game
     * @param score score of player
     * @throws SQLException when this exceptional condition happens
     */
    public void insertGame(int id, String username, String alias, Date timestamp, int score)  {

        try {
            Connection conn = DriverManager.getConnection(this.getUrl());
            PreparedStatement stm = conn.prepareStatement("insert into game values(? ? ? ? ?)");
            stm.setInt(1,id);
            stm.setString(2, username);
            stm.setString(3, alias);
            stm.setDate(4, timestamp);
            stm.setInt(5, score);

            stm.execute();

            stm.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a record into the user table.
     * @param user the User that will be added to the database
     */
    public void insertUser(User user) {

        try {
            Connection conn = DriverManager.getConnection(this.getUrl());

            PreparedStatement statement = conn.prepareStatement("insert into user values(?,?,?)");

            statement.setString(1, user.getUsername());
            statement.setBytes(2, user.getPassword());
            statement.setBytes(3, user.getSalt());

            statement.execute();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    /**
     * Inserts a record into the user table.
     * @param username username of user
     * @param password password of user
     * @param salt salt for hashing
     * @throws SQLException when this exceptional condition happens
     * /
    public void insertUser(String username, String password, String salt) {

        try {
            Connection conn = DriverManager.getConnection(this.getUrl());

            PreparedStatement statement = conn.prepareStatement("insert into user values(?,?,?)");

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, salt);

            statement.execute();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */
    //TODO construct and return user.User object


    /**
     * Retrieves a user.User from the user table based on the username.
     * @param username username of user.User
     * @return User object created from values retrieved from database
     * @throws SQLException when this exceptional condition happens
     */
    public User getUserByUsername(String username) {
        User user = new User(username);

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(this.getUrl());

            PreparedStatement stm = conn.prepareStatement("select * from user where username = ?");

            stm.setString(1, username);

            ResultSet resultSet = stm.executeQuery();

            user.setPassword(resultSet.getBytes(2));
            user.setSalt(resultSet.getBytes(3));

            stm.close();
            resultSet.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            user = null;
        }

        return user;
    }

    /**
     * Removes user from the database.
     * @param username Username of the user to remove
     * @return true iff user removed successfully (and was present before)
     */
    public boolean removeUserByUsername(String username) {
        boolean removed = false;
        try {
            Connection conn = DriverManager.getConnection(this.getUrl());

            User toRemove;

            // check if there is a user with the provided username
            if (getUserByUsername(username) == null) {
                return false;
            }

            PreparedStatement stm = conn.prepareStatement("delete from user where username = ?");

            stm.setString(1, username);
            stm.executeUpdate();

            // check if user is not present in the db anymore
            if (getUserByUsername(username) == null) {
                removed = true;
            }

            stm.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return removed;
    }


    /**
     * Retrieves a Game from the game table based on the id.
     * @param id id of Game
     * @throws SQLException when this exceptional condition happens
     */
    //TODO construct and return Game object
    public void getGameById(int id) {
        try {
            Connection conn = DriverManager.getConnection(this.getUrl());

            PreparedStatement statement = conn.prepareStatement("select * from game where id = ?");

            ResultSet resultSet = statement.executeQuery();

            statement.close();
            resultSet.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Main method that connects to the database and creates the user and
     * games table if they are not created yet.
     * @param args String[] args
     */
    public static void main(String[] args) {
        Database db = new Database("jdbc:sqlite:C:/sqlite/db/semdatabase.db");
        db.connect();
        //        String create_table_game =
        //        "CREATE TABLE IF NOT EXISTS game(id INTEGER PRIMARY_KEY," +
        //            "username TEXT NOT NULL, alias TEXT NOT NULL,
        //             timestamp DATE NOT NULL, score INTEGER NOT NULL)";
        String createTableUser =
            "CREATE TABLE IF NOT EXISTS user(username TEXT PRIMARY KEY,"
            + "password BLOB NOT NULL, salt BLOB NOT NULL)";

        db.createNewTable(createTableUser);
    }
}
