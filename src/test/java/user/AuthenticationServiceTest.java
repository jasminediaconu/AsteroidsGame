package user;

import database.Database;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationServiceTest {

    private static User attemptedUser;
    private static User userFromDB;
    private static AuthenticationService authService = new AuthenticationService();
    private static byte[] expectedPwEncrypted;
    private static final byte[] saltBytes = "salt".getBytes();

    private static final String username = "username";
    private static final String password = "password";

    @BeforeAll
    void setUp() throws InvalidKeySpecException,
            NoSuchAlgorithmException,
            UnsupportedEncodingException {

        attemptedUser = new User(username);
        attemptedUser.setPassword(password);

        userFromDB = new User(username);
        expectedPwEncrypted = authService.encryptPassword(saltBytes, password);
        userFromDB.setPassword(new String(expectedPwEncrypted, "UTF-8"));
        userFromDB.setSalt(saltBytes);
    }

    @Test
    void authenticateUserNotRetrievedFromDatabaseCorrect() {
        Assertions.assertTrue(authService.authenticate(attemptedUser, userFromDB));
    }

    @Test
    void authenticateUserNotRetrievedFromDatabaseWrongPassword() {
        User wrongpw = new User(username);
        wrongpw.setPassword("wrongPassword");

        Assertions.assertFalse(authService.authenticate(wrongpw, userFromDB));
    }

    @Test
    void encryptPasswordTest() throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] encrypted = authService.encryptPassword(saltBytes, password);
        byte[] encryptedOtherSalt = authService.encryptPassword("othersalt".getBytes(), password);
        byte[] encryptedOtherPassword = authService.encryptPassword(saltBytes, "otherpassword");

        Assertions.assertArrayEquals(encrypted, expectedPwEncrypted);
        Assertions.assertFalse(Arrays.equals(encrypted, encryptedOtherSalt));
        Assertions.assertFalse(Arrays.equals(encrypted, encryptedOtherPassword));
    }

    @Test
    void insertUser() {
        Database db = new Database();

        db.insertUser(userFromDB);
    }
}