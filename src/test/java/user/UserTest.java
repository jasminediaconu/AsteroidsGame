package user;

import java.io.UnsupportedEncodingException;
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
    void getSalt() {
        Assertions.assertEquals(bytes, user.getSalt());
    }

    @Test
    void setSalt() {
        byte[] newBytes = "newBytes".getBytes();
        user.setSalt(newBytes);

        Assertions.assertEquals(newBytes, user.getSalt());
    }

    @Test
    void getSaltAsString() throws UnsupportedEncodingException {
        Assertions.assertEquals(username, user.getSaltAsString());
    }

    @Test
    void getUsername() {
        Assertions.assertEquals(username, user.getUsername());
    }

    @Test
    void setUsername() {
        String newUsername = "newUsername";
        user.setUsername(newUsername);

        Assertions.assertEquals(newUsername, user.getUsername());
    }

    @Test
    void getPassword() {
        Assertions.assertEquals(password, user.getPassword());
    }

    @Test
    void getPasswordAsString() throws UnsupportedEncodingException {
        Assertions.assertEquals(new String(password, "UTF-8"), user.getPasswordAsString());
    }

    @Test
    void setPassword() {
        byte[] newPassword = "newPassword".getBytes();
        user.setPassword(newPassword);

        Assertions.assertEquals(newPassword, user.getPassword());
    }

    @Test
    void getAlias() {
        Assertions.assertEquals(alias, user.getAlias());
    }

    @Test
    void setAlias() {
        String newAlias = "newAlias";
        user.setAlias(newAlias);

        Assertions.assertEquals(newAlias, user.getAlias());
    }
}