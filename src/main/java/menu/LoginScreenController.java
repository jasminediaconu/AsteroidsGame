package menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import user.AuthenticationService;
import user.User;

/**
 * The type LoginScreen Controller.
 */
public class LoginScreenController {

    private Scene mainScreen;

    private Scene menuScreen;

    @FXML
    public transient PasswordField passwordField;

    @FXML
    public transient TextField usernameField;

    @FXML
    public transient Button loginButton;

    /**
     * Authenticates User.
     */
    public void login() {
        String password = passwordField.getText();
        String username = usernameField.getText();

        User attemptedUser = new User(username, password);
        AuthenticationService authService = new AuthenticationService();

        if (authService.authenticate(attemptedUser)) {
            System.out.println("Login successful");
            loginButton.setStyle("-fx-background-color: green; ");
            //openMainScreen();
            return;
        }
        loginButton.setStyle("-fx-background-color: red; ");
        System.out.println("Login failed");
        return;
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
}