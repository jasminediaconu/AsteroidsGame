package menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import user.AuthenticationService;
import user.User;

/**
 * The type LoginScreen ViewController.
 */
public class LoginScreenController {
    private static final int minPasswordLength = 5;
    private static final int minUsernameLength = 4;

    private Scene mainScreen;

    private Scene menuScreen;

    @FXML
    public transient Button loginButton;

    @FXML
    public transient PasswordField passwordField;

    @FXML
    private transient TextField usernameField;

    @FXML
    private transient Label errorMessage;

    @FXML
    private transient Label successMessage;

    @FXML
    private transient Label usernameErrorLabel;

    @FXML
    private transient Label passwordErrorLabel;

    /**
     * Authenticates User.
     */
    public void login(ActionEvent actionEvent) {
        String password = passwordField.getText();
        String username = usernameField.getText();

        User attemptedUser = new User(username, password.getBytes());
        AuthenticationService authService = new AuthenticationService();

        validateInput();
        if (authService.authenticate(attemptedUser)) {
            System.out.println("Login successful");
            errorMessage.setStyle("-fx-opacity: 0;");
            successMessage.setStyle("-fx-opacity: 100;");
            openMenuScreen(actionEvent);
        } else {
            successMessage.setStyle("-fx-opacity: 0;");
            errorMessage.setStyle("-fx-opacity: 100;");
            System.out.println("Login failed");
        }
    }

    /**
     * Calls helper methods that check if username and password are of sufficient length
     * and if not it notifies the user.
     * @param actionEvent Action Event
     */
    public void validateInput(KeyEvent actionEvent) {
        validateUsername(usernameField, usernameErrorLabel);
        validatePassword(passwordField, passwordErrorLabel);
    }

    /**
     * Calls helper methods that check if username and password are of sufficient length
     * and if not it notifies the user.
     */
    public boolean validateInput() {
        return validateUsername(usernameField, usernameErrorLabel)
                && validatePassword(passwordField, passwordErrorLabel);
    }

    /**
     * Checks if username is long enough.
     * If not displays error message and gives the usernameField a red border.
     * @param username TextField
     * @param errorLabel Label to display
     * @return true iff username is long enough
     */
    public static boolean validateUsername(TextField username, Label errorLabel) {
        if (username.getText().length() < minUsernameLength) {
            // alert user
            Border border = new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
            errorLabel.setOpacity(100);
            username.setBorder(border);
            return false;
        } else {
            errorLabel.setOpacity(0);
            username.setBorder(null);
            return true;
        }
    }

    /**
     * Checks if username is long enough.
     * If not displays error message and gives the usernameField a red border.
     * @param password TextField
     * @param passwordErrorLabel Label to display
     * @return true iff password is long enough
     */
    public static boolean validatePassword(PasswordField password, Label passwordErrorLabel) {
        if (password.getText().length() < minPasswordLength) {
            // alert user
            Border border = new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
            password.setBorder(border);
            passwordErrorLabel.setOpacity(100);
            return false;
        } else {
            passwordErrorLabel.setOpacity(0);
            password.setBorder(null);
            return true;
        }
    }

    /**
     * Getter for Main Screen scene.
     * @return mainScreen
     */
    public Scene getMainScreen() {
        return mainScreen;
    }

    /**
     * Setter for Main Screen Scene.
     * @param scene type Scene
     */
    public void setMainScreen(Scene scene) {
        mainScreen = scene;
    }

    /**
     * Function triggered when pressing the 'Back' button.
     * It returns the Main Screen scene.
     * @param actionEvent type ActionEvent
     */
    public void openMainScreen(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(mainScreen);
    }

    /**
     * Getter for Menu Screen scene.
     * @return menuScreen
     */
    public Scene getMenuScreen() {
        return menuScreen;
    }

    /**
     * Setter for Main Screen Scene.
     * @param scene type Scene
     */
    public void setMenuScreen(Scene scene) {
        menuScreen = scene;
    }

    /**
     * Function triggered when pressing the 'Login' button.
     * It returns the Menu Screen scene.
     */
    public void openMenuScreen(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(menuScreen);
    }

    private void fillScene(Parent root) {

        Stage stage = (Stage) menuScreen.getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    /**
     * This function handles the closing of the window, with the exit button.
     *
     * @param event MouseEvent type
     */
    @FXML
    public void exit(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }
}