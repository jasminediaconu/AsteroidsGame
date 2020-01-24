package controllers;

import database.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.authentication.AuthenticationService;
import models.authentication.User;

/**
 * The type LoginScreen ViewController.
 */
public class LoginScreenController {
    private static final int minPasswordLength = 5;
    private static final int minUsernameLength = 4;
    private static int numberOfAttempts = 0;
    private static final int numberOfAttemptsAllowed = 3;

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
        if (validateInput()) {

            AuthenticationService authService = new AuthenticationService(new Database());

            if (!authService.isLoginLocked()) {
                String password = passwordField.getText();
                String username = usernameField.getText();

                User attemptedUser = new User(username, password.getBytes());

                if (authService.authenticate(attemptedUser)) {
                    System.out.println("Login successful");
                    errorMessage.setStyle("-fx-opacity: 0;");
                    successMessage.setStyle("-fx-opacity: 100;");

                    openMenuScreen(actionEvent);
                } else {
                    successMessage.setStyle("-fx-opacity: 0;");
                    errorMessage.setStyle("-fx-opacity: 100;");
                    errorMessage.setText("Login failed. \n" + (2 - numberOfAttempts)
                            + " More failed attempts and login will be locked for 5 minutes");
                    System.out.println("Login failed");

                    if (numberOfAttempts < numberOfAttemptsAllowed) {
                        numberOfAttempts++;
                    } else {
                        errorMessage.setText("3rd login attempt failed. Try again in 5 minutes");
                        System.out.println("3rd login attempt failed");
                        // lock login for 5 mins
                        authService.setLoginLocked(System.currentTimeMillis());
                    }
                }
            } else {
                long lockedFor = authService.loginLockedForSeconds();
                successMessage.setStyle("-fx-opacity: 0;");
                errorMessage.setStyle("-fx-opacity: 100;");
                errorMessage.setText("3rd login attempt failed.\nTry again in "
                        + lockedFor / 1000 + " seconds.");
            }
        }
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
     *
     * @param username   TextField
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
     *
     * @param password           TextField
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
     * Adds a listener to the TextField that checks if the input is valid.
     * (contains no disallowed characters)
     *
     * @param textField TextField to add listener to
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public static void setInvalidCharListener(TextField textField) {
        textField.textProperty()
                .addListener((obs, oldValue, newValue) -> { // Currently only checks for whitespaces
                    textField.setText(newValue.replaceAll(" ", ""));
                });
    }

    /**
     * Getter for Main Screen scene.
     *
     * @return mainScreen
     */
    public Scene getMainScreen() {
        return mainScreen;
    }

    /**
     * Setter for Main Screen Scene.
     *
     * @param scene type Scene
     */
    public void setMainScreen(Scene scene) {
        mainScreen = scene;
        setInvalidCharListener(usernameField);
        setInvalidCharListener(passwordField);
    }

    /**
     * Function triggered when pressing the 'Back' button.
     * It returns the Main Screen scene.
     *
     * @param actionEvent type ActionEvent
     */
    public void openMainScreen(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(mainScreen);
    }

    /**
     * Getter for Menu Screen scene.
     *
     * @return menuScreen
     */
    public Scene getMenuScreen() {
        return menuScreen;
    }

    /**
     * Setter for Main Screen Scene.
     *
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
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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