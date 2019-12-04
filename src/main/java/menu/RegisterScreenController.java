package menu;

import database.Database;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import user.AuthenticationService;
import user.User;

public class RegisterScreenController {

    private static Database db = new Database();

    private static AuthenticationService authService = new AuthenticationService();

    private Scene mainScreen;

    private Scene menuScreen;

    @FXML
    private transient TextField usernameField;

    @FXML
    private transient PasswordField passwordField;

    @FXML
    private transient PasswordField passwordCheckField;

    @FXML
    private transient Label errorMessage;

    @FXML
    private transient Label errorMessage2;

    @FXML
    private transient Label usernameErrorLabel;

    @FXML
    private transient Label passwordErrorLabel;

    @FXML
    private transient Label passwordCheckErrorLabel;

    /**
     * Registers a new user (if the username is not taken yet).
     */
    public void registerUser(ActionEvent actionEvent) {
        String password = passwordField.getText();
        String passwordCheck = passwordCheckField.getText();

        if (validateInput() && password.equals(passwordCheck)) {
            if (db.getUserByUsername(usernameField.getText()) == null) {
                User user = new User(usernameField.getText());

                try {
                    user.setSalt(authService.generateSalt());
                    user.setPassword(authService.encryptPassword(user.getSalt(),
                            password.getBytes()));

                    db.insertUser(user);

                    errorMessage.setStyle("-fx-opacity: 0;");
                    errorMessage2.setStyle("-fx-opacity: 0;");
                    openMenuScreen(actionEvent);
                    System.out.println("user inserted into db");
                } catch (NoSuchAlgorithmException
                        | InvalidKeySpecException
                        | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            } else {
                // user already in database
                errorMessage.setStyle("-fx-opacity: 100;");
                usernameErrorLabel.setOpacity(0);
                System.out.println("user already in db");
            }
        } else {
            errorMessage2.setStyle("-fx-opacity: 100;");
            System.out.println("passwords don't match");
            // password not the same
        }

    }

    /**
     * Calls helper methods that check if username and password are of sufficient length
     * and if not it notifies the user.
     */
    public boolean validateInput() {
        return LoginScreenController.validateUsername(usernameField, usernameErrorLabel)
                && LoginScreenController.validatePassword(passwordField, passwordErrorLabel)
                && LoginScreenController.validatePassword(passwordCheckField,
                passwordCheckErrorLabel);
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
     *
     * @param scene type Scene
     */
    public void setMainScreen(Scene scene) {
        LoginScreenController.setInvalidCharListener(passwordField);
        LoginScreenController.setInvalidCharListener(passwordCheckField);
        LoginScreenController.setInvalidCharListener(usernameField);

        mainScreen = scene;
    }

    /**
     * Function triggered when pressing the 'Back' button.
     * It returns the Main Screen scene.
     *
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
     * @param actionEvent type ActionEvent
     */
    public void openMenuScreen(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(menuScreen);
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

    private void fillScene(Parent root) {
    }
}