package menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * The type LoginScreen Controller.
 */
public class MenuScreenController {

    private Scene mainScreen;

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