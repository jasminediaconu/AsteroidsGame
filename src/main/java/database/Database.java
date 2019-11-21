package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("PMD")
public class Database {

    /**
     * Url containing the path to where the database is stored.
     */
    private String url;


    /**
     * Constructor.
     * @param url String to the path where the db is stored
     */
    public Database(String url) {
        this.url = url;
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
     * @param username username of user
     * @param password password of user
     * @throws SQLException when this exceptional condition happens
     */
    public void insertUser(String username, String password) {

        try {
            Connection conn = DriverManager.getConnection(this.getUrl());

            PreparedStatement statement = conn.prepareStatement("insert into user values(?,?)");

            statement.setString(1, username);
            statement.setString(2, password);

            statement.execute();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TODO construct and return User object

    /**
     * Retrieves a User from the user table based on the username.
     * @param username username of User
     * @throws SQLException when this exceptional condition happens
     */
    public void getUserByUsername(String username) {
        try {
            Connection conn = DriverManager.getConnection(this.getUrl());

            PreparedStatement stm = conn.prepareStatement("select * from user where username = ?");

            stm.setString(1, username);

            ResultSet resultSet = stm.executeQuery();

            stm.close();
            resultSet.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

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

    /*public static void main(String[] args) {
        Database db = new Database("jdbc:sqlite:C:/sqlite/db/semdatabase.db");
        String create_table_game =
        "CREATE TABLE IF NOT EXISTS game(id INTEGER PRIMARY_KEY," +
            "username TEXT NOT NULL, alias TEXT NOT NULL,
             timestamp DATE NOT NULL, score INTEGER NOT NULL)";
        String create_table_user =
        "CREATE TABLE IF NOT EXISTS user(username TEXT PRIMARY KEY, password TEXT NOT NULL)";

        db.createNewTable(create_table_user);
    }*/
}
