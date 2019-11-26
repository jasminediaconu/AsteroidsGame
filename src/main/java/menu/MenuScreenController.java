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

    private Scene leaderBoardScreen;
    private Scene gameScreen;

    /**
     * Getter for LeaderBoard Screen scene.
     * @return mainScreen
     */
    public Scene getLeaderBoardScreen() {
        return leaderBoardScreen;
    }

    /**
     * Setter for LeaderBoard Screen Scene.
     * @param scene type Scene
     */
    public void setLeaderBoardScreen(Scene scene) {
        leaderBoardScreen = scene;
    }

    /**
     * Function triggered when pressing the 'Back' button.
     * It returns the LeaderBoard Screen scene.
     * @param actionEvent type ActionEvent
     */
    public void openLeaderBoardScreen(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(leaderBoardScreen);
    }

    /**
     * Getter for Game Screen scene.
     * @return mainScreen
     */
    public Scene getGameScreen() {
        return gameScreen;
    }

    /**
     * Setter for Game Screen scene.
     * @param scene type Scene
     */
    public void setGameScreen(Scene scene) {
        gameScreen = scene;
    }

    /**
     * Function triggered when pressing the 'Play' button.
     * It returns the Game Screen scene.
     * @param actionEvent type ActionEvent
     */
    public void openGameScreen(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(gameScreen);
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
        stage.hide();
    }
}