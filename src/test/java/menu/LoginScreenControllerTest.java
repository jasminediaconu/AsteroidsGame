package menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javax.swing.SwingUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginScreenControllerTest {
    private transient PasswordField passwordField;
    private transient TextField usernameField;
    private transient Label usernameError;
    private transient Label passwordError;

    /**
     * Initializes JavaFX toolkit.
     * from: https://stackoverflow.com/questions/28501307/
     * javafx-toolkit-not-initialized-in-one-test-class-but-not-two-others-where-is
     *
     * @throws InterruptedException when interrupted
     */
    @BeforeAll
    public static void initToolkit()
            throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel(); // initializes JavaFX environment
            latch.countDown();
        });

        // That's a pretty reasonable delay... Right?
        if (!latch.await(5L, TimeUnit.SECONDS)) {
            throw new ExceptionInInitializerError();
        }
    }

    @BeforeEach
    void setUp() {
        usernameField = new TextField();
        usernameField.setText("longEnoughUsername");
        usernameError = new Label();
        usernameError.setOpacity(100);

        passwordField = new PasswordField();
        passwordField.setText("longEnoughPassword");
        passwordError = new Label();
        passwordError.setOpacity(100);
    }

    @Test
    void validateUsernamePositive() {
        assertTrue(LoginScreenController.validateUsername(usernameField, usernameError));
        assertNull(usernameField.getBorder());
        assertEquals(0, usernameError.getOpacity());
    }

    @Test
    void validateUsernameNegative() {
        usernameField.setText("..");
        usernameField.setOpacity(0);

        Border border = new Border(new BorderStroke(Color.RED,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

        assertFalse(LoginScreenController.validateUsername(usernameField, usernameError));
        assertEquals(border, usernameField.getBorder());
        assertEquals(100, usernameError.getOpacity());
    }

    @Test
    void validatePasswordPositive() {
        assertTrue(LoginScreenController.validatePassword(passwordField, passwordError));
        assertNull(passwordField.getBorder());
        assertEquals(0, passwordError.getOpacity());
    }

    @Test
    void validatePasswordNegative() {
        passwordField.setText("test");
        passwordError.setOpacity(0);

        Border border = new Border(new BorderStroke(Color.RED,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));

        assertFalse(LoginScreenController.validatePassword(passwordField, passwordError));
        assertEquals(border, passwordField.getBorder());
        assertEquals(100, passwordError.getOpacity());
    }

}