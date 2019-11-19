package Database;

import java.sql.*;

public class Database {

    /**
     * Url containing the path to where the database is stored
     */
    private String url;


    public Database(String url) {
        this.url = url;
        this.connect();
    }

    /**
     * Getter for the url
     * @return String url
     */
    public String getUrl(){
        return this.url;
    }

    /**
     * Connect to the database
     */
    public void connect() {
        Connection conn = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(this.getUrl());

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Create a new table in the databased specified in the url
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
     * Inserts a record into the game table
     * @param id  id of game
     * @param username username of player
     * @param alias alias of player
     * @param timestamp timestamp of game
     * @param score score of player
     * @throws SQLException
     */
    public void insertGame(int id, String username, String alias, Date timestamp, int score) throws SQLException {
        Connection conn = DriverManager.getConnection(this.getUrl());
        PreparedStatement statement = conn.prepareStatement("insert into game values(? ? ? ? ?)");
        statement.setInt(1,id);
        statement.setString(2, username);
        statement.setString(3, alias);
        statement.setDate(4, timestamp);
        statement.setInt(5, score);

        statement.execute();
    }

    /**
     * Inserts a record into the user table
     * @param username username of user
     * @param password password of user
     * @throws SQLException
     */
    public void insertUser(String username, String password) throws SQLException {
        Connection conn = DriverManager.getConnection(this.getUrl());
        PreparedStatement statement = conn.prepareStatement("insert into user values(?,?)");
        statement.setString(1, username);
        statement.setString(2, password);

        statement.execute();
    }

    //TODO construct and return User object

    /**
     * Retrieves a User from the user table based on the username
     * @param username username of User
     * @throws SQLException
     */
    public void getUserByUsername(String username) throws SQLException {
        Connection conn = DriverManager.getConnection(this.getUrl());
        PreparedStatement statement = conn.prepareStatement("select * from user where username = ?");
        statement.setString(1, username);

        ResultSet resultSet = statement.executeQuery();
    }

    /**
     * Retrieves a Game from the game table based on the id
     * @param id id of Game
     * @throws SQLException
     */
    //TODO construct and return Game object
    public void getGameById(int id) throws SQLException {
        Connection conn = DriverManager.getConnection(this.getUrl());
        PreparedStatement statement = conn.prepareStatement("select * from game where id = ?");
    }

    /*public static void main(String[] args) {
        Database db = new Database("jdbc:sqlite:C:/sqlite/db/semdatabase.db");
        String create_table_game = "CREATE TABLE IF NOT EXISTS game(id INTEGER PRIMARY_KEY," +
            "username TEXT NOT NULL, alias TEXT NOT NULL, timestamp DATE NOT NULL, score INTEGER NOT NULL)";
        String create_table_user = "CREATE TABLE IF NOT EXISTS user(username TEXT PRIMARY KEY, password TEXT NOT NULL)";

        db.createNewTable(create_table_user);
    }*/
}
