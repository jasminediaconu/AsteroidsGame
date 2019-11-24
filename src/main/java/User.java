public class User {
    /**
     * salt retrieved from database to authenticate a user.
     */
    private byte[] salt;

    /**
     * username of the user.
     */
    private String username;

    /**
     * (attempted) password of the user.
     */
    private String password;

    /**
     * If the user does not want their username public the alias can
     * be used instead of the username in e.g. leaderboards.
     */
    private String alias;


    /**
     * Constructs a User object.
     * Only a username is required because this class can be used to construct
     * a User in "environments" where no password/alias/salt may be available
     * (e.g. when retrieving a user from the database the password is not available)
     * @param username Username of the user
     */
    public User(String username) {
        this.username = username;
    }

    /**
     * Getter for the salt.
     * @return the salt as a byte array
     */
    public byte[] getSalt() {
        return salt;
    }

    /**
     * Setter for the salt.
     * @param salt byte array of salt to be set
     */
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    /**
     * Getter for the username.
     * @return username as a String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the username.
     * @param username String to set username to
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the password.
     * @return password String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password.
     * @param password String to set password to
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for the alias.
     * @return alias String
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Setter for the alias.
     * @param alias String to set alias to
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }
}
