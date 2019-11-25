package user;

import database.Database;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class AuthenticationService {

    /**
     * Checks if the user created on login is a registered user.
     * This is done by comparing the encrypted password stored in the database
     * with the hash of the password the user filled in + the salt.
     * @param user User that holds username and attempted password
     * @return true iff the user provided a correct username and password combination
     */
    public boolean authenticate(User user) {
        Database db = new Database();
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
            byte [] passwordAttempted = encryptPassword(salt, attemptedUser.getPassword());

            String pwString = userFromDB.getPassword();
            //byte[] passwordActual = pwString.getBytes();

            String pwAttempted;
            try {
                pwAttempted = new String(passwordAttempted, "UTF-8");
                return pwString.equals(pwAttempted);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

        return false;
        //return Arrays.equals(passwordAttempted, passwordActual);
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
    public byte[] encryptPassword(byte[] salt, String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 10_000, 160);

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = keyFactory.generateSecret(keySpec);

        return key.getEncoded();
    }

    /*private byte[] getEncryptedPassword(User user)
            throws InvalidKeySpecException, NoSuchAlgorithmException {

        Database db = new Database();
        User userFromDB = db.getUserByUsername(user.getUsername());

        String attemptedPw = user.getPassword();
        byte[] salt = userFromDB.getSalt();

        return encryptPassword(salt, attemptedPw);
    }*/
}
