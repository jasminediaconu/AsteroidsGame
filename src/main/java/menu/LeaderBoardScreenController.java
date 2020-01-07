package menu;

import database.Database;
import game.Game;
import game.LeaderBoardGame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * The type LoginScreen ViewController.
 */
public class LeaderBoardScreenController implements Initializable {

    private Scene menuScreen;

    private ArrayList<Game> top5Scores;

    private transient ArrayList<LeaderBoardGame> leaderBoardGame = new ArrayList<>();

    @FXML
    private transient TableView<LeaderBoardGame> leaderBoard = new TableView<>();
    @FXML
    private transient TableColumn<LeaderBoardGame, String> usernameColumn;
    @FXML
    private transient TableColumn<LeaderBoardGame, Integer> scoreColumn;
    @FXML
    private transient TableColumn<LeaderBoardGame, Date> dateColumn;

    private transient ArrayList<Game> top5;

    private transient Database db = new Database();
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

    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    public void initialize(URL url, ResourceBundle resourceBundle) {
        top5 = db.getTop5Scores();
        for(Game game : top5) {
            if(game.getAlias() == null)
                leaderBoardGame.add(new LeaderBoardGame(game.getUsername(), game.getScore(), game.getTimestamp()));
            else
                leaderBoardGame.add(new LeaderBoardGame(game.getAlias(), game.getScore(), game.getTimestamp()));
        }

        usernameColumn.setCellValueFactory(new PropertyValueFactory<LeaderBoardGame, String>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<LeaderBoardGame, Integer>("score"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<LeaderBoardGame, Date>("date"));
        ObservableList<LeaderBoardGame> top5Scores = FXCollections.observableArrayList(leaderBoardGame);
        leaderBoard.setItems(top5Scores);
    }
}