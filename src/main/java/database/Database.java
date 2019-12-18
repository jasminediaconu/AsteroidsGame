package database;

import game.Game;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import user.AuthenticationService;
import user.User;

@SuppressWarnings("PMD")
public class Database {

    /**
     * Url containing the path to where the database is stored.
     */
    private String url;

    private static final String defaultURL =
            "jdbc:sqlite:src/main/resources/database/semdatabase.db";

    private Connection connection;

    /**
     * Constructor used when no specific database url is supplied.
     */
    public Database() {
        this.url = defaultURL;
        this.connect();
    }

    /**
     * Constructor that aids in testing.
     * @param connection connection
     */
    public Database(Connection connection) {
        this.connection = connection;
        this.url = defaultURL;
    }

    /**
     * Getter for the url.
     *
     * @return String url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Setter for the url.
     *
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
            connection = DriverManager.getConnection(this.url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.print("invalid path to database\n");
        }
    }

    /**
     * Create a new table in the databased specified in the url.
     *
     * @param sql statement for creating a new table
     */
    public void createNewTable(String sql) {
        try {
            Statement stmt = connection.createStatement();
            // create a new table
            stmt.execute(sql);
            System.out.println("table created");

        } catch (SQLException e) {
            System.out.print("table couldn't be created\n");
            System.out.print("possible reasons for the error: invalid sql "
                    + "statement passed as input or connection couldn't be "
                    + "established because of"
                    + "invalid path to the database\n");
        }
    }

    /**
     * Inserts Game object into the database.
     * @param game object to insert
     */
    public void insertGame(Game game) {
        insertGame(game.getId(),
                game.getUsername(),
                game.getAlias(),
                game.getTimestamp(),
                game.getScore());
    }

    /**
     * Inserts a record into the game table.
     *
     * @param id        id of game
     * @param username  username of player
     * @param alias     alias of player
     * @param timestamp timestamp of game
     * @param score     score of player
     */
    public void insertGame(int id, String username, String alias, Date timestamp, int score)  {

        try (PreparedStatement stm = connection.prepareStatement(
                "insert into game values(?, ?, ?, ?, ?)")) {

            stm.setInt(1,id);
            stm.setString(2, username);
            stm.setString(3, alias);
            stm.setDate(4, timestamp);
            stm.setInt(5, score);

            stm.execute();
        } catch (SQLException e) {
            System.out.print("error: connection couldn't be established\n");
        }
    }


    /**
     * Inserts a record into the user table.
     *
     * @param user the User that will be added to the database
     */
    public void insertUser(User user) {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into user values(?,?,?)")) {

            statement.setString(1, user.getUsername());
            statement.setBytes(2, user.getPassword());
            statement.setBytes(3, user.getSalt());

            statement.execute();

        } catch (SQLException e) {
            System.out.println("error when inserting user, user"
                    + "already in database or connection could not be established: "
                    + e.getMessage());
        }
    }

    /**
     * Retrieves a user.User from the user table based on the username.
     *
     * @param username username of user.User
     * @return User object created from values retrieved from database
     */
    public User getUserByUsername(String username) {
        User user = new User(username);

        try (PreparedStatement stm = connection.prepareStatement(
                "select * from user where username = ?")) {

            stm.setString(1, username);

            ResultSet resultSet = stm.executeQuery();

            if (!resultSet.next()) {
                System.out.println("no user found");
                return null;
            }

            user.setPassword(resultSet.getBytes(2));
            user.setSalt(resultSet.getBytes(3));

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("error: connection couldn't be established"
                    + "couldn't find user");
            user = null;
        }

        return user;
    }

    /**
     * Removes user from the database.
     *
     * @param username Username of the user to remove
     * @return true iff user removed successfully (and was present before)
     */
    public boolean removeUserByUsername(String username) {
        try {
            PreparedStatement stm = connection.prepareStatement(
                    "delete from user where username = ?");

            stm.setString(1, username);
            int rowsAffected = stm.executeUpdate();

            stm.close();

            if (rowsAffected == 0) {
                return false;
            } else {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("error: connection couldn't be established,"
                    + " item wasn't removed");
            return false;
        }
    }


    /**
     * Retrieves a Game from the game table based on the id.
     *
     * @param id id of Game
     */
    public Game getGameById(int id) {
        Game game = new Game();

        try (PreparedStatement statement = connection.prepareStatement(
                "select * from game where id = ?")) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int gameId = resultSet.getInt(1);
                String username = resultSet.getString(2);
                String alias = resultSet.getString(3);
                Date timestamp = resultSet.getDate(4);
                int score = resultSet.getInt(5);

                // we expect only one row to be returned,
                // so the while loop only iterates once
                game = new Game(gameId, username, alias, timestamp, score);
            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.print("error: connection couldn't be established\n");
        }

        return game;
    }

    /**
     * Gets the Games with the top 5 highscores.
     * @return ArrayList containing the top 5 games.
     */
    public ArrayList<Game> getTop5Scores() {
        ArrayList<Game> highScores = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select "
                + "* from game order by score desc limit 5")) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int score = resultSet.getInt("score");
                String username = resultSet.getString("username");
                String alias = resultSet.getString("alias");
                Date timestamp = resultSet.getDate("timestamp");
                int id = resultSet.getInt("id");

                Game game = new Game(id, username, alias, timestamp, score);

                highScores.add(game);
            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.print("error: connection couldn't be established\n");
        }

        return highScores;
    }


    /**
     * Main method that connects to the database and creates the user and
     * games table if they are not created yet.
     */
    public static void createDatabase() {
        Database db = new Database();
        db.connect();
        String createTableGame =
                "CREATE TABLE IF NOT EXISTS game(id INTEGER PRIMARY KEY,"
                        + "username TEXT NOT NULL, alias TEXT NOT NULL, "
                        + "timestamp DATE NOT NULL, score INTEGER NOT NULL)";
        String createTableUser =
                "CREATE TABLE IF NOT EXISTS user(username TEXT PRIMARY KEY,"
                        + "password BLOB NOT NULL, salt BLOB NOT NULL)";

        db.createNewTable(createTableUser);
        db.createNewTable(createTableGame);
        db.populateDatabase(db);
    }

    /**
     * Populates the database with standard data from resources/database/standard_data.
     */
    protected void populateDatabase(Database database) {
        ArrayList<User> userList = database.makeUsersFromFile(
                "src/main/resources/database/standard_data/users.txt");

        for (User user : userList) {
            database.insertUser(user);
        }

        ArrayList<Game> gamesList = database.makeGamesFromFile(
                "src/main/resources/database/standard_data/games.txt");

        for (Game game : gamesList) {
            database.insertGame(game);
        }
    }

    protected ArrayList<Game> makeGamesFromFile(String filepath) {
        ArrayList<Game> gamesList = new ArrayList<>();
        Scanner sc;

        try {
            sc = new Scanner(new File(filepath)).useDelimiter(",|\\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return gamesList;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        sc.nextLine(); // ignore first line (has format of data)
        int numberOfGames = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < numberOfGames; i++) {
            String line = sc.nextLine();
            String[] values = line.split(",");

            String username = values[0];
            String alias = values[1];
            Date date = null;

            try {
                java.util.Date javaDate = format.parse(values[2]);
                date = new Date(javaDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int score = Integer.parseInt(values[3]);

            gamesList.add(new Game(i + 1, username, alias, date, score));
        }

        return gamesList;
    }

    /**
     * Turns a csv file located at specified path to an ArrayList of Users.
     * @param filepath location of csv User file
     * @return ArrayList of Users.
     */
    protected ArrayList<User> makeUsersFromFile(String filepath) {
        ArrayList<User> userList = new ArrayList<>();
        File userFile = new File(filepath);
        Scanner sc;

        try {
            sc = new Scanner(userFile).useDelimiter(",|\\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return userList;
        }

        AuthenticationService as = new AuthenticationService();

        int numberOfUsers = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < numberOfUsers; i++) {
            String line = sc.nextLine();
            String[] credentials = line.split(",");
            String username = credentials[0];
            String password = credentials[1];

            User user = as.encryptUser(username, password, as.generateSalt());
            userList.add(user);
        }

        sc.close();
        return userList;
    }
}
