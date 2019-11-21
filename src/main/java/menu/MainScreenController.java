package menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainScreenController {

    private Scene loginScreen;

    private Scene registerScreen;

    /**
     * Getter for Login Screen scene.
     * @return loginScreen
     */
    public Scene getLoginScreen() {
        return loginScreen;
    }

    /**
     * Function triggered when pressing the 'Login' button.
     * It returns the Login Screen scene.
     *
     * @param actionEvent type ActionEvent
     */
    public void openLoginScreen(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(loginScreen);
    }

    /**
     * Setter for Login Screen Scene.
     *
     * @param scene type Scene
     */
    public void setLoginScreen(Scene scene) {
        loginScreen = scene;
    }

    /**
     * Getter for Register Screen scene.
     * @return registerScreen
     */
    public Scene getRegisterScreen() {
        return registerScreen;
    }

    /**
     * Setter for Register Screen Scene.
     *
     * @param scene type Scene
     */
    public void setRegisterScreen(Scene scene) {
        registerScreen = scene;
    }

    /**
     * Function triggered when pressing the 'Register' button.
     * It returns the Register Screen scene.
     *
     * @param actionEvent type ActionEvent
     */
    public void openRegisterScreen(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(registerScreen);
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


