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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import user.AuthenticationService;
import user.User;

/**
 * The type LoginScreen ViewController.
 */
public class LoginScreenController {

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

    /**
     * Authenticates User.
     */
    public void login(ActionEvent actionEvent) {
        String password = passwordField.getText();
        String username = usernameField.getText();

        User attemptedUser = new User(username, password.getBytes());
        AuthenticationService authService = new AuthenticationService();

        if (authService.authenticate(attemptedUser)) {
            System.out.println("Login successful");
            errorMessage.setStyle("-fx-opacity: 0;");
            successMessage.setStyle("-fx-opacity: 100;");
            openMenuScreen(actionEvent);
            return;
        } else {
            successMessage.setStyle("-fx-opacity: 0;");
            errorMessage.setStyle("-fx-opacity: 100;");
            System.out.println("Login failed");
            return;
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