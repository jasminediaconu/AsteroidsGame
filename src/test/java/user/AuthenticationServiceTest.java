package user;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import database.Database;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import models.authentication.AuthenticationService;
import models.authentication.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationServiceTest {

    private static User attemptedUser;
    private static User userFromDB;
    private static AuthenticationService authService = new AuthenticationService();
    private static byte[] expectedPwEncrypted;
    private static final byte[] saltBytes = "salt".getBytes();
    private static byte[] pw;

    private static final String username = "username";
    private static final String password = "password";

    private transient ByteArrayOutputStream outContent;


    @BeforeEach
    void setUp() throws InvalidKeySpecException,
            NoSuchAlgorithmException,
            UnsupportedEncodingException {

        attemptedUser = new User(username);
        pw = password.getBytes();
        attemptedUser.setPassword(pw);

        userFromDB = new User(username);
        expectedPwEncrypted = authService.encryptPassword(saltBytes, pw);
        userFromDB.setPassword(expectedPwEncrypted);
        userFromDB.setSalt(saltBytes);

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void authenticateUserRandomSalt() throws NoSuchAlgorithmException,
            InvalidKeySpecException,
            UnsupportedEncodingException {
        byte[] salt = authService.generateSalt();

        assertFalse(Arrays.equals(new byte[8], salt));

        userFromDB.setSalt(salt);

        expectedPwEncrypted = authService.encryptPassword(salt, pw);
        userFromDB.setPassword(expectedPwEncrypted);

        assertTrue(authService.authenticate(attemptedUser, userFromDB));
    }

    @Test
    void authenticateUserNotRetrievedFromDatabaseCorrect() {
        assertTrue(authService.authenticate(attemptedUser, userFromDB));
    }

    @Test
    void authenticateUserNotRetrievedFromDatabaseWrongPassword() {
        User wrongpw = new User(username);
        wrongpw.setPassword("wrongPassword".getBytes());

        assertFalse(authService.authenticate(wrongpw, userFromDB));
    }

    @Test
    void encryptPasswordTest() throws InvalidKeySpecException,
            NoSuchAlgorithmException,
            UnsupportedEncodingException {
        byte[] encrypted = authService.encryptPassword(saltBytes, pw);
        byte[] encryptedOtherSalt = authService.encryptPassword("othersalt".getBytes(), pw);
        byte[] encryptedOtherPassword = authService
                .encryptPassword(saltBytes, "otherpassword".getBytes());

        assertArrayEquals(encrypted, expectedPwEncrypted);
        assertFalse(Arrays.equals(encrypted, encryptedOtherSalt));
        assertFalse(Arrays.equals(encrypted, encryptedOtherPassword));
    }

    @Test
    void insertUser() {
        Database db = new Database();

        db.insertUser(userFromDB);
    }

    @Test
    void generateSalt() {
        // salts should be random
        byte[] salt1 = authService.generateSalt();
        byte[] salt2 = authService.generateSalt();

        assertFalse(Arrays.equals(salt1, salt2));
    }

    @Test
    void encryptUser() throws NoSuchAlgorithmException,
            UnsupportedEncodingException,
            InvalidKeySpecException {
        User encrypted = authService.encryptUser(username, password, saltBytes);

        assertEquals("username", encrypted.getUsername());
        assertEquals(saltBytes, encrypted.getSalt());
        assertNotEquals(password, encrypted.getPassword());

        byte[] encryptedPW = authService.encryptPassword(saltBytes, password.getBytes());

        assertArrayEquals(encryptedPW, encrypted.getPassword());
    }

    @Test
    void authenticateEncryptedUser() throws NoSuchAlgorithmException,
            UnsupportedEncodingException,
            InvalidKeySpecException {
        User encrypted = authService.encryptUser(username, password, saltBytes);

        User nonEncrypted = new User(username, password.getBytes());

        assertArrayEquals(encrypted.getPassword(),
                authService.encryptPassword(saltBytes, nonEncrypted.getPassword()));
        assertTrue(authService.authenticate(nonEncrypted, encrypted));
    }

    @Test
    void authenticateException() {
        User mockUser = Mockito.mock(User.class);
        Mockito.when(mockUser.getPassword()).thenThrow(NoSuchAlgorithmException.class);
        Mockito.when(mockUser.getSalt()).thenReturn("salt".getBytes());

        boolean res = authService.authenticate(userFromDB, mockUser);

        assertFalse(res);
        assertFalse(outContent.toString().isEmpty());
    }
}