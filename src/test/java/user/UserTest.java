package user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.UnsupportedEncodingException;

import models.authentication.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
    private static User user;
    private static final String username = "username";
    private static final String alias = "alias";
    private static final byte[] password = "password".getBytes();
    private static byte[] bytes = username.getBytes();


    /**
     * Creates a new user to use in tests.
     */
    @BeforeEach
    void setUp() {
        user = new User(username);
        user.setAlias(alias);
        user.setPassword(password);
        user.setSalt(bytes);
    }

    @Test
    void testAltConstructor() throws UnsupportedEncodingException {
        User testUser = new User("user", "pw".getBytes());

        assertNotNull(testUser);
        assertEquals("user", testUser.getUsername());
        assertEquals("pw", testUser.getPasswordAsString());
    }

    @Test
    void getSalt() {
        assertEquals(bytes, user.getSalt());
    }

    @Test
    void setSaltNull() {
        user.setSalt(null);
        assertNull(user.getSalt());
    }

    @Test
    void setSalt() {
        byte[] newBytes = "newBytes".getBytes();
        user.setSalt(newBytes);

        assertEquals(newBytes, user.getSalt());
    }

    @Test
    void getSaltAsString() throws UnsupportedEncodingException {
        assertEquals(username, user.getSaltAsString());
    }

    @Test
    void getUsername() {
        assertNotNull(user.getUsername());
        assertEquals(username, user.getUsername());
    }

    @Test
    void setUsername() {
        String newUsername = "newUsername";
        user.setUsername(newUsername);

        assertEquals(newUsername, user.getUsername());
    }

    @Test
    void getPassword() {
        assertNotNull(user.getPassword());
        assertEquals(password, user.getPassword());
    }

    @Test
    void getPasswordAsString() throws UnsupportedEncodingException {
        assertNotNull(user.getPasswordAsString());
        assertNotNull(user.getPasswordAsString().getBytes());

        assertEquals(new String(password, "UTF-8"), user.getPasswordAsString());
    }

    @Test
    void setPassword() {
        byte[] newPassword = "newPassword".getBytes();
        user.setPassword(newPassword);

        assertEquals(newPassword, user.getPassword());
    }

    @Test
    void getAlias() {
        assertNotNull(user.getAlias().getBytes());
        assertEquals(alias, user.getAlias());
    }

    @Test
    void setAlias() {
        String newAlias = "newAlias";
        user.setAlias(newAlias);

        assertEquals(newAlias, user.getAlias());
    }

    @Test
    void getMutatedUsername() {
        assertEquals(username, user.getUsername());
    }

    @Test
    void getMutatedPassword() {
        assertEquals(password, user.getPassword());
    }
}