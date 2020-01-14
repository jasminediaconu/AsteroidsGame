package controllers;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The type ViewController.
 */
public class MainController {

    private transient Scene mainScene;
    private transient Scene loginScene;
    private transient Scene registerScene;
    private transient Scene menuScene;
    private transient Scene gameScene;
    private transient Scene leaderBoardScene;

    private transient FXMLLoader mainScreenLoader;
    private transient FXMLLoader loginScreenLoader;
    private transient FXMLLoader registerScreenLoader;
    private transient FXMLLoader gameScreenLoader;
    private transient FXMLLoader leaderBoardScreenLoader;
    private transient FXMLLoader menuScreenLoader;

    private transient Stage mainStage;

    /**
     * ViewController constructor. This creates the MainStage
     * of the application with the default settings.
     *
     * @throws IOException Exception type
     */
    public MainController() throws IOException {
        mainScreenLoader = new FXMLLoader(getClass()
                .getResource("/views/fxml/mainScreen.fxml"));
        Parent main = mainScreenLoader.load();
        mainScene = new Scene(main);
        mainStage = new Stage();
        mainStage.setTitle("Asteroids");
        mainStage.setScene(mainScene);
        mainStage.setResizable(false);
        mainStage.show();
    }

    /**
     * Getter for the Main Stage.
     *
     * @return mainStage Stage type
     */
    public Stage getMainStage() {
        return mainStage;
    }

    /**
     * Setter for the Login Scene.
     * @throws IOException Exception type
     */
    public void setLoginScene() throws IOException {
        loginScreenLoader = new FXMLLoader(getClass()
                .getResource("/views/fxml/login.fxml"));
        Parent login = loginScreenLoader.load();
        loginScene = new Scene(login);
    }

    /**
     * Setter for the Register Scene.
     * @throws IOException Exception type
     */
    public void setRegisterScene() throws IOException {
        registerScreenLoader = new FXMLLoader(getClass()
                .getResource("/views/fxml/register.fxml"));
        Parent register = registerScreenLoader.load();
        registerScene = new Scene(register);
    }

    /**
     * Setter for the LeaderBoard Scene.
     * @throws IOException Exception type
     */
    public void setLeaderBoardScene() throws IOException {
        leaderBoardScreenLoader = new FXMLLoader(getClass()
                .getResource("/views/fxml/leaderBoardScreen.fxml"));
        Parent leaderBoard = leaderBoardScreenLoader.load();
        leaderBoardScene = new Scene(leaderBoard);
    }

    /**
     * Setter for the Menu Scene.
     * @throws IOException Exception type
     */
    public void setMenuScene() throws IOException {
        menuScreenLoader = new FXMLLoader(getClass()
                .getResource("/views/fxml/menuScreen.fxml"));
        Parent menu = menuScreenLoader.load();
        menuScene = new Scene(menu);
    }

    /**
     * This method makes it possible to switch from the Main Screen to
     * the Login Screen or Register Screen.
     */
    public void injectMainScreenScenes() {
        MainScreenController mainScreenController = mainScreenLoader.getController();
        mainScreenController.setLoginScreen(loginScene);
        mainScreenController.setRegisterScreen(registerScene);
    }

    /**
     * This method makes it possible to switch from the Login Screen to
     * the Main Screen or Menu Screen.
     */
    public void injectLoginScreenScenes() {
        LoginScreenController loginScreenController = loginScreenLoader.getController();
        loginScreenController.setMainScreen(mainScene);
        loginScreenController.setMenuScreen(menuScene);
    }

    /**
     * This method makes it possible to switch from the Register Screen to
     * the Main Screen or Menu Screen.
     */
    public void injectRegisterScreenScenes() {
        RegisterScreenController registerScreenController = registerScreenLoader.getController();
        registerScreenController.setMainScreen(mainScene);
        registerScreenController.setMenuScreen(menuScene);
    }

    /**
     * This method makes it possible to switch from the Menu Screen to
     * the Game Screen or LeaderBoard Screen.
     */
    public void injectMenuScreenScenes() {
        MenuScreenController menuScreenController = menuScreenLoader.getController();
        menuScreenController.setLeaderBoardScreen(leaderBoardScene);
        menuScreenController.setGameScreen(gameScene);
    }

    /**
     * This method makes it possible to switch from the LeaderBoard Screen to
     * the Menu Screen.
     */
    public void injectLeaderBoardScenes() {
        LeaderBoardScreenController leaderBoardScreenController
                = leaderBoardScreenLoader.getController();
        leaderBoardScreenController.setMenuScreen(menuScene);
    }

    /**
     * When the screen is changed this function will be called to update the screen.
     */
    public void update() {

    }
}
