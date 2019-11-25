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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    public transient TextField usernameField;

    @FXML
    public transient PasswordField passwordField;

    @FXML
    public transient PasswordField passwordCheckField;

    /**
     * Registers a new user (if the username is not taken yet).
     */
    public void registerUser() {
        String password = passwordField.getText();
        String passwordCheck = passwordCheckField.getText();

        if (password.equals(passwordCheck)) {
            if (db.getUserByUsername(usernameField.getText()) == null) {
                User user = new User(usernameField.getText());

                try {
                    user.setSalt(authService.generateSalt());
                    user.setPassword(authService.encryptPassword(user.getSalt(),
                            password.getBytes()));

                    db.insertUser(user);
                    System.out.println("user inserted into db");

                } catch (NoSuchAlgorithmException
                        | InvalidKeySpecException
                        | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            } else {
                // user already in database
                System.out.println("user already in db");
            }
        } else {
            System.out.println("passwords don't match");
            // password not the same
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
     *
     * @param scene type Scene
     */
    public void setMainScreen(Scene scene) {
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