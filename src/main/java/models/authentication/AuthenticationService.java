package models.authentication;

import database.Database;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Based on: https://www.javacodegeeks.com/2012/05/secure-password-storage-donts-dos-and.html
 */
public class AuthenticationService {

    private transient Database db;

    public AuthenticationService(Database db) {
        this.db = db;
    }

    /**
     * Checks if the user created on login is a registered user.
     * This is done by comparing the encrypted password stored in the database
     * with the hash of the password the user filled in + the salt.
     * @param user User that holds username and attempted password
     * @return true iff the user provided a correct username and password combination
     */
    public boolean authenticate(User user) {
        User userFromDB = db.getUserByUsername(user.getUsername());

        if (userFromDB == null) {
            return false;
        }

        return authenticate(user, userFromDB);
    }

    /**
     * Checks if the attemptedUser provided the correct details by checking
     * the encrypted password against the password stored in the database.
     * @param attemptedUser User created on login
     * @param userFromDB User retrieved from Database
     * @return true iff encrypted user password and encrypted stored password match
     */
    public boolean authenticate(User attemptedUser, User userFromDB) {
        byte[] salt = userFromDB.getSalt();

        try {
            byte[] passwordAttempted = encryptPassword(salt, attemptedUser.getPassword());
            byte[] passwordActual = userFromDB.getPassword();

            String pwAttempted;

            return Arrays.equals(passwordActual, passwordAttempted);
        } catch (NoSuchAlgorithmException
                | InvalidKeySpecException
                | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Hashes the provided password with the provided salt using
     * PDKDF2 with SHA-1 hasing algorithm.
     * @param salt byte[] salt to hash together with password
     * @param password String password to hash
     * @return byte[] of the encryptedPassword
     * @throws NoSuchAlgorithmException when the keyFactory fails
     * @throws InvalidKeySpecException when the keyFactory.generateSecret fails
     */
    public byte[] encryptPassword(byte[] salt, byte[] password)
            throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        String pw = new String(password, "UTF-8");
        KeySpec keySpec = new PBEKeySpec(pw.toCharArray(), salt, 10_000, 160);

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = keyFactory.generateSecret(keySpec);

        return key.getEncoded();
    }

    /**
     * Generate a random salt to encrypt together with the password for security.
     * @return randomly generated byte[] salt
     */
    public byte[] generateSalt() {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");

            byte[] salt = new byte[8];
            secureRandom.nextBytes(salt);

            return salt;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("could not find algorithm during salt generation");
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * Takes a user without a salt and with a non-encrypted password and
     * encrypts the password using a randomly generated salt.
     */
    public User encryptUser(String username, String password, byte[] salt) {
        User user = new User(username);

        try {
            user.setSalt(salt);
            user.setPassword(encryptPassword(user.getSalt(), password.getBytes()));
        } catch (NoSuchAlgorithmException
                | InvalidKeySpecException
                | UnsupportedEncodingException e) {
            System.out.println("Exception during user encryption");
            e.printStackTrace();
        }

        return user;
    }

    /**
     * Stores the current System time in the database to keep track
     * of when the user should be allowed to login again.
     */
    public void setLoginLocked(long timestamp) {
        db.insertLoginAttempt(timestamp);
        System.out.println("inserted into db login locked: " + timestamp);
    }

    /**
     * Checks the database to see if the stored time is more than 5 minutes ago.
     * @return true iff user is allowed to attempt login again.
     */
    public long loginLockedForSeconds() {
        long timeLocked = db.getLastLoginLocked();
        long now = System.currentTimeMillis();

        if (timeLocked >= now) {
            return 0L;
        }

        long millis = now - timeLocked;
        return 300000L - millis;
    }

    /**
     * Checks the database to see if the stored time is more than 5 minutes ago.
     * @return true iff user is allowed to attempt login again.
     */
    public boolean isLoginLocked() {
        long lockedfor = loginLockedForSeconds();
        return lockedfor > 0L;
    }
}
