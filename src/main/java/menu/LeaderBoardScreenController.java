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
public class LeaderBoardScreenController {

    private Scene menuScreen;

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
     * Function triggered when pressing the 'Back' button.
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